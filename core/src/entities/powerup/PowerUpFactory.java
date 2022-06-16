package entities.powerup;

import entities.platform.Platform;

public interface PowerUpFactory {

    PowerUp create(Platform platform);
}
