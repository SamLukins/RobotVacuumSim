package robotvacuum.house.furniture;

import java.util.Collection;
import robotvacuum.collision.CollisionTestData;

/**
 *
 * @author Austen Seidler
 */

public interface Furniture {

    public Collection<CollisionTestData> getCollisionTestData();
}
