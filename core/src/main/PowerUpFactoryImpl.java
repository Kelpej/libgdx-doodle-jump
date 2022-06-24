package main;

import com.badlogic.gdx.graphics.Texture;
import entities.platform.Platform;
import entities.powerup.PowerUp;
import entities.powerup.PowerUpFactory;
import entities.powerup.Propeller;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.function.BiFunction;
import java.util.function.Supplier;

public class PowerUpFactoryImpl implements PowerUpFactory {

    private final List<BiFunction<Float, Float, PowerUp>> powerUps = new ArrayList<>();

    private final Random random = new Random();

    public PowerUpFactoryImpl() {
        powerUps.add(Propeller::new);
    }

    @Override
    public PowerUp create(Platform platform) {
        int index = random.nextInt(0, powerUps.size());

        return powerUps.get(index).apply(platform.getPosition().x, platform.getPosition().y);
    }
}
