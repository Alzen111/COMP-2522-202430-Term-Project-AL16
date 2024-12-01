package korin.climb.screen;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import korin.climb.KorinClimb;

/**
 * Displays the win screen when the player wins
 */
public class WinScreen implements Screen {
    private final Stage stage;

    private final Game game;

    /**
     * Sets up the win screen.
     *
     * @param game The game instance
     */
    public WinScreen(Game game) {
        this.game = game;
        Viewport viewport = new StretchViewport(KorinClimb.V_WIDTH, KorinClimb.V_HEIGTH, new OrthographicCamera());
        stage = new Stage(viewport, ((KorinClimb) game).batch);

        Label.LabelStyle gameOverFont = new Label.LabelStyle(new BitmapFont(), Color.GOLD);
        Label.LabelStyle playAgainFont = new Label.LabelStyle(new BitmapFont(), Color.WHITE);



        Table table = new Table();
        table.center();
        table.setFillParent(true);

        Label gameOverLabel = new Label("CONGRATULATIONS!", gameOverFont);
        Label playAgainLabel = new Label(" CLICK SPACE\nTO PLAY AGAIN", playAgainFont);

        table.add(gameOverLabel). expandX();
        table.row();
        table.add(playAgainLabel). expandX().padTop(10);

        stage.addActor(table);
    }

    @Override
    public void show() {

    }

    /**
     * Renders the win screen and checks for space key to play again.
     *
     * @param v Time since last render
     */
    @Override
    public void render(float v) {
        if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
            game.setScreen(new PlayScreen((KorinClimb) game));
            dispose();
        }

        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.draw();
    }

    @Override
    public void resize(int i, int i1) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    /**
     * Cleans up resources.
     */
    @Override
    public void dispose() {
        stage.dispose();
    }
}
