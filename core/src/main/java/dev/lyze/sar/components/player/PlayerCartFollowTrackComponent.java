package dev.lyze.sar.components.player;

import com.artemis.Component;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@ToString
public class PlayerCartFollowTrackComponent extends Component {
    @Getter @Setter private int trackId;
    @Getter @Setter private int section;
}
