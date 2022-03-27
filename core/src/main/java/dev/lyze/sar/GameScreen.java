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
import dev.lyze.sar.systems.debug.*;
import dev.lyze.sar.systems.*;
import dev.lyze.sar.systems.player.PlayerDrawerSystem;
import dev.lyze.sar.systems.player.PlayerCartFallStateSystem;
import dev.lyze.sar.systems.player.PlayerCartFollowTrackSystem;
import dev.lyze.sar.systems.sprites.SpriteDrawerSystem;
import dev.lyze.sar.systems.sprites.SpritePositionUpdaterSystem;
import dev.lyze.sar.systems.sprites.SpriteRotationUpdaterSystem;
import dev.lyze.sar.utils.Constants;
import lombok.var;
import space.earlygrey.shapedrawer.ShapeDrawer;

public class GameScreen extends ScreenAdapter {
	private final World world;
	public GameScreen() {
		var builder = new WorldConfigurationBuilder()
				.with(new MapSpawnerSystem())
				.with(new MapEntitySpawner())

				.with(new PlayerCartFallStateSystem())
				.with(new PlayerCartFollowTrackSystem())

				.with(new CameraSystem())

				.with(new MapRenderingSystem())

				.with(new CopyPositionFromEntitySystem())
				.with(new CopyRotationFromEntitySystem())

				.with(new BeginBatchSystem())

				.with(new SpritePositionUpdaterSystem())
				.with(new SpriteRotationUpdaterSystem())
				.with(new SpriteDrawerSystem())

				.with(new TrackDebugDrawerSystem())
				.with(new PositionSizeRotationDebugDrawerSystem())
				.with(new GizmoSystem())

				.with(new EndBatchSystem())

				.build();

		var batch = new SpriteBatch();
		builder.register(new Map("Maps/DevMap.tmx"));
		builder.register(new ExtendViewport(24, 13.5f));
		builder.register(new EventManager());
		builder.register(batch);
		builder.register("constants", new Constants());
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