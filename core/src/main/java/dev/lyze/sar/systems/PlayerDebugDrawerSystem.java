package dev.lyze.sar.systems;

import com.artemis.ComponentMapper;
import com.artemis.annotations.All;
import com.artemis.annotations.Wire;
import com.artemis.systems.IteratingSystem;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import dev.lyze.sar.components.PlayerComponent;
import dev.lyze.sar.components.PositionComponent;
import dev.lyze.sar.components.SizeComponent;
import lombok.var;
import space.earlygrey.shapedrawer.ShapeDrawer;

@All({PlayerComponent.class, PositionComponent.class, SizeComponent.class})
public class PlayerDebugDrawerSystem extends IteratingSystem {
    @Wire private ExtendViewport viewport;
    @Wire private ShapeDrawer drawer;

    private ComponentMapper<PlayerComponent> playerMapper;
    private ComponentMapper<PositionComponent> positionMapper;
    private ComponentMapper<SizeComponent> sizeMapper;

    @Override
    protected void begin() {
        drawer.getBatch().setProjectionMatrix(viewport.getCamera().combined);
        drawer.getBatch().begin();
    }

    @Override
    protected void process(int entityId) {
        drawer.setColor(Color.TEAL);

        var player = playerMapper.get(entityId);
        var position = positionMapper.get(entityId);
        var size = sizeMapper.get(entityId);

        drawer.rectangle(position.getX(), position.getY(), size.getWidth(), size.getHeight(), 8);
    }

    @Override
    protected void end() {
        drawer.getBatch().end();
    }
}
