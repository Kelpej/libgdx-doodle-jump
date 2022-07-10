package main.ui.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;
import main.DoodleJump;
import main.World;
import main.ui.screen.button.Button;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class WinScreen implements DoodleJumpScreen {
    private final DoodleJump game;
    private final SpriteBatch batch;
    private final BitmapFont font = new BitmapFont();

    private final List<Button> buttons = new ArrayList<>();


    public WinScreen(DoodleJump game) {
        this.game = game;
        this.batch = new SpriteBatch();
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0, 0, 0, 1);

        handleClick();

        getBatch().begin();
        getBatch().draw(BACKGROUND, 0, 0);
        getButtons().forEach(button -> button.update(getBatch(), delta));

        Texture winTexture = new Texture(Gdx.files.internal("environment/win2.png"));
        batch.draw(winTexture,
                WIDTH/2 - winTexture.getWidth()*2 + 10, (HEIGHT-winTexture.getHeight()*8 + 50),
                (float) (winTexture.getWidth()*4), (float) (winTexture.getHeight()*4));


        Texture winImg = new Texture(Gdx.files.internal("environment/win3.png"));
        batch.draw(winImg,
                (float) (WIDTH/2 - winImg.getWidth()/2.0), (HEIGHT-winImg.getHeight()*2) + 50);


        getBatch().end();
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
