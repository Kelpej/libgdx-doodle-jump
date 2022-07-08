package entities;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;


public interface Collider {

    void collideDoodle(Doodler doodler);

    default boolean collidesDoodler(Doodler doodler) {
        return getBounds().overlaps(doodler.getBounds());
    }

    void update(SpriteBatch batch, float delta);

    Vector2 getPosition();

    Rectangle getBounds();
}
