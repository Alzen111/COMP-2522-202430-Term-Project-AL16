package korin.climb.screen;

import Tools.WorldContactListener;
import Tools.worldCreator;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthoCachedTiledMapRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.*;
import korin.climb.KorinClimb;
import korin.climb.Scenes.Hud;
import korin.climb.Sprites.Danger;
import korin.climb.Sprites.Ninja;

/**
 * Main gameplay screen for the game.
 */
public class PlayScreen implements Screen {
    private final KorinClimb game;
    private final TextureAtlas atlas;
    private final TextureAtlas dangerAtlas;

    private final OrthographicCamera gamecam;
    private final Viewport gamePort;
    private final Hud hud;

    private final TiledMap map;
    private final OrthoCachedTiledMapRenderer renderer;

    private final World world;
    private final Box2DDebugRenderer debugRenderer;

    private final Ninja player;

    private final Vector2 gravityDirection;
    private final WorldContactListener worldContactListener;
    private final Array<Danger> dangers;

    /**
     * Creates and initializes game screen.
     *
     * @param game The game instance
     */
    public PlayScreen(KorinClimb game) {
        atlas = new TextureAtlas(Gdx.files.internal("Ninja.atlas"));
        dangerAtlas = new TextureAtlas(Gdx.files.internal("TrapsFalling.atlas"));

        this.game = game;
        gamecam = new OrthographicCamera();
        gamePort = new StretchViewport(KorinClimb.V_WIDTH / KorinClimb.PPM, KorinClimb.V_HEIGTH / KorinClimb.PPM, gamecam);

        hud = new Hud(game.batch);

        TmxMapLoader mapLoader = new TmxMapLoader();
        map = mapLoader.load("newLevel1.tmx");
        renderer = new OrthoCachedTiledMapRenderer(map, 1 / KorinClimb.PPM);
        gamecam.position.set(gamePort.getWorldWidth() / 2, gamePort.getWorldHeight() / 2, 0);

        gravityDirection = new Vector2(-100, 0);
        world = new World(gravityDirection, true);
        debugRenderer = new Box2DDebugRenderer();

        new worldCreator(this);

        WorldContactListener worldContactListener = new WorldContactListener();
        player = new Ninja(this, worldContactListener);

        world.setContactListener(worldContactListener);

        this.worldContactListener = worldContactListener;

        dangers = new Array<>();

        for (int i = 0; i < 20; i++) {
            float xPosition = MathUtils.random(.15f, 1.1f);
            float yPosition = MathUtils.random(5f, 100f);
            dangers.add(new Danger(this, dangerAtlas, xPosition, yPosition));
        }
    }

    /**
     * Returns the texture atlas for the player
     *
     * @return the texture atlas for the player
     */
    public TextureAtlas getAtlas() {
        return atlas;
    }

    @Override
    public void show() {}

    /**
     * Handles player input for changing the gravity direction.
     */
    public void handleInput() {
        if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE) && gravityDirection.x == 100) {
            gravityDirection.set(-100, 0);
            world.setGravity(gravityDirection);
        } else if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
            gravityDirection.set(100, 0);
            world.setGravity(gravityDirection);
        }
    }

    /**
     * Updates the game state.
     *
     * @param dt Time since the last update.
     */
    public void update(float dt) {
        handleInput();

        if (gravityDirection.x < 0) {
            player.body.setLinearVelocity(0, 2);
        } else if (gravityDirection.x > 0) {
            player.body.setLinearVelocity(0, 2);
        }

        if (worldContactListener.isDead()) {
            gravityDirection.set(0, -10);
            world.setGravity(gravityDirection);
        }

        player.update(dt);

        for (Danger danger : dangers) {
            danger.update(dt);
        }

        world.step(1 / 60f, 6, 2);
        gamecam.position.y = player.body.getPosition().y;

        gamecam.update();
        renderer.setView(gamecam);
    }

    /**
     * Renders the game screen
     *
     * @param delta the time passed since the last frame
     */
    @Override
    public void render(float delta) {
        update(delta);

        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        renderer.render();
        debugRenderer.render(world, gamecam.combined);

        game.batch.setProjectionMatrix(gamecam.combined);
        game.batch.begin();
        player.draw(game.batch);

        for (Danger danger : dangers) {
            danger.draw(game.batch);
        }

        game.batch.end();

        game.batch.setProjectionMatrix(hud.stage.getCamera().combined);
        hud.stage.draw();

        if (gameOver()) {
            game.setScreen(new GameOverScreen(game));
            dispose();
        }

        if (win()) {
            game.setScreen(new WinScreen(game));
            dispose();
        }
    }

    /**
     * Checks if the game is over.
     *
     * @return True if the player is dead and the state timer exceeds 1 second
     */
    public boolean gameOver() {
        return player.isDead() && player.getStateTimer() > 1;
    }

    /**
     * Checks if the player has won.
     *
     * @return True if the player has reached the finish line.
     */
    public boolean win() {
        return player.hasWon();
    }

    /**
     * Resizes the game viewport when the screen size changes.
     *
     * @param width The new width of the screen.
     * @param height The new height of the screen.
     */
    @Override
    public void resize(int width, int height) {
        gamePort.update(width, height);
    }

    /**
     * Returns the map of the game.
     *
     * @return the map
     */
    public TiledMap getMap() {
        return map;
    }

    /**
     * Returns the world of the game.
     *
     * @return The world
     */
    public World getWorld() {
        return world;
    }

    @Override
    public void pause() {}

    @Override
    public void resume() {}

    @Override
    public void hide() {}

    /**
     * Cleans up resources
     */
    @Override
    public void dispose() {
        map.dispose();
        renderer.dispose();
        world.dispose();
        debugRenderer.dispose();
        hud.dispose();
        dangerAtlas.dispose();
    }
}
