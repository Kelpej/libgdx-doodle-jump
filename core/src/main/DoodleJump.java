package main;

import com.badlogic.gdx.Game;

public class DoodleJump extends Game {

    @Override
    public void create() {
        batch = new SpriteBatch();
        setScreen(new MainScreen(this));
    }
}
