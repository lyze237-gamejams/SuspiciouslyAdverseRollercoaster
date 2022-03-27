package dev.lyze.sar.systems;

import com.artemis.ComponentMapper;
import com.artemis.annotations.All;
import com.artemis.systems.IteratingSystem;
import dev.lyze.sar.components.HackLightComponent;
import dev.lyze.sar.components.movement.PositionComponent;
import lombok.var;

@All({HackLightComponent.class, PositionComponent.class})
public class HackLightPositionUpdateSystem extends IteratingSystem {
    private ComponentMapper<HackLightComponent> hackLightMapper;
    private ComponentMapper<PositionComponent> positionMapper;

    @Override
    protected void process(int entityId) {
        var position = positionMapper.get(entityId).getPosition();
        var light = hackLightMapper.get(entityId).getLight();

        light.setOriginBasedPosition(position.x, position.y);
    }
}
