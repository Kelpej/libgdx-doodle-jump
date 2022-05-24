package entities;

import com.badlogic.gdx.graphics.Texture;

public class Doodle extends DynamicGameObject{
    private final Texture texture = new Texture("player/doodle.webpack");
    private final double JUMP_POWER = 5;

    public Doodle(float x, float y, float width, float height) {
        super(x, y, width, height);
    }

    private void jump(double dt) {

    }
}
