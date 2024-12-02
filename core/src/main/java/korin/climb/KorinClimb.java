package korin.climb;

import com.badlogic.gdx.Game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import korin.climb.screen.PlayScreen;
import com.badlogic.gdx.audio.Music;


/**
 * Main class for the game.
 */
public class KorinClimb extends Game {
    public static final int V_WIDTH = 145;
    public static final int V_HEIGTH = 400;
    public static final float PPM = 100;

    public SpriteBatch batch;

    public static AssetManager assetManager;

    /**
     * Sets up the game.
     */
    @Override
    public void create() {
        batch = new SpriteBatch();
        assetManager = new AssetManager();
        assetManager.load("Music/Castlevania_ Bloody Tears  EPIC VERSION.mp3", Music.class);
        assetManager.load("Sounds/Anime People Running (One Piece) - Sound Effect for editing-[AudioTrimmer.com].mp3", Sound.class);
        assetManager.load("Sounds/Anime Smash Punch - Sound Effect for editing.mp3", Sound.class);
        assetManager.load("Sounds/Zoro Dash Sound Effect FX.mp3", Sound.class);
        assetManager.load("Sounds/Victory Sound Effect.mp3", Sound.class);
        assetManager.load("Sounds/Fail Sound Effect.mp3", Sound.class);

        assetManager.finishLoading();


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
