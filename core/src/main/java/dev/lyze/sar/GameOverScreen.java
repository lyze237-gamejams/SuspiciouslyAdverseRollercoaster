package dev.lyze.sar;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import lombok.var;

public class GameOverScreen extends HorribleMenuScreen {
    private final CharacterEnum character;

    public GameOverScreen(CharacterEnum character) {
        super();

        this.character = character;
        setupStage();
    }

    @Override
    protected void setupStage() {
        var root = new Table();
        root.setFillParent(true);

        root.add(new Label("Game Over", titleStyle)).colspan(CharacterEnum.values().length).row();
        root.add(new Label("(Ran out of time to make a proper menu)", subSubTitleStyle)).colspan(CharacterEnum.values().length).padBottom(12).row();

        var table = new Table();
        var image = new ImageButton(new TextureRegionDrawable(textureAtlas.findRegion(character.getPath() + "/Landing", 1)));
        table.add(image).colspan(3).row();

        var retry = new TextButton("Retry", buttonStyle);
        retry.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                ((Game) Gdx.app.getApplicationListener()).setScreen(new GameScreen(character));
            }
        });
        var mainMenu = new TextButton("Main Menu", buttonStyle);
        mainMenu.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                ((Game) Gdx.app.getApplicationListener()).setScreen(new MainMenuScreen());
            }
        });
        table.add(retry);
        table.add().pad(32);
        table.add(mainMenu);

        root.add(table);

        stage.addActor(root);
    }
}
