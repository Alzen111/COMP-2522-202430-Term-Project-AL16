package korin.climb.Sprites;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.Array;
import korin.climb.KorinClimb;
import korin.climb.screen.PlayScreen;
import Tools.WorldContactListener;

/**
 * The Ninja character.
 */
public class Ninja extends Sprite {
    public enum state {JUMPING, RUNNING, DEAD, FINISH}
    public state currentState;
    public state previousState;
    public World world;
    public Body body;
    private final TextureRegion ninjaDead;
    private final Animation ninjaRun;
    private final Animation ninjaJump;
    private float stateTimer;
    private boolean isDead;
    private boolean isFinish;

    private final WorldContactListener contactListener;

    /**
     * Creates Ninja.
     *
     * @param screen The game screen
     * @param contactListener The contact listener to detect events
     */
    public Ninja(PlayScreen screen, WorldContactListener contactListener) {
        super(screen.getAtlas().findRegion("PC Computer - Skul The Hero Slayer - Ninja Ninja Master"));
        this.world = screen.getWorld();
        this.contactListener = contactListener;
        currentState = state.RUNNING;
        previousState = state.RUNNING;
        stateTimer = 0;

        Array<TextureRegion> frames = new Array<>();
        frames.add(new TextureRegion(getTexture(), 7, 80, 57, 44));
        frames.add(new TextureRegion(getTexture(), 70, 80, 57, 44));
        frames.add(new TextureRegion(getTexture(), 132, 80, 57, 44));
        frames.add(new TextureRegion(getTexture(), 194, 80, 57, 44));
        frames.add(new TextureRegion(getTexture(), 256, 80, 56, 44));
        frames.add(new TextureRegion(getTexture(), 317, 80, 56, 44));
        ninjaRun = new Animation(0.1f, frames);
        frames.clear();

        frames.add(new TextureRegion(getTexture(), 191, 554, 48, 39)); // jump
        frames.add(new TextureRegion(getTexture(), 244, 554, 48, 39)); // jump
        frames.add(new TextureRegion(getTexture(), 7, 608, 47, 46)); //ready
        frames.add(new TextureRegion(getTexture(), 59, 608, 47, 46)); //ready
        frames.add(new TextureRegion(getTexture(), 145, 669, 70, 48)); //spin
        frames.add(new TextureRegion(getTexture(), 74, 669, 66, 62)); //spin
        frames.add(new TextureRegion(getTexture(), 7, 669, 62, 62)); //spin
        ninjaJump = new Animation(0.05f, frames);

        ninjaDead = new TextureRegion(getTexture(), 8, 17, 46, 46);
        defineNinja();
        setBounds(0, 0,16 / KorinClimb.PPM, 16 / KorinClimb.PPM);
        setRegion(ninjaDead);
    }

    /**
     * Updates the Ninja's position and animation frame.
     *
     * @param delta Time delta for the frame update.
     */
    public void update(float delta) {
        setPosition(body.getPosition().x - getWidth() / 2, body.getPosition().y - getHeight() / 2);
        setRegion(getFrame(delta));
        setOriginCenter();
        setScale(1.4f);

        if (currentState == state.DEAD) {
            hit();
        }

        if (currentState == state.FINISH) {
            finish();
        }
    }

    /**
     * Returns the correct animation frame based on the current state.
     *
     * @param delta Time delta for animation frame change
     * @return The correct frame for the current state
     */
    public TextureRegion getFrame(float delta) {
        currentState = getState();
        TextureRegion region;

        switch (currentState) {
            case JUMPING:
                region = (TextureRegion) ninjaJump.getKeyFrame(stateTimer, true);
                break;
            case RUNNING:
                region = (TextureRegion) ninjaRun.getKeyFrame(stateTimer, true);
                break;
            default:
                region = ninjaDead;
                break;
        }

        Vector2 gravity = world.getGravity();
        if (gravity.x < 0 && !region.isFlipX()) {
            region.flip(true, false);
            setRotation(270);
        } else if (gravity.x > 0 && region.isFlipX()) {
            region.flip(true, false);
            setRotation(90);
        }

        stateTimer = currentState == previousState ? stateTimer + delta : 0;
        previousState = currentState;
        return region;
    }

    /**
     * Determines the current state based on the contact listener.
     *
     * @return The current state of the Ninja.
     */
    public state getState() {
        if (contactListener.isDead()) {
            return state.DEAD;
        } else if (contactListener.isFinish()) {
            return state.FINISH;
        } else if (!contactListener.isGrounded()) {
            return state.JUMPING;
        } else if (body.getLinearVelocity().y != 0) {
            return state.RUNNING;
        } else {
            return state.RUNNING;
        }
    }

    /**
     * Defines the Ninja's physical body.
     */
    public void defineNinja() {
        BodyDef bodydef = new BodyDef();
        bodydef.position.set(32 / KorinClimb.PPM, 32 / KorinClimb.PPM);
        bodydef.type = BodyDef.BodyType.DynamicBody;
        body = world.createBody(bodydef);

        FixtureDef fixtureDef = new FixtureDef();
        CircleShape shape = new CircleShape();
        shape.setRadius(10 / KorinClimb.PPM);
        fixtureDef.shape = shape;
        body.createFixture(fixtureDef);
        fixtureDef.isSensor = true;

        body.setUserData(this);
    }


    /**
     * Sets the Ninja as dead.
     */
    public void hit() {
        isDead = true;
    }

    /**
     * Sets the Ninja as finished.
     */
    public void finish() {
        isFinish = true;
    }

    /**
     * Checks if the Ninja has finished.
     *
     * @return True if the Ninja has finished, false otherwise
     */
    public boolean hasWon(){
        return isFinish;
    }

    /**
     * Checks if the Ninja is dead.
     *
     * @return True if the Ninja is dead, false otherwise
     */
    public boolean isDead() {
        return isDead;
    }

    /**
     * Gets the current state timer.
     *
     * @return The current state timer
     */
    public float getStateTimer() {
        return stateTimer;
    }
}
