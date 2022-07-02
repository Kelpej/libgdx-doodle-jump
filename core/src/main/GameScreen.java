package main;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import entities.Doodler;

public class GameScreen implements Screen {

    public static final float WORLD_WIDTH = 400;
    public static final float WORLD_HEIGHT = 800;

    private final Camera camera = new OrthographicCamera();
    private Viewport viewport = new StretchViewport(WORLD_WIDTH, WORLD_HEIGHT, camera);


    private SpriteBatch batch = new SpriteBatch();
    private final Texture background = new Texture("environment/bg.png");

    private World world = new World();

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0, 0, 0, 1);
        camera.update();
        batch.setProjectionMatrix(camera.combined);

        batch.begin();
        batch.draw(background, camera.position.x - WORLD_WIDTH / 2, camera.position.y - WORLD_HEIGHT / 2,
                WORLD_WIDTH, WORLD_HEIGHT);


        if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            world.doodler.setXVelocity(-Doodler.X_VELOCITY);

        } else if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {

            world.doodler.setXVelocity(Doodler.X_VELOCITY);
        } else {

            world.doodler.setXVelocity(0);
        }

        world.doodler.draw(batch);
        world.doodler.update(delta);

        world.getMovables().forEach(movable -> {
            movable.move(delta);
            movable.draw(batch);
        });

        world.getObstacles().stream()
                .filter(collider -> collider.collidesDoodler(world.doodler))
                .findFirst()
                .ifPresent(collider -> collider.collideDoodle(world.doodler));

        world.getObstacles().forEach(obstacle -> obstacle.draw(batch));

        if (world.doodler.getPosition().y > camera.position.y)
            camera.position.y = world.doodler.getPosition().y;

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
