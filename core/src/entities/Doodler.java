package entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import entities.monster.Monster;
import entities.platform.Platform;
import entities.powerup.PowerUp;
import main.GameSound;
import main.Sounds;
import main.World;
import main.ui.screen.DoodleJumpScreen;

import static entities.Doodler.State.*;

public class Doodler extends DynamicGameObject {

    public static final float X_VELOCITY = 20;
    public static final float Y_VELOCITY = 45;
    public static final float DOODLER_SIZE = 50;

    private static final Texture FALL_TEXTURE = new Texture(Gdx.files.internal("player/right_jump.png"));
    private static final Sprite JUMP_SPRITE = new Sprite(new Texture(Gdx.files.internal("player/right.png")));
    private static final Sprite SHOOTING_SPRITE = new Sprite(new Texture(Gdx.files.internal("player/shooting.png")));
    private State currentState = FALL;
    private Sprite currentSprite = getSprite();
    private boolean isAlive = true;
    private boolean orientedRight = true;
    private Doodler(Platform platform, Vector2 velocity) {
        super(FALL_TEXTURE, DOODLER_SIZE, DOODLER_SIZE, platform, velocity);
    }

    public static Doodler createDoodler(Platform platform) {
        return new Doodler(platform, new Vector2(0, Y_VELOCITY));
    }

    @Override
    public void draw(SpriteBatch batch) {
        batch.draw(currentSprite, getPosition().x, getPosition().y, getBounds().width, getBounds().height);
    }

    @Override
    public void move(float deltaTime) {
        deltaTime *= 10;

        getVelocity().add(World.GRAVITY.x * deltaTime, World.GRAVITY.y * deltaTime);

        getPosition().add(getVelocity().x * deltaTime, getVelocity().y * deltaTime);
        getBounds().setPosition(getPosition().x, getPosition().y);

        if (getVelocity().y > 0 && notPoweredUp()) {
            jump();
            resetTime();
        }

        if (getVelocity().y < 0) {
            fall();
            resetTime();
        }

        if (getPosition().x + DOODLER_SIZE < 0) {
            getPosition().x = DoodleJumpScreen.WIDTH;
        }

        if (getPosition().x > DoodleJumpScreen.WIDTH) {
            getPosition().x = 0;
        }
    }

    public void collideMonster(Monster monster) {
        if (this.isFalling() && this.getPosition().y >= monster.getPosition().y + monster.getBounds().height / 3) {
            monster.bounce(this);
            return;
        }

        setDead();
    }

    public void collidePlatform(Platform platform) {
        platform.bounce(this);
        resetTime();
    }

    public void collidePowerUp(PowerUp powerUp) {
        powerUp();
        powerUp.apply(this);
    }

    public boolean isAlive() {
        return isAlive;
    }

    public void setDead() {
        this.isAlive = false;
        Sounds.playSound(GameSound.DOODLER_DEAD);
    }

    public void setXVelocity(float x) {
        getVelocity().x = x;
    }

    public void jump() {
        currentState = JUMP;
        if (currentSprite != SHOOTING_SPRITE) {
            currentSprite = JUMP_SPRITE;
        }
    }

    public void fall() {
        currentState = State.FALL;
        if (currentSprite != SHOOTING_SPRITE) {
            currentSprite = getSprite();
        }
    }

    public boolean isFalling() {
        return currentState == State.FALL;
    }

    public boolean notPoweredUp() {
        return currentState != POWERED_UP;
    }

    public void powerUp() {
        currentState = POWERED_UP;
    }

    public void shoot() {
        Sounds.playSound(GameSound.SHOOT);

        currentSprite = SHOOTING_SPRITE;
        new java.util.Timer().schedule(
                new java.util.TimerTask() {
                    @Override
                    public void run() {
                        currentSprite = JUMP_SPRITE;
                    }
                },
                100
        );
    }

    public boolean isOrientedRight() {
        return orientedRight;
    }

    public void switchOrientation() {
        orientedRight = !orientedRight;
        getSprite().flip(true, false);
        JUMP_SPRITE.flip(true, false);
    }

    enum State {
        JUMP,
        FALL,
        POWERED_UP,
    }
}
