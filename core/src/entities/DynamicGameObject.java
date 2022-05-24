package entities;

import com.badlogic.gdx.math.Vector2;

public abstract class DynamicGameObject extends GameObject {
    public final Vector2 velocity;
    public final Vector2 acceleration;

    public DynamicGameObject (float x, float y, float width, float height) {
        super(x, y, width, height);
        velocity = new Vector2();
        acceleration = new Vector2();
    }

//    acceleration?
    public void move() {
        position.set(position.x + velocity.x, position.y + velocity.y);
    }
}
