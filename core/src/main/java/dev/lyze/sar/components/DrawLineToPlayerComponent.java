package dev.lyze.sar.components;

import com.artemis.Component;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
public class DrawLineToPlayerComponent extends Component {
    @Getter private float offsetY = 0.5f;
    @Getter private float offsetX = 0.5f;

    @Getter @Setter private float time = 0f;
    @Getter @Setter private float timeToFullSize;
    @Getter @Setter private float maxWidth = 0.1f;
    @Getter @Setter private Color color = new Color(Color.RED);
    @Getter @Setter private boolean freeze;

    @Getter @Setter private Vector2 freezePos = null;

    public DrawLineToPlayerComponent(float timeToFullSize) {
        this.timeToFullSize = timeToFullSize;
    }

    public float calculateWidth() {
        return Interpolation.exp5In.apply(MathUtils.map(0, timeToFullSize, 0, 1, time)) * maxWidth;
    }
}
