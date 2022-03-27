package dev.lyze.sar.components.player;

import com.artemis.Component;
import com.badlogic.gdx.graphics.Color;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
public class FlashSpriteComponent extends Component {
    @Getter @Setter private float duration;
    @Getter @Setter private Color color = Color.RED;

    public FlashSpriteComponent(float duration) {
        this.duration = duration;
    }
}
