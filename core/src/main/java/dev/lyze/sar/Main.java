package dev.lyze.sar;

import com.badlogic.gdx.Game;
import com.kotcrab.vis.ui.VisUI;

public class Main extends Game {
	@Override
	public void create() {
		MusicStuff.init();
		VisUI.load();
		MusicStuff.getTown().play();

		setScreen(new IntroScreen());
	}
}