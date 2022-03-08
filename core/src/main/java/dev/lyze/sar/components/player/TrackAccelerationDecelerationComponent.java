package dev.lyze.sar.components.player;

import com.artemis.Component;
import lombok.Getter;
import lombok.ToString;

@ToString
public class TrackAccelerationDecelerationComponent extends Component {
    @Getter private float acceleration = 0.5f * 128f;
    @Getter private float deceleration = -0.5f * 128f;

    @Getter private float minSpeed = 1f * 128f;
}
