package entities.platform;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import entities.DynamicGameObject;

public class MovingPlatform extends DynamicGameObject implements Platform {
    private static final Texture TEXTURE = new Texture(Gdx.files.internal("environment/platform/moving.png"));

    public MovingPlatform(float x, float y) {
        super(TEXTURE, x, y, Platform.PLATFORM_WIDTH, Platform.PLATFORM_HEIGHT);
    }

    @Override
    public void move(float deltaTime) {

    }

    @Override
    public void update(float deltaTime) {

    }
}
