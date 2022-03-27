package dev.lyze.sar.systems.map;

import com.artemis.BaseSystem;
import com.artemis.annotations.Wire;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import dev.lyze.sar.eventsystem.EventListener;
import dev.lyze.sar.eventsystem.EventManager;
import dev.lyze.sar.eventsystem.events.ResizeEvent;

public class MapRenderingSystem extends BaseSystem {
    @Wire private ExtendViewport viewport;
    @Wire private EventManager eventManager;
    @Wire private Map map;

    @Override
    protected void initialize() {
        eventManager.addListener(new EventListener<ResizeEvent>(ResizeEvent.class) {
            @Override
            protected void fire(ResizeEvent event) {
                viewport.update(event.getWidth(), event.getHeight());
            }
        });
    }

    @Override
    protected void begin() {
        viewport.apply();
    }

    @Override
    protected void processSystem() {
        map.getRenderer().setView((OrthographicCamera) viewport.getCamera());
        map.getRenderer().render();
    }
}
