package entities.monster;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import entities.Collider;
import entities.Doodler;
import entities.DynamicGameObject;
import entities.bullet.Bullet;
import entities.platform.Platform;
import main.GameSound;
import main.Sounds;

import static entities.Doodler.Y_VELOCITY;

public class Monster extends DynamicGameObject implements Collider {

    public static final float WIDTH = 90;
    public static final float HEIGHT = 90;

    private boolean isAlive = true;

    public Monster(Texture texture, Platform platform) {
        super(texture, WIDTH, HEIGHT, platform, new Vector2(1, 0));
    }

    public void bounce(Doodler doodler) {
        doodler.jump();
        doodler.getVelocity().y = Y_VELOCITY;
    }

    public boolean collidesBullet(Bullet bullet) {
        return getBounds().overlaps(bullet.getBounds());
    }

    @Override
    public void collideDoodle(Doodler doodler) {
        doodler.collideMonster(this);

        if (doodler.isAlive()) {
            Sounds.playSound(GameSound.MONSTER_DEAD);
            isAlive = false;
        }
    }

    public boolean isAlive() {
        return isAlive;
    }
}