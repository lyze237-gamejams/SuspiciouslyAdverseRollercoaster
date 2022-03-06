package dev.lyze.sar.systems;

import com.artemis.ComponentMapper;
import com.artemis.annotations.All;
import com.artemis.annotations.Wire;
import com.artemis.systems.IteratingSystem;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import dev.lyze.sar.components.MapComponent;
import dev.lyze.sar.eventsystem.EventListener;
import dev.lyze.sar.eventsystem.EventManager;
import dev.lyze.sar.eventsystem.events.ResizeEvent;
import lombok.var;

@All({MapComponent.class})
public class MapRenderingSystem extends IteratingSystem {
    @Wire private ExtendViewport viewport;
    @Wire private EventManager eventManager;

    private ComponentMapper<MapComponent> mapMapper;

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
    protected void process(int entityId) {
        var map = mapMapper.get(entityId);

        map.getMap().getRenderer().setView((OrthographicCamera) viewport.getCamera());
        map.getMap().getRenderer().render();
    }
}
