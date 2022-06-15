package entities;

import com.badlogic.gdx.graphics.Texture;
import main.World;
import static entities.Doodler.State.*;

public class Doodler extends DynamicGameObject {

    enum State {
        JUMP,
        FALL,
        HIT,

    }

    private State currentState = JUMP;

    public static final float JUMP_VELOCITY = 11;
    public static final float MOVE_VELOCITY = 20;

    public static final float WIDTH = 0.8f;
    public static final float HEIGHT = 0.8f;

    private float stateTime;

    public Doodler(float x, float y) {
        super(new Texture("player/doodle.webpack"), x, y, WIDTH, HEIGHT);
        currentState = FALL;
        stateTime = 0;
    }

    @Override
    public void update (float deltaTime) {

        getVelocity().add(World.gravity.x * deltaTime, World.gravity.y * deltaTime);

        getPosition().add(getVelocity().x * deltaTime, getVelocity().y * deltaTime);

        getBounds().x = getPosition().x - getBounds().width / 2;
        getBounds().y = getPosition().y - getBounds().height / 2;

        if (getVelocity().y > 0 && currentState != HIT) {

            if (currentState != JUMP) {
                currentState = JUMP;
                stateTime = 0;
            }
        }

        if (getVelocity().y < 0 && currentState != HIT) {

            if (currentState != FALL) {
                currentState = FALL;
                stateTime = 0;
            }
        }

        if (getPosition().x < 0) {
            getPosition().x = World.WORLD_WIDTH;
        }

        if (getPosition().x > World.WORLD_WIDTH) {
            getPosition().x = 0;
        }

        stateTime += deltaTime;
    }

    public void collideMonster() {
        getVelocity().set(0, 0);
        currentState = HIT;
        stateTime = 0;
    }

    public void collidePlatform() {
        getVelocity().y = JUMP_VELOCITY;
        currentState = JUMP;
        stateTime = 0;
    }

    public void collidePowerUp() {
        getVelocity().y = JUMP_VELOCITY * 1.5f;
        currentState = JUMP;
        stateTime = 0;
    }
}
