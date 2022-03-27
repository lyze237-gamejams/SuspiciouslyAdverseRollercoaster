package dev.lyze.sar;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import lombok.var;

public class MainMenuScreen extends HorribleMenuScreen {
    public MainMenuScreen() {
        super();
        setupStage();
    }

    @Override
    protected void setupStage() {
        var root = new Table();
        root.setFillParent(true);

        root.add(new Label("Suspiciously Adverse Roller-coaster", titleStyle)).colspan(CharacterEnum.values().length).row();
        root.add(new Label("(Ran out of time to make a proper menu)", subSubTitleStyle)).colspan(CharacterEnum.values().length).padBottom(12).row();
        root.add(new Label("Select a character", subTitleStyle)).colspan(CharacterEnum.values().length).padBottom(32).row();

        for (CharacterEnum c : CharacterEnum.values()) {
            var table = new Table();
            var image = new ImageButton(new TextureRegionDrawable(textureAtlas.findRegion(c.getPath() + "/Idle")));
            image.addListener(new ChangeListener() {
                @Override
                public void changed(ChangeEvent event, Actor actor) {
                    ((Game) Gdx.app.getApplicationListener()).setScreen(new GameScreen(c));
                }
            });
            table.add(image).row();
            var text = new Label(c.toString(), subTitleStyle);
            text.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    ((Game) Gdx.app.getApplicationListener()).setScreen(new GameScreen(c));
                }
            });
            table.add(text);
            root.add(table);
        }

        stage.addActor(root);
    }
}
