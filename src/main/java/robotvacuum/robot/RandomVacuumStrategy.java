package robotvacuum.robot;


import robotvacuum.collision.Collision;

import java.util.Optional;

public class RandomVacuumStrategy implements VacuumStrategy {

    private final double defaultVacuumDistance;

    public RandomVacuumStrategy(double defaultVacuumDistance) {
        this.defaultVacuumDistance = defaultVacuumDistance;
    }

    @Override
    public <T extends Movement> ProposedMovement<T> vacuum(RobotSimulationState rSimState, Optional<Collision> previousCollision) {
        if (previousCollision.isEmpty()) {

        }
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
