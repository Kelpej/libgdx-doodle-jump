package main;

import entities.platform.DefaultPlatform;
import entities.platform.MovingPlatform;
import entities.platform.Platform;
import entities.platform.PlatformFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.function.BiFunction;

public class PlatformFactoryImpl implements PlatformFactory {

    private final List<BiFunction<Float, Float, Platform>> platforms = new ArrayList<>();

    private final Random random = new Random();

    public PlatformFactoryImpl() {
        platforms.add(DefaultPlatform::new);
        platforms.add(MovingPlatform::new);
    }

    @Override
    public Platform create(float x, float y) {
        int index = random.nextInt(0, platforms.size());

        return platforms.get(index).apply(x ,y);
    }
}
