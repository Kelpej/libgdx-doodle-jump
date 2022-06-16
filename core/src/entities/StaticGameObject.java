package entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public abstract class StaticGameObject implements GameObject {

    private final Texture texture;
    private final Vector2 position;
    private final Rectangle bounds;

    protected StaticGameObject(Texture texture, float x, float y) {
        this.texture = texture;
        this.position = new Vector2(x, y);

        var width = texture.getWidth();
        var height = texture.getHeight();

        this.bounds = new Rectangle(x - (float) width / 2, y - (float) height / 2,  width, height);
    }

    protected StaticGameObject(Texture texture, float x, float y, float width, float height) {
        this.texture = texture;
        this.position = new Vector2(x, y);
        this.bounds = new Rectangle(x - width / 2, y - height / 2,  width, height);
    }

    @Override
    public boolean collidesWith(Obstacle object) {
        return this.bounds.overlaps(object.getBounds());
    }

    public abstract void update(float deltaTime);

    @Override
    public void setPosition(float x, float y) {
        position.set(x, y);
    }

    @Override
    public Vector2 getPosition() {
        return position;
    }

    @Override
    public Rectangle getBounds() {
        return bounds;
    }

    @Override
    public Texture getTexture() {
        return texture;
    }
}
