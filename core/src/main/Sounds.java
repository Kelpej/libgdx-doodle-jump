package main;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;

public class Sounds {
    public static void dead() {
        Sound sound = Gdx.audio.newSound(Gdx.files.internal("sounds/dead.mp3"));
        sound.play();
    }

    public static void trampoline() {
        Sound sound = Gdx.audio.newSound(Gdx.files.internal("sounds/trampoline.mp3"));
        sound.play();
    }

    public static void propeller() {
        Sound sound = Gdx.audio.newSound(Gdx.files.internal("sounds/propeller.mp3"));
        sound.play();
    }

    public static void bounce() {
        Sound sound = Gdx.audio.newSound(Gdx.files.internal("sounds/bounce.mp3"));
        sound.play();
    }

    public static void monsterDeath() {
        Sound sound = Gdx.audio.newSound(Gdx.files.internal("sounds/monster_death.mp3"));
        sound.play();
    }

    public static void shot() {
        Sound sound = Gdx.audio.newSound(Gdx.files.internal("sounds/shot.mp3"));
        sound.play();
    }
}
