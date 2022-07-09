package main.ui.screen;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;
import main.DoodleJump;
import main.ui.screen.button.Button;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class GameOverScreen implements DoodleJumpScreen {
    private final DoodleJump game;
    private final SpriteBatch batch;

    private final List<Button> buttons = new ArrayList<>();

    public GameOverScreen(DoodleJump game) {
        this.game = game;
        this.batch = new SpriteBatch();
    }

    @Override
    public SpriteBatch getBatch() {
        return batch;
    }

    @Override
    public Collection<Button> getButtons() {
        return buttons;
    }


    @Override
    public void show() {
        System.out.println("GAME OVER SCREEN");
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
