package entities.platform;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import entities.Doodler;
import entities.GameObject;

public class DefaultPlatform extends GameObject implements Platform {

    private static final Texture TEXTURE = new Texture(Gdx.files.internal("environment/platform/default.png"));

    public DefaultPlatform(float x, float y) {
        super(TEXTURE, x, y, Platform.WIDTH, Platform.HEIGHT);
    }

    @Override
    public void collideDoodle(Doodler doodler) {
        bounce(doodler);
    }

    @Override
    public void draw(SpriteBatch batch) {
        Platform.super.draw(batch);
    }
}
