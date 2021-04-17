package robotvacuum.collision;

import robotvacuum.house.House;
import robotvacuum.robot.ActualMovement;
import robotvacuum.robot.Movement;
import robotvacuum.robot.ProposedMovement;
import robotvacuum.robot.RobotVacuum;
import robotvacuum.robot.VacuumStrategy;

import java.util.Collections;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author SamL
 */
public class CollisionDetector {

    public Optional<Collision> detectStaticCollision(CollisionTestData ctd1, CollisionTestData ctd2) {
        if (Shape.CIRCLE == ctd1.getcShape().getShape() && Shape.CIRCLE == ctd2.getcShape().getShape()) {
            return detectStaticCollisionBetweenCircles(ctd1, ctd2);
        } else if (Shape.CIRCLE == ctd1.getcShape().getShape() && Shape.RECTANGLE == ctd2.getcShape().getShape()) {
            return detectStaticCollisionBetweenCircleAndRectangle(ctd1, ctd2);
        } else if (Shape.RECTANGLE == ctd1.getcShape().getShape() && Shape.CIRCLE == ctd2.getcShape().getShape()) {
            return detectStaticCollisionBetweenRectangleAndCircle(ctd1, ctd2);
        } else if (Shape.RECTANGLE == ctd1.getcShape().getShape() && Shape.RECTANGLE == ctd2.getcShape().getShape()) {
            return detectStaticCollisionBetweenRectangles(ctd1, ctd2);
        }
        throw new UnsupportedOperationException("Can only detect collisions between a combination of rectangles and circles");
    }

    public Set<Collision> detectStaticCollision(CollisionTestData ctd1, Set<CollisionTestData> ctds) {
        return ctds.stream()
                .flatMap(ctd -> detectStaticCollision(ctd1, ctd).stream())
                .collect(Collectors.toSet());
    }

    public Set<Collision> detectStaticCollision(RobotVacuum<VacuumStrategy> rv, House house) {
        return detectStaticCollision(
                new CollisionTestData(
                        rv.getrSimState().getPosition(),
                        rv.getProperties().getcCircle()
                ),
                house.getRooms().entrySet().stream().flatMap(
                        entry -> entry
                                .getValue()
                                .getWalls()
                                .entrySet()
                                .stream()
                                .map(wallEntry -> new CollisionTestData(entry.getKey().offsetPositionCartesian(wallEntry.getKey()),
                                        wallEntry.getValue().getcRect()))
                ).collect(Collectors.toSet()));
    }

    Optional<Collision> detectStaticCollisionBetweenRectangles(CollisionTestData ctd1, CollisionTestData ctd2) {
        final CollisionRectangle cRect1 = (CollisionRectangle) ctd1.getcShape();
        final CollisionRectangle cRect2 = (CollisionRectangle) ctd2.getcShape();
        //max distance that still has potential overlap
        final double xOverlap = (cRect1.getWidth() / 2) + (cRect2.getWidth() / 2);
        final double yOverlap = (cRect1.getHeight() / 2) + (cRect2.getHeight() / 2);
        //diffs between object centers
        final double xDiff = ctd1.getPos().xDiff(ctd2.getPos());
        final double yDiff = ctd1.getPos().yDiff(ctd2.getPos());

        //if there is overlap (collision) somewhere
        if (Math.abs(xDiff) <= xOverlap && Math.abs(yDiff) <= yOverlap) {
            //get positions of sides of the overlap rectangle
            double leftSideX = Math.max(ctd1.getPos().getX() - (cRect1.getWidth() / 2), ctd2.getPos().getX() - (cRect2.getWidth() / 2));
            double rightSideX = Math.min(ctd1.getPos().getX() + (cRect1.getWidth() / 2), ctd2.getPos().getX() + (cRect2.getWidth() / 2));
            double topSideY = Math.min(ctd1.getPos().getY() + (cRect1.getHeight() / 2), ctd2.getPos().getY() + (cRect2.getHeight() / 2));
            double bottomSideY = Math.max(ctd1.getPos().getY() - (cRect1.getHeight() / 2), ctd2.getPos().getY() - (cRect2.getHeight() / 2));

            Position overlapRectangleCenter = new Position((leftSideX + rightSideX) / 2, (topSideY + bottomSideY) / 2);

            return Optional.of(new Collision(
                    overlapRectangleCenter,
                    ctd1.getPos().directionTo(overlapRectangleCenter),
                    ctd1,
                    ctd2));
        }
        return Optional.empty();
    }

