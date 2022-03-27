package dev.lyze.sar;

import com.badlogic.gdx.Game;
import com.kotcrab.vis.ui.VisUI;

public class Main extends Game {
	@Override
	public void create() {
		MusicStuff.init();
		VisUI.load();
		setScreen(new GameScreen(CharacterEnum.LYZE));
		//MusicStuff.getTown().play();
		//setScreen(new IntroScreen());
	}
}