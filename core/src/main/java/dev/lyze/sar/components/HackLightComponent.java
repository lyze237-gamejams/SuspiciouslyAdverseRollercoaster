package dev.lyze.sar.components;

import com.aliasifkhan.hackLights.HackLight;
import com.artemis.Component;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
public class HackLightComponent extends Component {
    @Getter private HackLight light;
}
