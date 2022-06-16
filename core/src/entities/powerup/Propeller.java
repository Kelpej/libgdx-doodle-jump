package entities.powerup;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import entities.Doodler;

public class Propeller extends PowerUp {
    private static final Texture TEXTURE = new Texture(Gdx.files.internal("environment/powerup/propeller.png"));
    public Propeller(float x, float y) {
        super(TEXTURE, x, y);
    }

    @Override
    public void apply(Doodler doodler) {

    }
}
