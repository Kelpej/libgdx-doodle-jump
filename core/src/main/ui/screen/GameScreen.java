package main.ui.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import entities.DelayedTaskCollider;
import entities.Doodler;
import entities.GameObject;
import main.DoodleJump;
import main.World;
import main.ui.screen.button.Button;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.function.Supplier;

import static main.ui.screen.GameScreen.State.*;

public class GameScreen implements DoodleJumpScreen {

    private final BitmapFont font = new BitmapFont();
    private final Camera camera = new OrthographicCamera();
    private final Viewport viewport = new StretchViewport(WIDTH, HEIGHT, camera);
    private final World world = new World();
    private final List<Button> buttons = new ArrayList<>();

    private final Supplier<Button> pauseSupplier;
    private final Supplier<Button> resumeSupplier;
    private final DoodleJump game;
    private final SpriteBatch batch;
    private Button pauseGameButton;
    private float cameraToFall;
    private int score;
    private State currentState = PLAY;

    public GameScreen(DoodleJump game) {
        this.game = game;
        this.batch = game.batch();

        pauseSupplier = () ->
                new Button(new Texture(Gdx.files.internal("buttons/pause.png")),
                        WIDTH - 25, HEIGHT - 25,
                        25, 25,
                        game, doodleJump -> pause());

        resumeSupplier = () ->
                new Button(new Texture(Gdx.files.internal("buttons/play.png")),
                        WIDTH - 25, HEIGHT - 25,
                        25, 25,
                        game, doodleJump -> resume());
    }

    private void changeButton(Supplier<Button> supplier) {
        buttons.remove(pauseGameButton);
        pauseGameButton = supplier.get();
        buttons.add(pauseGameButton);
    }


    @Override
    public void render(float delta) {
        ScreenUtils.clear(0, 0, 0, 1);
        batch.setProjectionMatrix(camera.combined);

        batch.begin();

        batch.draw(BACKGROUND,
                camera.position.x - WIDTH / 2,
                camera.position.y - HEIGHT / 2);

        buttons.forEach(button -> button.update(camera.position.y + HEIGHT / 2 - 25 - 10, batch, delta));

        handleClick();

        font.draw(batch, String.valueOf(score), 0, camera.position.y + HEIGHT / 2 - 15);

        world.refreshScene();

        switch (currentState) {

            case PLAY -> {
                world.update(batch, delta);
                pullUpCamera();
                batch.end();

                /*
                Check dead by fall
                */
                if (world.doodler().getPosition().y < camera.position.y - HEIGHT / 2) {
                    world.doodler().setDead();
                }

                /*
                Change state if doodler died during updating
                */
                if (!world.doodler().isAlive()) {
                    cameraToFall = camera.position.y - HEIGHT;
                    currentState = GAME_OVER;
                }
            }

            case PAUSE -> {
                /*
                Draw objects when paused
                */
                world.doodler().draw(batch);
                world.obstacles.forEach(collider -> ((GameObject) collider).draw(batch));
                world.optionalBullet().ifPresent(bullet -> bullet.draw(batch));
                batch.end();
            }

            case GAME_OVER -> {
                world.doodler().update(batch, delta);

                /*
                Fix velocity for game over animation scene
                */
                if (world.doodler().getVelocity().y < -100) {
                    world.doodler().getVelocity().set(0, -100);
                }

                world.obstacles.forEach(collider -> ((GameObject) collider).draw(batch));
                batch.end();
                pullDownCamera();
            }
        }

        camera.update();
    }

    private void pullDownCamera() {
        if (camera.position.y > cameraToFall) {
            camera.position.y = world.doodler().getPosition().y;
            return;
        }

        if (world.doodler().getPosition().y < camera.position.y - HEIGHT / 2 - Doodler.DOODLER_SIZE) {
            world.doodler().getVelocity().set(0, 0);
            game.setScreen(new MainScreen(game));
        }
    }

    private void pullUpCamera() {
        if (world.doodler().getPosition().y > camera.position.y) {
            camera.position.y = world.doodler().getPosition().y;
            score = (int) world.doodler().getPosition().y / 10;
        }
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
        pauseGameButton = pauseSupplier.get();
        buttons.add(pauseGameButton);
        font.setColor(Color.BLACK);
        font.getData().setScale(1.5f);
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height, true);
        batch.setProjectionMatrix(camera.combined);
    }

    @Override
    public void pause() {
        currentState = PAUSE;
        changeButton(resumeSupplier);

        world.getObstacles().stream()
                .filter(DelayedTaskCollider.class::isInstance)
                .forEach(collider -> ((DelayedTaskCollider) collider).cancelTask());
    }

    @Override
    public void resume() {
        currentState = PLAY;
        changeButton(pauseSupplier);

        world.getObstacles().stream()
                .filter(DelayedTaskCollider.class::isInstance)
                .forEach(collider -> ((DelayedTaskCollider) collider).scheduleTask());
    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }

    enum State {
        PLAY,
        PAUSE,
        GAME_OVER,
    }
}
