package main;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

public class GameScreen implements Screen {

    public static final float WORLD_WIDTH = 400;
    public static final float WORLD_HEIGHT = 800;

    private final Camera camera = new OrthographicCamera();
    private final Texture background = new Texture("environment/bg.png");
    private Viewport viewport = new StretchViewport(WORLD_WIDTH, WORLD_HEIGHT, camera);
    private SpriteBatch batch = new SpriteBatch();
    private World world = new World();

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0, 0, 0, 1);
        camera.update();
        batch.setProjectionMatrix(camera.combined);

        world.update(delta);

        if (world.doodler.getPosition().y > camera.position.y) {
            camera.position.y = world.doodler.getPosition().y;
            world.clearScene();
        }

        batch.begin();

        batch.draw(background,
                camera.position.x - WORLD_WIDTH / 2,
                camera.position.y - WORLD_HEIGHT / 2,
                WORLD_WIDTH, WORLD_HEIGHT);

        world.doodler.draw(batch);

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
