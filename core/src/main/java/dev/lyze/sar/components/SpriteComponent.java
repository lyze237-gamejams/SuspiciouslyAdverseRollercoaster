package dev.lyze.sar.components;

import com.artemis.Component;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import dev.lyze.sar.systems.sprites.SpriteOrder;
import lombok.Getter;
import lombok.Setter;
import lombok.var;

public class SpriteComponent extends Component {
    @Getter private final Sprite sprite;
    @Getter @Setter private int order = 0;

    public SpriteComponent(TextureRegion region, SpriteOrder order) {
        this.order = order.getOrder();

        var scale = 1 / 600f * 3;

        sprite = new Sprite(region);
        sprite.setScale(scale);
        sprite.setOrigin(sprite.getWidth() / 2f, 0);
    }

    public SpriteComponent() {
        sprite = new Sprite();
    }
}
