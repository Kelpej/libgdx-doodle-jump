package entities.powerup;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import entities.Doodler;
import entities.GameObject;

public class Propeller extends GameObject implements PowerUp {
    private static final Texture TEXTURE = new Texture(Gdx.files.internal("environment/powerup/propeller.png"));

    public Propeller(float x, float y) {
        super(TEXTURE, x, y);
    }

    @Override
    public void apply(Doodler doodler) {

    }

    @Override
    public void collideDoodle(Doodler doodler) {
        doodler.collidePowerUp(this);
    }
}
