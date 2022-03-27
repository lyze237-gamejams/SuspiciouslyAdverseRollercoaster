package dev.lyze.sar;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import lombok.var;

public class MainMenuScreen extends ScreenAdapter {
    private final Stage stage = new Stage(new FitViewport(1600, 900));
    private final Label.LabelStyle titleStyle, subTitleStyle, subSubTitleStyle;

    private final FreeTypeFontGenerator fontGen = new FreeTypeFontGenerator(Gdx.files.internal("Fonts/LiberationSans-Bold.ttf"));
    private final TextureAtlas textureAtlas;

    public MainMenuScreen() {
        textureAtlas = new TextureAtlas("Atlases/Main.atlas");

        titleStyle = setupFont(72, Color.WHITE);
        subTitleStyle = setupFont(42, Color.WHITE);
        subSubTitleStyle = setupFont(18, Color.WHITE);

        setupStage();
    }

    private Label.LabelStyle setupFont(int size, Color color) {
        var fontParams = new FreeTypeFontGenerator.FreeTypeFontParameter();
        fontParams.size = size;
        fontParams.incremental = true;

        var style = new Label.LabelStyle();
        style.font = fontGen.generateFont(fontParams);
        style.font.setUseIntegerPositions(true);
        style.fontColor = color;

        return style;
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);
    }

    private void setupStage() {
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
            table.add(new Label(c.toString(), subTitleStyle));
            root.add(table);
        }

        stage.addActor(root);
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(Color.BLACK);
        stage.getViewport().apply();

        stage.act();
        stage.draw();
    }

    @Override
    public void hide() {
        stage.dispose();
        textureAtlas.dispose();
        Gdx.input.setInputProcessor(null);
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
    }
}
