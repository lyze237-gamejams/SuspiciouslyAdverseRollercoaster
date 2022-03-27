package dev.lyze.sar.components.player;

import com.artemis.Component;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.MathUtils;
import lombok.Getter;
import lombok.Setter;

public class PlayerJumpStateComponent extends Component {
    @Getter private final float scale = 8f;
    @Getter private final float maxTime = 0.8f;
    @Getter @Setter private float hoverTime = 1f;


    @Getter @Setter private float time;

    private final Interpolation interpolation = new Interpolation() {
        @Override
        public float apply(float a) {
            return (float) Math.sqrt(Math.max(1 - (a * 2 - 1) * (a * 2 - 1), 0)) / 2f;
        }
    };

    public float calculateOffset() {
        return interpolation.apply(getPercent()) * scale;
    }

    public boolean isFinished() {
        return time >= maxTime;
    }

    public float getPercent() {
        return MathUtils.map(0, maxTime, 0, 1, time);
    }
}
