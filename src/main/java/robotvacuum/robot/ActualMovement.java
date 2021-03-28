package robotvacuum.robot;

import robotvacuum.collision.Collision;

import java.util.Optional;

public class ActualMovement<T extends Movement> {
    private final ProposedMovement<T> proposedMovement;
    private final Optional<T> actualMovement;
    private final Optional<Collision> collision;

    public ActualMovement(ProposedMovement<T> proposedMovement, Optional<T> actualMovement, Optional<Collision> collision) {
        this.proposedMovement = proposedMovement;
        this.actualMovement = actualMovement;
        this.collision = collision;
    }

    public ProposedMovement<T> getProposedMovement() {
        return proposedMovement;
    }

    public Optional<T> getActualMovement() {
        return actualMovement;
    }

    public Optional<Collision> getCollision() {
        return collision;
    }
}
