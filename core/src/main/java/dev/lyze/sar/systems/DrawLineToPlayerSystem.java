package dev.lyze.sar.systems;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.EntitySubscription;
import com.artemis.annotations.All;
import com.artemis.annotations.Wire;
import com.artemis.systems.IteratingSystem;
import com.badlogic.gdx.math.Vector2;
import dev.lyze.sar.components.DrawLineToPlayerComponent;
import dev.lyze.sar.components.cart.CartComponent;
import dev.lyze.sar.components.movement.PositionComponent;
import dev.lyze.sar.components.player.PlayerOrCartComponent;
import lombok.var;
import space.earlygrey.shapedrawer.ShapeDrawer;

@All({PositionComponent.class, DrawLineToPlayerComponent.class})
public class DrawLineToPlayerSystem extends IteratingSystem {
    @Wire private ShapeDrawer drawer;

    private ComponentMapper<DrawLineToPlayerComponent> lineMapper;
    private ComponentMapper<PositionComponent> positionMapper;

    private EntitySubscription players;

    @Override
    protected void initialize() {
        players = world.getAspectSubscriptionManager().get(Aspect.all(CartComponent.class, PlayerOrCartComponent.class));
    }

    @Override
    protected void process(int entityId) {
        var line = lineMapper.get(entityId);
        var position = positionMapper.get(entityId).getPosition();

        line.setTime(line.getTime() + world.getDelta());

        for (int i = 0; i < players.getEntities().size(); i++) {
            var player = players.getEntities().get(i);
            var playerPos = positionMapper.get(player).getPosition();

            if (line.isFreeze()) {
                if (line.getFreezePos() == null)
                    line.setFreezePos(new Vector2(playerPos));

                playerPos = line.getFreezePos();
            }

            drawer.line(position.x, position.y + line.getOffsetY(), playerPos.x + line.getOffsetX(), playerPos.y + line.getOffsetY(), line.getColor(), line.calculateWidth());
        }
    }
}

