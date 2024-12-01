package korin.climb.Sprites;

import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.*;
import korin.climb.KorinClimb;
import korin.climb.screen.PlayScreen;

/**
 * Base class for interactive objects in the game.
 */
public abstract class InteractiveTileObject {
    protected World world;
    protected TiledMap map;
    protected Rectangle bounds;
    protected Body body;
    protected Fixture fixture;

    /**
     * Initializes the interactive object.
     *
     * @param screen The game screen
     * @param bounds The rectangle area for the object
     */
    public InteractiveTileObject(PlayScreen screen, Rectangle bounds) {
        this.world = screen.getWorld();
        this.map = screen.getMap();
        this.bounds = bounds;

        BodyDef bodyDef = new BodyDef();
        FixtureDef fixtureDef = new FixtureDef();
        PolygonShape shape = new PolygonShape();

        bodyDef.type = BodyDef.BodyType.StaticBody;
        bodyDef.position.set((bounds.getX() + bounds.getWidth() / 2) / KorinClimb.PPM, (bounds.getY() + bounds.getHeight() / 2) / KorinClimb.PPM);
        body = world.createBody(bodyDef);
        shape.setAsBox((bounds.getWidth() / 2) / KorinClimb.PPM, (bounds.getHeight() / 2) / KorinClimb.PPM);
        fixtureDef.shape = shape;
        fixture = body.createFixture(fixtureDef);

        fixture.setUserData(this);

        body.setUserData(this);
    }
}
