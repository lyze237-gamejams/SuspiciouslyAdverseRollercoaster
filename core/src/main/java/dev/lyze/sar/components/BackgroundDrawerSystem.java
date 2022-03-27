package dev.lyze.sar.components;

import com.artemis.BaseSystem;
import com.artemis.annotations.Wire;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import dev.lyze.sar.utils.Constants;
import lombok.var;

public class BackgroundDrawerSystem extends BaseSystem {
    @Wire private ExtendViewport viewport;
    @Wire(name = "constants") private Constants constants;
    @Wire private SpriteBatch batch;

    @Override
    protected void processSystem() {
        float camX = viewport.getCamera().position.x;
        float camY = viewport.getCamera().position.y;

        var width = viewport.getWorldWidth();
        var height = viewport.getWorldHeight();

        float x = camX - width / 2f;
        float y = camY - height / 2f;

        for (var region : constants.getBackground()) {
            batch.draw(region, x, y, width, height);
        }
    }
}
