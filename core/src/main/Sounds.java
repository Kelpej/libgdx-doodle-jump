package main;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;

import java.util.EnumMap;
import java.util.Map;

public final class Sounds {
    private static final Map<GameSound, Sound> gameSounds = new EnumMap<>(GameSound.class);
    private static final Sound TRAMPOLINE_SOUND = Gdx.audio.newSound(Gdx.files.internal("sounds/trampoline.mp3"));
    private static final Sound DOODLER_DEAD_SOUND = Gdx.audio.newSound(Gdx.files.internal("sounds/dead.mp3"));
    private static final Sound PROPELLER_SOUND = Gdx.audio.newSound(Gdx.files.internal("sounds/propeller2.mp3"));
    private static final Sound BOUNCE_SOUND = Gdx.audio.newSound(Gdx.files.internal("sounds/bounce.mp3"));
    private static final Sound MONSTER_DEATH_SOUND = Gdx.audio.newSound(Gdx.files.internal("sounds/monster_death.mp3"));
    private static final Sound SHOT_SOUND = Gdx.audio.newSound(Gdx.files.internal("sounds/shot.mp3"));
    private static final Sound CLICK_SOUND = Gdx.audio.newSound(Gdx.files.internal("sounds/retro_click.wav"));

    static {
        gameSounds.put(GameSound.PLATFORM_BOUNCE, BOUNCE_SOUND);
        gameSounds.put(GameSound.SHOOT, SHOT_SOUND);
        gameSounds.put(GameSound.DOODLER_DEAD, DOODLER_DEAD_SOUND);
        gameSounds.put(GameSound.MONSTER_DEAD, MONSTER_DEATH_SOUND);
        gameSounds.put(GameSound.PROPELLER, PROPELLER_SOUND);
        gameSounds.put(GameSound.TRAMPOLINE, TRAMPOLINE_SOUND);
        gameSounds.put(GameSound.CLICK, CLICK_SOUND);
    }

    private Sounds() {

    }

    public static void playSound(GameSound sound) {
        Sound actualSound = gameSounds.get(sound);
        actualSound.stop();
        actualSound.play();
    }
}
