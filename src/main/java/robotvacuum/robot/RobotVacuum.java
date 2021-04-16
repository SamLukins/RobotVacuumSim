package robotvacuum.robot;

/**
 * @author SamL
 */
public class RobotVacuum<T extends VacuumStrategy> {

    private final RobotVacuumProperties properties;
    private final RobotSimulationState rSimState;
    private T vacuumStrategy;

    public RobotVacuum(RobotVacuumProperties properties, RobotSimulationState rSimState, T vacuumStrategy) {
        this.properties = properties;
        this.rSimState = rSimState;
        this.vacuumStrategy = vacuumStrategy;
    }

    public RobotVacuumProperties getProperties() {
        return properties;
    }

    public RobotSimulationState getrSimState() {
        return rSimState;
    }

    public T getVacuumStrategy() {
        return vacuumStrategy;
    }

    public void setVacuumStrategy(T vacuumStrategy) {
        this.vacuumStrategy = vacuumStrategy;
    }
}