    Optional<Collision> detectStaticCollisionBetweenCircleAndRectangle(CollisionTestData ctd1, CollisionTestData ctd2) {
        final double circleRadius = ((CollisionCircle) ctd1.getcShape()).getRadius();
        final CollisionRectangle cRect = (CollisionRectangle) ctd2.getcShape();
        final double xOverlap = circleRadius + (cRect.getWidth() / 2);
        final double yOverlap = circleRadius + (cRect.getHeight() / 2);
        final double xDiff = ctd1.getPos().xDiff(ctd2.getPos());
        final double yDiff = ctd1.getPos().yDiff(ctd2.getPos());

        //if potential x or y overlap
        if (Math.abs(xDiff) <= xOverlap && Math.abs(yDiff) <= yOverlap) {
            //if above or below no corner
            if (Math.abs(xDiff) <= (cRect.getWidth() / 2)) {
                final double collisionYPos = ctd1.getPos().getY() + (Math.signum(yDiff) * (cRect.getHeight() / 2));

                final Position collisionPosition = new Position(ctd1.getPos().getX(), collisionYPos);
                final double collisionDirection = -Math.signum(yDiff) * Math.PI / 2;

                return Optional.of(new Collision(collisionPosition, collisionDirection, ctd1, ctd2));

                //if side no corner
            } else if (Math.abs(yDiff) <= (cRect.getHeight() / 2)) {
                final double collisionXPos = ctd1.getPos().getX() + (Math.signum(xDiff) * (cRect.getWidth() / 2));

                final Position collisionPosition = new Position(collisionXPos, ctd1.getPos().getY());
                double collisionDirection = 0;
                if (Math.signum(xDiff) < 0) {
                    collisionDirection = Math.PI;
                }

                return Optional.of(new Collision(collisionPosition, collisionDirection, ctd1, ctd2));

                //if in corner
            } else {
                final Position cornerPos = new Position(
                        ctd1.getPos().getX() + (-Math.signum(xDiff) * cRect.getWidth() / 2),
                        ctd1.getPos().getY() + (-Math.signum(yDiff) * cRect.getHeight() / 2)
                );

                if (ctd1.getPos().distanceTo(cornerPos) <= circleRadius) {
                    return Optional.of(new Collision(cornerPos, ctd1.getPos().directionTo(cornerPos), ctd1, ctd2));
                } else {
                    return Optional.empty();
                }
            }
        }
        return Optional.empty();
    }

    Optional<Collision> detectStaticCollisionBetweenRectangleAndCircle(CollisionTestData ctd1, CollisionTestData ctd2) {
        //call the other function with swapped arguments
        return detectStaticCollisionBetweenCircleAndRectangle(ctd2, ctd1)
                .map((Collision col) -> new Collision(col.getCollisionPosition(),
                        //swap the collision direction but make sure it stays in -PI to PI
                        col.getCollisionDirection() <= 0
                                ? col.getCollisionDirection() + Math.PI
                                : col.getCollisionDirection() - Math.PI,
                        ctd1, ctd2));
    }

    Optional<Collision> detectStaticCollisionBetweenCircles(CollisionTestData ctd1, CollisionTestData ctd2) {
        double circle1Radius = ((CollisionCircle) ctd1.getcShape()).getRadius();
        double circle2Radius = ((CollisionCircle) ctd2.getcShape()).getRadius();
        double radiusSum = circle1Radius + circle2Radius;

        double distanceBetweenCenters = ctd1.getPos().distanceTo(ctd2.getPos());

        if (distanceBetweenCenters <= radiusSum) {
            double relativeSize = circle1Radius / radiusSum;
            Position collisionPosition = ctd1.getPos().linearInterpolateTo(ctd2.getPos(), relativeSize);
            double collisionDirection = ctd1.getPos().directionTo(ctd2.getPos());

            return Optional.of(new Collision(collisionPosition, collisionDirection, ctd1, ctd2));
        }
        return Optional.empty();
    }

    public ActualMovement detectDynamicCollision(CollisionTestData ctd1, CollisionTestData ctd2, ProposedMovement proposedMovement) {
        return detectDynamicCollision(ctd1, Set.of(ctd2), proposedMovement);
    }

