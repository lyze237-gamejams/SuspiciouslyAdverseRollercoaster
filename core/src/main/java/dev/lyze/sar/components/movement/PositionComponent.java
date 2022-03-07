package dev.lyze.sar.components.movement;

import com.artemis.Component;
import com.badlogic.gdx.math.Vector2;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@ToString
public class PositionComponent extends Component {
    @Getter private Vector2 position;
}
