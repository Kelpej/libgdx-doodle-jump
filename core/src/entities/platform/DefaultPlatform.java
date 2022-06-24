package entities.platform;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import entities.StaticGameObject;

public class DefaultPlatform extends StaticGameObject implements Platform {

    private static final Texture TEXTURE = new Texture(Gdx.files.internal("environment/platform/default.png"));

    public DefaultPlatform(float x, float y) {
        super(TEXTURE, x, y, Platform.PLATFORM_WIDTH, Platform.PLATFORM_HEIGHT);
    }

    @Override
    public void collideDoodle(Doodler doodler) {
        bounce(doodler);
    }
}
