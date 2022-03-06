package dev.lyze.sar.components;

import com.artemis.Component;
import com.badlogic.gdx.maps.MapObject;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class EntitySpawnerComponent extends Component {
    @Getter private MapObject object;

    public EntitySpawnerComponent(MapObject object) {
        this.object = object;
    }
}
