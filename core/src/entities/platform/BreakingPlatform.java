package entities.platform;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import entities.DelayedTaskCollider;
import entities.Doodler;
import entities.DynamicGameObject;

import java.util.Timer;
import java.util.TimerTask;

import static com.badlogic.gdx.Gdx.files;
import static entities.platform.BreakingPlatform.State.*;

public class BreakingPlatform extends DynamicGameObject implements Platform, DelayedTaskCollider {

    private Timer timer = new Timer();
    private State currentState = UNUSED;
    private final float spawnPosY;

    public BreakingPlatform(float x, float y) {
        super(UNUSED.texture, x, y, Platform.WIDTH, Platform.HEIGHT, zeroVelocity);
        this.spawnPosY = y;
    }

    @Override
    public void collideDoodle(Doodler doodler) {
        if (currentState == UNUSED) {
            currentState = DAMAGED;
            bounce(doodler);
            return;
        }

        if (currentState == DAMAGED) {
            currentState = BROKEN;
            bounce(doodler);
            scheduleTask();
        }
    }

    @Override
    public void draw(SpriteBatch batch) {
        batch.draw(currentState.texture, getPosition().x, getPosition().y, getBounds().width, getBounds().height);
    }

    enum State {
        UNUSED(new Texture(files.internal("environment/platform/breaking0.png"))),
        DAMAGED(new Texture(files.internal("environment/platform/breaking1.png"))),
        BROKEN(new Texture(files.internal("environment/platform/breaking3.png"))),
        FALL(new Texture(files.internal("environment/platform/breaking3.png")));

        private final Texture texture;

        State(Texture texture) {
            this.texture = texture;
        }

    }
    @Override
    public TimerTask getTimerTask() {
        return new TimerTask() {
            @Override
            public void run() {
                getVelocity().set(fallVelocity);
                currentState = FALL;
            }
        };
    }

    @Override
    public void move(float deltaTime) {
        if (currentState == FALL) {
            super.move(deltaTime);
        }
    }

    @Override
    public DelayedTaskCollider.State getCurrentState() {
        return switch (currentState) {
            case UNUSED, DAMAGED -> DelayedTaskCollider.State.UNUSED;
            case BROKEN -> DelayedTaskCollider.State.COLLIDED;
            case FALL -> DelayedTaskCollider.State.USED;
        };
    }

    @Override
    public void createTimer() {
        this.timer = new Timer();
    }

    @Override
    public Timer getTimer() {
        return timer;
    }

    @Override
    public long getDelay() {
        return 80;
    }

    public float spawnPosY() {
        return spawnPosY;
    }
}



























