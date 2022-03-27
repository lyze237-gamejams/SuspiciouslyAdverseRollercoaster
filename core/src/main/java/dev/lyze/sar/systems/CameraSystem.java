package dev.lyze.sar.systems;

import com.artemis.ComponentMapper;
import com.artemis.annotations.All;
import com.artemis.annotations.Wire;
import com.artemis.systems.IteratingSystem;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import dev.lyze.sar.components.player.PlayerCartComponent;
import dev.lyze.sar.components.movement.PositionComponent;
import dev.lyze.sar.components.movement.SizeComponent;
import lombok.var;

@All({PlayerCartComponent.class, PositionComponent.class, SizeComponent.class})
public class CameraSystem extends IteratingSystem {
    private static final float MOVEMENT_SPEED = 800;

    private ComponentMapper<PlayerCartComponent> playerMapper;
    private ComponentMapper<PositionComponent> positionMapper;
    private ComponentMapper<SizeComponent> sizeMapper;

    @Wire private ExtendViewport viewport;

    @Override
    protected void inserted(int entityId) {

    }

    @Override
    protected void process(int entityId) {
        var position = positionMapper.get(entityId);
        var size = sizeMapper.get(entityId);

        viewport.getCamera().position.set(position.getPosition().x + size.getWidth() / 2f, position.getPosition().y + size.getHeight() / 2f, 0);

        //debugMovement();
    }

    private void debugMovement() {
        var camera = (OrthographicCamera) viewport.getCamera();

        var position = new Vector2(camera.position.x, camera.position.y);

        if (Gdx.input.isKeyPressed(Input.Keys.W))
            position.set(position.x , position.y + MOVEMENT_SPEED * Gdx.graphics.getDeltaTime());
        if (Gdx.input.isKeyPressed(Input.Keys.S))
            position.set(position.x , position.y - MOVEMENT_SPEED * Gdx.graphics.getDeltaTime());

        if (Gdx.input.isKeyPressed(Input.Keys.A))
            position.set(position.x - MOVEMENT_SPEED * Gdx.graphics.getDeltaTime(), position.y);
        if (Gdx.input.isKeyPressed(Input.Keys.D))
            position.set(position.x + MOVEMENT_SPEED * Gdx.graphics.getDeltaTime(), position.y);

        if (Gdx.input.isKeyPressed(Input.Keys.UP))
            camera.zoom += 100f * Gdx.graphics.getDeltaTime();
        if (Gdx.input.isKeyPressed(Input.Keys.DOWN))
            camera.zoom -= 100f * Gdx.graphics.getDeltaTime();

        camera.position.set(position.x, position.y, camera.position.z);
    }
}
