package entities;

import com.badlogic.gdx.graphics.Texture;

public class Monster extends DynamicGameObject {

    public Monster(Texture texture, float x, float y) {
        super(texture, x, y, (float) texture.getWidth(), (float) texture.getHeight());
    }


}