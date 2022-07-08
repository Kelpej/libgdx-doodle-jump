package entities.powerup;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import entities.Doodler;
import entities.DynamicGameObject;
import entities.GameObject;
import entities.platform.Platform;
import main.Sounds;
import main.World;


public class Propeller extends DynamicGameObject implements PowerUp {
    private static final Texture TEXTURE = new Texture(Gdx.files.internal("environment/powerup/propeller_default.png"));


    private static final Texture ANIM_TEXTURE = new Texture(Gdx.files.internal("environment/powerup/propeller_animation.png"));
    private TextureRegion[][] sheetTiles = TextureRegion.split(ANIM_TEXTURE, 32, 32);
    private TextureRegion[] animationFrames;
    private Animation animation;
    private float elapsedTime;


    private Doodler doodler;
    private State currentState = State.UNUSED;

    enum State {
        UNUSED,
        APPLIED,
        USED,
    }

    public Propeller(Platform platform) {
//        super(TEXTURE, Platform.PLATFORM_WIDTH, TEXTURE.getHeight(), platform, new Vector2(0, 0));
        super(TEXTURE, TEXTURE.getWidth(), TEXTURE.getHeight(), platform, new Vector2(0, 0));


        animationFrames = new TextureRegion[3];
        animationFrames[0] = sheetTiles[0][0];
        animationFrames[1] = sheetTiles[0][1];
        animationFrames[2] = sheetTiles[0][2];

        animation = new Animation(1f/30f, animationFrames);
    }

    @Override
    public void apply(Doodler doodler) {
        this.doodler = doodler;
        currentState = State.APPLIED;


        Sounds.propeller();
        World.GRAVITY.set(0, 0);
        doodler.getVelocity().y = Doodler.Y_VELOCITY;
        new java.util.Timer().schedule(
                new java.util.TimerTask() {
                    @Override
                    public void run() {
                        World.GRAVITY.set(0, -6);
                        currentState = State.USED;
                    }
                },
                5000
        );
    }

    @Override
    public void move(float deltaTime) {
        if (currentState == State.APPLIED) {
            getPosition().set(doodler.getPosition().x + 10, doodler.getPosition().y + getBounds().height - 5);
        } else if (currentState == State.USED) {
            getVelocity().set(0, -5);
            getPosition().add(getVelocity().x, getVelocity().y);
        }
    }

    @Override
    public void draw(SpriteBatch batch) {
        if (currentState == State.APPLIED) {
            elapsedTime += Gdx.graphics.getDeltaTime();
            batch.draw((TextureRegion) animation.getKeyFrame(elapsedTime, true), getPosition().x, getPosition().y, getBounds().width, getBounds().height);
        } else {
            batch.draw(TEXTURE, getPosition().x, getPosition().y, getBounds().width, getBounds().height);
        }
    }

    @Override
    public void collideDoodle(Doodler doodler) {
        doodler.collidePowerUp(this);
    }
}
