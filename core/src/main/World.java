package main;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.Vector2;
import entities.Collider;
import entities.Doodler;
import entities.Movable;
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
    public static final int WORLD_STATE_RUNNING = 0;
    public static final int WORLD_STATE_NEXT_LEVEL = 1;
    public static final int WORLD_STATE_GAME_OVER = 2;
    private static final int INITIAL_CAPACITY = 64;
    private static final double MAX_JUMP_HEIGHT = StrictMath.pow(Doodler.Y_VELOCITY, 2) / -(2 * GRAVITY.y);
    public final Random random = new Random();
    public final List<Movable> movables = new ArrayList<>(INITIAL_CAPACITY);
    public final List<Collider> obstacles = new ArrayList<>(INITIAL_CAPACITY);
    private final PlatformFactory platformFactory;
    private final MonsterFactory monsterFactory;
    private final PowerUpFactory powerUpFactory;
    public Doodler doodler;
    private float heightSoFar = 0;
    private int score = 0;
    private int state = WORLD_STATE_RUNNING;

    public World() {
        platformFactory = new PlatformFactoryImpl();
        monsterFactory = new MonsterFactoryImpl();
        powerUpFactory = new PowerUpFactoryImpl();

        generateLevel();
    }

    public void update(float delta) {
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            doodler.setXVelocity(-Doodler.X_VELOCITY);
        } else if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            doodler.setXVelocity(Doodler.X_VELOCITY);
        } else {
            doodler.setXVelocity(0);
        }

        doodler.update(delta);

        obstacles.stream()
                .filter(collider -> collider.collidesDoodler(doodler))
                .findFirst()
                .ifPresent(collider -> collider.collideDoodle(doodler));

        movables.forEach(movable -> movable.move(delta));
    }

    private void generateLevel() {
        float y = Platform.PLATFORM_HEIGHT / 2;

        float maxJumpHeight = (float) MAX_JUMP_HEIGHT;
        boolean spawnDoodle = true;

        while (y < WORLD_HEIGHT) {

            float x = random.nextFloat() * (WORLD_WIDTH - Platform.PLATFORM_WIDTH) + Platform.PLATFORM_WIDTH / 2;

            Platform platform = platformFactory.create(x, y);
            addPlatform(platform);

            if (spawnDoodle) {
                this.doodler = Doodler.createDoodler(platform);
                spawnDoodle = false;
            }

            if (!(platform instanceof MovingPlatform) && random.nextFloat() > 0.9f) {
                PowerUp powerUp = powerUpFactory.create(platform);
                addCollider(powerUp);
            }

//            if (y > WORLD_HEIGHT / 3 && random.nextFloat() > 0.8f) {
//                var monster = monsterFactory.create(platform);
//                addCollider(monster);
//                addMoving(monster);
//            }

            y += (maxJumpHeight - 0.8f);
            y -= random.nextFloat() * maxJumpHeight / 3;
        }
    }

    void createPlatform(float y) {
        float x = getRandomX();

        Platform platform = platformFactory.create(x, y);
        addPlatform(platform);

        if (!(platform instanceof MovingPlatform) && random.nextFloat() > 0.9f) {
            PowerUp powerUp = powerUpFactory.create(platform);
            addCollider(powerUp);
        }

//        if (random.nextFloat() > 0.8f) {
//            var monster = monsterFactory.create(platform);
//            addCollider(monster);
//            addMoving(monster);
//        }

    }

    public void clearScene() {

        for (int i = 0; i < obstacles.size(); i++)
            if (obstacles.get(i).getPosition().y < doodler.getPosition().y - WORLD_HEIGHT / 2) {
                Collider o = obstacles.get(i);
                obstacles.remove(o);

                if (o instanceof Platform)
                    createPlatform(o.getPosition().y + WORLD_HEIGHT);
            }

        for (int i = 0; i < movables.size(); i++)
            if (movables.get(i).getPosition().y < doodler.getPosition().y - WORLD_HEIGHT / 2) {
                movables.remove(movables.get(i));
            }
    }

    private float getRandomX() {
        return random.nextFloat() * (WORLD_WIDTH - Platform.PLATFORM_WIDTH) + Platform.PLATFORM_WIDTH / 2;
    }


    private void checkGameOver() {
        if (heightSoFar - 7.5f > doodler.getPosition().y) {
            state = WORLD_STATE_GAME_OVER;
        }
    }

    private void addPlatform(Platform platform) {
        addCollider(platform);

        if (platform instanceof MovingPlatform) {
            addMoving((Movable) platform);
        }
    }

    private void addCollider(Collider obstacle) {
        obstacles.add(obstacle);
    }

    private void addMoving(Movable movable) {
        movables.add(movable);
    }

    public List<Movable> getMovables() {
        return Collections.unmodifiableList(movables);
    }

    public List<Collider> getObstacles() {
        return Collections.unmodifiableList(obstacles);
    }
}