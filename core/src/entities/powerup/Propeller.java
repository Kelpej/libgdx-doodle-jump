package entities.powerup;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import entities.DelayedTaskCollider;
import entities.Doodler;
import entities.DynamicGameObject;
import entities.platform.Platform;
import main.GameSound;
import main.Sounds;
import main.World;

import java.util.Timer;
import java.util.TimerTask;


public class Propeller extends DynamicGameObject implements PowerUp, DelayedTaskCollider {
    private static final Texture TEXTURE = new Texture(Gdx.files.internal("environment/powerup/propeller_default.png"));

    private static final Texture ANIM_TEXTURE = new Texture(Gdx.files.internal("environment/powerup/propeller_animation.png"));
    private final Animation<TextureRegion> animation;

    private Doodler doodler;
    private State currentState = State.UNUSED;
    private Timer timer = new Timer();

    public Propeller(Platform platform) {
        super(TEXTURE, TEXTURE.getWidth(), TEXTURE.getHeight(), platform, new Vector2(0, 0));

        TextureRegion[] animationFrames = new TextureRegion[3];
        TextureRegion[][] sheetTiles = TextureRegion.split(ANIM_TEXTURE, 32, 32);
        animationFrames[0] = sheetTiles[0][0];
        animationFrames[1] = sheetTiles[0][1];
        animationFrames[2] = sheetTiles[0][2];

        animation = new Animation<>(1f / 30f, animationFrames);
    }

    @Override
    public void createTimer() {
        this.timer = new Timer();
    }

    @Override
    public Timer getTimer() {
        return timer;
    }

    @Override
    public TimerTask getTimerTask() {
        return new TimerTask() {
            @Override
            public void run() {
                World.GRAVITY.set(0, -6);
                currentState = State.USED;
            }
        };
    }

    @Override
    public long getDelay() {
        return 7000;
    }

    @Override
    public State getCurrentState() {
        return currentState;
    }

    @Override
    public void apply(Doodler doodler) {
        if (currentState == State.UNUSED) {
            this.doodler = doodler;
            currentState = State.COLLIDED;

            Sounds.playSound(GameSound.PROPELLER);
            World.GRAVITY.set(0, 0);
            doodler.getVelocity().y = Doodler.Y_VELOCITY;

            scheduleTask();
        }
    }

    @Override
    public void move(float deltaTime) {
        if (currentState == State.COLLIDED) {
            getPosition().set(doodler.getPosition().x + 10, doodler.getPosition().y + getBounds().height - 5);
        } else if (currentState == State.USED) {
            getVelocity().set(fallVelocity);
            getPosition().add(getVelocity().x, getVelocity().y);
        }
    }


    @Override
    public void update(SpriteBatch batch, float deltaTime) {
        if (currentState == State.COLLIDED)
            addTime(deltaTime);

        move(deltaTime);
        draw(batch);
    }

    @Override
    public void draw(SpriteBatch batch) {
        if (currentState == State.COLLIDED) {
            batch.draw(animation.getKeyFrame(getStateTime(), true),
                    getPosition().x, getPosition().y, getBounds().width, getBounds().height);
            return;
        }

        batch.draw(TEXTURE, getPosition().x, getPosition().y, getBounds().width, getBounds().height);
    }

    @Override
    public void collideDoodle(Doodler doodler) {
        doodler.collidePowerUp(this);
    }
}
