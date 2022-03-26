package dev.lyze.sar.utils;

import com.artemis.Component;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import lombok.Getter;

public class Constants extends Component {
    @Getter private final TextureAtlas main = new TextureAtlas("Atlases/Main.atlas");
    @Getter private final boolean debug = false;
}
