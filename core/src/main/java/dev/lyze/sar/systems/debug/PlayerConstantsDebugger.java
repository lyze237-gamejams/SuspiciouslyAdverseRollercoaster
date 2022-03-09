package dev.lyze.sar.systems.debug;

import com.artemis.BaseSystem;
import com.artemis.annotations.All;
import com.artemis.annotations.Wire;
import com.artemis.systems.IteratingSystem;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import dev.lyze.sar.components.player.PlayerConstants;
import dev.lyze.sar.eventsystem.EventListener;
import dev.lyze.sar.eventsystem.EventManager;
import dev.lyze.sar.eventsystem.events.ResizeEvent;

public class PlayerConstantsDebugger extends BaseSystem {
    private final Stage stage = new Stage(new ScreenViewport());

    @Wire
    private EventManager eventManager;

    @Override
    protected void initialize() {
        eventManager.addListener(new EventListener<ResizeEvent>(ResizeEvent.class) {
            @Override
            protected void fire(ResizeEvent event) {
                stage.getViewport().update(event.getWidth(), event.getHeight(), true);
            }
        });
    }

    @Override
    protected void processSystem() {

    }

    @Override
    protected void end() {
        stage.getViewport().apply();
        stage.act();
        stage.draw();
    }
}
