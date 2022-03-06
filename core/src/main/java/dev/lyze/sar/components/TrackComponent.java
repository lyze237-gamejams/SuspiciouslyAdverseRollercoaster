package dev.lyze.sar.components;

import com.artemis.Component;
import com.badlogic.gdx.math.Polyline;
import com.badlogic.gdx.math.Vector2;
import dev.lyze.sar.utils.IntersectionExtensions;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
public class TrackComponent extends Component {
    @Getter @Setter Polyline line;
}
