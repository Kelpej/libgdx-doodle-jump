package entities.platform;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import entities.DynamicGameObject;

import java.util.Random;

public class MovingPlatform extends DynamicGameObject implements Platform {
    private static final Texture TEXTURE = new Texture(Gdx.files.internal("environment/platform/moving.png"));

    private static final Vector2 velocity = new Vector2(1, 0);
    private static final Vector2 negativeVelocity = new Vector2(-1, 0);

    private static final Random random = new Random();

    public MovingPlatform(float x, float y) {
        super(TEXTURE, x, y, Platform.WIDTH, Platform.HEIGHT,
                random.nextBoolean() ? velocity : negativeVelocity);
    }

    @Override
    public void draw(SpriteBatch batch) {
        super.draw(batch);
    }
}
