package entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import entities.platform.Platform;

import static main.ui.screen.DoodleJumpScreen.WIDTH;

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

        getBounds().setPosition(getPosition().x, getPosition().y);

        if (getPosition().x < 0) {

            getPosition().x = 0;
            velocity.x = -getVelocity().x;
        }

        if (getPosition().x > WIDTH - getBounds().width) {

            getPosition().x = WIDTH - getBounds().width;
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
