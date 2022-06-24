package main;

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
    private static final int INITIAL_CAPACITY = 64;

    public static final int WORLD_STATE_RUNNING = 0;
    public static final int WORLD_STATE_NEXT_LEVEL = 1;
    public static final int WORLD_STATE_GAME_OVER = 2;

    public static final Vector2 gravity = new Vector2(0, -12);

    public final WorldListener listener;
    public final Random random = new Random();
    public final Doodler doodler = new Doodler(0, 0);

    private final PlatformFactory platformFactory;
    private final MonsterFactory monsterFactory;
    private final PowerUpFactory powerUpFactory;

    public final List<Movable> movables = new ArrayList<>(INITIAL_CAPACITY);
    public final List<Collider> obstacles = new ArrayList<>(INITIAL_CAPACITY);

    private float heightSoFar = 0;
    private int score = 0;
    private int state = WORLD_STATE_RUNNING;

    public World(WorldListener listener) {
        this.listener = listener;

        platformFactory = new PlatformFactoryImpl();
        monsterFactory = new MonsterFactoryImpl();
        powerUpFactory = new PowerUpFactoryImpl();

        generateLevel();
    }

    private void generateLevel() {
        float y = Platform.PLATFORM_HEIGHT / 2;

        float maxJumpHeight = (float) StrictMath.pow(Doodler.Y_VELOCITY, 2) / - (2 * gravity.y);

        while (y < WORLD_HEIGHT - WORLD_WIDTH / 2) {

            float x = random.nextFloat() * (WORLD_WIDTH - Platform.PLATFORM_WIDTH) + Platform.PLATFORM_WIDTH / 2;

            Platform platform = platformFactory.create(x, y);
            addPlatform(platform);

            if (!(platform instanceof MovingPlatform)) {
                if (random.nextFloat() > 0.9f) {
                    PowerUp powerUp = powerUpFactory.create(platform);
                    addCollider(powerUp);
                }
            } else {
                addMoving((Movable) platform);
            }

            if (y > WORLD_HEIGHT / 3 && random.nextFloat() > 0.8f) {
                var monster = monsterFactory.create(platform);
                addCollider(monster);
                addMoving(monster);
                System.out.println("addMonster");
            }

            y += (maxJumpHeight - 0.5f);
            y -= random.nextFloat() * maxJumpHeight / 3;
            System.out.println(y);
        }

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