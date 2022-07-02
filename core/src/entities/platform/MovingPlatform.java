package entities.platform;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import entities.Doodler;
import entities.DynamicGameObject;

public class MovingPlatform extends DynamicGameObject implements Platform {

    private static final Texture TEXTURE = new Texture(Gdx.files.internal("environment/platform/moving.png"));

    private static final Vector2 velocity = new Vector2(1, 0);

    public MovingPlatform(float x, float y) {
        super(TEXTURE, x, y, Platform.PLATFORM_WIDTH, Platform.PLATFORM_HEIGHT, velocity);
    }

    @Override
    public void update(float deltaTime) {

    }
}
