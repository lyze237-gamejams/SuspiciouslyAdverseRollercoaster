package dev.lyze.sar.components;

import com.artemis.Component;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
public class HitboxComponent extends Component {
    @Getter @Setter private float width, height;
}
