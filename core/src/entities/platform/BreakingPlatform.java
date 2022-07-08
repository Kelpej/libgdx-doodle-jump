package entities.platform;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import entities.Doodler;
import entities.GameObject;

public class BreakingPlatform extends GameObject implements Platform {

    private static final Texture TEXTURE = new Texture(Gdx.files.internal("environment/platform/breaking0.png"));
    private static final Texture TEXTURE2 = new Texture(Gdx.files.internal("environment/platform/breaking1.png"));
    private static final Texture TEXTURE3 = new Texture(Gdx.files.internal("environment/platform/breaking3.png"));


    private Texture currentTexture = TEXTURE;

    public BreakingPlatform(float x, float y) {
        super(TEXTURE, x, y, Platform.PLATFORM_WIDTH, Platform.PLATFORM_HEIGHT);
    }

    @Override
    public void collideDoodle(Doodler doodler) {
        currentTexture = TEXTURE2;
        new java.util.Timer().schedule(
                new java.util.TimerTask() {
                    @Override
                    public void run() {
                        currentTexture = TEXTURE3;
                    }
                },
                100
        );
    }

    @Override
    public void draw(SpriteBatch batch) {
        batch.draw(currentTexture, getPosition().x, getPosition().y, getBounds().width, getBounds().height);
    }
}
