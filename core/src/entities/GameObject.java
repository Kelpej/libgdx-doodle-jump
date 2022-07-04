package entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import entities.platform.Platform;

public abstract class GameObject {
    private final Texture texture;
    private final Vector2 position;
    private final Rectangle bounds;

    protected GameObject(Texture texture, float x, float y, float width, float height) {
        this.texture = texture;
        this.position = new Vector2(x, y);
        this.bounds = new Rectangle(x - width / 2, y - height / 2, width, height);
    }

    protected GameObject(Texture texture, float x, float y) {
        this.texture = texture;
        this.position = new Vector2(x, y);

        var width = texture.getWidth();
        var height = texture.getHeight();

        this.bounds = new Rectangle(x - (float) width / 2, y - (float) height / 2, width, height);
    }

    protected GameObject(Texture texture, float width, float height, Platform platform) {
        this.texture = texture;

        float x = platform.getPosition().x + (Platform.PLATFORM_WIDTH - width) / 2;
        float y = platform.getPosition().y + Platform.PLATFORM_HEIGHT;

        this.position = new Vector2(x, y);

        this.bounds = new Rectangle(x - width / 2, y - height / 2, width, height);
    }

    public void update(SpriteBatch batch, float delta) {
        draw(batch);
    }

    protected void draw(SpriteBatch batch) {
        batch.draw(getTexture(), getPosition().x, getPosition().y, getBounds().width, getBounds().height);
    }

    public Vector2 getPosition() {
        return position;
    }

    public Texture getTexture() {
        return texture;
    }

    public Rectangle getBounds() {
        return bounds;
    }
}
