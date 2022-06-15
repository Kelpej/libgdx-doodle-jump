package main;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;

import com.badlogic.gdx.math.Vector2;
import entities.Doodler;
import entities.GameObject;
import entities.Monster;
import entities.PowerUp;

public class World {

    public interface WorldListener {
        public void jump ();

        public void highJump ();

        public void hit ();

        public void coin ();
    }

    public static final float WORLD_WIDTH = 10;
    public static final float WORLD_HEIGHT = 15 * 20;
    public static final int WORLD_STATE_RUNNING = 0;
    public static final int WORLD_STATE_NEXT_LEVEL = 1;
    public static final int WORLD_STATE_GAME_OVER = 2;
    public static final Vector2 gravity = new Vector2(0, -12);

    public final Doodler doodler;
    public final List<Platform> platforms;
    public final List<PowerUp> powerUps;
    public final List<Monster> monsters;
    public final WorldListener listener;
    public final Random rand;

    public float heightSoFar;
    public int score;
    public int state;

    public World (WorldListener listener) {
        this.doodler = new Doodler(5, 1);
        this.platforms = new ArrayList<Platform>();
        this.powerUpList = new ArrayList<Spring>();
        this.monsters = new ArrayList<Monster>();
        this.coins = new ArrayList<Coin>();
        this.listener = listener;
        rand = new Random();
        generateLevel();

        this.heightSoFar = 0;
        this.score = 0;
        this.state = WORLD_STATE_RUNNING;
    }

    private void generateLevel () {
        float y = Platform.PLATFORM_HEIGHT / 2;
        float maxJumpHeight = Bob.BOB_JUMP_VELOCITY * Bob.BOB_JUMP_VELOCITY / (2 * -gravity.y);
        while (y < WORLD_HEIGHT - WORLD_WIDTH / 2) {
            int type = rand.nextFloat() > 0.8f ? Platform.PLATFORM_TYPE_MOVING : Platform.PLATFORM_TYPE_STATIC;
            float x = rand.nextFloat() * (WORLD_WIDTH - Platform.PLATFORM_WIDTH) + Platform.PLATFORM_WIDTH / 2;

            Platform platform = new Platform(type, x, y);
            platforms.add(platform);

            if (rand.nextFloat() > 0.9f && type != Platform.PLATFORM_TYPE_MOVING) {
                Spring spring = new Spring(platform.position.x, platform.position.y + Platform.PLATFORM_HEIGHT / 2
                        + Spring.SPRING_HEIGHT / 2);
                powerUpList.add(spring);
            }

            if (y > WORLD_HEIGHT / 3 && rand.nextFloat() > 0.8f) {
                var monster = new Monster(platform.x + rand.nextFloat(), platform.position.y
                        + Squirrel.SQUIRREL_HEIGHT + rand.nextFloat() * 2);
                monsters.add(monster);
            }

            if (rand.nextFloat() > 0.6f) {
                Coin coin = new Coin(platform.position.x + rand.nextFloat(), platform.position.y + Coin.COIN_HEIGHT
                        + rand.nextFloat() * 3);
                coins.add(coin);
            }

            y += (maxJumpHeight - 0.5f);
            y -= rand.nextFloat() * (maxJumpHeight / 3);
        }

        castle = new Castle(WORLD_WIDTH / 2, y);
    }

    public void update (float deltaTime, float accelX) {
        updateBob(deltaTime, accelX);
        updatePlatforms(deltaTime);
        updateSquirrels(deltaTime);
        updateCoins(deltaTime);
        if (doodler.state != Bob.BOB_STATE_HIT) checkCollisions();
        checkGameOver();
    }

    private void updateBob (float deltaTime, float accelX) {
        if (doodler.state != Bob.BOB_STATE_HIT && doodler.position.y <= 0.5f) doodler.collidePlatform();
        if (doodler.state != Bob.BOB_STATE_HIT) doodler.velocity.x = -accelX / 10 * Bob.BOB_MOVE_VELOCITY;
        doodler.update(deltaTime);
        heightSoFar = Math.max(doodler.position.y, heightSoFar);
    }

    private void updatePlatforms (float deltaTime) {
        int len = platforms.size();
        for (int i = 0; i < len; i++) {
            Platform platform = platforms.get(i);
            platform.update(deltaTime);
            if (platform.state == Platform.PLATFORM_STATE_PULVERIZING && platform.stateTime > Platform.PLATFORM_PULVERIZE_TIME) {
                platforms.remove(platform);
                len = platforms.size();
            }
        }
    }


//    private void updateCoins (float deltaTime) {
//        int len = coins.size();
//        for (int i = 0; i < len; i++) {
//            Coin coin = coins.get(i);
//            coin.update(deltaTime);
//        }
//    }

    private void checkCollisions () {
        checkPlatformCollisions();
        checkSquirrelCollisions();
        checkItemCollisions();
        checkCastleCollisions();
    }



    private void checkGameOver () {
        if (heightSoFar - 7.5f > doodler.position.y) {
            state = WORLD_STATE_GAME_OVER;
        }
    }

    public static void main(String[] args) {
        Doodler doodler = new Doodler(0, 0);
        //
        List<GameObject> objectList = new ArrayList<>();

        objectList.forEach(GameObject::update);

        Optional<GameObject> collider = objectList.stream()
                .filter(o -> doodler.getBounds().overlaps(o.getBounds()))
                .findFirst();

        collider.ifPresent(gameObject -> gameObject.collide(doodler));

    }
}