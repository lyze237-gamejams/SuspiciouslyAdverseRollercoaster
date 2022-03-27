package dev.lyze.sar.systems.player;

import com.artemis.ComponentMapper;
import com.artemis.annotations.All;
import com.artemis.systems.IteratingSystem;
import dev.lyze.sar.components.movement.PositionComponent;
import dev.lyze.sar.components.movement.RotationComponent;
import dev.lyze.sar.components.player.PlayerJumpStateComponent;
import lombok.var;

@All({PlayerJumpStateComponent.class, PositionComponent.class, RotationComponent.class})
public class ApplyJumpStateOffsetSystem extends IteratingSystem {
    private ComponentMapper<PlayerJumpStateComponent> jumpStateMapper;
    private ComponentMapper<PositionComponent> positionMapper;
    private ComponentMapper<RotationComponent> rotationMapper;

    @Override
    protected void process(int entityId) {
        var jumpState = jumpStateMapper.get(entityId);
        var position = positionMapper.get(entityId).getPosition();
        var rotation = rotationMapper.get(entityId).getRotation();

        position.add(0, (float) (jumpState.calculateOffset() * Math.cos(rotation)));
    }
}
