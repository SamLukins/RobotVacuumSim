package robotvacuum.robot;

import org.junit.jupiter.api.Test;
import robotvacuum.collision.Collision;
import robotvacuum.collision.CollisionCircle;
import robotvacuum.collision.CollisionDetector;
import robotvacuum.collision.CollisionRectangle;
import robotvacuum.collision.Position;
import robotvacuum.house.FlooringType;
import robotvacuum.house.House;
import robotvacuum.house.Room;
import robotvacuum.house.Wall;

import java.util.Collections;
import java.util.Map;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class RobotVacuumIntegrationTest {

    final double root2 = Math.sqrt(2.0);
    long defaultSeed = 100;

    RobotVacuumProperties testRobotProperties = new RobotVacuumProperties(
            new CollisionCircle(1.0),
            0.5,
            Map.of(
                    FlooringType.HARD, 0.9,
                    FlooringType.CUT, 0.8,
                    FlooringType.LOOP, 0.7,
                    FlooringType.FRIEZE, 0.6
            ),
            1.2,
            Map.of(
                    FlooringType.HARD, 0.8,
                    FlooringType.CUT, 0.7,
                    FlooringType.LOOP, 0.6,
                    FlooringType.FRIEZE, 0.5
            ),
            1.0,
            100
    );

    @Test
    void canCreateRobotVacuum() {

        RobotVacuum<VacuumStrategy> rv = new RobotVacuum<>(
                testRobotProperties,
                new RobotSimulationState(
                        new Position(5.0, 5.0),
                        0,
                        100.0
                ),
                new RandomVacuumStrategy(defaultSeed, 3.0)
        );

        assertEquals(5.0, rv.getrSimState().getPosition().getX(), Math.ulp(0.0));
        assertEquals(5.0, rv.getrSimState().getPosition().getY(), Math.ulp(0.0));
        assertEquals(0.0, rv.getrSimState().getFacingDirection(), Math.ulp(0.0));
        assertEquals(100.0, rv.getrSimState().getRemainingBattery(), Math.ulp(0.0));
    }

    @Test
    void robotProposesMovement() {
        RobotVacuum<VacuumStrategy> rv = new RobotVacuum<>(
                testRobotProperties,
                new RobotSimulationState(
                        new Position(2.0, 5.0),
                        0,
                        100.0
                ),
                new RandomVacuumStrategy(defaultSeed, 3.0)
        );

        ProposedMovement proposedVacuumMovement = rv.getVacuumStrategy().vacuum(rv.getrSimState());
        assertNotNull(proposedVacuumMovement);
        assertEquals(0.0, proposedVacuumMovement.getMov().getStartPos().directionTo(proposedVacuumMovement.getMov().getStopPos()), Math.ulp(0.0));
        assertEquals(3.0, proposedVacuumMovement.getMov().getStartPos().distanceTo(proposedVacuumMovement.getMov().getStopPos()), Math.ulp(0.0));
    }

    @Test
    void robotCanMove() {
        RobotVacuum<VacuumStrategy> rv = new RobotVacuum<>(
                testRobotProperties,
                new RobotSimulationState(
                        new Position(2.0, 5.0),
                        0,
                        100.0
                ),
                new RandomVacuumStrategy(defaultSeed, 3.0)
        );

        House testHouse = new House(
                Map.of(
                        new Position(0.0, 0.0),
                        new Room(Map.of(
                                new Position(8.0, 5.0), new Wall(new CollisionRectangle(2.0, 10.0)),
                                new Position(5.0, 8.0), new Wall(new CollisionRectangle(10.0, 2.0))
                        ))
                ),
                FlooringType.HARD
        );

        ProposedMovement proposedVacuumMovement = rv.getVacuumStrategy().vacuum(rv.getrSimState());
        assertNotNull(proposedVacuumMovement);
        assertEquals(0.0, proposedVacuumMovement.getMov().getStartPos().directionTo(proposedVacuumMovement.getMov().getStopPos()), Math.ulp(0.0));
        assertEquals(3.0, proposedVacuumMovement.getMov().getStartPos().distanceTo(proposedVacuumMovement.getMov().getStopPos()), Math.ulp(0.0));


        CollisionDetector cd = new CollisionDetector();
        ActualMovement actualMovement = cd.detectDynamicCollision(rv, testHouse, proposedVacuumMovement);

        Set<Collision> collisions = actualMovement.getCollisions();
        assertTrue(collisions.isEmpty());
        Position stopPos = actualMovement.getMovement().orElseThrow().getStopPos();

        assertEquals(5.0, stopPos.getX(), Math.ulp(0.0));
        assertEquals(5.0, stopPos.getY(), Math.ulp(0.0));
    }

    @Test
    void robotCollidesWithSingleWall() {
        RobotVacuum<VacuumStrategy> rv = new RobotVacuum<>(
                testRobotProperties,
                new RobotSimulationState(
                        new Position(5.0, 5.0),
                        0,
                        100.0
                ),
                new RandomVacuumStrategy(defaultSeed, 3.0)
        );

        House testHouse = new House(
                Map.of(
                        new Position(0.0, 0.0),
                        new Room(Map.of(
                                new Position(7.0, 0.0), new Wall(new CollisionRectangle(2.0, 10.0))
                        ))
                ),
                FlooringType.HARD
        );

        ProposedMovement proposedVacuumMovement = rv.getVacuumStrategy().vacuum(rv.getrSimState());
        assertNotNull(proposedVacuumMovement);

        CollisionDetector cd = new CollisionDetector();
        ActualMovement actualMovement = cd.detectDynamicCollision(rv, testHouse, proposedVacuumMovement);

        Set<Collision> collisions = actualMovement.getCollisions();
        assertFalse(collisions.isEmpty());
        assertEquals(1, collisions.size());
        Collision collision = collisions.stream().findFirst().orElseThrow();

        Position collisionPosition = collision.getCollisionPosition();
        assertEquals(7.0, collisionPosition.getX(), Math.ulp(7.0));
        assertEquals(5.0, collisionPosition.getY(), Math.ulp(5.0));
    }

    @Test
    void robotCollidesWithSingleWallMultipleWallsPresent() {
        RobotVacuum<VacuumStrategy> rv = new RobotVacuum<>(
                testRobotProperties,
                new RobotSimulationState(
                        new Position(5.0, 5.0),
                        0,
                        100.0
                ),
                new RandomVacuumStrategy(defaultSeed, 3.0)
        );

        House testHouse = new House(
                Map.of(
                        new Position(0.0, 0.0),
                        new Room(Map.of(
                                new Position(7.0, 0.0), new Wall(new CollisionRectangle(2.0, 10.0)),
                                new Position(0.0, 7.0), new Wall(new CollisionRectangle(10.0, 2.0))
                        ))
                ),
                FlooringType.HARD
        );

        ProposedMovement proposedVacuumMovement = rv.getVacuumStrategy().vacuum(rv.getrSimState());
        assertNotNull(proposedVacuumMovement);

        CollisionDetector cd = new CollisionDetector();
        ActualMovement actualMovement = cd.detectDynamicCollision(rv, testHouse, proposedVacuumMovement);

        Set<Collision> collisions = actualMovement.getCollisions();
        assertFalse(collisions.isEmpty());
        assertEquals(1, collisions.size());
        Collision collision = collisions.stream().findFirst().orElseThrow();

        Position collisionPosition = collision.getCollisionPosition();
        assertEquals(7.0, collisionPosition.getX(), Math.ulp(7.0));
        assertEquals(5.0, collisionPosition.getY(), Math.ulp(5.0));
    }

    @Test
    void robotCollidesWithSecondWall() {
        RobotVacuum<VacuumStrategy> rv = new RobotVacuum<>(
                testRobotProperties,
                new RobotSimulationState(
                        new Position(5.0, 5.0),
                        0,
                        100.0
                ),
                new RandomVacuumStrategy(defaultSeed, 3.0, Math.PI / 2)
        );

        House testHouse = new House(
                Map.of(
                        new Position(0.0, 0.0),
                        new Room(Map.of(
                                new Position(7.0, 0.0), new Wall(new CollisionRectangle(2.0, 10.0)),
                                new Position(0.0, 7.0), new Wall(new CollisionRectangle(10.0, 2.0))
                        ))
                ),
                FlooringType.HARD
        );

        ProposedMovement proposedVacuumMovement = rv.getVacuumStrategy().vacuum(rv.getrSimState());
        assertNotNull(proposedVacuumMovement);

        CollisionDetector cd = new CollisionDetector();
        ActualMovement actualMovement = cd.detectDynamicCollision(rv, testHouse, proposedVacuumMovement);

        Set<Collision> collisions = actualMovement.getCollisions();
        assertFalse(collisions.isEmpty());
        assertEquals(1, collisions.size());
        Collision collision = collisions.stream().findFirst().orElseThrow();

        Position collisionPosition = collision.getCollisionPosition();
        assertEquals(5.0, collisionPosition.getX(), Math.ulp(5.0));
        assertEquals(7.0, collisionPosition.getY(), Math.ulp(7.0));
    }

    @Test
    void robotCollidesWithWallCorner() {
        RobotVacuum<VacuumStrategy> rv = new RobotVacuum<>(
                testRobotProperties,
                new RobotSimulationState(
                        new Position(5.0, 5.0),
                        0,
                        100.0
                ),
                new RandomVacuumStrategy(defaultSeed, 5.0, Math.PI / 4)
        );

        House testHouse = new House(
                Map.of(
                        new Position(0.0, 0.0),
                        new Room(Map.of(
                                new Position(7.0, 0.0), new Wall(new CollisionRectangle(2.0, 10.0)),
                                new Position(0.0, 7.0), new Wall(new CollisionRectangle(10.0, 2.0))
                        ))
                ),
                FlooringType.HARD
        );

        ProposedMovement proposedVacuumMovement = rv.getVacuumStrategy().vacuum(rv.getrSimState());
        assertNotNull(proposedVacuumMovement);
        Movement mov = proposedVacuumMovement.getMov();
        assertEquals(Math.PI / 4, mov.getStartPos().directionTo(mov.getStopPos()));

        CollisionDetector cd = new CollisionDetector();
        ActualMovement actualMovement = cd.detectDynamicCollision(rv, testHouse, proposedVacuumMovement);

        Set<Collision> collisions = actualMovement.getCollisions();
        assertFalse(collisions.isEmpty());
        assertEquals(2, collisions.size());

        Position stopPos = actualMovement.getMovement().orElseThrow().getStopPos();

        assertEquals(root2, actualMovement.getMovement().get().totalTravelDistance(), 0.01);
        assertEquals(6.0, stopPos.getX(), 0.01);
        assertEquals(6.0, stopPos.getY(), 0.01);
    }

    @Test
    void robotMoveAfterCollision() {
        RobotVacuum<VacuumStrategy> rv = new RobotVacuum<>(
                testRobotProperties,
                new RobotSimulationState(
                        new Position(5.0, 5.0),
                        0,
                        100.0
                ),
                new RandomVacuumStrategy(defaultSeed, 3.0)
        );

        House testHouse = new House(
                Map.of(
                        new Position(0.0, 0.0),
                        new Room(Map.of(
                                new Position(8.0, 5.0), new Wall(new CollisionRectangle(2.0, 10.0))
                        ))
                ),
                FlooringType.HARD
        );

        ProposedMovement proposedVacuumMovement = rv.getVacuumStrategy().vacuum(rv.getrSimState());
        assertNotNull(proposedVacuumMovement);

        CollisionDetector cd = new CollisionDetector();
        ActualMovement actualMovement = cd.detectDynamicCollision(rv, testHouse, proposedVacuumMovement);

        Set<Collision> collisions = actualMovement.getCollisions();
        assertFalse(collisions.isEmpty());
        assertEquals(1, collisions.size());

        rv.getrSimState().updatePosition(actualMovement);

        ProposedMovement secondVacuum = rv.getVacuumStrategy().vacuum(rv.getrSimState());
        ActualMovement actualMovement2 = cd.detectDynamicCollision(rv, testHouse, secondVacuum);

        assertTrue(actualMovement2.getCollisions().isEmpty());
    }
}