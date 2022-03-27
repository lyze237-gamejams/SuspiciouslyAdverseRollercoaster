package dev.lyze.sar.components;

import com.artemis.Component;
import com.artemis.annotations.EntityId;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
public class CopyPositionFromEntityComponent extends Component {
    @Getter @EntityId private int target;
    @Getter @Setter private float offsetX, offsetY;

    public CopyPositionFromEntityComponent(int target) {
        this.target = target;
    }
}
