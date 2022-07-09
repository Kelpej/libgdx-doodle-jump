package main;

import entities.platform.Platform;
import entities.powerup.PowerUp;
import entities.powerup.PowerUpFactory;
import entities.powerup.Propeller;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.function.Function;

public class PowerUpFactoryImpl implements PowerUpFactory {

    private final List<Function<Platform, PowerUp>> powerUps = new ArrayList<>();

    private final Random random = new Random();

    public PowerUpFactoryImpl() {
        powerUps.add(Trampoline::new);
        powerUps.add(Propeller::new);
    }

    @Override
    public PowerUp create(Platform platform) {
        int index = random.nextInt(0, powerUps.size());
        return powerUps.get(index).apply(platform);
    }
}
