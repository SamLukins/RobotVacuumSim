package robotvacuum.robot;

/**
 * @author SamL
 */
public interface VacuumStrategy {
    ProposedMovement vacuum(RobotSimulationState rSimState);
}
