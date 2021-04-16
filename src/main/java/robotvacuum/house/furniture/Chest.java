package robotvacuum.house.furniture;

import robotvacuum.collision.CollisionTestData;

import java.io.Serializable;
import java.util.Collection;
import java.util.Set;

/**
 * @author Austen Seidler
 */

public class Chest implements Furniture, Serializable {

    private final CollisionTestData cData;

    public Chest(CollisionTestData cData) {
        this.cData = cData;
    }

    @Override
    public Collection<CollisionTestData> getCollisionTestData() {
        return Set.of(cData);
    }

    /**
     * Get the value of cData
     *
     * @return the value of cData
     */
    public CollisionTestData getcData() {
        return cData;
    }
}
