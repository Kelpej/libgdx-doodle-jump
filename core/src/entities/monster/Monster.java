package entities.monster;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import entities.Collider;
import entities.Doodler;
import entities.DynamicGameObject;
import entities.platform.Platform;

public class Monster extends DynamicGameObject implements Collider {

    public static final float WIDTH = 90;
    public static final float HEIGHT = 90;

    private boolean isAlive = true;

    public Monster(Texture texture, Platform platform) {
        super(texture, WIDTH, HEIGHT, platform, new Vector2(5, 0));
    }

    public boolean collidesBullet(Bullet bullet) {
        return getBounds().overlaps(bullet.getBounds());
    }

    @Override
    public void collideDoodle(Doodler doodler) {
        doodler.collideMonster(this);

        if (doodler.isAlive()) {
            Sounds.monsterDeath();
            isAlive = false;
        }
    }
}