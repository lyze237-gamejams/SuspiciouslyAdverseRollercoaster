package dev.lyze.sar.components;

import com.artemis.Component;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
public class PositionComponent extends Component {
    @Getter @Setter private float x;
    @Getter @Setter private float y;

    public int getTileX() {
        return (int) (x / 128f);
    }

    public int getTileY() {
        return (int) (y / 128f);
    }
}
