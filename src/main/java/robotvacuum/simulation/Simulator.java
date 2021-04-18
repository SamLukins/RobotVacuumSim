package robotvacuum.simulation;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import robotvacuum.collision.*;
import robotvacuum.house.*;
import robotvacuum.robot.*;
import java.awt.Rectangle;

public class Simulator {
    //**********************
    //test code - not final
    //**********************
    House h;
    CollisionDetector cd;
    RobotVacuumProperties robotProperties;
    RobotVacuum<VacuumStrategy> rv;
    Collection<Collision> previousCollisions;
    
    public Simulator(House h) {
        robotProperties = new RobotVacuumProperties(
            new CollisionCircle(0.33),
            0.15,
            Map.of(
                    FlooringType.HARD, 0.9,
                    FlooringType.CUT, 0.75,
                    FlooringType.LOOP, 0.7,
                    FlooringType.FRIEZE, 0.65
            ),
            0.34,
            Map.of(
                    FlooringType.HARD, 0.3,
                    FlooringType.CUT, 0.3,
                    FlooringType.LOOP, 0.3,
                    FlooringType.FRIEZE, 0.3
            ),
            0.3,
            150
        );
        rv = new RobotVacuum<>(
                robotProperties,
                new RobotSimulationState(
                        new Position(5.0, 2.0),
                        0,
                        100.0
                ),
                new RandomVacuumStrategy(100, 1.0)//change later
        );
        this.h = h;
        previousCollisions = Collections.emptySet();
        cd = new CollisionDetector();
    }
    
    public void singleMovement() {
        ProposedMovement proposedVacuumMovement = rv.getVacuumStrategy().vacuum(rv.getrSimState(), previousCollisions);
        ActualMovement actualMovement = cd.detectDynamicCollision(rv, h, proposedVacuumMovement);
        previousCollisions.addAll(actualMovement.getCollisions());
        rv.getrSimState().updatePosition(actualMovement);
    }
    
    //obviously the vacuum isn't actually a rectangle, 
    //but the gui makes circles using rectangular bounds, so conversion is necessary
    public Rectangle getVacuum() {
        double tempX, tempY, tempWidth, tempHeight;
        tempX = (rv.getrSimState().getPosition().getX() - rv.getProperties().getcCircle().getRadius()) * HouseManager.SCALE_FACTOR;
        tempY = (rv.getrSimState().getPosition().getY() - rv.getProperties().getcCircle().getRadius()) * HouseManager.SCALE_FACTOR;
        tempWidth = rv.getProperties().getcCircle().getRadius() * 2 * HouseManager.SCALE_FACTOR;
        tempHeight = rv.getProperties().getcCircle().getRadius() * 2 * HouseManager.SCALE_FACTOR;
        return new Rectangle((int)tempX, (int)tempY, (int)tempWidth, (int)tempHeight);
    }
}
