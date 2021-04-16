package robotvacuum.robot;

import robotvacuum.collision.Position;

public class MovementLineSegment implements Movement<MovementLineSegment> {

    private final Position startPos;
    private final Position stopPos;

    public MovementLineSegment(Position startPos, Position stopPos) {
        this.startPos = startPos;
        this.stopPos = stopPos;
    }

    @Override
    public Position linearInterpolatedPosition(double percent) {
        if (!(0 <= percent && percent <= 1)) {
            throw new IllegalArgumentException("Percent out of bounds");
        }
        return startPos.linearInterpolateTo(stopPos, percent);
    }

    /**
     * @param distance distance from start position along movement in meters
     * @return the position distance from the start along the path
     * @throws RuntimeException if the distance is greater than the total movement distance
     */
    @Override
    public Position fixedDistancePosition(double distance) {
        return linearInterpolatedPosition(distance / totalTravelDistance());
    }

    /**
     * @param distance distance of the original movement that the new movement will cover in meters
     * @return A new movement (or subclass) object that covers distance amount of the original movement
     * @throws Exception if the distance is less than 0 or greater than the total distance
     */
    @Override
    public MovementLineSegment partialFixedDistanceMovement(double distance) {
        return new MovementLineSegment(startPos, fixedDistancePosition(distance));
    }

    @Override
    public MovementLineSegment partialLinearInterpolatedMovement(double percent) {
        return new MovementLineSegment(startPos, linearInterpolatedPosition(percent));
    }

    /**
     * @return the distance travelled from the center of the start position along the movement to the final position
     */
    @Override
    public double totalTravelDistance() {
        return startPos.distanceTo(stopPos);
    }

    public double getDirection() {
        return startPos.directionTo(stopPos);
    }

    /**
     * @return the startPos
     */
    public Position getStartPos() {
        return startPos;
    }

    /**
     * @return the stopPos
     */
    @Override
    public Position getStopPos() {
        return stopPos;
    }

}
