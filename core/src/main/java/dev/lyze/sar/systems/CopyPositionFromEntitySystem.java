package dev.lyze.sar.systems;

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
        var copyRotation = copyPositionMapper.get(entityId);

        var position = positionMapper.get(entityId);
        var targetPosition = positionMapper.get(copyRotation.getTarget());

        position.getPosition().set(targetPosition.getPosition());
    }
}
