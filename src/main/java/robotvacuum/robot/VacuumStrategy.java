package robotvacuum.robot;

import robotvacuum.collision.Collision;

import java.util.Collection;

/**
 * @author SamL
 */
public interface VacuumStrategy<T extends Movement<T>> {
    public ProposedMovement<T> vacuum(RobotSimulationState rSimState, Collection<Collision> previousCollisions);
}
