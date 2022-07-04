package entities;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;


public interface Collider {

    default boolean collidesDoodler(Doodler doodler) {
        return getBounds().overlaps(doodler.getBounds());
    }

    Rectangle getBounds();

    Vector2 getPosition();

    void collideDoodle(Doodler doodler);

    void draw(SpriteBatch batch);
}
