package entities;

import com.badlogic.gdx.math.Vector2;

public interface Movable {

    void move(float deltaTime);

    Vector2 getVelocity();

    Vector2 getPosition();
}
