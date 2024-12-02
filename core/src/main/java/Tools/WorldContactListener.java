package Tools;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.physics.box2d.*;
import korin.climb.KorinClimb;
import korin.climb.Sprites.*;
import korin.climb.screen.PlayScreen;

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
            Sound groundSound = KorinClimb.assetManager.get("Sounds/Anime People Running (One Piece) - Sound Effect for editing-[AudioTrimmer.com].mp3", Sound.class);
            groundSound.loop();
        }

        if ((fixtureA.getUserData() instanceof Danger && !isDead) || (fixtureB.getUserData() instanceof Danger && !isDead)) {
            isDead = true;
            KorinClimb.assetManager.get("Sounds/Anime Smash Punch - Sound Effect for editing.mp3", Sound.class).play();
            KorinClimb.assetManager.get("Sounds/Fail Sound Effect.mp3", Sound.class).play();

        }

        if ((fixtureA.getUserData() instanceof Traps && !isDead) || (fixtureB.getUserData() instanceof Traps && !isDead)) {
            isDead = true;
            KorinClimb.assetManager.get("Sounds/Anime Smash Punch - Sound Effect for editing.mp3", Sound.class).play();
            KorinClimb.assetManager.get("Sounds/Fail Sound Effect.mp3", Sound.class).play();

        }

        if (fixtureA.getUserData() instanceof Finish || fixtureB.getUserData() instanceof Finish) {
            KorinClimb.assetManager.get("Sounds/Victory Sound Effect.mp3", Sound.class).play();
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
            Sound groundSound = KorinClimb.assetManager.get("Sounds/Anime People Running (One Piece) - Sound Effect for editing-[AudioTrimmer.com].mp3", Sound.class);
            groundSound.stop();
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
        if (isDead) {
            Sound groundSound = KorinClimb.assetManager.get("Sounds/Anime People Running (One Piece) - Sound Effect for editing-[AudioTrimmer.com].mp3", Sound.class);
            groundSound.stop();
        }
        return isDead;
    }

    /**
     * Checks if the player has finished.
     *
     * @return True if the player has finished, false otherwise
     */
    public boolean isFinish() {
        if (isFinish) {
            Sound groundSound = KorinClimb.assetManager.get("Sounds/Anime People Running (One Piece) - Sound Effect for editing-[AudioTrimmer.com].mp3", Sound.class);
            groundSound.stop();
        }
        return isFinish;
    }

    @Override
    public void preSolve(Contact contact, Manifold manifold) {}

    @Override
    public void postSolve(Contact contact, ContactImpulse contactImpulse) {}
}
