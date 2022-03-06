package dev.lyze.sar.components;

import com.artemis.Component;
import com.badlogic.gdx.math.Polyline;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
public class TrackComponent extends Component {
    @Getter private float startX, startY;
    @Getter private float endX, endY;
}
