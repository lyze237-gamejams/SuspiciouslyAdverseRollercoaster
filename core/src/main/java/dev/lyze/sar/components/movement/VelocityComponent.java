package dev.lyze.sar.components.movement;

import com.artemis.Component;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.var;

@NoArgsConstructor
public class VelocityComponent extends Component {
    @Getter private final Vector2 velocity = new Vector2();

    @Getter @Setter private float maxXVelocity = 7 * 128f;
    @Getter @Setter private float maxYVelocity = 7 * 128f;

    public void clamp() {
        velocity.x = MathUtils.clamp(velocity.x, -maxXVelocity, maxXVelocity);
        velocity.y = MathUtils.clamp(velocity.y, -maxYVelocity, maxYVelocity);
    }
}
