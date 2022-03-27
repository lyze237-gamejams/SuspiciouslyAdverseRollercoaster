package dev.lyze.sar.components;

import com.artemis.Component;
import com.badlogic.gdx.math.Polygon;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class ObstacleComponent extends Component {
    @Getter private Polygon polygon = new Polygon();

    public ObstacleComponent(float[] verts) {
        this.polygon.setVertices(verts);
    }
}
