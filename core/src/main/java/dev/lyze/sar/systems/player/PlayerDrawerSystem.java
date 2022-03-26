package dev.lyze.sar.systems.player;

import com.artemis.ComponentMapper;
import com.artemis.annotations.All;
import com.artemis.annotations.Wire;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import dev.lyze.sar.components.movement.*;
import dev.lyze.sar.components.player.PlayerComponent;
import dev.lyze.sar.components.player.PlayerConstants;
import dev.lyze.sar.components.player.PlayerFallStateComponent;
import dev.lyze.sar.components.player.PlayerFollowTrackComponent;
import dev.lyze.sar.eventsystem.EventManager;
import dev.lyze.sar.utils.Constants;
import lombok.var;

@All({PlayerComponent.class})
public class PlayerDrawerSystem extends PlayerAbstractSystem {
    @Wire private SpriteBatch batch;
    @Wire private EventManager eventManager;
    @Wire private Constants constants;

    private ComponentMapper<PlayerFallStateComponent> playerFallStateMapper;
    private ComponentMapper<PlayerFollowTrackComponent> playerFollowTrackMapper;

    private Sprite minecartBackSprite, minecartFrontSprite, playerSprite;
    private Animation<TextureAtlas.AtlasRegion> currentAnimation;
    private float animationTime;

    @Override
    protected void initialize() {
        setupMinecart();

        setAnimation(constants.getPlayerIdle());
        playerSprite = new Sprite(currentAnimation.getKeyFrame(0));
        playerSprite.setOrigin(playerSprite.getWidth() / 2f, 0);
    }

    private void setupMinecart() {
        minecartBackSprite = new Sprite(constants.getMain().findRegion("Misc/Minecart_back"));
        minecartFrontSprite = new Sprite(constants.getMain().findRegion("Misc/Minecart_front"));

        minecartBackSprite.setOrigin(minecartBackSprite.getWidth() / 2f, 0);
        minecartFrontSprite.setOrigin(minecartFrontSprite.getWidth() / 2f, 0);
    }

    @Override
    protected void begin() {
        animationTime += world.getDelta();
    }

    @Override
    protected void process(int entityId, PlayerComponent player, PlayerConstants playerConstants, PositionComponent position, SizeComponent size, RotationComponent rotation, VelocityComponent velocity, GravityComponent gravity) {
        updatePlayerAnimation(entityId);
        playerSprite.setRegion(currentAnimation.getKeyFrame(animationTime));
        setSpritePositionAndRotation(entityId, position.getPosition(), rotation.getRotation(), size);

        minecartBackSprite.draw(batch);
        playerSprite.draw(batch);
        minecartFrontSprite.draw(batch);
    }

    private void updatePlayerAnimation(int entityId) {
        if (playerFollowTrackMapper.has(entityId)) {
            setAnimation(constants.getPlayerIdle());
        } else if (playerFallStateMapper.has(entityId)) {
            var velocity = velocityMapper.get(entityId).getVelocity();
            if (velocity.y > 0) {
                setAnimation(constants.getPlayerJump());
            } else {
                setAnimation(constants.getPlayerFall());
            }
        }
    }

    private void setSpritePositionAndRotation(int entityId, Vector2 position, float rotation, SizeComponent size) {
        float scale = 1 / 600f * 3;
        minecartBackSprite.setScale(scale);
        playerSprite.setScale(scale);
        minecartFrontSprite.setScale(scale);

        minecartBackSprite.setOriginBasedPosition(position.x, position.y);
        playerSprite.setOriginBasedPosition(position.x, position.y);
        minecartFrontSprite.setOriginBasedPosition(position.x, position.y);

        minecartBackSprite.setRotation(rotation * MathUtils.radiansToDegrees);
        playerSprite.setRotation(rotation * MathUtils.radiansToDegrees);
        minecartFrontSprite.setRotation(rotation * MathUtils.radiansToDegrees);
    }

    protected void setAnimation(Animation<TextureAtlas.AtlasRegion> newAnimation) {
        if (this.currentAnimation == newAnimation)
            return;

        if (newAnimation == null)
            return;

        this.currentAnimation = newAnimation;
        animationTime = 0;
    }
}
