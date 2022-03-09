package dev.lyze.sar.components.player;

import com.artemis.Component;
import com.artemis.annotations.All;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class PlayerComponent extends Component {
    public final PlayerConstants playerConstants = new PlayerConstants();
}
