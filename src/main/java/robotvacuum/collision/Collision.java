package robotvacuum.collision;

/**
 *
 * @author SamL
 */
public class Collision {

    private final Position collisionPosition;
    /**
     * Direction from the center of the colliding object to the collision in radians from -PI to PI
     */
    private final double collisionDirection;

    public Collision(Position collisionPosition, double collisionDirection) {
        this.collisionPosition = collisionPosition;
        this.collisionDirection = collisionDirection;
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

}
