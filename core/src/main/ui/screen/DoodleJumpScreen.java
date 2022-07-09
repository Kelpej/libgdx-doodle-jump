package main.ui.screen;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.ScreenUtils;
import main.ui.screen.button.Button;

import java.util.Collection;

import static com.badlogic.gdx.Gdx.input;

public interface DoodleJumpScreen extends Screen {
    float WIDTH = 400;
    float HEIGHT = 800;
    Texture BACKGROUND = new Texture("environment/bg.png");

    default void handleClick() {
        if (input.justTouched()) {
            var click = new Vector2((float) input.getX(), HEIGHT - input.getY());
            getButtons().stream()
                    .filter(button -> button.isClicked(click))
                    .findFirst()
                    .ifPresent(Button::handleClick);
        }
    }

    default void render(float delta) {
        ScreenUtils.clear(0, 0, 0, 1);

        handleClick();

        getBatch().begin();
        getBatch().draw(BACKGROUND, 0, 0);
        getButtons().forEach(button -> button.update(getBatch(), delta));
        getBatch().end();
    }

    SpriteBatch getBatch();

    Collection<Button> getButtons();
}
