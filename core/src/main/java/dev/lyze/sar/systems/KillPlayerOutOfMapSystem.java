package dev.lyze.sar.systems;

import com.artemis.ComponentMapper;
import com.artemis.annotations.All;
import com.artemis.annotations.Wire;
import com.artemis.systems.IteratingSystem;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import dev.lyze.sar.GameOverScreen;
import dev.lyze.sar.components.movement.PositionComponent;
import dev.lyze.sar.components.player.PlayerComponent;
import dev.lyze.sar.utils.Constants;
import lombok.var;

@All({PlayerComponent.class, PositionComponent.class})
public class KillPlayerOutOfMapSystem extends IteratingSystem {
    private ComponentMapper<PositionComponent> positionMapper;

    @Wire(name = "constants") private Constants constants;

    @Override
    protected void process(int entityId) {
        var position = positionMapper.get(entityId).getPosition();

        if (position.y < 0)
            ((Game) Gdx.app.getApplicationListener()).setScreen(new GameOverScreen(constants.getCharacter(), constants.getLevelIndex()));
    }
}
