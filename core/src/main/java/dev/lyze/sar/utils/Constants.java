package dev.lyze.sar.utils;

import com.artemis.Component;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;
import dev.lyze.sar.CharacterEnum;
import dev.lyze.sar.components.movement.GravityComponent;
import lombok.Getter;
import lombok.var;

public class Constants extends Component {
    @Getter private final boolean debug = false;
    @Getter private final int maxHealth = 3;

    @Getter private final TextureAtlas main, bg;

    @Getter private final Array<TextureAtlas.AtlasRegion> background;

    @Getter private final Animation<TextureAtlas.AtlasRegion> playerCrouchIn, playerCrouchOut, playerFall, playerIdle, playerJump, playerAir, playerLanding;
    @Getter private final TextureAtlas.AtlasRegion playerProfile;
    @Getter private final TextureAtlas.AtlasRegion health;

    @Getter private final TextureAtlas.AtlasRegion minecartBack, minecartFront;

    @Getter private final Animation<TextureAtlas.AtlasRegion> battoFlight, battoShoot;

    @Getter private final TextureAtlas lights;

    @Getter private final CharacterEnum character;

    @Getter private final Sound hitSound, jumpSound, jumpCartSound, thudSound, duckSound;

    @Getter private static final String[] levels = new String[] { "Maps/Level1.tmx", "Maps/StraightAhead.tmx", "Maps/Zealot.tmx", "Maps/DevMap.tmx" };
    @Getter private int levelIndex;

    public Constants(CharacterEnum character, int levelIndex) {
        this.levelIndex = levelIndex;
        this.character = character;
        main = new TextureAtlas("Atlases/Main.atlas");
        bg = new TextureAtlas("Atlases/Bg.atlas");
        background = bg.findRegions("Background");

        lights = new TextureAtlas("lights.atlas");

        float speed = 0.05f;
        playerCrouchIn = new Animation<>(speed, main.findRegions(character.getPath() + "/CrouchIn"), Animation.PlayMode.NORMAL);
        playerCrouchOut = new Animation<>(speed, main.findRegions(character.getPath() + "/CrouchOut"), Animation.PlayMode.NORMAL);
        playerFall = new Animation<>(speed, main.findRegions(character.getPath() + "/Fall"), Animation.PlayMode.NORMAL);
        playerIdle = new Animation<>(speed, main.findRegions(character.getPath() + "/Idle"), Animation.PlayMode.LOOP);
        playerJump = new Animation<>(speed, main.findRegions(character.getPath() + "/Jump"), Animation.PlayMode.NORMAL);
        playerAir = new Animation<>(speed, main.findRegions(character.getPath() + "/Air"), Animation.PlayMode.NORMAL);
        playerLanding = new Animation<>(speed, main.findRegions(character.getPath() + "/Landing"), Animation.PlayMode.NORMAL);

        playerProfile = main.findRegion(character.getPath() + "/Headshot");
        health = main.findRegion("Misc/Health");

        minecartBack = main.findRegion("Misc/Minecart_back");
        minecartFront = main.findRegion("Misc/Minecart_front");

        battoFlight = new Animation<>(speed, main.findRegions("Batto/Flight"), Animation.PlayMode.LOOP);
        battoShoot = new Animation<>(speed, main.findRegions("Batto/Shoot"), Animation.PlayMode.NORMAL);

        hitSound = Gdx.audio.newSound(Gdx.files.internal("Sounds/hitHurt.ogg"));
        jumpSound = Gdx.audio.newSound(Gdx.files.internal("Sounds/jumpCart.ogg"));
        jumpCartSound = Gdx.audio.newSound(Gdx.files.internal("Sounds/jump.ogg"));
        thudSound = Gdx.audio.newSound(Gdx.files.internal("Sounds/thud.ogg"));
        duckSound = Gdx.audio.newSound(Gdx.files.internal("Sounds/duck.ogg"));
    }

    public void dispose() {
        main.dispose();
        lights.dispose();
        bg.dispose();
    }
}
