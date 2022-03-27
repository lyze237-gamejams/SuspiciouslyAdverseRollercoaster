package dev.lyze.sar.utils;

import com.artemis.Component;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import lombok.Getter;
import lombok.var;

public class Constants extends Component {
    @Getter private final TextureAtlas main;
    @Getter private final boolean debug = false;

    @Getter private final Animation<TextureAtlas.AtlasRegion> playerCrouchIn, playerCrouchOut, playerFall, playerIdle, playerJump, playerLanding;

    @Getter private final TextureAtlas.AtlasRegion minecartBack, minecartFront;

    @Getter private final TextureAtlas lights;

    @Getter private final Texture background;

    public Constants() {
        main = new TextureAtlas("Atlases/Main.atlas");

        lights = new TextureAtlas("lights.atlas");

        background = new Texture("Backgrounds/Purple Nebula 2 - 1024x1024.png");

        float speed = 0.05f;
        playerCrouchIn = new Animation<>(speed, main.findRegions("Fox/CrouchIn"), Animation.PlayMode.NORMAL);
        playerCrouchOut = new Animation<>(speed, main.findRegions("Fox/CrouchOut"), Animation.PlayMode.NORMAL);
        playerFall = new Animation<>(speed, main.findRegions("Fox/Fall"), Animation.PlayMode.NORMAL);
        playerIdle = new Animation<>(speed, main.findRegions("Fox/Idle"), Animation.PlayMode.LOOP);
        playerJump = new Animation<>(speed, main.findRegions("Fox/Jump"), Animation.PlayMode.NORMAL);
        playerLanding = new Animation<>(speed, main.findRegions("Fox/Landing"), Animation.PlayMode.NORMAL);

        minecartBack = main.findRegion("Misc/Minecart_back");
        minecartFront = main.findRegion("Misc/Minecart_front");
    }

}
