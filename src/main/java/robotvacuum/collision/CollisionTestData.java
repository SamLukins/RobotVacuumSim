package robotvacuum.collision;

import robotvacuum.space.Position;

/**
 *
 * @author SamL
 */
public class CollisionTestData {
    private final Position pos;
    private final CollisionShape cShape;

    public CollisionTestData(Position pos, CollisionShape cShape) {
        this.pos = pos;
        this.cShape = cShape;
    }

    /**
     * @return the pos
     */
    public Position getPos() {
        return pos;
    }

    /**
     * @return the cShape
     */
    public CollisionShape getcShape() {
        return cShape;
    }

}
