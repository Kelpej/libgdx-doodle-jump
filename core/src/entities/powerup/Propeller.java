package entities.powerup;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import entities.Doodler;
import entities.GameObject;
import entities.platform.Platform;
import main.Sounds;
import main.World;

public class Propeller extends GameObject implements PowerUp {
    private static final Texture TEXTURE = new Texture(Gdx.files.internal("environment/powerup/propeller.png"));

    public Propeller(Platform platform) {
        super(TEXTURE, Platform.PLATFORM_WIDTH, TEXTURE.getHeight(), platform);
    }

    @Override
    public void apply(Doodler doodler) {
        Sounds.propeller();
        World.GRAVITY.set(0, 0);
        doodler.getVelocity().y = Doodler.Y_VELOCITY;
        new java.util.Timer().schedule(
                new java.util.TimerTask() {
                    @Override
                    public void run() {
                        World.GRAVITY.set(0, -6);
                    }
                },
                5000
        );
    }

    @Override
    public void collideDoodle(Doodler doodler) {
        doodler.collidePowerUp(this);
    }
}
