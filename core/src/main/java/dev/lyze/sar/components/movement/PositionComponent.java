package dev.lyze.sar.components.movement;

import com.artemis.Component;
import com.badlogic.gdx.math.Vector2;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
public class PositionComponent extends Component {
    @Getter private Vector2 position;
}
