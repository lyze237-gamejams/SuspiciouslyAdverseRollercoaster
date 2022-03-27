package dev.lyze.sar.systems;

import com.artemis.BaseSystem;
import com.artemis.annotations.Wire;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.FitViewport;
import dev.lyze.sar.eventsystem.EventListener;
import dev.lyze.sar.eventsystem.EventManager;
import dev.lyze.sar.eventsystem.events.HitEvent;
import dev.lyze.sar.eventsystem.events.ResizeEvent;
import dev.lyze.sar.utils.Constants;
import lombok.var;

public class UiSystem extends BaseSystem {
    private Stage stage;
    
    private Image[] healths;

    @Wire private EventManager eventManager;
    @Wire(name = "constants") private Constants constants;

    @Override
    protected void initialize() {
        stage = new Stage(new FitViewport(1600, 900));

        eventManager.addListener(new EventListener<ResizeEvent>(ResizeEvent.class) {
            @Override
            protected void fire(ResizeEvent event) {
                stage.getViewport().update(event.getWidth(), event.getHeight(), true);
            }
        });

        eventManager.addListener(new EventListener<HitEvent>(HitEvent.class) {
            @Override
            protected void fire(HitEvent event) {
                for (int i = healths.length - 1; i >= 0; i--) {
                    if (healths[i].isVisible()) {
                        healths[i].setVisible(false);
                        return;
                    }
                }
            }
        });

        var root = new Table();
        root.setFillParent(true);

        var inner = new Table();
        var pfp = new Image(constants.getPlayerProfile());

        inner.add(pfp).padRight(8);
        
        healths = new Image[constants.getMaxHealth()];
        for (int i = 0; i < healths.length; i++) {
           healths[i] = new Image(constants.getHealth());
           inner.add(healths[i]);
        }

        root.add(inner).pad(24).expand().top().left();
        
        stage.addActor(root);
    }

    @Override
    protected void processSystem() {
        stage.getViewport().apply();

        stage.act();
        stage.draw();
    }
}
