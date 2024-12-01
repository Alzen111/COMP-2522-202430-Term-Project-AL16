package korin.climb.Scenes;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import korin.climb.KorinClimb;

/**
 * Displays the game's HUD, including the current level.
 */
public class Hud implements Disposable {
    public Stage stage;

    Label levelNum;
    Label level;

    /**
     * Create the HUD and set up labels.
     *
     * @param sb SpriteBatch for drawing
     */
    public Hud(SpriteBatch sb) {

        Viewport viewport = new FitViewport(KorinClimb.V_WIDTH, KorinClimb.V_HEIGTH, new OrthographicCamera());
        stage = new Stage(viewport, sb);

        Table table = new Table();
        table.top();
        table.setFillParent(true);

        levelNum = new Label("1", new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        level = new Label("LEVEL", new Label.LabelStyle(new BitmapFont(), Color.WHITE));

        table.add(level).expandX().padTop(10);
        table.row();
        table.add(levelNum).expandX().padTop(2);

        stage.addActor(table);
    }

    /**
     * Cleans up resources.
     */
    @Override
    public void dispose() {
        stage.dispose();
    }

}
