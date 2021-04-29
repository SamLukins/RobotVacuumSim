package robotvacuum.robot;

import robotvacuum.collision.Position;
import robotvacuum.utility.MathHelper;

public class MovementSpiral implements Movement {

    private final Position startPos;
    private final Position spiralCenter;
    private final double linearOffsetPerRotation;
    private final double totalRotation;
    private final CircleDirection circleDirection;
    private final double startingDistanceFromCenter;
    private final double totalTravelDistance;
    private final MathHelper mathHelper;

    public MovementSpiral(final Position startPos, final Position spiralCenter, final double totalRotation,
                          final double linearOffsetPerRotation, final CircleDirection circleDirection) {
        this(startPos, spiralCenter, totalRotation, linearOffsetPerRotation, circleDirection, new MathHelper());
    }

    public MovementSpiral(final Position startPos, final Position spiralCenter, final double totalRotation,
                          final double linearOffsetPerRotation, final CircleDirection circleDirection, MathHelper mathHelper) {
        this.startPos = startPos;
        this.spiralCenter = spiralCenter;
        this.linearOffsetPerRotation = linearOffsetPerRotation;
        this.totalRotation = totalRotation;
        this.circleDirection = circleDirection;
        this.startingDistanceFromCenter = spiralCenter.distanceTo(startPos);
        this.mathHelper = mathHelper;

        //wikipedia algorithm for arc length of archimedean spiral
        //https://en.wikipedia.org/wiki/Archimedean_spiral
        final double a = spiralCenter.distanceTo(startPos);
        double subValue = Math.sqrt(1 + (totalRotation * totalRotation));
        this.totalTravelDistance = (a / 2) * (totalRotation * subValue + Math.log(totalRotation + subValue));
    }


    /**
     * @param percent the percent along the movement
     * @return the position that is percent along the movement from the start
     * @throws IllegalArgumentException if the percent is less than 0 or greater than 1
     */
    @Override
    public Position linearInterpolatedPosition(double percent) {
        if (!(0 <= percent && percent <= 1)) {
            throw new IllegalArgumentException("Percent out of bounds. Value: " + percent);
        }
        return spiralCenter.offsetPositionPolar(totalRotation * percent + getStartAngle(),
                totalRotation * percent * linearOffsetPerRotation);
    }

    /**
     * @param distance distance from start position along movement in meters
     * @return the position distance from the start along the path
     * @throws IllegalArgumentException if the distance is greater than the total movement distance
     */
    @Override
    public Position fixedDistancePosition(double distance) {
        return linearInterpolatedPosition(distance / totalTravelDistance());
    }

    /**
     * @param percent percent of the original movement that the new movement will cover
     * @return A new movement (or subclass) object that covers percent amount of the original movement
     * @throws IllegalArgumentException if the percent is less than 0 or greater than 1
     */
    @Override
    public Movement partialLinearInterpolatedMovement(double percent) {
        if (!(0 <= percent && percent <= 1)) {
            throw new IllegalArgumentException("Percent out of bounds. Value: " + percent);
        }
        return new MovementSpiral(startPos, spiralCenter, percent * totalRotation, linearOffsetPerRotation, circleDirection);
    }

    /**
     * @param distance distance of the original movement that the new movement will cover in meters
     * @return A new movement (or subclass) object that covers distance amount of the original movement
     * @throws IllegalArgumentException if the distance is less than 0 or greater than the total distance
     */
    @Override
    public Movement partialFixedDistanceMovement(double distance) {
        return partialLinearInterpolatedMovement(distance / totalTravelDistance());
    }

    /**
     * @return the distance travelled from the start position along the movement to the final position
     */
    @Override
    public double totalTravelDistance() {
        return totalTravelDistance;
    }

    @Override
    public Position getStartPos() {
        return startPos;
    }

    @Override
    public Position getStopPos() {
        return linearInterpolatedPosition(1.0);
    }

    @Override
    public double getFinalFacingDirection() {
        final double endRotation = totalRotation + getStartAngle();
        if (this.circleDirection == CircleDirection.CLOCKWISE) {
            return mathHelper.normalizeAngle(endRotation + (Math.PI / 2));
        } else {
            return mathHelper.normalizeAngle(endRotation - (Math.PI / 2));
        }
    }

    private double getStartAngle() {
        return spiralCenter.directionTo(startPos);
    }
}
