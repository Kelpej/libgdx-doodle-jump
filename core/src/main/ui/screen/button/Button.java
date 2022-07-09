package main.ui.screen.button;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import entities.GameObject;
import main.DoodleJump;

import java.util.function.Consumer;

public class Button extends GameObject {
    private final DoodleJump game;
    private final Consumer<DoodleJump> screenChanger;

    public Button(Texture texture, float x, float y, float width, float height,
                  DoodleJump game,
                  Consumer<DoodleJump> screenChanger) {
        super(texture, x, y, width, height);
        this.game = game;
        this.screenChanger = screenChanger;
    }

    public boolean isClicked(Vector2 vector) {
        return getBounds().contains(vector);
    }

    public void update(float y, SpriteBatch batch, float delta) {
        super.update(batch, delta);
        getPosition().y = y;
    }

    public void handleClick() {
        screenChanger.accept(game);
    }

    @Override
    public String toString() {
        return "Button{" + getBounds() + '}';
    }
}
