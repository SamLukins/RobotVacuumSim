package robotvacuum.collision;

import java.util.Set;

public class MovementCollisions {
    private final Set<Collision> collisions;
    private final double collisionDistance;

    public MovementCollisions(Set<Collision> collisions, double collisionDistance) {
        this.collisions = collisions;
        this.collisionDistance = collisionDistance;
    }

    public Set<Collision> getCollisions() {
        return collisions;
    }

    public double getCollisionDistance() {
        return collisionDistance;
    }
}
