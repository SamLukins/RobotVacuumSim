package robotvacuum.robot;

import robotvacuum.collision.Position;

public class MovementLineSegment implements Movement {

    private final Position startPos;
    private final Position stopPos;

    public MovementLineSegment(Position startPos, Position stopPos) {
        this.startPos = startPos;
        this.stopPos = stopPos;
    }

    @Override
    public Position linearInterpolatedPosition(double percent) throws Exception {
        if (!(0 <= percent && percent <= 1)) {
            throw new Exception("Percent out of bounds");
        }
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    /**
     * @param distance distance from start position along movement in meters
     * @return the position distance from the start along the path
     * @throws RuntimeException if the distance is greater than the total movement distance
     */
    @Override
    public Position fixedDistancePosition(double distance) throws Exception {
        return linearInterpolatedPosition(distance / totalTravelDistance());
    }

    /**
     * @param distance distance of the original movement that the new movement will cover in meters
     * @return A new movement (or subclass) object that covers distance amount of the original movement
     * @throws Exception if the distance is less than 0 or greater than the total distance
     */
    @Override
    public MovementLineSegment partialFixedDistanceMovement(double distance) throws Exception {
        return new MovementLineSegment(startPos, fixedDistancePosition(distance));
    }

    @Override
    public MovementLineSegment partialLinearInterpolatedMovement(double percent) throws Exception {
        return new MovementLineSegment(startPos, linearInterpolatedPosition(percent));
    }

    /**
     * @return the distance travelled from the center of the start position along the movement to the final position
     */
    @Override
    public double totalTravelDistance() {
        return startPos.distanceTo(stopPos);
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
    public Position getStopPos() {
        return stopPos;
    }

}
