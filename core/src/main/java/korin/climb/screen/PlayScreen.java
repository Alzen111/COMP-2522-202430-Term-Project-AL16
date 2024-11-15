package korin.climb.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthoCachedTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import korin.climb.KorinClimb;
import korin.climb.Scenes.Hud;

public class PlayScreen implements Screen {
    private final KorinClimb game;

    private final OrthographicCamera gamecam;
    private final Viewport gamePort;
    private Hud hud;

    private TmxMapLoader mapLoader;
    private TiledMap map;
    private OrthoCachedTiledMapRenderer renderer;

    private World world;
    private Box2DDebugRenderer debugRenderer;

    public PlayScreen(KorinClimb game) {
        this.game = game;
        gamecam = new OrthographicCamera();
        gamePort = new FitViewport(KorinClimb.V_WIDTH, KorinClimb.V_HEIGTH, gamecam);

        hud = new Hud(game.batch);

        mapLoader = new TmxMapLoader();
        map = mapLoader.load("Level1.tmx");
        renderer = new OrthoCachedTiledMapRenderer(map);
        gamecam.position.set(gamePort.getWorldWidth() / 2, gamePort.getWorldHeight() / 2, 0);
    }

    @Override
    public void show() {

    }

    public void handleInput(float dt){
        if (Gdx.input.isTouched()) {
            gamecam.position.y += 200 * dt;
        }
    }

    public void update(float dt) {
        handleInput(dt);
        gamecam.update();
        renderer.setView(gamecam);
    }

    @Override
    public void render(float v) {
        update(v);

        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        renderer.render();

        game.batch.setProjectionMatrix(hud.stage.getCamera().combined);
        hud.stage.draw();
    }

    @Override
    public void resize(int i, int i1) {
        gamePort.update(i, i1);
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

    @Override
    public void dispose() {

    }
}
