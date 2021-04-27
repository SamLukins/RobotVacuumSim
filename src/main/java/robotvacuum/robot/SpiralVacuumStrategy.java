package robotvacuum.robot;

import robotvacuum.collision.Collision;

import java.util.Collection;
import java.io.Serializable;

public class SpiralVacuumStrategy implements VacuumStrategy, Serializable {

    @Override
    public ProposedMovement vacuum(RobotSimulationState rSimState, Collection<Collision> previousCollisions) {
        return null;
    }
}
