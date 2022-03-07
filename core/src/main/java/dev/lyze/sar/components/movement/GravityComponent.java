package dev.lyze.sar.components.movement;

import com.artemis.Component;
import com.badlogic.gdx.math.MathUtils;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
public class GravityComponent extends Component {
    @Getter private float gravity = -(0.5f * 128f);

    @Override
    public String toString() {
        return "GravityComponent{" +
                "gravity=" + ((int) gravity * 100) / 100f +
                '}';
    }
}
