package dev.lyze.sar.components.cart;

import com.artemis.Component;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@ToString
public class CartFollowTrackComponent extends Component {
    @Getter @Setter private int trackId;
    @Getter @Setter private int section;
}
