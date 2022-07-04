package entities.powerup;

import entities.Collider;
import entities.Doodler;

public interface PowerUp extends Collider {

    void apply(Doodler doodler);
}
