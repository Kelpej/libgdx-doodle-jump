package entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;

public abstract class DynamicGameObject extends StaticGameObject implements Movable {
    private final Vector2 velocity = new Vector2();
    private float stateTime = 0;

    protected DynamicGameObject(Texture texture, float x, float y) {
        super(texture, x, y);
    }

    protected DynamicGameObject(Texture texture, float x, float y, float width, float height) {
        super(texture, x, y, width, height);
    }

    @Override
    public void move(float deltaTime) {
        getPosition().add(velocity.x, velocity.y);
    }

    @Override
    public Vector2 getVelocity() {
        return velocity;
    }

    protected void setVelocity(float x, float y) {
        velocity.set(x, y);
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
