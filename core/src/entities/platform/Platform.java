package entities.platform;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import entities.Obstacle;
import entities.Doodler;

import static entities.Doodler.Y_VELOCITY;

public interface Platform extends Obstacle {
    float PLATFORM_WIDTH = 60;
    float PLATFORM_HEIGHT = 20;

    default void collideDoodle(Doodler doodler) {
        doodler.collidePlatform(this);
    }

    default void bounce(Doodler doodler) {
        doodler.getVelocity().y = Y_VELOCITY;
    }

    Vector2 getPosition();

    default void draw(SpriteBatch batch) {
        batch.draw(getTexture(), getPosition().x, getPosition().y, PLATFORM_WIDTH, PLATFORM_HEIGHT);
    }
}
