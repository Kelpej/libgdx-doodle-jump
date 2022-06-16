package entities;

import com.badlogic.gdx.math.Vector2;

public interface Movable extends GameObject {

    void move(float deltaTime);

    Vector2 getVelocity();
}
