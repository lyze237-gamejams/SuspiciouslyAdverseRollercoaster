package dev.lyze.sar.components.wait;

import com.artemis.Component;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
public class WaitTimeComponent extends Component {
    @Getter @Setter private float timeToWait;
    @Getter private Component target;
}
