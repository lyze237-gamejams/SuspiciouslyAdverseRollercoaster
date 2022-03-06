package dev.lyze.sar.components;

import com.artemis.Component;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
public class AccelerationComponent extends Component {
    @Getter private float acceleration = 2f * 128f;
}
