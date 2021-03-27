package robotvacuum.robot;

/**
 *
 * @author SamL
 */
public class RobotVacuum {

    private final RobotVacuumProperties properties;
    private final RobotSimulationState rSimState;
    private VacuumStrategy vacuumStrategy;

    public RobotVacuum(RobotVacuumProperties properties, RobotSimulationState rSimState, VacuumStrategy vacuumStrategy) {
        this.properties = properties;
        this.rSimState = rSimState;
        this.vacuumStrategy = vacuumStrategy;
    }

}
