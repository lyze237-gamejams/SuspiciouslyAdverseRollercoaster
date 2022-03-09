package dev.lyze.sar.components.movement;

import com.artemis.Component;
import com.badlogic.gdx.math.Vector2;
import lombok.*;

@NoArgsConstructor
@ToString
public class PositionComponent extends Component {
    @Getter private Vector2 position;
    @Getter private Vector2 startPosition;

    public PositionComponent(float x, float y) {
        this.position = new Vector2(x, y);
        this.startPosition = new Vector2(x, y);
    }
}
