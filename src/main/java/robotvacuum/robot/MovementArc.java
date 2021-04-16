package robotvacuum.robot;

import robotvacuum.collision.Position;

public class MovementArc implements Movement<MovementArc> {

    private final Position startPos;
    private final Position arcCenter;
    private final double arcDistance;

    public MovementArc(Position startPos, Position arcCenter, double arcDistance) {
        this.startPos = startPos;
        this.arcCenter = arcCenter;
        this.arcDistance = arcDistance;
    }

    @Override
    public Position linearInterpolatedPosition(double percent) throws Exception {
        //if negative or more than 100% throw exception
        if (percent < 0) {
            throw new IllegalArgumentException("percent cannot be less than 0");
        } else if (percent > 1) {
            throw new IllegalArgumentException("percent cannot be greater than 1");
        } else {
            return fixedDistancePosition(arcDistance * percent);
        }
    }

    /**
     * @param distance distance from start position along movement in meters
     * @return the position distance from the start along the path
     * @throws RuntimeException if the distance is greater than the total movement distance
     */
    @Override
    public Position fixedDistancePosition(double distance) {
        if (distance < 0) {
            throw new IllegalArgumentException("distance cannot be less than 0");
        } else if (distance > arcDistance) {
            throw new IllegalArgumentException("distance cannot be greater than the arcDistance");
        } else {
            double radius = startPos.distanceTo(arcCenter);
            double startingAngle = arcCenter.directionTo(startPos);
            double angle = distance / radius;

            double finalAngle = startingAngle + angle;
            return arcCenter.offsetPositionPolar(finalAngle, radius);
//            return new Position(arcCenter.getX() + Math.cos(finalAngle) * radius, arcCenter.getY() + Math.sin(finalAngle) * radius);
        }
    }

    /**
     * @param distance distance of the original movement that the new movement will cover in meters
     * @return A new movement (or subclass) object that covers distance amount of the original movement
     * @throws Exception if the distance is less than 0 or greater than the total distance
     */
    @Override
    public MovementArc partialFixedDistanceMovement(double distance) {
        if (distance < 0) {
            throw new IllegalArgumentException("distance cannot be less than 0");
        } else if (distance > arcDistance) {
            throw new IllegalArgumentException("distance cannot be greater than the arcDistance");
        } else {
            return new MovementArc(startPos, arcCenter, distance);
        }
    }

    @Override
    public MovementArc partialLinearInterpolatedMovement(double percent) {
        return partialFixedDistanceMovement(arcDistance * percent);
    }

    /**
     * @return the distance travelled from the center of the start position along the movement to the final position
     */
    @Override
    public double totalTravelDistance() {
        return arcDistance;
    }

    @Override
    public Position getStopPos() {
        double radius = startPos.distanceTo(arcCenter);
        double startingAngle = arcCenter.directionTo(startPos);
        double angle = arcDistance / radius;

        double finalAngle = startingAngle + angle;
        return arcCenter.offsetPositionPolar(finalAngle, radius);
    }

    /**
     * @return the startPos
     */
    public Position getStartPos() {
        return startPos;
    }

    /**
     * @return the arcCenter
     */
    public Position getArcCenter() {
        return arcCenter;
    }

    /**
     * @return the arcDistance
     */
    public double getArcDistance() {
        return arcDistance;
    }

}
