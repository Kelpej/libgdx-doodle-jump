package main.ui.screen;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import main.DoodleJump;
import main.ui.screen.button.Button;

import java.util.Collection;

public class GameOverScreen implements DoodleJumpScreen {
    private final DoodleJump game;

    public GameOverScreen(DoodleJump game) {
        this.game = game;
    }

    @Override
    public SpriteBatch getBatch() {
        return null;
    }

    @Override
    public Collection<Button> getButtons() {
        return null;
    }

    @Override
    public void show() {

    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }
}
