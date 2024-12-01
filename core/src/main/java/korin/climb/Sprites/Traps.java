package korin.climb.Sprites;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import korin.climb.KorinClimb;
import korin.climb.screen.PlayScreen;

/**
 * Represents traps in the game.
 */
public class Traps extends InteractiveTileObject {

    /**
     * Creates traps.
     *
     * @param screen The game screen
     * @param bounds The bounds of the trap
     */
    public Traps(PlayScreen screen, Rectangle bounds) {
        super(screen, bounds);
        BodyDef bdef = new BodyDef();
        FixtureDef fdef = new FixtureDef();
        PolygonShape shape = new PolygonShape();

        bdef.type = BodyDef.BodyType.StaticBody;
        bdef.position.set((bounds.getX() + bounds.getWidth() / 2) / KorinClimb.PPM, (bounds.getY() + bounds.getHeight() / 2) / KorinClimb.PPM);

        body = world.createBody(bdef);
        shape.setAsBox(bounds.getWidth() / 2 / KorinClimb.PPM, bounds.getHeight() / 2 / KorinClimb.PPM);
        fdef.shape = shape;
        fixture = body.createFixture(fdef);

        fixture.setUserData(this);
    }


}
