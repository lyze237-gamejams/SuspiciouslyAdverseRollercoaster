package dev.lyze.sar.components.movement;

import com.artemis.Component;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
public class RotationComponent extends Component {
    @Getter @Setter private float rotation;
}
