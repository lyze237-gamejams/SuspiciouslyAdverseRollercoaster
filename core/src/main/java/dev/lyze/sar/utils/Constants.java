package dev.lyze.sar.utils;

import com.artemis.Component;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import dev.lyze.sar.CharacterEnum;
import lombok.Getter;
import lombok.var;

public class Constants extends Component {
    @Getter private final TextureAtlas main;
    @Getter private final boolean debug = true;

    @Getter private final Animation<TextureAtlas.AtlasRegion> playerCrouchIn, playerCrouchOut, playerFall, playerIdle, playerJump, playerAir, playerLanding;

    @Getter private final TextureAtlas.AtlasRegion minecartBack, minecartFront;

    @Getter private final Animation<TextureAtlas.AtlasRegion> battoFlight, battoShoot;

    @Getter private final TextureAtlas lights;

    @Getter private final Texture background;

    public Constants(CharacterEnum character) {
        main = new TextureAtlas("Atlases/Main.atlas");

        lights = new TextureAtlas("lights.atlas");

        background = new Texture("Backgrounds/Purple Nebula 2 - 1024x1024.png");

        float speed = 0.05f;
        playerCrouchIn = new Animation<>(speed, main.findRegions(character.getPath() + "/CrouchIn"), Animation.PlayMode.NORMAL);
        playerCrouchOut = new Animation<>(speed, main.findRegions(character.getPath() + "/CrouchOut"), Animation.PlayMode.NORMAL);
        playerFall = new Animation<>(speed, main.findRegions(character.getPath() + "/Fall"), Animation.PlayMode.NORMAL);
        playerIdle = new Animation<>(speed, main.findRegions(character.getPath() + "/Idle"), Animation.PlayMode.LOOP);
        playerJump = new Animation<>(speed, main.findRegions(character.getPath() + "/Jump"), Animation.PlayMode.NORMAL);
        playerAir = new Animation<>(speed, main.findRegions(character.getPath() + "/Air"), Animation.PlayMode.NORMAL);
        playerLanding = new Animation<>(speed, main.findRegions(character.getPath() + "/Landing"), Animation.PlayMode.NORMAL);

        minecartBack = main.findRegion("Misc/Minecart_back");
        minecartFront = main.findRegion("Misc/Minecart_front");

        battoFlight = new Animation<>(speed, main.findRegions("Batto/Flight"), Animation.PlayMode.LOOP);
        battoShoot = new Animation<>(speed, main.findRegions("Batto/Shoot"), Animation.PlayMode.NORMAL);
    }

    public void dispose() {
        main.dispose();
        lights.dispose();
        background.dispose();
    }
}
