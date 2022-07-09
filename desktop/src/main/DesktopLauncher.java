package main;

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import main.ui.screen.DoodleJumpScreen;

public class DesktopLauncher {
	public static void main (String[] arg) {
		Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
		config.setWindowedMode((int) DoodleJumpScreen.WIDTH, (int) DoodleJumpScreen.HEIGHT);
		config.setForegroundFPS(60);
		config.setTitle("Doodle Jump");
		new Lwjgl3Application(new DoodleJump(), config);
	}
}
