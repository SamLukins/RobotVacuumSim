package robotvacuum.house.furniture;

import java.util.Collection;
import java.util.Set;
import robotvacuum.collision.CollisionTestData;

/**
 *
 * @author Austen Seidler
 */

public class Chest implements Furniture {

    private final CollisionTestData cData;

    public Chest(CollisionTestData cData) {
        this.cData = cData;
    }

    @Override
    public Collection<CollisionTestData> getCollisionTestData() {
        return Set.of(cData);
    }

    /**
     * Get the value of cRect
     *
     * @return the value of cRect
     */
    public CollisionTestData getcRect() {
        return cData;
    }
}
