package dev.lyze.sar.components;

import com.artemis.Component;
import com.artemis.annotations.EntityId;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class CopyRotationFromEntityComponent extends Component {
    @Getter @EntityId private int target;

    public CopyRotationFromEntityComponent(int target) {
        this.target = target;
    }
}
