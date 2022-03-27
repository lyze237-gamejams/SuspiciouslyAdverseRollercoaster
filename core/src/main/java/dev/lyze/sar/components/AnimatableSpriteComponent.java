package dev.lyze.sar.components;

import com.artemis.Component;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import dev.lyze.sar.AnimatableSprite;
import dev.lyze.sar.systems.sprites.SpriteOrder;
import lombok.Getter;
import lombok.Setter;
import lombok.var;

public class AnimatableSpriteComponent extends Component {
    @Getter private final AnimatableSprite sprite;
    @Getter @Setter private int order = 0;

    public AnimatableSpriteComponent(TextureAtlas.AtlasRegion region, SpriteOrder order) {
        this.order = order.getOrder();

        var scale = 1 / 600f * 3;

        sprite = new AnimatableSprite(region);
        sprite.setScale(scale);
        sprite.setOrigin(sprite.getWidth() / 2f, 0);
    }

    public AnimatableSpriteComponent() {
        sprite = new AnimatableSprite();
    }
}
