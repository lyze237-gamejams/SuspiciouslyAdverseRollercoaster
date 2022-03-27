package dev.lyze.sar.systems.copy;

import com.artemis.ComponentMapper;
import com.artemis.annotations.All;
import com.artemis.systems.IteratingSystem;
import dev.lyze.sar.components.CopyRotationFromEntityComponent;
import dev.lyze.sar.components.movement.RotationComponent;
import lombok.var;

@All({CopyRotationFromEntityComponent.class, RotationComponent.class})
public class CopyRotationFromEntitySystem extends IteratingSystem {
    private ComponentMapper<CopyRotationFromEntityComponent> copyRotationMapper;
    private ComponentMapper<RotationComponent> rotationMapper;

    @Override
    protected void process(int entityId) {
        var copyRotation = copyRotationMapper.get(entityId);

        var target = copyRotation.getTarget();

        if (!rotationMapper.has(target)) {
            world.delete(entityId);
            return;
        }

        var rotation = rotationMapper.get(entityId);
        var targetRotation = rotationMapper.get(target);

        rotation.setRotation(targetRotation.getRotation());
    }
}
