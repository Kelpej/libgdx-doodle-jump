package entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import entities.monster.Monster;
import entities.platform.Platform;
import entities.powerup.PowerUp;
import main.World;

import static entities.Doodler.State.*;
import static main.GameScreen.WORLD_WIDTH;

public class Doodler extends DynamicGameObject {

    public static final float X_VELOCITY = 20;
    public static final float Y_VELOCITY = 40;

    private static final Texture TEXTURE = new Texture(Gdx.files.internal("player/right.png"));

    private boolean isAlive = true;
    private boolean orientedRight = true;


    public static Doodler createDoodler(Platform platform) {
        return new Doodler(TEXTURE, 50, 50, platform, new Vector2(0, Y_VELOCITY));
    }

    enum State {
        JUMP,
        FALL,
        HIT,
    }

    private State currentState = JUMP;

    private Doodler(Texture texture, float width, float height, Platform platform, Vector2 velocity) {
        super(texture, width, height, platform, velocity);
    }

    @Override
    public void update(float deltaTime) {
        deltaTime *= 10;

        getVelocity().add(World.gravity.x * deltaTime, World.gravity.y * deltaTime);

        getPosition().add(getVelocity().x * deltaTime, getVelocity().y * deltaTime);

        getBounds().x = getPosition().x - getBounds().width / 2;
        getBounds().y = getPosition().y - getBounds().height / 2;

        if (getVelocity().y > 0 && currentState != HIT && currentState != JUMP) {
            currentState = JUMP;
            resetTime();
        }

        if (getVelocity().y < 0 && currentState != HIT && currentState != FALL) {
            currentState = FALL;
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
            currentState = HIT;
            resetTime();

            return;
        }

        setDead();
    }

    public void collidePlatform(Platform platform) {
        platform.bounce(this);
        currentState = JUMP;
        resetTime();
    }

    public void collidePowerUp(PowerUp powerUp) {
        powerUp.apply(this);
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
}
