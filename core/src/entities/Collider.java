package entities;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public interface Collider {

    void collideDoodle(Doodler doodler);

    void draw(SpriteBatch batch);
}
