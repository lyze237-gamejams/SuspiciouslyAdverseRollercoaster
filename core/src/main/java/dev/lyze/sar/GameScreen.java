package dev.lyze.sar;

import com.artemis.World;
import com.artemis.WorldConfigurationBuilder;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import dev.lyze.sar.eventsystem.EventManager;
import dev.lyze.sar.eventsystem.events.ResizeEvent;
import dev.lyze.sar.systems.debug.GizmoSystem;
import dev.lyze.sar.systems.*;
import dev.lyze.sar.systems.debug.PlayerDebugDrawerSystem;
import dev.lyze.sar.systems.debug.PositionSizeRotationDebugDrawerSystem;
import dev.lyze.sar.systems.debug.TrackDebugDrawerSystem;
import dev.lyze.sar.systems.player.PlayerFallStateSystem;
import dev.lyze.sar.systems.player.PlayerFollowTrackSystem;
import lombok.var;
import space.earlygrey.shapedrawer.ShapeDrawer;

public class GameScreen extends ScreenAdapter {
	private final World world;
	public GameScreen() {
		var builder = new WorldConfigurationBuilder()
				.with(new MapSpawnerSystem())
				.with(new MapEntitySpawner())

				.with(new PlayerFallStateSystem())
				.with(new PlayerFollowTrackSystem())

				.with(new CameraSystem())

				.with(new MapRenderingSystem())

				.with(new TrackDebugDrawerSystem())
				.with(new PositionSizeRotationDebugDrawerSystem())
				.with(new PlayerDebugDrawerSystem())
				.with(new GizmoSystem())
				.build();

		var batch = new SpriteBatch();
		builder.register(new Map("Maps/DevMap.tmx"));
		builder.register(new ExtendViewport(24, 13.5f));
		builder.register(new EventManager());
		builder.register(batch);
		builder.register(new ShapeDrawer(batch, new TextureRegion(new Texture("Pixel.png"))));

		world = new World(builder);
	}

	@Override
	public void render(float delta) {
		ScreenUtils.clear(Color.BLACK);

		world.setDelta(delta);
		world.process();
	}

	@Override
	public void resize(int width, int height) {
		world.getInjector().getRegistered(EventManager.class).fire(new ResizeEvent(width, height));
	}
}