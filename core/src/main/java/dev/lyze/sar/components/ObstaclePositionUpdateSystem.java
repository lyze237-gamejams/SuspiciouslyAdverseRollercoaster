package dev.lyze.sar.components;

import com.artemis.ComponentMapper;
import com.artemis.annotations.All;
import com.artemis.systems.IteratingSystem;
import dev.lyze.sar.components.movement.PositionComponent;
import lombok.var;

@All({ObstacleComponent.class, PositionComponent.class})
public class ObstaclePositionUpdateSystem extends IteratingSystem {
    private ComponentMapper<ObstacleComponent> obstacleMapper;
    private ComponentMapper<PositionComponent> positionMapper;

    @Override
    protected void process(int entityId) {
        var position = positionMapper.get(entityId).getPosition();
        var obstacle = obstacleMapper.get(entityId).getPolygon();

        obstacle.setPosition(position.x, position.y);
    }
}
