package robotvacuum.collision;

import java.util.Optional;

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

    private Optional<Collision> detectStaticCollisionBetweenRectangles(CollisionTestData ctd1, CollisionTestData ctd2) {
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

            return Optional.of(new Collision(overlapRectangleCenter, ctd1.getPos().directionTo(overlapRectangleCenter)));
        }
        return Optional.empty();
    }

    private Optional<Collision> detectStaticCollisionBetweenCircleAndRectangle(CollisionTestData ctd1, CollisionTestData ctd2) {
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
                final double collisionYPos = ctd2.getPos().getY() + (Math.signum(yDiff) * (cRect.getHeight() / 2));

                final Position collisionPosition = new Position(ctd1.getPos().getX(), collisionYPos);
                final double collisionDirection = Math.signum(yDiff) * Math.PI / 2;

                return Optional.of(new Collision(collisionPosition, collisionDirection));

                //if side no corner
            } else if (Math.abs(yDiff) <= (cRect.getHeight() / 2)) {
                final double collisionXPos = ctd2.getPos().getX() + (Math.signum(xDiff) * (cRect.getWidth() / 2));

                final Position collisionPosition = new Position(collisionXPos, ctd1.getPos().getY());
                double collisionDirection = 0;
                if (Math.signum(xDiff) < 0) {
                    collisionDirection = Math.PI;
                }

                return Optional.of(new Collision(collisionPosition, collisionDirection));

                //if in corner
            } else {
                final Position cornerPos = new Position(
                        ctd2.getPos().getX() + (-Math.signum(xDiff) * cRect.getWidth() / 2),
                        ctd2.getPos().getY() + (-Math.signum(yDiff) * cRect.getHeight() / 2)
                );

                if (ctd1.getPos().distanceTo(cornerPos) <= circleRadius) {
                    return Optional.of(new Collision(cornerPos, ctd1.getPos().directionTo(cornerPos)));
                } else {
                    return Optional.empty();
                }
            }
        }
        return Optional.empty();
    }

    private Optional<Collision> detectStaticCollisionBetweenRectangleAndCircle(CollisionTestData ctd1, CollisionTestData ctd2) {
        //call the other function with swapped arguments
        return detectStaticCollisionBetweenCircleAndRectangle(ctd2, ctd1)
                .map((Collision col) -> new Collision(col.getCollisionPosition(),
                        //swap the collision direction but make sure it stays in -PI to PI
                        col.getCollisionDirection() <= 0
                                ? col.getCollisionDirection() + Math.PI
                                : col.getCollisionDirection() - Math.PI));
    }

    private Optional<Collision> detectStaticCollisionBetweenCircles(CollisionTestData ctd1, CollisionTestData ctd2) {
        double circle1Radius = ((CollisionCircle) ctd1.getcShape()).getRadius();
        double circle2Radius = ((CollisionCircle) ctd2.getcShape()).getRadius();
        double radiusSum = circle1Radius + circle2Radius;

        double distanceBetweenCenters = ctd1.getPos().distanceTo(ctd2.getPos());

        if (distanceBetweenCenters < radiusSum) {
            double relativeSize = circle1Radius / radiusSum;
            Position collisionPosition = ctd1.getPos().linearInterpolateTo(ctd2.getPos(), relativeSize);
            double collisionDirection = ctd1.getPos().directionTo(ctd2.getPos());

            return Optional.of(new Collision(collisionPosition, collisionDirection));
        }
        return Optional.empty();
    }
}
