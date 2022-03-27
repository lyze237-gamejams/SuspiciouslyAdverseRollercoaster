package dev.lyze.sar.systems.sprites;

import com.artemis.Aspect;
import com.artemis.BaseSystem;
import com.artemis.ComponentMapper;
import com.artemis.EntitySubscription;
import com.artemis.annotations.Wire;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Array;
import dev.lyze.sar.components.SpriteComponent;
import lombok.var;

import java.util.Comparator;

public class SpriteDrawerSystem extends BaseSystem {
    private final Array<SpriteComponent> orderArray = new Array<>();

    private ComponentMapper<SpriteComponent> spriteMapper;
    private EntitySubscription sprites;

    @Wire private SpriteBatch batch;

    @Override
    protected void initialize() {
        super.initialize();

        sprites = world.getAspectSubscriptionManager().get(Aspect.all(SpriteComponent.class));
    }

    @Override
    protected void processSystem() {
        orderArray.clear();

        for (int i = 0; i < sprites.getEntities().size(); i++) {
            orderArray.add(spriteMapper.get(sprites.getEntities().get(i)));
        }

        orderArray.sort(Comparator.comparingInt(SpriteComponent::getOrder));

        orderArray.forEach(this::processEntity);
    }

    private void processEntity(SpriteComponent sprite) {
        sprite.getSprite().draw(batch);
    }
}
