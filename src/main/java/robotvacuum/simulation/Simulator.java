package robotvacuum.simulation;

import robotvacuum.collision.Collision;
import robotvacuum.collision.CollisionCircle;
import robotvacuum.collision.CollisionDetector;
import robotvacuum.collision.Position;
import robotvacuum.house.FlooringType;
import robotvacuum.house.House;
import robotvacuum.house.HouseManager;
import robotvacuum.robot.ActualMovement;
import robotvacuum.robot.ProposedMovement;
import robotvacuum.robot.RandomVacuumStrategy;
import robotvacuum.robot.RobotSimulationState;
import robotvacuum.robot.RobotVacuum;
import robotvacuum.robot.RobotVacuumProperties;
import robotvacuum.robot.VacuumStrategy;

import java.awt.*;
import java.util.Collection;
import java.util.Collections;
import java.util.Map;

public class Simulator {
    //**********************
    //test code - not final
    //**********************
    House h;
    CollisionDetector cd;
    RobotVacuumProperties robotProperties;
    RobotVacuum<? extends VacuumStrategy> rv;
    Collection<Collision> previousCollisions;

    public Simulator(House h, Position pos, int batteryLife, int vacuumEfficiency,
                     int whiskerEfficiency, int vacuumSpeed, int algorithmCode) {
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
                ((double) vacuumSpeed) / 100,
                150
        );
        rv = new RobotVacuum<>(
                robotProperties,
                new RobotSimulationState(
                        pos,
                        0,
                        batteryLife
                ),
                new RandomVacuumStrategy(100, ((double) vacuumSpeed) / 1000, 0) //change to different strategy based on algorithm code
        );
        this.h = h;
        previousCollisions = Collections.emptySet();
        cd = new CollisionDetector();
    }

    public long movement() {
        ProposedMovement proposedVacuumMovement = rv.getVacuumStrategy().vacuum(rv.getrSimState(), previousCollisions);
        ActualMovement actualMovement = cd.detectDynamicCollision(rv, h, proposedVacuumMovement);
        previousCollisions = actualMovement.getCollisions();
        rv.getrSimState().updatePosition(actualMovement);

        if (actualMovement.getMovement().isPresent()) {
            return Math.round(Math.floor((actualMovement.getMovement().get().totalTravelDistance() / rv.getProperties().getSpeed()) * 1000));
        } else {
            throw new RuntimeException("Stuck!");
        }
    }

    //obviously the vacuum isn't actually a rectangle, 
    //but the gui makes circles using rectangular bounds, so conversion is necessary
    public Rectangle getVacuumShape() {
        double tempX, tempY, tempWidth, tempHeight;
        tempX = (rv.getrSimState().getPosition().getX() - rv.getProperties().getcCircle().getRadius()) * HouseManager.SCALE_FACTOR;
        tempY = (rv.getrSimState().getPosition().getY() - rv.getProperties().getcCircle().getRadius()) * HouseManager.SCALE_FACTOR;
        tempWidth = rv.getProperties().getcCircle().getRadius() * 2 * HouseManager.SCALE_FACTOR;
        tempHeight = rv.getProperties().getcCircle().getRadius() * 2 * HouseManager.SCALE_FACTOR;
        return new Rectangle((int) tempX, (int) tempY, (int) tempWidth, (int) tempHeight);
    }

    public RobotVacuum<? extends VacuumStrategy> getVacuum() {
        return rv;
    }
}
