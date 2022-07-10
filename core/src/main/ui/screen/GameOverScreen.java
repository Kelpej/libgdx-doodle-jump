package main.ui.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;
import main.DoodleJump;
import main.ui.screen.button.Button;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class GameOverScreen implements DoodleJumpScreen {
    private final DoodleJump game;
    private final GameScreen gameScreen;
    private final SpriteBatch batch;
    private final BitmapFont font = new BitmapFont();


    private final List<Button> buttons = new ArrayList<>();

    public GameOverScreen(DoodleJump game, GameScreen gameScreen) {
        this.game = game;
        this.gameScreen = gameScreen;
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
    public void render(float delta) {
        ScreenUtils.clear(0, 0, 0, 1);

        handleClick();

        getBatch().begin();
        getBatch().draw(BACKGROUND, 0, 0);
        getButtons().forEach(button -> button.update(getBatch(), delta));

        Texture gameOverTexture = new Texture(Gdx.files.internal("environment/gameover.png"));
        batch.draw(gameOverTexture,
                (float) (WIDTH/2 - gameOverTexture.getWidth()/6.0), (float) (HEIGHT-gameOverTexture.getHeight()/3.0 - 50),
                (float) (gameOverTexture.getWidth()/3.0), (float) (gameOverTexture.getHeight()/3.0));



        GlyphLayout layout = new GlyphLayout(font, "Your score is: " + gameScreen.getScore());
        float fontX = (WIDTH - layout.width) / 2;
        float fontY = (float) (HEIGHT-gameOverTexture.getHeight()/3.0 - 70);
        font.draw(batch, layout, fontX, fontY);

        layout = new GlyphLayout(font, "Highest score: " + DoodleJump.highestScore);
        fontX = (WIDTH - layout.width) / 2;
        fontY = (float) (HEIGHT-gameOverTexture.getHeight()/3.0 - 100);
        font.draw(batch, layout, fontX, fontY);

        Texture injuredTexture = new Texture(Gdx.files.internal("player/injured.png"));
        batch.draw(injuredTexture,
                (float) (WIDTH/2 - injuredTexture.getWidth()/18.0 + 10), (float) (HEIGHT-injuredTexture.getHeight()/6.0 - 150),
                (float) (injuredTexture.getWidth()/9.0), (float) (injuredTexture.getHeight()/9.0));


        getBatch().end();
    }


    @Override
    public void show() {
        font.setColor(Color.BLACK);
        font.getData().setScale(1.5f);

        var restartGameTexture = new Texture(Gdx.files.internal("buttons/restart_game2.png"));
        var restartGame = new Button(restartGameTexture,
                (float) (WIDTH/2 - restartGameTexture.getWidth()/2.0), HEIGHT/2 - restartGameTexture.getHeight() -30,
                restartGameTexture.getWidth(), restartGameTexture.getHeight(),
                game, doodleJump -> doodleJump.setScreen(new GameScreen(game, gameScreen.currentDifficulty)));
        buttons.add(restartGame);


        var mainMenuTexture = new Texture(Gdx.files.internal("buttons/home.png"));
        var mainMenu = new Button(mainMenuTexture,
                (float) (WIDTH/2 - mainMenuTexture.getWidth()/2.0), HEIGHT/2 - mainMenuTexture.getHeight() - 150,
                mainMenuTexture.getWidth(), mainMenuTexture.getHeight(),
                game, doodleJump -> doodleJump.setScreen(new MainScreen(game)));
        buttons.add(mainMenu);


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
