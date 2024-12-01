package korin.climb;

import com.badlogic.gdx.Game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import korin.climb.screen.PlayScreen;

/**
 * Main class for the game.
 */
public class KorinClimb extends Game {
    public static final int V_WIDTH = 145;
    public static final int V_HEIGTH = 400;
    public static final float PPM = 100;

    public SpriteBatch batch;

    /**
     * Sets up the game.
     */
    @Override
    public void create() {
        batch = new SpriteBatch();
        setScreen(new PlayScreen(this));

        Gdx.graphics.setWindowedMode(350, 500);
    }

    /**
     * Renders the game.
     */
    @Override
    public void render() {
        super.render();
    }
}
