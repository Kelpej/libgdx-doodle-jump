package main;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import main.ui.screen.MainScreen;

public class DoodleJump extends Game {
    private SpriteBatch batch;

    @Override
    public void create() {
        batch = new SpriteBatch();
        setScreen(new MainScreen(this));
    }

    public SpriteBatch batch() {
        return batch;
    }
}
