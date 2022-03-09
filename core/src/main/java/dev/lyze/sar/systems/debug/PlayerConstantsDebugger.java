package dev.lyze.sar.systems.debug;

import com.artemis.ComponentMapper;
import com.artemis.annotations.All;
import com.artemis.annotations.Wire;
import com.artemis.systems.IteratingSystem;
import com.artemis.utils.reflect.ClassReflection;
import com.artemis.utils.reflect.Field;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.kotcrab.vis.ui.util.FloatDigitsOnlyFilter;
import com.kotcrab.vis.ui.widget.VisLabel;
import com.kotcrab.vis.ui.widget.VisTextField;
import dev.lyze.sar.components.movement.PositionComponent;
import dev.lyze.sar.components.movement.VelocityComponent;
import dev.lyze.sar.components.player.PlayerComponent;
import dev.lyze.sar.components.player.PlayerConstants;
import dev.lyze.sar.components.player.PlayerFallStateComponent;
import dev.lyze.sar.components.player.PlayerFollowTrackComponent;
import dev.lyze.sar.eventsystem.EventListener;
import dev.lyze.sar.eventsystem.EventManager;
import dev.lyze.sar.eventsystem.events.ResizeEvent;
import lombok.SneakyThrows;
import lombok.var;

import java.util.HashMap;

@All({PlayerComponent.class, PositionComponent.class})
public class PlayerConstantsDebugger extends IteratingSystem {
    private final Stage stage = new Stage(new ScreenViewport());

    @Wire private EventManager eventManager;

    private ComponentMapper<PlayerComponent> playerMapper;
    private ComponentMapper<PositionComponent> positionMapper;

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

        if (Gdx.input.isKeyJustPressed(Input.Keys.F10)) {
            var position = positionMapper.get(entityId);
            position.getPosition().set(position.getStartPosition());

            world.edit(entityId)
                    .remove(VelocityComponent.class)
                    .add(new VelocityComponent())
                    .remove(PlayerFollowTrackComponent.class)
                    .add(new PlayerFallStateComponent());
        }

        if (textFields != null)
            return;

        setupStage(playerConstants);
    }

    @SneakyThrows
    private void setupStage(PlayerConstants playerConstants) throws IllegalAccessException {
        textFields = new HashMap<>();

        var constantsClass = PlayerConstants.class;

        var root = new Table();
        root.setFillParent(true);

        var table = new Table();

        for (var field : ClassReflection.getFields(constantsClass)) {
            var textField = new VisTextField(String.valueOf(field.get(playerConstants)));
            if (field.getType() == float.class)
                textField.setTextFieldFilter(new FloatDigitsOnlyFilter(true));

            textField.addListener(new ChangeListener() {
                @SneakyThrows
                @Override
                public void changed(ChangeEvent event, Actor actor) {
                    try {
                        if (field.getType() == float.class) {
                            field.set(playerConstants, Float.parseFloat(textField.getText()));
                        }
                        if (field.getType() == Vector2.class) {
                            var text = textField.getText();
                            var splitted = text.replace("(", "").replace(")", "").split(",");
                            field.set(playerConstants, new Vector2(Float.parseFloat(splitted[0]), Float.parseFloat(splitted[1])));
                        }
                        textField.setInputValid(true);
                    } catch (Exception e) {
                        textField.setInputValid(false);
                    }
                }
            });

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
