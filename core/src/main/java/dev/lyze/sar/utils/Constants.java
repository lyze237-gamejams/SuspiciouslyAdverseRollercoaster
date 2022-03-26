package dev.lyze.sar.utils;

import com.artemis.Component;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import lombok.Getter;

public class Constants extends Component {
    @Getter private final TextureAtlas main;
    @Getter private final boolean debug = false;

    @Getter private final Animation<TextureAtlas.AtlasRegion> playerCrouch, playerFall, playerIdle, playerJump, playerLanding;

    public Constants() {
        main = new TextureAtlas("Atlases/Main.atlas");

        playerCrouch = new Animation<>(0.1f, main.findRegions("Fox/Crouch"), Animation.PlayMode.NORMAL);
        playerFall = new Animation<>(0.1f, main.findRegions("Fox/Fall"), Animation.PlayMode.NORMAL);
        playerIdle = new Animation<>(0.1f, main.findRegions("Fox/Idle"), Animation.PlayMode.LOOP);
        playerJump = new Animation<>(0.1f, main.findRegions("Fox/Jump"), Animation.PlayMode.NORMAL);
        playerLanding = new Animation<>(0.1f, main.findRegions("Fox/Landing"), Animation.PlayMode.NORMAL);
    }
}
