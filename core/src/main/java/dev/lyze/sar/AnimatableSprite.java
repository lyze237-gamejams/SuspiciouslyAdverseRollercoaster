package dev.lyze.sar;

import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.utils.Array;
import lombok.Getter;

public class AnimatableSprite extends Sprite {
    @Getter private Animation<TextureAtlas.AtlasRegion> currentAnimation;
    private float animationTime;

    public AnimatableSprite(TextureAtlas.AtlasRegion textureRegion) {
        super(textureRegion);

        currentAnimation = new Animation<>(1f, new Array<>(new TextureAtlas.AtlasRegion[]{textureRegion}), Animation.PlayMode.LOOP);
    }

    public AnimatableSprite() {

    }

    public void tick(float delta) {
        animationTime += delta;
    }

    @Override
    public void draw(Batch batch) {
        if (currentAnimation == null)
            return;

        setRegion(currentAnimation.getKeyFrame(animationTime));

        super.draw(batch);
    }

    public void setAnimation(Animation<TextureAtlas.AtlasRegion> newAnimation) {
        if (newAnimation == null)
            return;

        if (currentAnimation == newAnimation)
            return;

        this.currentAnimation = newAnimation;
        animationTime = 0;
    }

    public boolean isAnimationFinished() {
        return currentAnimation.isAnimationFinished(animationTime);
    }
}
