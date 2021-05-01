package robotvacuum.house.furniture;

import java.util.Collection;

import robotvacuum.collision.CollisionShape;
import robotvacuum.collision.CollisionTestData;

/**
 *
 * @author Austen Seidler
 */

public interface Furniture {

    Collection<CollisionTestData<? extends CollisionShape>> getCollisionTestData();
}
