package dev.lyze.sar;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Align;
import lombok.var;

public class IntroScreen extends HorribleMenuScreen {
    public IntroScreen() {
        super();
        setupStage();
    }

    @Override
    protected void setupStage() {
        var titleImage = new Image(new Texture("Backgrounds/Title.png"));
        titleImage.setColor(1, 1, 1, 0);
        titleImage.addAction(Actions.fadeIn(2f));

        stage.addActor(titleImage);

        var root = new Table();
        root.setFillParent(true);

        var table = new Table();
        var title = new Label("Suspiciously Adverse\nRoller-coaster", titleStyle);
        title.setColor(1, 1, 1, 0);
        title.setAlignment(Align.right);
        title.addAction(Actions.sequence(Actions.delay(1.2f), Actions.fadeIn(1f)));
        table.add(title).row();

        var subTitle = new Label("Press any key", subSubTitleStyle);
        subTitle.setColor(1, 1, 1, 0);
        subTitle.setAlignment(Align.right);
        subTitle.addAction(Actions.sequence(Actions.delay(2f), Actions.fadeIn(1f)));
        table.add(subTitle).expand().right();

        root.add(table).expand().right().padRight(84);
        stage.addActor(root);
    }

    @Override
    public void render(float delta) {
        super.render(delta);

        if (Gdx.input.isKeyPressed(Input.Keys.ANY_KEY) || Gdx.input.isButtonPressed(Input.Buttons.LEFT)) {
            stage.addAction(Actions.sequence(Actions.fadeOut(1f), Actions.run(() -> ((Game) Gdx.app.getApplicationListener()).setScreen(new MainMenuScreen()))));
        }
    }
}
