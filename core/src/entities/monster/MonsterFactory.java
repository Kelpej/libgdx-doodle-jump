package entities.monster;

import entities.monster.Monster;
import entities.platform.Platform;

public interface MonsterFactory {

    Monster create(Platform platform);
}
