package main;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import entities.Collider;
import entities.DelayedTaskCollider;
import entities.Doodler;
import entities.bullet.Bullet;
import entities.monster.Monster;
import entities.monster.MonsterFactory;
import entities.platform.BreakingPlatform;
import entities.platform.MovingPlatform;
import entities.platform.Platform;
import entities.platform.PlatformFactory;
import entities.powerup.PowerUp;
import entities.powerup.PowerUpFactory;
import main.ui.screen.GameScreen;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;

import static com.badlogic.gdx.Gdx.input;
import static com.badlogic.gdx.Input.Keys.*;
import static main.ui.screen.DoodleJumpScreen.HEIGHT;
import static main.ui.screen.DoodleJumpScreen.WIDTH;


public class World {

    public enum Difficulty {
        EASY(2f, 0, 0.2f),
        NORMAL(1f, 0.2f, 0.15f),
        HARD(0f, 0.4f, 0.1f);

        private final float platformGap;
        private final float monsterSpawn;
        public final float powerUpSpawn;

        Difficulty(float platformGap, float monsterSpawn, float powerUpSpawn) {
            this.platformGap = platformGap;
            this.monsterSpawn = monsterSpawn;
            this.powerUpSpawn = powerUpSpawn;
        }
    }

    public static final Vector2 GRAVITY = new Vector2(0, -6);
    private static final int INITIAL_CAPACITY = 32;

    private static final double MAX_JUMP_HEIGHT = StrictMath.pow(Doodler.Y_VELOCITY, 2) / -(2 * GRAVITY.y);

    private static final Random random = new Random();

    private final List<Collider> obstacles = new ArrayList<>(INITIAL_CAPACITY);

    private final PlatformFactory platformFactory = new PlatformFactoryImpl();
    private final MonsterFactory monsterFactory = new MonsterFactoryImpl();
    private final PowerUpFactory powerUpFactory = new PowerUpFactoryImpl();

    private Doodler doodler;
    private Optional<Bullet> optionalBullet = Optional.empty();

    private final Difficulty difficulty;

    public World(Difficulty difficulty) {
        this.difficulty = difficulty;
        generateScene();
    }

    public List<Collider> getObstacles() {
        return obstacles;
    }

    public void update(SpriteBatch batch, float delta) {
        /*
            Handle input
         */
        if (input.isKeyPressed(LEFT)) {
            doodler.setXVelocity(-Doodler.X_VELOCITY);
            if (doodler.isOrientedRight()) {
                doodler.switchOrientation();
            }
        } else if (input.isKeyPressed(RIGHT)) {
            doodler.setXVelocity(Doodler.X_VELOCITY);
            if (!doodler.isOrientedRight()) {
                doodler.switchOrientation();
            }
        } else {
            doodler.setXVelocity(0);
        }

        if (input.isKeyPressed(SPACE) && doodler.notPoweredUp() && doodler.isAlive() && optionalBullet.isEmpty()) {
            doodler.shoot();
            optionalBullet = Optional.of(new Bullet(doodler));
        }

        doodler.update(batch, delta);

        /*
        Bullet logic
         */
        optionalBullet.ifPresent(bullet -> {
            bullet.update(batch, delta);

            obstacles.stream()
                    .filter(collider -> collider instanceof Monster && ((Monster) collider).collidesBullet(bullet))
                    .findFirst()
                    .ifPresent(collider -> {
                        Sounds.playSound(GameSound.MONSTER_DEAD);
                        obstacles.remove(collider);
                        World.this.optionalBullet = Optional.empty();
                    });
            if (bullet.getPosition().y > doodler.getPosition().y + HEIGHT) {
                World.this.optionalBullet = Optional.empty();
            }
        });

        /*
        Check collisions
         */
        if (doodler.isAlive() && doodler.notPoweredUp()) {
            obstacles.stream()
                    .filter(collider -> collider.collidesDoodler(doodler))
                    .findFirst()
                    .ifPresent(collider -> collider.collideDoodle(doodler));
        }

        /*
        Remove dead monster
         */
        obstacles.stream()
                .filter(collider -> collider instanceof Monster && !((Monster) collider).isAlive())
                .findFirst()
                .ifPresent(obstacles::remove);

        obstacles.forEach(collider -> collider.update(batch, delta));
    }

    private void generateScene() {
        float y = Platform.HEIGHT / 2;

        float maxJumpHeight = (float) MAX_JUMP_HEIGHT;
        boolean spawnDoodle = true;

        while (y < HEIGHT) {
            float x = getRandomX();

            Platform platform = createPlatform(x, y);

            if (spawnDoodle) {
                this.doodler = Doodler.createDoodler(platform);
                spawnDoodle = false;
            }

            createPowerUp(platform);

            if (y > HEIGHT / 3) {
                createMonster(platform);
            }

            y += (maxJumpHeight - 0.8f - difficulty.platformGap);
            y -= random.nextFloat() * maxJumpHeight / 3;
        }
    }

    public void refreshScene(float cameraPosY) {
        for (int i = 0; i < obstacles.size(); i++) {

            if (obstacles.get(i).getPosition().y < cameraPosY - HEIGHT / 2) {
                Collider o = obstacles.get(i);

                obstacles.remove(o);

                if (o instanceof BreakingPlatform &&
                        ((BreakingPlatform) o).getCurrentState() == DelayedTaskCollider.State.USED) {
                    createPlatform(((BreakingPlatform) o).spawnPosY() + HEIGHT);
                    return;
                }

                if (o instanceof Platform)
                    createPlatform(cameraPosY + HEIGHT / 2);
            }
        }
    }

    private Platform createPlatform(float x, float y) {
        Platform platform = platformFactory.create(x, y);
        addObstacle(platform);
        return platform;
    }

    private void createPlatform(float y) {
        float x = getRandomX();

        Platform platform = createPlatform(x, y);

        createPowerUp(platform);

        createMonster(platform);
    }

    private void createMonster(Platform platform) {
        if (doodler.notPoweredUp() && random.nextFloat() < difficulty.monsterSpawn) {
            var monster = monsterFactory.create(platform);
            addObstacle(monster);
        }
    }

    private void createPowerUp(Platform platform) {
        if (!(platform instanceof MovingPlatform) && random.nextFloat() < difficulty.powerUpSpawn) {
            PowerUp powerUp = powerUpFactory.create(platform);
            addObstacle(powerUp);
        }
    }

    private float getRandomX() {
        return random.nextFloat() * (WIDTH - Platform.WIDTH) + Platform.WIDTH / 2;
    }

    private void addObstacle(Collider obstacle) {
        obstacles.add(obstacle);
    }

    public Doodler doodler() {
        return doodler;
    }

    public Optional<Bullet> optionalBullet() {
        return optionalBullet;
    }
}