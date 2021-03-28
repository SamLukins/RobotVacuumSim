package robotvacuum.collision;

public class MovementCollision {
    private final Collision collision;
    private final double collisionDistance;

    public MovementCollision(Collision collision, double collisionDistance) {
        this.collision = collision;
        this.collisionDistance = collisionDistance;
    }

    public Collision getCollision() {
        return collision;
    }

    public double getCollisionDistance() {
        return collisionDistance;
    }
}
