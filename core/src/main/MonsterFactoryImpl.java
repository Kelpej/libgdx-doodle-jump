package main;

import entities.Monster;
import entities.MonsterFactory;
import com.badlogic.gdx.graphics.Texture;

import java.util.*;

public class MonsterFactoryImpl implements MonsterFactory {

    private final List<Texture> textures = new ArrayList<>();
    private final Random random = new Random();

    MonsterFactoryImpl() {
        textures.add(new Texture("monsters/monster-1.png"));
        textures.add(new Texture("monsters/monster-2.png"));
        textures.add(new Texture("monsters/monster-3.png"));
        textures.add(new Texture("monsters/monster-4.png"));
        textures.add(new Texture("monsters/monster-5.png"));
    }

    @Override
    public Monster create(float x, float y) {
        Texture texture = textures.get(random.nextInt(0, textures.size()));
        return new Monster(texture, x, y);
    }
}
