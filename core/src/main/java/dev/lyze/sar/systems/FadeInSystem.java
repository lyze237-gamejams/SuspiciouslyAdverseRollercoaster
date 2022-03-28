package dev.lyze.sar.systems;

import com.artemis.ComponentMapper;
import com.artemis.annotations.All;
import com.artemis.annotations.Wire;
import com.artemis.systems.IteratingSystem;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import dev.lyze.sar.GameScreen;
import dev.lyze.sar.components.movement.PositionComponent;
import dev.lyze.sar.components.player.PlayerComponent;
import dev.lyze.sar.systems.map.Map;
import dev.lyze.sar.utils.Constants;
import lombok.var;
import space.earlygrey.shapedrawer.ShapeDrawer;

@All(PlayerComponent.class)
public class FadeInSystem extends IteratingSystem {
    private final Color color = new Color(Color.CLEAR);

    @Wire private ShapeDrawer drawer;
    @Wire private ExtendViewport viewport;
    @Wire(name = "constants") private Constants constants;

    private float time = 0;

    @Override
    protected void process(int entityId) {
        time += world.delta;

        var cameraPosition = viewport.getCamera().position;
        float fadeTime = 1.5f;

        color.a = MathUtils.map(0, fadeTime, 1, 0, Math.min(time, fadeTime));
        drawer.setColor(color);
        drawer.filledRectangle(cameraPosition.x - viewport.getWorldWidth() / 2f, cameraPosition.y - viewport.getWorldHeight() / 2f, viewport.getWorldWidth(), viewport.getWorldHeight());

        if (time > fadeTime)
            return;
    }
}
