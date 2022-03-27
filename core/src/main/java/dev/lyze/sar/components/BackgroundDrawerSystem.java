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
        float x = viewport.getCamera().position.x;
        float y = viewport.getCamera().position.y;

        var width = viewport.getWorldWidth();
        var height = viewport.getWorldHeight();

        batch.draw(constants.getBackground(), x - width / 2f, y - height / 2f, width, height);
    }
}
