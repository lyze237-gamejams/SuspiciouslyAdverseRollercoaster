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
public class VictorySystem extends IteratingSystem {
    private final Color color = new Color(Color.CLEAR);

    @Wire private Map map;
    @Wire private ShapeDrawer drawer;

    @Wire private ExtendViewport viewport;

    @Wire(name = "constants") private Constants constants;

    private ComponentMapper<PositionComponent> positionMapper;

    private float time = 0;

    @Override
    protected void process(int entityId) {
        var position = positionMapper.get(entityId).getPosition();

        if (!(time > 0) && !map.getVictoryRectangle().contains(position))
            return;

        time += world.delta;

        var cameraPosition = viewport.getCamera().position;
        float winTime = 1.5f;

        color.a = MathUtils.map(0, winTime, 0, 1, Math.min(time, winTime));
        drawer.setColor(color);
        drawer.filledRectangle(cameraPosition.x - viewport.getWorldWidth() / 2f, cameraPosition.y - viewport.getWorldHeight() / 2f, viewport.getWorldWidth(), viewport.getWorldHeight());

        if (time < winTime)
            return;

        ((Game) Gdx.app.getApplicationListener()).setScreen(new GameScreen(constants.getCharacter(), constants.getLevelIndex() + 1));
    }
}
