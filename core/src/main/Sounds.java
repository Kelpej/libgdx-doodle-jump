package main;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;

public final class Sounds {

    private Sounds() {

    }

    private static final Sound TRAMPOLINE = Gdx.audio.newSound(Gdx.files.internal("sounds/trampoline.mp3"));
    private static final Sound DEAD = Gdx.audio.newSound(Gdx.files.internal("sounds/dead.mp3"));
    private static final Sound PROPELLER = Gdx.audio.newSound(Gdx.files.internal("sounds/propeller.mp3"));
    private static final Sound BOUNCE = Gdx.audio.newSound(Gdx.files.internal("sounds/bounce.mp3"));
    private static final Sound MONSTER_DEATH = Gdx.audio.newSound(Gdx.files.internal("sounds/monster_death.mp3"));

    private static final Sound SHOT = Gdx.audio.newSound(Gdx.files.internal("sounds/shot.mp3"));

    public static void dead() {
        DEAD.play();
    }

    public static void trampoline() {
        TRAMPOLINE.play();
    }

    public static void propeller() {
        PROPELLER.play();
    }

    public static void bounce() {
        BOUNCE.play();
    }

    public static void monsterDeath() {
        MONSTER_DEATH.play();
    }

    public static void shot() {
        SHOT.play();
    }
}
