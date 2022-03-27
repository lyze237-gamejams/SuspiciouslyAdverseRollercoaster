package dev.lyze.sar.systems.sprites;

import com.artemis.BaseSystem;
import com.artemis.annotations.Wire;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.ExtendViewport;

public class EndBatchSystem extends BaseSystem {
    @Wire private ExtendViewport viewport;
    @Wire private SpriteBatch batch;

    @Override
    protected void processSystem() {
        batch.end();
    }
}
