package main;

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;

public class DesktopLauncher {
	public static void main (String[] arg) {
		Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
		config.setWindowedMode(400, 800);
		config.setForegroundFPS(30);
		config.setTitle("Doodle Jump");
		new Lwjgl3Application(new DoodleJump(), config);
	}
}
