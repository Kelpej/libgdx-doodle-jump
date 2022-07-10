package main.ui.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import main.DoodleJump;
import main.World;
import main.ui.screen.button.Button;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class MainScreen implements DoodleJumpScreen {
    private final DoodleJump game;
    private final SpriteBatch batch;

    private final List<Button> buttons = new ArrayList<>();

    public MainScreen(DoodleJump game) {
        this.game = game;
        this.batch = new SpriteBatch();
    }

    @Override
    public void show() {
        var startGameTexture = new Texture(Gdx.files.internal("buttons/start_game2.png"));
        var startGame = new Button(startGameTexture,
                (float) (DoodleJumpScreen.WIDTH/2 - startGameTexture.getWidth()/2.0), (float) (DoodleJumpScreen.HEIGHT/2 - startGameTexture.getHeight()/2.0),
                startGameTexture.getWidth(), startGameTexture.getHeight(),
                game, doodleJump -> doodleJump.setScreen(new DifficultyScreen(game)));
        buttons.add(startGame);
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
