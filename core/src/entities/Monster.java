package entities;

import com.badlogic.gdx.graphics.Texture;

public class Monster {
    private final Texture texture;
    private final double speed;

    private float x, y;
    private float width, height;

    public Monster(float x, float y) {
        this.x = x;
        this.y = y;
        var random = new Random();
//        setting a monster random texture from assets
        this.texture = switch (random.nextInt(0, 4)) {
            case 0 -> new Texture("monsters/monster-1.png");
            case 1 -> new Texture("monsters/monster-2.png");
            case 2 -> new Texture("monsters/monster-3.png");
            case 3 -> new Texture("monsters/monster-4.png");
            case 4 -> new Texture("monsters/monster-5.png");
            default -> throw new IllegalStateException("Unexpected value: " + random.nextInt(0, 4));
        };

        this.width = texture.getWidth();
        this.height = texture.getHeight();
        this.speed = random.nextDouble(0.5, 2);
    }

    public void draw(SpriteBatch batch) {
        batch.draw(texture, x, y, width, height);
    }
}