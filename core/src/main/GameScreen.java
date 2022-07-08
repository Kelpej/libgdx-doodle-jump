package main;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
public class GameScreen implements Screen {

    public static final float WORLD_WIDTH = 400;
    public static final float WORLD_HEIGHT = 800;
    private static final BitmapFont font = new BitmapFont();


    private final Camera camera = new OrthographicCamera();
    private final Viewport viewport = new StretchViewport(WORLD_WIDTH, WORLD_HEIGHT, camera);
    private final SpriteBatch batch = new SpriteBatch();
    private final Texture background = new Texture("environment/bg.png");
    private final World world = new World();
    private int score;

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0, 0, 0, 1);
        batch.setProjectionMatrix(camera.combined);

        if (world.doodler().getPosition().y > camera.position.y) {
            score = (int)world.doodler().getPosition().y / 10;
            camera.position.y = world.doodler().getPosition().y;
            world.refreshScene();
        }

        camera.update();

        batch.begin();

        batch.draw(background,
                camera.position.x - WORLD_WIDTH / 2,
                camera.position.y - WORLD_HEIGHT / 2,
                WORLD_WIDTH, WORLD_HEIGHT);

        font.setColor(Color.BLACK);
        font.getData().setScale(1.5f);
        font.draw(batch, String.valueOf(score), 0, camera.position.y + WORLD_HEIGHT / 2 - 20);

        world.update(batch, delta);

        batch.end();
    }

    @Override
    public void show() {

    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height, true);
        batch.setProjectionMatrix(camera.combined);
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
