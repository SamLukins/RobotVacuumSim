package robotvacuum.simulation;

import robotvacuum.collision.CollisionCircle;
import robotvacuum.collision.CollisionDetector;
import robotvacuum.collision.CollisionShape;
import robotvacuum.collision.Position;
import robotvacuum.house.FlooringType;
import robotvacuum.house.House;
import robotvacuum.house.HouseManager;
import robotvacuum.house.Room;
import robotvacuum.house.Wall;
import robotvacuum.house.furniture.Chest;
import robotvacuum.house.furniture.Furniture;
import robotvacuum.house.furniture.Leg;
import robotvacuum.house.furniture.Table;
import robotvacuum.robot.ActualMovement;
import robotvacuum.robot.MovementHistory;
import robotvacuum.robot.ProposedMovement;
import robotvacuum.robot.RandomVacuumStrategy;
import robotvacuum.robot.RobotSimulationState;
import robotvacuum.robot.RobotVacuum;
import robotvacuum.robot.RobotVacuumProperties;
import robotvacuum.robot.SnakeVacuumStrategy;
import robotvacuum.robot.SpiralVacuumStrategy;
import robotvacuum.robot.VacuumStrategy;
import robotvacuum.utility.MathHelper;

import java.awt.*;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class Simulator implements Serializable {
    House h;
    CollisionDetector cd;
    RobotVacuumProperties robotProperties;
    RobotVacuum<? extends VacuumStrategy> rv;
    MathHelper mh;
    MovementHistory movementHistory = new MovementHistory();

    Map<Position, Double> cleanlinessMap;
    double totalArea, totalCleanableArea, totalCleanedArea, cleanPercent;
    Position previousPosition;
    private static final int CLEAN_SPOT_SIZE = 5;  //larger number = smaller size


    public Simulator(House h, Position pos, int batteryLife, int vacuumEfficiency,
                     int whiskerEfficiency, int vacuumSpeed, int algorithmCode) {
        double ve = ((double) vacuumEfficiency) / 100;
        double we = ((double) whiskerEfficiency) / 100;
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
        VacuumStrategy vs;
        if (algorithmCode == 2) {
            vs = new SpiralVacuumStrategy(2.0, 5.0, 0.02);
        } else if (algorithmCode == 3) {
            vs = new SnakeVacuumStrategy(((double) vacuumSpeed) / 1000, Math.PI / 6);
        } else {
            vs = new RandomVacuumStrategy(100, ((double) vacuumSpeed) / 1000, 2 * Math.PI * Math.random());
        }
        rv = new RobotVacuum<>(
                robotProperties,
                new RobotSimulationState(
                        pos,
                        0,
                        batteryLife
                ),
                vs
        );
        this.h = h;
        cd = new CollisionDetector();
        mh = new MathHelper();

        totalArea = h.getHouseWidth() * h.getHouseHeight();
        totalCleanableArea = totalArea;
        for (Room r : h.getRooms().values()) {
            for (Wall w : r.getWalls().values()) {
                totalCleanableArea -= (w.getcRect().getWidth() * w.getcRect().getHeight());
            }
            for (Furniture f : r.getAllFurniture()) {
                if (f instanceof Chest) {
                    totalCleanableArea -= (((Chest) f).getcData().getcShape().getWidth() * ((Chest) f).getcData().getcShape().getHeight());
                } else if (f instanceof Table) {
                    for (Leg l : ((Table) f).getLegs().values()) {
                        CollisionShape tempShape = l.getcShape();
                        totalCleanableArea -= ((CollisionCircle) tempShape).getRadius() * Math.PI * 2;
                    }
                }
            }
        }
        totalCleanedArea = 0;
        cleanPercent = 0;
        cleanlinessMap = new HashMap<>();
        for (int i = 0; i < h.getHouseWidth() * CLEAN_SPOT_SIZE; i++) {
            for (int j = 0; j < h.getHouseHeight() * CLEAN_SPOT_SIZE; j++) {
                cleanlinessMap.put(new Position(i, j), 0.0);
            }
        }
        previousPosition = null;
    }

    public long movement() {
        ProposedMovement proposedVacuumMovement = rv.getVacuumStrategy().vacuum(rv.getrSimState());
        ActualMovement actualMovement = cd.detectDynamicCollision(rv, h, proposedVacuumMovement);
        try {
            rv.getrSimState().updatePosition(actualMovement);
        } catch (RuntimeException e) {
            throw e;
        }
        actualMovement.getMovement().ifPresent(movementHistory::addMovement);

        int tempX = (int) (rv.getrSimState().getPosition().getX() * CLEAN_SPOT_SIZE);
        int tempY = (int) (rv.getrSimState().getPosition().getY() * CLEAN_SPOT_SIZE);
        Position tempPos = new Position(tempX, tempY);
        if (!tempPos.equals(previousPosition)) {
            for (Position p : cleanlinessMap.keySet()) {
                clean(tempPos, p);
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

    public void clean(Position tempPos, Position p) {
        if (tempPos.equals(p)) {
            double tempX = tempPos.getX();
            double tempY = tempPos.getY();
            Position p1, p2;
            double value = cleanlinessMap.get(p);
            value += rv.getProperties().getVacuumEfficiency().get(h.getFloorCovering());
            if (value > 1) {
                value = 1;
            }
            double amountCleaned = value - cleanlinessMap.get(p);
            totalCleanedArea += amountCleaned * (1.0 / (CLEAN_SPOT_SIZE * CLEAN_SPOT_SIZE));
            cleanPercent = (totalCleanedArea / totalCleanableArea) * 100;
            cleanlinessMap.put(p, value);
            previousPosition = p;
            ;
            if ((mh.normalizeAngle(rv.getrSimState().getFacingDirection()) >= Math.PI / 8 && mh.normalizeAngle(rv.getrSimState().getFacingDirection()) <= (3 * Math.PI) / 8)
                    || (mh.normalizeAngle(rv.getrSimState().getFacingDirection()) >= (-7 * Math.PI) / 8 && mh.normalizeAngle(rv.getrSimState().getFacingDirection()) <= (-5 * Math.PI) / 8)) {
                p1 = new Position(tempX + 1, tempY - 1);
                p2 = new Position(tempX - 1, tempY + 1);
            } else if ((mh.normalizeAngle(rv.getrSimState().getFacingDirection()) > (3 * Math.PI) / 8 && mh.normalizeAngle(rv.getrSimState().getFacingDirection()) < (5 * Math.PI) / 8)
                    || (mh.normalizeAngle(rv.getrSimState().getFacingDirection()) > (-5 * Math.PI) / 8 && mh.normalizeAngle(rv.getrSimState().getFacingDirection()) <= (-3 * Math.PI) / 8)) {
                p1 = new Position(tempX + 1, tempY);
                p2 = new Position(tempX - 1, tempY);
            } else if ((mh.normalizeAngle(rv.getrSimState().getFacingDirection()) > (5 * Math.PI) / 8 && mh.normalizeAngle(rv.getrSimState().getFacingDirection()) < (7 * Math.PI) / 8)
                    || (mh.normalizeAngle(rv.getrSimState().getFacingDirection()) > (-3 * Math.PI) / 8 && mh.normalizeAngle(rv.getrSimState().getFacingDirection()) <= -Math.PI / 8)) {
                p1 = new Position(tempX + 1, tempY + 1);
                p2 = new Position(tempX - 1, tempY - 1);
            } else {
                p1 = new Position(tempX, tempY - 1);
                p2 = new Position(tempX, tempY + 1);
            }

            value = cleanlinessMap.get(p1);
            value += rv.getProperties().getWhiskersEfficiency().get(h.getFloorCovering());
            if (value > 1) {
                value = 1;
            }
            amountCleaned = value - cleanlinessMap.get(p1);
            totalCleanedArea += amountCleaned * (1.0 / (CLEAN_SPOT_SIZE * CLEAN_SPOT_SIZE));
            cleanPercent = (totalCleanedArea / totalCleanableArea) * 100;
            cleanlinessMap.put(p1, value);

            value = cleanlinessMap.get(p2);
            value += rv.getProperties().getWhiskersEfficiency().get(h.getFloorCovering());
            if (value > 1) {
                value = 1;
            }
            amountCleaned = value - cleanlinessMap.get(p2);
            totalCleanedArea += amountCleaned * (1.0 / (CLEAN_SPOT_SIZE * CLEAN_SPOT_SIZE));
            cleanPercent = (totalCleanedArea / totalCleanableArea) * 100;
            cleanlinessMap.put(p2, value);
        }
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
            cleanSpots.put(new Rectangle((int) ((p.getX() / CLEAN_SPOT_SIZE) * HouseManager.SCALE_FACTOR), (int) ((p.getY() / CLEAN_SPOT_SIZE) * HouseManager.SCALE_FACTOR), (int) HouseManager.SCALE_FACTOR / CLEAN_SPOT_SIZE, (int) HouseManager.SCALE_FACTOR / CLEAN_SPOT_SIZE), cleanlinessMap.get(p));
        }
        return cleanSpots;
    }

    public House getHouse() {
        return h;
    }
}
