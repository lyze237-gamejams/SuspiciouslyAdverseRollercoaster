package dev.lyze.sar.systems.player;

import com.artemis.ComponentMapper;
import com.artemis.annotations.All;
import com.artemis.annotations.Wire;
import com.artemis.systems.IteratingSystem;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import dev.lyze.sar.components.movement.PositionComponent;
import dev.lyze.sar.components.movement.RotationComponent;
import dev.lyze.sar.components.movement.SizeComponent;
import dev.lyze.sar.components.player.PlayerComponent;
import dev.lyze.sar.components.player.PlayerFallStateComponent;
import dev.lyze.sar.eventsystem.EventManager;
import lombok.var;

@All({PlayerComponent.class, PositionComponent.class, RotationComponent.class, SizeComponent.class})
public class PlayerDrawerSystem extends IteratingSystem {
    @Wire private SpriteBatch batch;
    @Wire private EventManager eventManager;
    @Wire private ExtendViewport viewport;
    @Wire private TextureAtlas mainAtlas;

    private ComponentMapper<PositionComponent> positionMapper;
    private ComponentMapper<RotationComponent> rotationMapper;
    private ComponentMapper<SizeComponent> sizeMapper;

    private Sprite minecartBackSprite, minecartFrontSprite, player;
    private TextureAtlas.AtlasRegion crouch, fall, idle, jump, landing;

    @Override
    protected void initialize() {
        setupMinecart();

        crouch = mainAtlas.findRegion("Fox/Crouch");
        fall = mainAtlas.findRegion("Fox/Fall");
        idle = mainAtlas.findRegion("Fox/Idle");
        jump = mainAtlas.findRegion("Fox/Jump");
        landing = mainAtlas.findRegion("Fox/Landing");

        player = new Sprite(idle);
        player.setOrigin(player.getWidth() / 2f, 0);
    }

    private void setupMinecart() {
        minecartBackSprite = new Sprite(mainAtlas.findRegion("Misc/Minecart_back"));
        minecartFrontSprite = new Sprite(mainAtlas.findRegion("Misc/Minecart_front"));

        minecartBackSprite.setOrigin(minecartBackSprite.getWidth() / 2f, 0);
        minecartFrontSprite.setOrigin(minecartFrontSprite.getWidth() / 2f, 0);
    }

    @Override
    protected void begin() {
        viewport.apply();

        batch.setProjectionMatrix(viewport.getCamera().combined);
        batch.begin();
    }

    @Override
    protected void process(int entityId) {
        var position = positionMapper.get(entityId).getPosition();
        var size = sizeMapper.get(entityId);
        var rotation = rotationMapper.get(entityId).getRotation();

        float scale = 1 / 600f * 3;
        minecartBackSprite.setScale(scale);
        player.setScale(scale);
        minecartFrontSprite.setScale(scale);

        minecartBackSprite.setOriginBasedPosition(position.x, position.y);
        player.setOriginBasedPosition(position.x, position.y);
        minecartFrontSprite.setOriginBasedPosition(position.x, position.y);

        minecartBackSprite.setRotation(rotation * MathUtils.radiansToDegrees);
        player.setRotation(rotation * MathUtils.radiansToDegrees);
        minecartFrontSprite.setRotation(rotation * MathUtils.radiansToDegrees);

        minecartBackSprite.draw(batch);
        player.draw(batch);
        minecartFrontSprite.draw(batch);
    }

    @Override
    protected void end() {
        batch.end();
    }
}
