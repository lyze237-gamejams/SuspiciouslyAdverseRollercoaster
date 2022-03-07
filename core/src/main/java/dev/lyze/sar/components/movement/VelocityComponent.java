package dev.lyze.sar.components.movement;

import com.artemis.Component;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import lombok.*;

@NoArgsConstructor
public class VelocityComponent extends Component {
    @Getter private final Vector2 velocity = new Vector2();

    @Getter @Setter private float maxVelocityX = 7 * 128f;
    @Getter @Setter private float maxVelocityY = 7 * 128f;

    public void clamp() {
        velocity.x = MathUtils.clamp(velocity.x, -maxVelocityX, maxVelocityX);
        velocity.y = MathUtils.clamp(velocity.y, -maxVelocityY, maxVelocityY);
    }

    @Override
    public String toString() {
        return "VelocityComponent{" +
                "velocity=" + ((int) velocity.x * 100) / 100f + " / " + ((int) velocity.y * 100) / 100f +
                ", maxXVelocity=" + maxVelocityX +
                ", maxYVelocity=" + maxVelocityY +
                '}';
    }
}
