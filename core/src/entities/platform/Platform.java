package entities.platform;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import entities.Collider;
import entities.Doodler;
import main.GameSound;
import main.Sounds;

import static entities.Doodler.Y_VELOCITY;

public interface Platform extends Collider {

    float PLATFORM_WIDTH = 60;
    float PLATFORM_HEIGHT = 20;

    default void bounce(Doodler doodler) {
        Sounds.playSound(GameSound.PLATFORM_BOUNCE);
        doodler.jump();
        doodler.getVelocity().y = Y_VELOCITY;
    }

    @Override
    default boolean collidesDoodler(Doodler doodler) {
        return Collider.super.collidesDoodler(doodler)
                && doodler.isFalling()
                && doodler.getPosition().y >= this.getPosition().y + PLATFORM_HEIGHT / 3;
    }

    default void collideDoodle(Doodler doodler) {
        doodler.collidePlatform(this);
    }

    default void draw(SpriteBatch batch) {
        batch.draw(getSprite(), getPosition().x, getPosition().y, PLATFORM_WIDTH, PLATFORM_HEIGHT);
    }

    Vector2 getPosition();

    Sprite getSprite();
}
