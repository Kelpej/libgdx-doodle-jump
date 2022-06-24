package entities.powerup;

import com.badlogic.gdx.graphics.Texture;
import entities.Collider;
import entities.Doodler;
import entities.StaticGameObject;

public abstract class PowerUp extends StaticGameObject implements Collider {

    protected PowerUp(Texture texture, float x, float y) {
        super(texture, x, y);
    }

    @Override
    public void collideDoodle(Doodler doodler) {
        doodler.collidePowerUp(this);
    }

    public abstract void apply(Doodler doodler);
}
