package entities.powerup;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import entities.Doodler;
import entities.GameObject;
import entities.platform.Platform;
import main.GameSound;
import main.Sounds;

public class Trampoline extends GameObject implements PowerUp {

    private static final Texture TEXTURE = new Texture(Gdx.files.internal("environment/powerup/trampoline.png"));

    public Trampoline(Platform platform) {
        super(TEXTURE, Platform.WIDTH, TEXTURE.getHeight(), platform);
    }

    @Override
    public void apply(Doodler doodler) {
        Sounds.playSound(GameSound.TRAMPOLINE);
        doodler.getVelocity().set(0, 120);
    }

    @Override
    public void collideDoodle(Doodler doodler) {
        doodler.collidePowerUp(this);
    }
}
