package korin.climb.Scenes;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import korin.climb.KorinClimb;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.graphics.Color;


public class Hud {
    public Stage stage;
    private Viewport viewport;

    Label scoreLabel;
    Label strScore;

    public Hud(SpriteBatch sb) {

        Integer score = 0;

        viewport = new FitViewport(KorinClimb.V_WIDTH, KorinClimb.V_HEIGTH, new OrthographicCamera());
        stage = new Stage(viewport, sb);

        Table table = new Table();
        table.top();
        table.setFillParent(true);

        scoreLabel = new Label(String.format("%06d", score), new Label.LabelStyle(new BitmapFont(), Color.WHITE ));
        strScore = new Label("Score", new Label.LabelStyle(new BitmapFont(), Color.WHITE ));

        table.add(strScore).expandX().padTop(10);
        table.row();
        table.add(scoreLabel).expandX().padTop(10);

        stage.addActor(table);
    }
}
