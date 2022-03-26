package dev.lyze.sar.systems.player;

import com.artemis.ComponentMapper;
import com.artemis.annotations.All;
import com.artemis.annotations.Wire;
import com.artemis.systems.IteratingSystem;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import dev.lyze.sar.components.movement.PositionComponent;
import dev.lyze.sar.components.movement.RotationComponent;
import dev.lyze.sar.components.movement.SizeComponent;
import dev.lyze.sar.components.movement.VelocityComponent;
import dev.lyze.sar.components.player.PlayerComponent;
import dev.lyze.sar.components.player.PlayerFallStateComponent;
import dev.lyze.sar.components.player.PlayerFollowTrackComponent;
import dev.lyze.sar.eventsystem.EventManager;
import dev.lyze.sar.utils.Constants;
import lombok.var;

@All({PlayerComponent.class, PositionComponent.class, RotationComponent.class, SizeComponent.class, VelocityComponent.class})
public class PlayerDrawerSystem extends IteratingSystem {
    @Wire private SpriteBatch batch;
    @Wire private EventManager eventManager;
    @Wire private Constants constants;

    private ComponentMapper<PositionComponent> positionMapper;
    private ComponentMapper<RotationComponent> rotationMapper;
    private ComponentMapper<VelocityComponent> velocityMapper;
    private ComponentMapper<SizeComponent> sizeMapper;

    private ComponentMapper<PlayerFallStateComponent> playerFallStateMapper;
    private ComponentMapper<PlayerFollowTrackComponent> playerFollowTrackMapper;

    private Sprite minecartBackSprite, minecartFrontSprite, player;
    private Animation<TextureAtlas.AtlasRegion> crouch, fall, idle, jump, landing, currentAnimation;
    private float animationTime;

    @Override
    protected void initialize() {
        setupMinecart();

        crouch = new Animation<>(0.1f, constants.getMain().findRegions("Fox/Crouch"), Animation.PlayMode.NORMAL);
        fall = new Animation<>(0.1f, constants.getMain().findRegions("Fox/Fall"), Animation.PlayMode.NORMAL);
        idle = new Animation<>(0.1f, constants.getMain().findRegions("Fox/Idle"), Animation.PlayMode.LOOP);
        jump = new Animation<>(0.1f, constants.getMain().findRegions("Fox/Jump"), Animation.PlayMode.NORMAL);
        landing = new Animation<>(0.1f, constants.getMain().findRegions("Fox/Landing"), Animation.PlayMode.NORMAL);

        setAnimation(idle);
        player = new Sprite(currentAnimation.getKeyFrame(0));
        player.setOrigin(player.getWidth() / 2f, 0);
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
    protected void process(int entityId) {
        updatePlayerAnimation(entityId);
        player.setRegion(currentAnimation.getKeyFrame(animationTime));
        setSpritePositionAndRotation(entityId);

        minecartBackSprite.draw(batch);
        player.draw(batch);
        minecartFrontSprite.draw(batch);
    }

    private void updatePlayerAnimation(int entityId) {

        if (playerFollowTrackMapper.has(entityId)) {
            setAnimation(idle);
        } else if (playerFallStateMapper.has(entityId)) {
            var velocity = velocityMapper.get(entityId).getVelocity();
            if (velocity.y > 0) {
                setAnimation(jump);
            } else {
                setAnimation(fall);
            }
        }
    }

    private void setSpritePositionAndRotation(int entityId) {
        var rotation = rotationMapper.get(entityId).getRotation();
        var position = positionMapper.get(entityId).getPosition();
        var size = sizeMapper.get(entityId);

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
