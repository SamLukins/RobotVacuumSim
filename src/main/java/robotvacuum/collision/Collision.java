package robotvacuum.collision;

/**
 * @author SamL
 */
public class Collision {

    private final Position collisionPosition;
    /**
     * Direction from the center of the colliding object to the collision in radians from -PI to PI
     */
    private final double collisionDirection;

    private final CollisionTestData ctd1;
    private final CollisionTestData ctd2;

    public Collision(Position collisionPosition, double collisionDirection, CollisionTestData ctd1, CollisionTestData ctd2) {
        this.collisionPosition = collisionPosition;
        this.collisionDirection = collisionDirection;
        this.ctd1 = ctd1;
        this.ctd2 = ctd2;
    }

    public Collision(Collision otherCollision) {
        this.collisionPosition = otherCollision.getCollisionPosition();
        this.collisionDirection = otherCollision.getCollisionDirection();
        this.ctd1 = otherCollision.getCtd1();
        this.ctd2 = otherCollision.getCtd2();
    }

    /**
     * @return the collisionPosition
     */
    public Position getCollisionPosition() {
        return collisionPosition;
    }

    /**
     * @return the collisionDirection
     */
    public double getCollisionDirection() {
        return collisionDirection;
    }

    public CollisionTestData getCtd1() {
        return ctd1;
    }

    public CollisionTestData getCtd2() {
        return ctd2;
    }
}
