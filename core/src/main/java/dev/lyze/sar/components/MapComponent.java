package dev.lyze.sar.components;

import com.artemis.Component;
import dev.lyze.sar.Map;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class MapComponent extends Component {
    @Getter private Map map;

    public MapComponent(Map map) {
        this.map = map;
    }
}
