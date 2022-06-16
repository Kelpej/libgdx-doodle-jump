package main;

import entities.platform.DefaultPlatform;
import entities.platform.Platform;
import entities.platform.PlatformFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.function.Supplier;

public class PlatformFactoryImpl implements PlatformFactory {
    private final List<Supplier<Platform>> platforms = new ArrayList<>();
    private final Random random = new Random();
    public PlatformFactoryImpl() {
        platforms.add(() -> new DefaultPlatform(0, 0));
    }

    @Override
    public Platform create(float x, float y) {
        int index = random.nextInt(0, platforms.size());

        Platform platform = platforms.get(index).get();
        platform.setPosition(x, y);

        return platform;
    }
}
