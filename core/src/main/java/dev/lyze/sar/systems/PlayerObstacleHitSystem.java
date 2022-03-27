package dev.lyze.sar.systems;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.EntitySubscription;
import com.artemis.annotations.All;
import com.artemis.systems.IteratingSystem;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Polygon;
import dev.lyze.sar.GameScreen;
import dev.lyze.sar.components.HitboxComponent;
import dev.lyze.sar.components.ObstacleComponent;
import dev.lyze.sar.components.movement.PositionComponent;
import dev.lyze.sar.components.movement.RotationComponent;
import dev.lyze.sar.components.player.PlayerOrCartComponent;
import dev.lyze.sar.utils.RectangleUtils;
import lombok.var;

@All({PlayerOrCartComponent.class, PositionComponent.class, RotationComponent.class, HitboxComponent.class})
public class PlayerObstacleHitSystem extends IteratingSystem {
    private final Polygon playerPolygon = new Polygon(new float[8]);

    private ComponentMapper<PositionComponent> positionMapper;
    private ComponentMapper<RotationComponent> rotationMapper;
    private ComponentMapper<HitboxComponent> hitboxMapper;

    private ComponentMapper<ObstacleComponent> obstacleMapper;

    private EntitySubscription obstalces;

    @Override
    protected void initialize() {
        obstalces = world.getAspectSubscriptionManager().get(Aspect.all(ObstacleComponent.class));
    }

    @Override
    protected void process(int entityId) {
        var position = positionMapper.get(entityId).getPosition();
        var rotation = rotationMapper.get(entityId).getRotation();
        var hitbox = hitboxMapper.get(entityId);

        RectangleUtils.toPolygon(position.x, position.y, hitbox.getWidth(), hitbox.getHeight(), rotation, playerPolygon);

        for (int i = 0; i < obstalces.getEntities().size(); i++) {
            var obstaclePolygon = obstacleMapper.get(obstalces.getEntities().get(i)).getPolygon();

            if (Intersector.overlapConvexPolygons(playerPolygon, obstaclePolygon)) {
                ((Game) Gdx.app.getApplicationListener()).setScreen(new GameScreen());
            }
        }
    }
}
