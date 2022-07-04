package entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import entities.monster.Monster;
import entities.platform.Platform;
import entities.powerup.PowerUp;
import main.World;

import static entities.Doodler.State.*;
import static main.GameScreen.WORLD_WIDTH;

public class Doodler extends DynamicGameObject {

    public static final float X_VELOCITY = 20;
    public static final float Y_VELOCITY = 45;

    private static final Texture JUMP_TEXTURE = new Texture(Gdx.files.internal("player/right.png"));
    private static final Texture FALL_TEXTURE = new Texture(Gdx.files.internal("player/right_jump.png"));

    enum State {
        JUMP,
        FALL,
        POWERED_UP,
    }

    private State currentState = FALL;
    private Texture currentTexture = FALL_TEXTURE;

    private boolean isAlive = true;

    private Doodler(Texture texture, float width, float height, Platform platform, Vector2 velocity) {
        super(texture, width, height, platform, velocity);
    }

    public static Doodler createDoodler(Platform platform) {
        return new Doodler(JUMP_TEXTURE, 50, 50, platform, new Vector2(0, Y_VELOCITY));
    }

    @Override
    public void draw(SpriteBatch batch) {
        batch.draw(currentTexture, getPosition().x, getPosition().y, getBounds().width, getBounds().height);
    }

    @Override
    public void update(float deltaTime) {
        deltaTime *= 10;

        getVelocity().add(World.GRAVITY.x * deltaTime, World.GRAVITY.y * deltaTime);

        getPosition().add(getVelocity().x * deltaTime, getVelocity().y * deltaTime);

        getBounds().x = getPosition().x - getBounds().width / 2;
        getBounds().y = getPosition().y - getBounds().height / 2;

        if (getVelocity().y > 0) {
            jump();
            resetTime();
        }

        if (getVelocity().y < 0) {
            fall();
            resetTime();
        }

        if (getPosition().x < 0) {
            getPosition().x = WORLD_WIDTH;
        }

        if (getPosition().x > WORLD_WIDTH) {
            getPosition().x = 0;
        }

        addTime(deltaTime);
    }

    public void collideMonster(Monster monster) {
        //if doodler hits monster from above
        if (monster.getPosition().y > this.getPosition().y) {
            getVelocity().set(0, 0);
            jump();
            resetTime();
            return;
        }

        setDead();
    }

    public void collidePlatform(Platform platform) {
        platform.bounce(this);
        resetTime();
    }

    public void collidePowerUp(PowerUp powerUp) {
        powerUp.apply(this);
        powerUp();
    }

    public boolean isAlive() {
        return isAlive;
    }

    public void setDead() {
        this.isAlive = false;
    }

    public void setXVelocity(float x) {
        getVelocity().x = x;
    }

    public void jump() {
        currentState = JUMP;
        currentTexture = JUMP_TEXTURE;
    }

    public boolean isJumping() {
        return currentState == JUMP;
    }

    public void fall() {
        currentState = State.FALL;
        currentTexture = FALL_TEXTURE;
    }

    public boolean isFalling() {
        return currentState == State.FALL;
    }

    public boolean isPoweredUp() {
        return currentState == POWERED_UP;
    }

    public void powerUp() {
        currentState = POWERED_UP;
    }
}
