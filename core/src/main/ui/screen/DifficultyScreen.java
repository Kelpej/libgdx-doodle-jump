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

public class DifficultyScreen implements DoodleJumpScreen {
    private final DoodleJump game;
    private final SpriteBatch batch;
    private final BitmapFont font = new BitmapFont();

    private final List<Button> buttons = new ArrayList<>();

    public DifficultyScreen(DoodleJump game) {
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


        GlyphLayout layout = new GlyphLayout(font, "Select the difficulty");
        float fontX = (WIDTH - layout.width) / 2;
        float fontY = HEIGHT - layout.height - 20;
        font.draw(batch, layout, fontX, fontY);


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
        font.setColor(Color.BLACK);
        font.getData().setScale(1.5f);

        var easyTexture = new Texture(Gdx.files.internal("buttons/easy.png"));
        var selectEasy = new Button(easyTexture,
                (float) (WIDTH/2 - easyTexture.getWidth()/2.0), HEIGHT- easyTexture.getHeight() - 100,
                easyTexture.getWidth(), easyTexture.getHeight(),
                game, doodleJump -> doodleJump.setScreen(new GameScreen(game, World.Difficulty.EASY)));
        buttons.add(selectEasy);

        var normalTexture = new Texture(Gdx.files.internal("buttons/normal.png"));
        var selectNormal = new Button(normalTexture,
                (float) (WIDTH/2 - normalTexture.getWidth()/2.0), HEIGHT - easyTexture.getHeight() - normalTexture.getHeight() - 130,
                normalTexture.getWidth(), normalTexture.getHeight(),
                game, doodleJump -> doodleJump.setScreen(new GameScreen(game, World.Difficulty.NORMAL)));
        buttons.add(selectNormal);

        var hardTexture = new Texture(Gdx.files.internal("buttons/hard.png"));
        var selectHard = new Button(hardTexture,
                (float) (WIDTH/2 - hardTexture.getWidth()/2.0), HEIGHT  - easyTexture.getHeight() - normalTexture.getHeight() - hardTexture.getHeight() - 160,
                hardTexture.getWidth(), hardTexture.getHeight(),
                game, doodleJump -> doodleJump.setScreen(new GameScreen(game, World.Difficulty.HARD)));
        buttons.add(selectHard);
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
