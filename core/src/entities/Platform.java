package entities;

import com.badlogic.gdx.graphics.Texture;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Platform extends GameObject {

    public Platform(Texture texture, float x, float y, float width, float height) {
        super(texture, x, y, width, height);
    }

    @Override
    public void collide(Doodler doodler) {
        doodler.collidePlatform();
    }
}
