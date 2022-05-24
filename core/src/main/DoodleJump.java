package main;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.ScreenUtils;

public class DoodleJump extends Game {
	GameScreen gameScreen;
	World gameScene;

	@Override
	public void create () {
		gameScreen = new GameScreen();
		setScreen(gameScreen);
//		initializing game world with setting gravity to -10
		gameScene = new World(new Vector2(0, -10), true);
	}

	@Override
	public void render () {
		ScreenUtils.clear(0, 0, 0, 1);
		gameScreen.render(0);
	}
	
	@Override
	public void dispose () {
		super.dispose();
		gameScreen.dispose();
	}
}
