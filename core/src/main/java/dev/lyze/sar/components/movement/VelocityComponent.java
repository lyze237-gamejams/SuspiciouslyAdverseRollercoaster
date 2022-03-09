package dev.lyze.sar.components.movement;

import com.artemis.Component;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import dev.lyze.sar.components.player.PlayerConstants;
import lombok.*;

@NoArgsConstructor
public class VelocityComponent extends Component {
    @Getter private final Vector2 velocity = new Vector2();

    public void clamp(Vector2 maxVelocity) {
        velocity.x = MathUtils.clamp(velocity.x, -maxVelocity.x, maxVelocity.x);
        velocity.y = MathUtils.clamp(velocity.y, -maxVelocity.y, maxVelocity.y);
    }

    @Override
    public String toString() {
        return "VelocityComponent{" +
                "velocity=" + ((int) velocity.x * 100) / 100f + " / " + ((int) velocity.y * 100) / 100f +
                '}';
    }
}
