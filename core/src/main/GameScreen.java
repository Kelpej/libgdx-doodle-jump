package main;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.TextureData;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

public class GameScreen implements Screen {

    public static final float WORLD_WIDTH = 400;
    public static final float WORLD_HEIGHT = 800;

    private final Camera camera = new OrthographicCamera();;
    private Viewport viewport  = new StretchViewport(WORLD_WIDTH, WORLD_HEIGHT, camera);


    private SpriteBatch batch = new SpriteBatch();;
    private Texture background = new Texture("environment/bg.png");
    private Texture testTexture = new Texture("environment/kavunyaka.png");

    private World world = new World(new WordListenerImpl());

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0, 0, 0.2f, 1);
        camera.update();
        batch.setProjectionMatrix(camera.combined);

        batch.begin();
        batch.draw(background, 0, 0);


        batch.draw(testTexture, 0, 0, 700, 700);

        world.getMovables().forEach(movable -> movable.move(delta));

        world.getMovables().forEach(movable -> {
            movable.move(delta);
            movable.draw(batch);
        });

        world.getObstacles().forEach(obstacle -> obstacle.draw(batch));

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
