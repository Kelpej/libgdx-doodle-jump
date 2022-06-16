package main;

import com.badlogic.gdx.graphics.Texture;
import entities.platform.Platform;
import entities.powerup.PowerUp;
import entities.powerup.PowerUpFactory;
import entities.powerup.Propeller;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.function.Supplier;

public class PowerUpFactoryImpl implements PowerUpFactory {
    private final List<Supplier<PowerUp>> powerUps = new ArrayList<>();
    private final Random random = new Random();

    public PowerUpFactoryImpl() {
        powerUps.add(() -> new Propeller(0, 0));

    }

    @Override
    public PowerUp create(Platform platform) {
        int index = random.nextInt(0, powerUps.size());

        PowerUp powerUp = powerUps.get(index).get();
        powerUp.setPosition(platform.getPosition().x,
                platform.getPosition().y + Platform.PLATFORM_HEIGHT / 2 + powerUp.getBounds().height / 2);

        return powerUp;
    }
}
