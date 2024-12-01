package korin.climb.Sprites;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.utils.Array;
import korin.climb.KorinClimb;
import korin.climb.screen.PlayScreen;

/**
 * A falling trap.
 */
public class Danger extends FallingTrap {
    private float stateTime;
    private final Animation<TextureRegion> changeColor;

    /**
     * Creates the trap.
     *
     * @param screen The game screen
     * @param dangerAtlas The texture atlas for the trap
     * @param x The x position
     * @param y The y position
     */
    public Danger(PlayScreen screen, TextureAtlas dangerAtlas, float x, float y) {
        super(screen, x, y);
        Array<TextureRegion> frames = new Array<>();

        TextureRegion trapRegion = dangerAtlas.findRegion("Browser Games - Office Trap - Zombie Trap");

        frames.add(new TextureRegion(trapRegion, 1, 305, 22, 20));
        frames.add(new TextureRegion(trapRegion, 24, 305, 22, 20));

        changeColor = new Animation<>(0.2f, frames);
        stateTime = 0;

        setBounds(getX(), getY(), 16 / KorinClimb.PPM, 16 / KorinClimb.PPM);
    }

    /**
     * Updates the trap position and animation.
     *
     * @param delta Time passed since last update
     */
    public void update(float delta) {
        stateTime += delta;

        float fallSpeed = 0.01f;
        float newY = getY() - fallSpeed;
        setPosition(getX(), newY);

        setRegion(changeColor.getKeyFrame(stateTime, true));

        body.setTransform(getX() + getWidth() / 2, getY() + getHeight() / 2, 0);
    }

    /**
     * Sets up the trap's physical properties.
     */
    @Override
    protected void defineTrap() {
        BodyDef bodyDef = new BodyDef();
        bodyDef.position.set(getX(), getY());
        bodyDef.type = BodyDef.BodyType.KinematicBody;
        body = world.createBody(bodyDef);

        FixtureDef fixtureDef = new FixtureDef();
        CircleShape shape = new CircleShape();
        shape.setRadius(8 / KorinClimb.PPM);
        fixtureDef.shape = shape;

        body.createFixture(fixtureDef).setUserData(this);

        shape.dispose();
    }
}
