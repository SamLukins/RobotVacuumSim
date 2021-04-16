package robotvacuum.house.furniture;

import robotvacuum.collision.CollisionTestData;

import java.util.Collection;

/**
 * @author Austen Seidler
 */

public interface Furniture {

    public Collection<CollisionTestData> getCollisionTestData();
}
