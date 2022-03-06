package dev.lyze.sar.components.player;

import com.artemis.Component;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
public class PlayerFollowTrackComponent extends Component {
    @Getter @Setter private int trackId;
    @Getter @Setter private int section;
}
