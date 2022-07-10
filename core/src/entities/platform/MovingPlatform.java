package entities.platform;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import entities.DynamicGameObject;

import static com.badlogic.gdx.math.MathUtils.random;

public class MovingPlatform extends DynamicGameObject implements Platform {
    private static final Texture TEXTURE = new Texture(Gdx.files.internal("environment/platform/moving.png"));

    public MovingPlatform(float x, float y) {
        super(TEXTURE, x, y, Platform.WIDTH, Platform.HEIGHT,
                random.nextBoolean() ? defaultVelocity : negativeVelocity);
    }
}
