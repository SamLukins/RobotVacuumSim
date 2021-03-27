package robotvacuum.robot;

import robotvacuum.collision.Position;

public class MovementArc implements Movement {

    private final Position startPos;
    private final Position arcCenter;
    private final double arcDistance;

    public MovementArc(Position startPos, Position arcCenter, double arcDistance) {
        this.startPos = startPos;
        this.arcCenter = arcCenter;
        this.arcDistance = arcDistance;
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
