package dev.lyze.sar;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import lombok.var;

public abstract class HorribleMenuScreen extends ScreenAdapter {
    protected final Stage stage = new Stage(new FitViewport(1600, 900));
    protected final Label.LabelStyle titleStyle, subTitleStyle, subSubTitleStyle;
    protected final TextButton.TextButtonStyle buttonStyle;

    protected final FreeTypeFontGenerator fontGen = new FreeTypeFontGenerator(Gdx.files.internal("Fonts/EvilEmpire-4BBVK.ttf"));
    protected final TextureAtlas textureAtlas = new TextureAtlas("Atlases/Main.atlas");

    protected final Texture mainMenuBackground = new Texture("Backgrounds/MenuBackground.png");

    public HorribleMenuScreen() {
        titleStyle = setupFont(72, Color.WHITE);
        subTitleStyle = setupFont(42, Color.WHITE);
        subSubTitleStyle = setupFont(18, Color.WHITE);

        buttonStyle = new TextButton.TextButtonStyle();
        buttonStyle.font = subTitleStyle.font;
        buttonStyle.fontColor = Color.WHITE;

        var table = new Table();
        table.setFillParent(true);
        table.setBackground(new TextureRegionDrawable(mainMenuBackground));
        stage.addActor(table);
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

    protected abstract void setupStage();

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
        titleStyle.font.dispose();
        subTitleStyle.font.dispose();
        subSubTitleStyle.font.dispose();
        fontGen.dispose();
        Gdx.input.setInputProcessor(null);
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
    }
}
