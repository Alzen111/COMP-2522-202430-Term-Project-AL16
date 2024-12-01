package Tools;

import com.badlogic.gdx.physics.box2d.*;
import korin.climb.Sprites.*;

/**
 * Handles collisions in the game.
 */
public class WorldContactListener implements ContactListener {
    private boolean isGrounded = false;
    private boolean isDead = false;
    private boolean isFinish = false;

    /**
     * Called when two fixtures begin to collide.
     *
     * @param contact The collision contact
     */
    @Override
    public void beginContact(Contact contact) {
        Fixture fixtureA = contact.getFixtureA();
        Fixture fixtureB = contact.getFixtureB();

        if (fixtureA.getUserData() instanceof Ground || fixtureB.getUserData() instanceof Ground) {
            isGrounded = true;
        }

        if (fixtureA.getUserData() instanceof Danger || fixtureB.getUserData() instanceof Danger) {
            isDead = true;
        }

        if (fixtureA.getUserData() instanceof Traps || fixtureB.getUserData() instanceof Traps) {
            isDead = true;
        }

        if (fixtureA.getUserData() instanceof Finish || fixtureB.getUserData() instanceof Finish) {
            isFinish = true;
        }

    }

    /**
     * Called when two fixtures stop colliding.
     *
     * @param contact The collision contact
     */
    @Override
    public void endContact(Contact contact) {
        Fixture fixtureA = contact.getFixtureA();
        Fixture fixtureB = contact.getFixtureB();

        if (fixtureA.getUserData() instanceof Ground || fixtureB.getUserData() instanceof Ground) {
            isGrounded = false;
        }
    }

    /**
     * Checks if the player is grounded.
     *
     * @return True if the player is grounded, false otherwise
     */
    public boolean isGrounded() {
        return isGrounded;
    }

    /**
     * Checks if the player is dead.
     *
     * @return True if the player is dead, false otherwise
     */
    public boolean isDead() {
        return isDead;
    }

    /**
     * Checks if the player has finished.
     *
     * @return True if the player has finished, false otherwise
     */
    public boolean isFinish() {
        return isFinish;
    }

    @Override
    public void preSolve(Contact contact, Manifold manifold) {}

    @Override
    public void postSolve(Contact contact, ContactImpulse contactImpulse) {}
}
