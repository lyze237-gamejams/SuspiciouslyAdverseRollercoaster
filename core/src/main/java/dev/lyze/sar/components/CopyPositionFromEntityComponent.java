package dev.lyze.sar.components;

import com.artemis.Component;
import com.artemis.annotations.EntityId;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class CopyPositionFromEntityComponent extends Component {
    @Getter @EntityId private int target;

    public CopyPositionFromEntityComponent(int target) {
        this.target = target;
    }
}
