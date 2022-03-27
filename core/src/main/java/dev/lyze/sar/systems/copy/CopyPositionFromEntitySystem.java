package dev.lyze.sar.systems.copy;

import com.artemis.ComponentMapper;
import com.artemis.annotations.All;
import com.artemis.systems.IteratingSystem;
import dev.lyze.sar.components.CopyPositionFromEntityComponent;
import dev.lyze.sar.components.movement.PositionComponent;
import lombok.var;

@All({CopyPositionFromEntityComponent.class, PositionComponent.class})
public class CopyPositionFromEntitySystem extends IteratingSystem {
    private ComponentMapper<CopyPositionFromEntityComponent> copyPositionMapper;
    private ComponentMapper<PositionComponent> positionMapper;

    @Override
    protected void process(int entityId) {
        var copyPosition = copyPositionMapper.get(entityId);

        var target = copyPosition.getTarget();

        if (!positionMapper.has(target)) {
            world.delete(entityId);
            return;
        }

        var position = positionMapper.get(entityId).getPosition();
        var targetPosition = positionMapper.get(target).getPosition();

        position.set(targetPosition.x + copyPosition.getOffsetX(), targetPosition.y + copyPosition.getOffsetY());
    }
}
