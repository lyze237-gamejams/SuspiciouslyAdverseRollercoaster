package dev.lyze.sar.systems.debug;

import com.artemis.ComponentMapper;
import com.artemis.annotations.All;
import com.artemis.annotations.Wire;
import com.artemis.systems.IteratingSystem;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.kotcrab.vis.ui.widget.VisLabel;
import com.kotcrab.vis.ui.widget.VisTextField;
import dev.lyze.sar.components.player.PlayerComponent;
import dev.lyze.sar.components.player.PlayerConstants;
import dev.lyze.sar.eventsystem.EventListener;
import dev.lyze.sar.eventsystem.EventManager;
import dev.lyze.sar.eventsystem.events.ResizeEvent;
import lombok.SneakyThrows;
import lombok.var;

import java.lang.reflect.Field;
import java.util.HashMap;

@All(PlayerComponent.class)
public class PlayerConstantsDebugger extends IteratingSystem {
    private final Stage stage = new Stage(new ScreenViewport());

    @Wire private EventManager eventManager;

    private ComponentMapper<PlayerComponent> playerMapper;

    private HashMap<Field, VisTextField> textFields;

    @Override
    protected void initialize() {
        eventManager.addListener(new EventListener<ResizeEvent>(ResizeEvent.class) {
            @Override
            protected void fire(ResizeEvent event) {
                stage.getViewport().update(event.getWidth(), event.getHeight(), true);
            }
        });

        Gdx.input.setInputProcessor(stage);
    }

    @SneakyThrows
    @Override
    protected void process(int entityId) {
        var playerConstants = playerMapper.get(entityId).playerConstants;

        if (textFields != null)
            return;

        textFields = new HashMap<>();

        var constantsClass = PlayerConstants.class;

        var root = new Table();
        root.setFillParent(true);

        var table = new Table();

        for (Field field : constantsClass.getFields()) {
            var textField = new VisTextField(String.valueOf(field.get(playerConstants)));

            table.add(new VisLabel(field.getName())).left().padRight(4);
            table.add(textField);

            textFields.put(field, textField);

            table.row();
        }

        root.add(table).top().left().expand().pad(4);

        stage.addActor(root);
    }

    @Override
    protected void end() {
        stage.getViewport().apply();
        stage.act();
        stage.draw();
    }
}
