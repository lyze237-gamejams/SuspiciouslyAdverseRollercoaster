package dev.lyze.sar.systems.debug;

import com.artemis.Component;
import com.artemis.ComponentMapper;
import com.artemis.annotations.All;
import com.artemis.annotations.Wire;
import com.artemis.systems.IteratingSystem;
import com.artemis.utils.Bag;
import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import dev.lyze.sar.components.player.PlayerComponent;
import dev.lyze.sar.eventsystem.EventListener;
import dev.lyze.sar.eventsystem.EventManager;
import dev.lyze.sar.eventsystem.events.ResizeEvent;
import lombok.var;

@All({PlayerComponent.class})
public class PlayerDebugDrawerSystem extends IteratingSystem {
    @Wire private SpriteBatch batch;
    @Wire private EventManager eventManager;

    private final Bag<Component> componentsBag = new Bag<>();

    private final ScreenViewport viewport = new ScreenViewport();

    private BitmapFont font;

    @Override
    protected void initialize() {
        font = new BitmapFont();

        eventManager.addListener(new EventListener<ResizeEvent>(ResizeEvent.class) {
            @Override
            protected void fire(ResizeEvent event) {
                viewport.update(event.getWidth(), event.getHeight(), true);
            }
        });
    }

    @Override
    protected void begin() {
        viewport.apply();

        batch.setProjectionMatrix(viewport.getCamera().combined);
        batch.begin();
    }

    @Override
    protected void process(int entityId) {
        var entity = world.getEntity(entityId);

        componentsBag.clear();
        entity.getComponents(componentsBag);

        for (int i = 0; i < componentsBag.size(); i++) {
            var component = componentsBag.get(i);

            font.draw(batch, component.toString(), 12, 24 + i * 18);
        }
    }

    @Override
    protected void end() {
        batch.end();
    }
}
