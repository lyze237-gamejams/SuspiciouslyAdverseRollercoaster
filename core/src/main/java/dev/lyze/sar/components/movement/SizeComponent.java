package dev.lyze.sar.components.movement;

import com.artemis.Component;
import com.badlogic.gdx.math.MathUtils;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@ToString
public class SizeComponent extends Component {
    @Getter @Setter private float width;
    @Getter @Setter private float height;
}
