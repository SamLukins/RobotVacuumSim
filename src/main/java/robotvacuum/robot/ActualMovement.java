package robotvacuum.robot;

import robotvacuum.collision.Collision;

import java.util.Optional;
import java.util.Set;

public class ActualMovement<T extends Movement> {
    private final ProposedMovement<T> proposedMovement;
    private final Optional<T> actualMovement;
    private final Set<Collision> collisions;

    public ActualMovement(ProposedMovement<T> proposedMovement, Optional<T> actualMovement, Set<Collision> collisions) {
        this.proposedMovement = proposedMovement;
        this.actualMovement = actualMovement;
        this.collisions = collisions;
    }

    public ProposedMovement<T> getProposedMovement() {
        return proposedMovement;
    }

    public Optional<T> getMovement() {
        return actualMovement;
    }

    public Set<Collision> getCollisions() {
        return collisions;
    }
}
