package entities.powerup;

import com.badlogic.gdx.graphics.Texture;
import entities.Obstacle;
import entities.Doodler;
import entities.StaticGameObject;

public abstract class PowerUp extends StaticGameObject implements Obstacle {

    protected PowerUp(Texture texture, float x, float y) {
        super(texture, x, y);
    }

    @Override
    public void collideDoodle(Doodler doodler) {
        doodler.collidePowerUp(this);
    }

    @Override
    public void update(float deltaTime) {

    }

    public abstract void apply(Doodler doodler);
}
