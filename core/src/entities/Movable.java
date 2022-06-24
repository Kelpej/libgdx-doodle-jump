package entities;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

public interface Movable {

    void move(float deltaTime);

    Vector2 getVelocity();

    void draw(SpriteBatch batch);
}
