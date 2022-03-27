package dev.lyze.sar;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.kotcrab.vis.ui.VisUI;

public class Main extends Game {
	@Override
	public void create() {
		MusicStuff.init();
		VisUI.load();

		if (Gdx.input.isKeyPressed(Input.Keys.SPACE)) {
			setScreen(new GameScreen(CharacterEnum.LYZE));
			return;
		}

		MusicStuff.getTown().play();
		setScreen(new IntroScreen());
	}
}