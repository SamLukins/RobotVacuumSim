package robotvacuum.robot;

/**
 *
 * @author SamL
 */
public interface VacuumStrategy {
    public Movement vacuum(RobotSimulationState rSimState);
}
