package dev.lyze.sar.systems;

import com.artemis.BaseSystem;
import com.artemis.annotations.Wire;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.ExtendViewport;

public class BeginBatchSystem extends BaseSystem {
    @Wire private ExtendViewport viewport;
    @Wire private SpriteBatch batch;

    @Override
    protected void processSystem() {
        viewport.apply();

        batch.setProjectionMatrix(viewport.getCamera().combined);
        batch.begin();
    }
}
