package robotvacuum.robot;

import robotvacuum.collision.Collision;

import java.util.Optional;

/**
 *
 * @author SamL
 */
public interface VacuumStrategy {
    public <T extends Movement> ProposedMovement<T> vacuum(RobotSimulationState rSimState, Optional<Collision> previousCollision);
}
