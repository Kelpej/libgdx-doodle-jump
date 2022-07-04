package main;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import entities.Collider;
import entities.Doodler;
import entities.monster.MonsterFactory;
import entities.platform.MovingPlatform;
import entities.platform.Platform;
import entities.platform.PlatformFactory;
import entities.powerup.PowerUp;
import entities.powerup.PowerUpFactory;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import static main.GameScreen.WORLD_HEIGHT;
import static main.GameScreen.WORLD_WIDTH;

public class World {
    public static final Vector2 GRAVITY = new Vector2(0, -6);
    private static final int INITIAL_CAPACITY = 32;

    private static final double MAX_JUMP_HEIGHT = StrictMath.pow(Doodler.Y_VELOCITY, 2) / -(2 * GRAVITY.y);

    public final Random random = new Random();

    public final List<Collider> obstacles = new ArrayList<>(INITIAL_CAPACITY);

    private final PlatformFactory platformFactory = new PlatformFactoryImpl();
    private final MonsterFactory monsterFactory = new MonsterFactoryImpl();
    private final PowerUpFactory powerUpFactory = new PowerUpFactoryImpl();

    public Doodler doodler;

    public World() {
        generateScene();
    }

    public void update(SpriteBatch batch, float delta) {
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            doodler.setXVelocity(-Doodler.X_VELOCITY);
        } else if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            doodler.setXVelocity(Doodler.X_VELOCITY);
        } else {
            doodler.setXVelocity(0);
        }

        doodler.update(batch, delta);

        if (doodler.isAlive() && !doodler.isPoweredUp()) {
            obstacles.stream()
                    .filter(collider -> collider.collidesDoodler(doodler))
                    .findFirst()
                    .ifPresent(collider -> collider.collideDoodle(doodler));
        }

        getObstacles().forEach(collider -> collider.update(batch, delta));
    }

    private void generateScene() {
        float y = Platform.PLATFORM_HEIGHT / 2;

        float maxJumpHeight = (float) MAX_JUMP_HEIGHT;
        boolean spawnDoodle = true;

        while (y < WORLD_HEIGHT) {
            float x = getRandomX();

            Platform platform = createPlatform(x, y);

            if (spawnDoodle) {
                this.doodler = Doodler.createDoodler(platform);
                spawnDoodle = false;
            }

            createPowerUp(platform);

            if (y > WORLD_HEIGHT / 3 ) {
                createMonster(platform);
            }

            y += (maxJumpHeight - 0.8f);
            y -= random.nextFloat() * maxJumpHeight / 3;
        }
    }

    private Platform createPlatform(float x, float y) {
        Platform platform = platformFactory.create(x, y);
        addCollider(platform);
        return platform;
    }

    private void createMonster(Platform platform) {
        if (random.nextFloat() > 0.8f) {
            var monster = monsterFactory.create(platform);
            addCollider(monster);
        }
    }

    private void createPowerUp(Platform platform) {
        if (!(platform instanceof MovingPlatform) && random.nextFloat() > 0.9f) {
            PowerUp powerUp = powerUpFactory.create(platform);
            addCollider(powerUp);
        }
    }

    void createPlatform(float y) {
        float x = getRandomX();

        Platform platform = createPlatform(x, y);

        createPowerUp(platform);

        createMonster(platform);
    }

    public void refreshScene() {
        for (int i = 0; i < obstacles.size(); i++) {
            if (obstacles.get(i).getPosition().y < doodler.getPosition().y - WORLD_HEIGHT / 2) {
                Collider o = obstacles.get(i);

                obstacles.remove(o);

                if (o instanceof Platform)
                    createPlatform(o.getPosition().y + WORLD_HEIGHT);
            }
        }
    }

    private float getRandomX() {
        return random.nextFloat() * (WORLD_WIDTH - Platform.PLATFORM_WIDTH) + Platform.PLATFORM_WIDTH / 2;
    }

    private void addCollider(Collider obstacle) {
        obstacles.add(obstacle);
    }

    public List<Collider> getObstacles() {
        return Collections.unmodifiableList(obstacles);
    }
}