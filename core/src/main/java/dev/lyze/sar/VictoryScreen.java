package dev.lyze.sar;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import lombok.var;

public class VictoryScreen extends HorribleMenuScreen {
    private final CharacterEnum character;

    public VictoryScreen(CharacterEnum character) {
        super();

        this.character = character;

        addBackground();
        setupStage();
    }

    @Override
    public void show() {
        super.show();
        MusicStuff.getClouds().stop();
        MusicStuff.getTown().play();
    }

    @Override
    protected void setupStage() {
        var root = new Table();
        root.setFillParent(true);

        root.add(new Label("Victory!", titleStyle)).colspan(CharacterEnum.values().length).row();
        root.add(new Label("(Ran out of time to make a proper menu)", subSubTitleStyle)).colspan(CharacterEnum.values().length).padBottom(12).row();
        root.add(new Label("Thanks for playing", subTitleStyle)).padBottom(32).row();

        var table = new Table();
        var image = new ImageButton(new TextureRegionDrawable(textureAtlas.findRegion(character.getPath() + "/" + "Idle")));
        table.add(image).row();

        var mainMenu = new TextButton("Main Menu", buttonStyle);
        mainMenu.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                ((Game) Gdx.app.getApplicationListener()).setScreen(new MainMenuScreen());
            }
        });
        table.add(mainMenu);

        root.add(table);

        stage.addActor(root);
    }
}
