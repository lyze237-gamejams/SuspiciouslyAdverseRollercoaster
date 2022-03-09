package dev.lyze.sar.components.movement;

import com.artemis.Component;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
public class GravityComponent extends Component {
    @Getter private float gravity = -75f;
    @Getter private final float slowDownXTo = 3f;
    @Getter private final float slowdownXSpeed = 4f;

    @Override
    public String toString() {
        return "GravityComponent{" +
                "gravity=" + ((int) gravity * 100) / 100f +
                '}';
    }
}
