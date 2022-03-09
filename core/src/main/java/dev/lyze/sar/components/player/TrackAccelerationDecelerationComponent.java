package dev.lyze.sar.components.player;

import com.artemis.Component;
import lombok.Getter;
import lombok.ToString;

@ToString
public class TrackAccelerationDecelerationComponent extends Component {
    @Getter private float acceleration = 100f;
    @Getter private float deceleration = -100f;

    @Getter private float minSpeed = 4f;
}
