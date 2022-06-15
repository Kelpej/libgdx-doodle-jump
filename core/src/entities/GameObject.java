package entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public abstract class GameObject {
    private final Texture texture;

    private final Vector2 position;
    private final Rectangle bounds;

    public GameObject (Texture texture, float x, float y, float width, float height) {
        this.texture = texture;
        this.position = new Vector2(x, y);
        this.bounds = new Rectangle(x - width / 2, y - height / 2, width, height);
    }

    public void draw(SpriteBatch batch) {
        batch.draw(getTexture(), getPosition().x, getPosition().y, getBounds().width, getBounds().height);
    }

    public abstract void update(float deltaTime);

    public abstract void collide(Doodler doodler);

    public Vector2 getPosition() {
        return position;
    }

    public Rectangle getBounds() {
        return bounds;
    }

    public Texture getTexture() {
        return texture;
    }
}
