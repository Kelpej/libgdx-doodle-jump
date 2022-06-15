package entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;

public abstract class DynamicGameObject extends GameObject implements Movable {
    private final Vector2 velocity;

    public DynamicGameObject(Texture texture, float x, float y, float width, float height) {
        super(texture, x, y, width, height);
        velocity = new Vector2();
    }

    @Override
    public void move() {
        getPosition().add(velocity.x, velocity.y);
    }

    public void changeVelocity(float x, float y) {
        velocity.add(x, y);
    }

    public Vector2 getVelocity() {
        return velocity;
    }
}
