package entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import entities.platform.Platform;

public abstract class GameObject {
    private final Sprite sprite;
    private final Vector2 position;
    private final Rectangle bounds;
    private float stateTime = 0;


    protected GameObject(Texture texture, float x, float y, float width, float height) {
        this.sprite = new Sprite(texture);
        this.position = new Vector2(x, y);
        this.bounds = new Rectangle(x, y, width, height);
    }

    protected GameObject(Texture texture, float x, float y) {
        this.sprite = new Sprite(texture);
        this.position = new Vector2(x, y);

        var width = texture.getWidth();
        var height = texture.getHeight();

        this.bounds = new Rectangle(x, y, width, height);
    }

    protected GameObject(Texture texture, float width, float height, Platform platform) {
        this.sprite = new Sprite(texture);

        float x = platform.getPosition().x;
        float y = platform.getPosition().y;

        this.position = new Vector2(x, y);

        this.bounds = new Rectangle(x, y, width, height);
    }

    public void update(SpriteBatch batch, float delta) {
        draw(batch);
    }

    public void draw(SpriteBatch batch) {
        batch.draw(sprite, position.x, position.y, bounds.width, bounds.height);
    }

    public Vector2 getPosition() {
        return position;
    }

    public Sprite getSprite() {
        return sprite;
    }

    public Rectangle getBounds() {
        return bounds;
    }

    public float getStateTime() {
        return stateTime;
    }

    protected void addTime(float deltaTime) {
        stateTime += deltaTime;
    }

    protected void resetTime() {
        stateTime = 0;
    }
}
