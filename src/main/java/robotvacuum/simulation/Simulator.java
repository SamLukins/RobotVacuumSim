package robotvacuum.simulation;

import robotvacuum.collision.*;
import robotvacuum.house.*;
import robotvacuum.house.furniture.*;
import robotvacuum.robot.*;

import java.awt.*;
import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.HashMap;

public class Simulator {
    //**********************
    //test code - not final
    //**********************
    House h;
    CollisionDetector cd;
    RobotVacuumProperties robotProperties;
    RobotVacuum<? extends VacuumStrategy> rv;
    Collection<Collision> previousCollisions;
    
    //test for cleaning area
    Map<Position, Double> cleanlinessMap;
    double totalArea, totalCleanableArea, totalCleanedArea, cleanPercent;
    Position previousPosition;

    public Simulator(House h, Position pos, int batteryLife, int vacuumEfficiency,
                     int whiskerEfficiency, int vacuumSpeed, int algorithmCode) {
        double ve = ((double)vacuumEfficiency)/100;
        double we = ((double)whiskerEfficiency)/100;
        robotProperties = new RobotVacuumProperties(
                new CollisionCircle(0.33),
                0.15,
                Map.of(
                        FlooringType.HARD, ve,
                        FlooringType.CUT, ve,
                        FlooringType.LOOP, ve,
                        FlooringType.FRIEZE, ve
                ),
                0.34,
                Map.of(
                        FlooringType.HARD, we,
                        FlooringType.CUT, we,
                        FlooringType.LOOP, we,
                        FlooringType.FRIEZE, we
                ),
                ((double) vacuumSpeed) / 100,
                batteryLife
        );
        rv = new RobotVacuum<>(
                robotProperties,
                new RobotSimulationState(
                        pos,
                        0,
                        batteryLife
                ),
                new RandomVacuumStrategy((int)(Math.random()*100), ((double) vacuumSpeed) / 1000, 0) //change to different strategy based on algorithm code
        );
        this.h = h;
        previousCollisions = Collections.emptySet();
        cd = new CollisionDetector();
        
        //test cleaning area
        totalArea = h.getHouseWidth()*h.getHouseHeight();
        totalCleanableArea = totalArea;
        for (Room r : h.getRooms().values()) {
            for (Wall w : r.getWalls().values()) {
                totalCleanableArea -= (w.getcRect().getWidth()*w.getcRect().getHeight());
            }
            for (Furniture f : r.getAllFurniture()) {
                if (f instanceof Chest) {
                    totalCleanableArea -= (((Chest) f).getcData().getcShape().getWidth()*((Chest) f).getcData().getcShape().getHeight());
                }
                else if (f instanceof Table) {
                    for (Leg l : ((Table) f).getLegs().values()) {
                        CollisionShape tempShape = l.getcShape();
                        totalCleanableArea -= ((CollisionCircle) tempShape).getRadius()*Math.PI*2;
                    }
                }
            }
        }
        totalCleanedArea = 0;
        cleanPercent = 0;
        cleanlinessMap = new HashMap<>();
        for (int i = 0; i < h.getHouseWidth()*2; i++) {
            for (int j = 0; j < h.getHouseHeight()*2; j++) {
                cleanlinessMap.put(new Position(i, j), 0.0);
            }
        }
        previousPosition = null;
    }

    public long movement() {
        ProposedMovement proposedVacuumMovement = rv.getVacuumStrategy().vacuum(rv.getrSimState(), previousCollisions);
        ActualMovement actualMovement = cd.detectDynamicCollision(rv, h, proposedVacuumMovement);
        previousCollisions = actualMovement.getCollisions();
        rv.getrSimState().updatePosition(actualMovement);
        
        //test cleaning area
        int tempX = (int)(rv.getrSimState().getPosition().getX()*2);
        int tempY = (int)(rv.getrSimState().getPosition().getY()*2);
        if (!(new Position(tempX, tempY)).equals(previousPosition)) {    
            for (Position p : cleanlinessMap.keySet()) {
                if (p.equals(new Position(tempX, tempY))) {
                    double value = cleanlinessMap.get(p);
                    value += rv.getProperties().getVacuumEfficiency().get(h.getFloorCovering());
                    if (value > 1) {
                        value = 1;
                    }
                    double amountCleaned = value - cleanlinessMap.get(p);
                    totalCleanedArea += amountCleaned * 0.25;
                    cleanPercent = (totalCleanedArea / totalCleanableArea) * 100;
                    cleanlinessMap.put(p, value);
                    previousPosition = p;
                }
            }
        }

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
    
    public double getTotalArea() {
        return totalArea;
    }
    
    public double getCleanableArea() {
        return totalCleanableArea;
    }
    
    public double getCleanedArea() {
        return totalCleanedArea;
    }
    
    public double getCleanPercent() {
        return cleanPercent;
    }
    
    public Map<Position, Double> getCleanlinessMap() {
        return cleanlinessMap;
    }
    
    public Map<Rectangle, Double> getCleanSpots() {
        Map<Rectangle, Double> cleanSpots = new HashMap<>();
        for (Position p : cleanlinessMap.keySet()) {
            cleanSpots.put(new Rectangle((int)((p.getX()/2)*HouseManager.SCALE_FACTOR), (int)((p.getY()/2)*HouseManager.SCALE_FACTOR), (int)HouseManager.SCALE_FACTOR/2, (int)HouseManager.SCALE_FACTOR/2), cleanlinessMap.get(p));
        }
        return cleanSpots;
    }
}
