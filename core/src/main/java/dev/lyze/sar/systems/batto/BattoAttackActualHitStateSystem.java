package dev.lyze.sar.systems.batto;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.EntitySubscription;
import com.artemis.annotations.All;
import com.artemis.annotations.Wire;
import com.artemis.systems.IteratingSystem;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import dev.lyze.sar.components.AnimatableSpriteComponent;
import dev.lyze.sar.components.BattoComponent;
import dev.lyze.sar.components.DrawLineToPlayerComponent;
import dev.lyze.sar.components.HitboxComponent;
import dev.lyze.sar.components.batto.BattoAttackActualHitComponent;
import dev.lyze.sar.components.batto.BattoIdleComponent;
import dev.lyze.sar.components.cart.CartComponent;
import dev.lyze.sar.components.movement.PositionComponent;
import dev.lyze.sar.components.movement.RotationComponent;
import dev.lyze.sar.components.player.PlayerComponent;
import dev.lyze.sar.components.player.PlayerOrCartComponent;
import dev.lyze.sar.eventsystem.EventManager;
import dev.lyze.sar.utils.Constants;
import dev.lyze.sar.utils.RectangleUtils;
import lombok.var;

@All({BattoComponent.class, PositionComponent.class, AnimatableSpriteComponent.class, BattoAttackActualHitComponent.class})
public class BattoAttackActualHitStateSystem extends IteratingSystem {
    private final Polygon polygon = new Polygon(new float[8]);

    @Wire private EventManager eventManager;
    @Wire private ExtendViewport viewport;
    @Wire(name = "constants") private Constants constants;

    private ComponentMapper<PositionComponent> positionMapper;
    private ComponentMapper<AnimatableSpriteComponent> spriteMapper;
    private ComponentMapper<RotationComponent> rotationMapper;
    private ComponentMapper<HitboxComponent> hitboxMapper;

    private EntitySubscription players;

    @Override
    protected void initialize() {
        players = world.getAspectSubscriptionManager().get(Aspect.all(PlayerComponent.class));
    }

    @Override
    protected void process(int entityId) {
        var position = positionMapper.get(entityId).getPosition();

        for (int i = 0; i < players.getEntities().size(); i++) {
            var player = players.getEntities().get(i);
            var playerPos = positionMapper.get(player).getPosition();
            var rotation = rotationMapper.get(player).getRotation();
            var hitbox = hitboxMapper.get(player);

            RectangleUtils.toPolygon(position.x, position.y, hitbox.getWidth(), hitbox.getHeight(), rotation, polygon);

            if (Intersector.intersectLinePolygon(new Vector2(position.x, position.y + 0.5f), new Vector2(playerPos.x + 0.5f, playerPos.y + 0.5f), polygon)) {
                System.out.println("HIT");
            }
        }

        world.edit(entityId)
                .remove(BattoAttackActualHitComponent.class)
                .remove(DrawLineToPlayerComponent.class)
                .add(new BattoIdleComponent());
    }
}
