package entities.bullet;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import entities.Doodler;
import entities.DynamicGameObject;

public class Bullet extends DynamicGameObject {
    private static final Texture TEXTURE = new Texture(Gdx.files.internal("player/bullet.png"));
    public static final Vector2 velocity = new Vector2(0, 15);

    public Bullet(Doodler doodler) {
        super(TEXTURE, doodler.getPosition().x, doodler.getPosition().y, velocity);
    }

    @Override
    public void draw(SpriteBatch batch) {
        super.draw(batch);
    }

}
