package Tools;

import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Rectangle;
import korin.climb.Sprites.Finish;
import korin.climb.Sprites.Ground;
import korin.climb.Sprites.Traps;
import korin.climb.screen.PlayScreen;

/**
 * Creates the game world.
 */
public class worldCreator {

    /**
     * Initializes the world objects.
     *
     * @param screen The play screen to access the map
     */
    public worldCreator(PlayScreen screen) {
        TiledMap map = screen.getMap();

        for (RectangleMapObject object : map.getLayers().get(4).getObjects().getByType(RectangleMapObject.class)) {
            Rectangle rectangle = object.getRectangle();
            new Traps(screen, rectangle);
        }

        for (RectangleMapObject object : map.getLayers().get(3).getObjects().getByType(RectangleMapObject.class)) {
            Rectangle rectangle = object.getRectangle();
            new Ground(screen, rectangle);
        }

        for (RectangleMapObject object : map.getLayers().get(2).getObjects().getByType(RectangleMapObject.class)) {
            Rectangle rectangle = object.getRectangle();
            new Finish(screen, rectangle);
        }

    }
}
