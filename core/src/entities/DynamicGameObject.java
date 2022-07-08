package entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import entities.platform.Platform;
import main.GameScreen;

public abstract class DynamicGameObject extends GameObject implements Movable {

    private final Vector2 velocity;
    private float stateTime = 0;

    protected DynamicGameObject(Texture texture, float x, float y, Vector2 velocity) {
        super(texture, x, y);
        this.velocity = velocity;
    }

    protected DynamicGameObject(Texture texture, float x, float y, float width, float height, Vector2 velocity) {
        super(texture, x, y, width, height);
        this.velocity = velocity;
    }

    protected DynamicGameObject(Texture texture, float width, float height, Platform platform, Vector2 velocity) {
        super(texture, width, height, platform);
        this.velocity = velocity;
    }

    @Override
    public void move(float deltaTime) {
        getPosition().add(getVelocity().x, getVelocity().y);

        getBounds().x = getPosition().x - getBounds().width / 2;
        getBounds().y = getPosition().y - getBounds().height / 2;

        if (getPosition().x < getBounds().width / 2) {

            getPosition().x = getBounds().width / 2;
            velocity.x = -getVelocity().x;
        }

        if (getPosition().x > GameScreen.WORLD_WIDTH - getBounds().width / 2) {

            getPosition().x = GameScreen.WORLD_WIDTH - getBounds().width / 2;
            velocity.x = -getVelocity().x;
        }

        stateTime += deltaTime;
    }

    @Override
    public void update(SpriteBatch batch, float deltaTime) {
        move(deltaTime);
        draw(batch);
    }

    @Override
    public Vector2 getVelocity() {
        return velocity;
    }

    public float getStateTime() {
        return stateTime;
    }

    protected void addTime(float deltaTime) {
        stateTime += deltaTime;
    }

    protected void resetTime() {
        stateTime = 0;
    }
}
