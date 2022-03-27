package dev.lyze.sar;

import com.badlogic.gdx.Game;
import com.kotcrab.vis.ui.VisUI;

public class Main extends Game {
	@Override
	public void create() {
		VisUI.load();
		setScreen(new MainMenuScreen());
	}
}