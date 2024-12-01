package korin.climb.Sprites;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import korin.climb.screen.PlayScreen;

/**
 * Abstract class for traps that fall.
 */
public abstract class FallingTrap extends Sprite {
    protected World world;
    protected PlayScreen screen;
    public Body body;

    /**
     * Creates a falling trap.
     *
     * @param screen The game screen
     * @param x The x position
     * @param y The y position
     */
    public FallingTrap(PlayScreen screen, float x, float y) {
        this.world = screen.getWorld();
        this.screen = screen;
        setPosition(x, y);
        defineTrap();
    }

    /**
     * Defines the trap's physical properties.
     */
    protected abstract void defineTrap();
}
