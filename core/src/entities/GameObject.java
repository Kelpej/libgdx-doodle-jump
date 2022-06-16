package entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public interface GameObject {

    default void draw(SpriteBatch batch) {
        batch.draw(getTexture(), getPosition().x, getPosition().y, getBounds().width, getBounds().height);
    }

    boolean collidesWith(Obstacle object);

    void setPosition(float x, float y);

    Vector2 getPosition();

    Rectangle getBounds();

    Texture getTexture();
}
