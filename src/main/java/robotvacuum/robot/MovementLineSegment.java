package robotvacuum.robot;

import robotvacuum.collision.Position;

public class MovementLineSegment implements Movement {

    private final Position startPos;
    private final Position stopPos;

    public MovementLineSegment(Position startPos, Position stopPos) {
        this.startPos = startPos;
        this.stopPos = stopPos;
    }

    public double getDistance() {
        return this.getStartPos().distanceTo(this.getStopPos());
    }

    @Override
    public Position linearInterpolatedPosition(double percent) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
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
