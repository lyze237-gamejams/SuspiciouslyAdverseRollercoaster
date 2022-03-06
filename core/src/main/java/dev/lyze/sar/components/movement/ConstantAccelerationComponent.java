package dev.lyze.sar.components.movement;

import com.artemis.Component;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
public class ConstantAccelerationComponent extends Component {
    @Getter private float acceleration = 2f * 128f;
    @Getter private float deceleration = -2f * 128f;
}