    public ActualMovement detectDynamicCollision(CollisionTestData ctd1, Set<CollisionTestData> ctds, ProposedMovement proposedMovement) {
        try {
            //if continue colliding
            Set<Collision> startCollisions = detectStaticCollision(ctd1, ctds);
            if (!startCollisions.isEmpty()) {
                Position collisionTestPosition = proposedMovement.getMov().fixedDistancePosition(Math.min(0.005, proposedMovement.getMov().totalTravelDistance()));
                Set<Collision> movementCollisions = detectStaticCollision(new CollisionTestData(collisionTestPosition, ctd1.getcShape()), ctds);


                //if movement would continue collision
                if (!movementCollisions.isEmpty()) {
                    //already colliding and movement continues colliding
                    return new ActualMovement(proposedMovement, Optional.empty(), startCollisions);
                }
            }

            //check every centimeter for a collision
            double initialCheckResolution = 0.01;
            for (double travelDistance = 0; travelDistance <= proposedMovement.getMov().totalTravelDistance(); travelDistance += initialCheckResolution) {
                Set<Collision> testCollisions = detectStaticCollision(
                        new CollisionTestData(proposedMovement.getMov().fixedDistancePosition(travelDistance), ctd1.getcShape()),
                        ctds);

                //if there is a collision
                if (!testCollisions.isEmpty()) {
                    //get a more accurate collision
                    //millimeter resolution
                    double resolution = 0.001;

                    binarySearchDynamicCollision(
                            ctd1, ctds, proposedMovement, travelDistance - initialCheckResolution, travelDistance, new HashSet<>(), resolution
                    );

                    MovementCollisions finalCollision = binarySearchDynamicCollision(
                            ctd1, ctds, proposedMovement, travelDistance - initialCheckResolution, travelDistance,
                            testCollisions, resolution);

                    //create and return the actual resulting movement to the collision
                    return new ActualMovement(
                            proposedMovement,
                            Optional.of(proposedMovement.getMov().partialFixedDistanceMovement(finalCollision.getCollisionDistance())),
                            finalCollision.getCollisions());
                }
            }
        } catch (Exception e) {
            System.err.println("Something has gone horribly wrong.");
            e.printStackTrace();
        }

        //no collision detected, so the actual movement is the entire proposed movement
        return new ActualMovement(proposedMovement, Optional.of(proposedMovement.getMov()), Collections.emptySet());
    }

    public ActualMovement detectDynamicCollision(RobotVacuum<VacuumStrategy> rv, House house, ProposedMovement proposedMovement) {
        return detectDynamicCollision(
                new CollisionTestData(rv.getrSimState().getPosition(), rv.getProperties().getcCircle()),
                house.getRooms().entrySet().stream().flatMap(
                        entry -> entry
                                .getValue()
                                .getWalls()
                                .entrySet()
                                .stream()
                                .map(wallEntry -> new CollisionTestData(entry.getKey().offsetPositionCartesian(wallEntry.getKey()),
                                        wallEntry.getValue().getcRect()))
                ).collect(Collectors.toSet()),
                proposedMovement);
    }

    MovementCollisions binarySearchDynamicCollision(
            CollisionTestData ctd1,
            CollisionTestData ctd2,
            ProposedMovement proposedMovement,
            double distanceToNoCollision,
            double distanceToCollision,
            Collision collision,
            double resolution) {
        return binarySearchDynamicCollision(ctd1, Set.of(ctd2), proposedMovement, distanceToNoCollision, distanceToCollision, Set.of(collision), resolution);
    }

    MovementCollisions binarySearchDynamicCollision(
            CollisionTestData ctd1,
            Set<CollisionTestData> ctds,
            ProposedMovement proposedMovement,
            double distanceToNoCollision,
            double distanceToCollision,
            Set<Collision> collisions,
            double resolution) {
        double startOffset = distanceToNoCollision;
        double halfway = (distanceToNoCollision + distanceToCollision) / 2;

        try {
            //if we would move less than the given resolution, exit search and return collisions
            //recursive exit condition
            double potentialMovement = (halfway - startOffset) / 2;
            if (potentialMovement < resolution) {
                return new MovementCollisions(collisions, distanceToCollision);
            }

            Set<Collision> halfwayCollisions =
                    detectStaticCollision(new CollisionTestData(proposedMovement.getMov().fixedDistancePosition(halfway), ctd1.getcShape()), ctds);
            if (!halfwayCollisions.isEmpty()) {
                return binarySearchDynamicCollision(ctd1, ctds, proposedMovement, distanceToNoCollision, halfway, halfwayCollisions, resolution);
            } else {
                return binarySearchDynamicCollision(ctd1, ctds, proposedMovement, halfway, distanceToCollision, collisions, resolution);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
