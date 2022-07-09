package entities;

public interface Movable {

    void move(float deltaTime);

    Vector2 getVelocity();

    Vector2 getPosition();
}
