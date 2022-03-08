package dev.lyze.sar.systems.debug;

import com.artemis.ComponentMapper;
import com.artemis.annotations.All;
import com.artemis.annotations.Wire;
import com.artemis.systems.IteratingSystem;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import dev.lyze.sar.components.movement.PositionComponent;
import dev.lyze.sar.components.movement.RotationComponent;
import dev.lyze.sar.components.movement.SizeComponent;
import lombok.var;
import space.earlygrey.shapedrawer.ShapeDrawer;

@All({PositionComponent.class, RotationComponent.class, SizeComponent.class})
public class PositionSizeRotationDebugDrawerSystem extends IteratingSystem {
    @Wire private ExtendViewport viewport;
    @Wire private ShapeDrawer drawer;

    private ComponentMapper<PositionComponent> positionMapper;
    private ComponentMapper<RotationComponent> rotationMapper;
    private ComponentMapper<SizeComponent> sizeMapper;

    @Override
    protected void begin() {
        drawer.getBatch().setProjectionMatrix(viewport.getCamera().combined);
        drawer.getBatch().begin();
    }

    @Override
    protected void process(int entityId) {
        drawer.setColor(Color.TEAL);

        var position = positionMapper.get(entityId);
        var size = sizeMapper.get(entityId);
        var rotation = rotationMapper.get(entityId);

        drawer.rectangle(position.getPosition().x - size.getWidth() / 2f, position.getPosition().y, size.getWidth(), size.getHeight(), 0.1f, rotation.getRotation());
    }

    @Override
    protected void end() {
        drawer.getBatch().end();
    }
}
