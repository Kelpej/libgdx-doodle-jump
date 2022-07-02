package entities.monster;

import entities.platform.Platform;

public interface MonsterFactory {

    Monster create(Platform platform);
}
