package dev.lyze.sar.components;

import com.artemis.Component;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
public class WaitForAnimationFinishComponent extends Component {
    @Getter private Component nextComponent;
}
