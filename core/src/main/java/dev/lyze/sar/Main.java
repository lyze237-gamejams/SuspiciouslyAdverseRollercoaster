package dev.lyze.sar;

import com.badlogic.gdx.Game;
import lombok.SneakyThrows;

public class Main extends Game {
	@SneakyThrows
	@Override
	public void create() {
		setScreen(new GameScreen());
	}
}