package dev.lyze.sar.components;

import com.artemis.Component;
import com.badlogic.gdx.math.MathUtils;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
public class SizeComponent extends Component {
    @Getter @Setter private float width;
    @Getter @Setter private float height;

    public int getTileWidth() {
        return MathUtils.ceil(width / 128f);
    }

    public int getTileHeight() {
        return MathUtils.ceil(height / 128f);
    }
}
