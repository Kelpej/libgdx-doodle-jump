package main;

import entities.platform.*;

import java.util.function.BiFunction;

import static com.badlogic.gdx.math.MathUtils.random;
import static main.PlatformFactoryImpl.Type.*;

public class PlatformFactoryImpl implements PlatformFactory {

    enum Type {
        DEFAULT(DefaultPlatform::new),
        MOVING(MovingPlatform::new),
        BREAKING(BreakingPlatform::new);

        private final BiFunction<Float, Float, Platform> spawner;

        Type(BiFunction<Float, Float, Platform> createFunction) {
            spawner = createFunction;
        }
    }

    @Override
    public Platform create(float x, float y) {
        BiFunction<Float, Float, Platform> spawner = DEFAULT.spawner;

        if (random.nextFloat() <= 0.25f) {
            spawner = MOVING.spawner;
        } else if (random.nextFloat() <= 0.125f) {
            spawner = BREAKING.spawner;
        }

        return spawner.apply(x, y);
    }
}
