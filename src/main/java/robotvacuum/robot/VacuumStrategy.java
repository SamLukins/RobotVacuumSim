package robotvacuum.robot;

import robotvacuum.collision.Collision;

import java.util.Collection;

/**
 * @author SamL
 */
public interface VacuumStrategy {
    public ProposedMovement vacuum(RobotSimulationState rSimState);
}
