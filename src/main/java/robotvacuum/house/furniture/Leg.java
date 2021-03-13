package robotvacuum.house.furniture;

import robotvacuum.collision.CollisionShape;

/**
 *
 * @author SamL
 */
public class Leg {

    private final CollisionShape cShape;

    public Leg(CollisionShape cShape) {
        this.cShape = cShape;
    }

    /**
     * @return the cShape
     */
    public CollisionShape getcShape() {
        return cShape;
    }

}
