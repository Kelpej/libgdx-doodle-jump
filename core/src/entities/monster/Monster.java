package entities.monster;

import com.badlogic.gdx.graphics.Texture;
import entities.Obstacle;
import entities.Doodler;
import entities.DynamicGameObject;

public class Monster extends DynamicGameObject implements Obstacle {
    public static final float WIDTH = 120;
    public static final float HEIGHT = 100;

    public Monster(Texture texture, float x, float y) {
        super(texture, x, y, WIDTH, HEIGHT);
    }

    @Override
    public void update(float deltaTime) {

    }

    @Override
    public void collideDoodle(Doodler doodler) {
        doodler.collideMonster(this);
    }
}