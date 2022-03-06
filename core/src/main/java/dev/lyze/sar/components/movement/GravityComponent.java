package dev.lyze.sar.components.movement;

import com.artemis.Component;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
public class GravityComponent extends Component {
    @Getter private float gravity = -(0.5f * 128f);
}
