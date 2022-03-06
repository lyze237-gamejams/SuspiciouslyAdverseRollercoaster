package dev.lyze.sar.components;

import com.artemis.Component;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
public class GravityComponent extends Component {
    @Getter private float gravity = 8f * 128f;
}
