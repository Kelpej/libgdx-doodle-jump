package entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import entities.platform.Platform;

public abstract class StaticGameObject extends GameObject implements Collider {

    protected StaticGameObject(Texture texture, float width, float height, Platform platform) {
        super(texture, width, height, platform);
    }

    protected StaticGameObject(Texture texture, float x, float y, float width, float height) {
        super(texture, x, y, width, height);
    }

    protected StaticGameObject(Texture texture, float x, float y) {
        super(texture, x, y);
    }

    public abstract void collideDoodle(Doodler doodler);

    @Override
    public void draw(SpriteBatch batch) {
        super.draw(batch);
    }
}
