package dev.lyze.sar.systems;

import com.aliasifkhan.hackLights.HackLightEngine;
import com.artemis.BaseSystem;
import com.artemis.annotations.Wire;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import dev.lyze.sar.eventsystem.EventListener;
import dev.lyze.sar.eventsystem.EventManager;
import dev.lyze.sar.eventsystem.events.ResizeEvent;

public class HackLightSystem extends BaseSystem {
    @Wire private ExtendViewport viewport;
    @Wire private EventManager eventManager;
    @Wire(name = "hackLightEngine") private HackLightEngine hackLightEngine;

    @Override
    protected void initialize() {
        eventManager.addListener(new EventListener<ResizeEvent>(ResizeEvent.class) {
            @Override
            protected void fire(ResizeEvent event) {
                hackLightEngine.update(event.getWidth(), event.getHeight());
            }
        });
    }

    @Override
    protected void processSystem() {
        hackLightEngine.draw(viewport.getCamera().combined);
    }
}
