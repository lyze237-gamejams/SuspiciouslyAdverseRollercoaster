package dev.lyze.sar.systems.player;

import com.artemis.annotations.All;
import com.artemis.systems.IteratingSystem;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.maps.tiled.tiles.AnimatedTiledMapTile;
import dev.lyze.sar.components.movement.VelocityComponent;
import dev.lyze.sar.components.player.PlayerComponent;
import dev.lyze.sar.components.player.PlayerFollowTrackComponent;

@All({PlayerComponent.class, PlayerFollowTrackComponent.class, VelocityComponent.class})
public class PlayerJumpInputState extends IteratingSystem {

    @Override
    protected void process(int entityId) {
        if (Gdx.input.isKeyPressed(Input.Keys.SPACE)) {
            AnimatedTiledMapTile.updateAnimationBaseTime();

        }
    }
}
