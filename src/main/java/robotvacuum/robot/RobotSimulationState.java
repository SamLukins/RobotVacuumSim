package robotvacuum.robot;

import robotvacuum.collision.Position;

/**
 * @author SamL
 */
public class RobotSimulationState {

    private Position position;
    private double facingDirection;
    private double remainingBattery;

    public RobotSimulationState(Position position, double facingDirection, double remainingBattery) {
        this.position = position;
        this.facingDirection = facingDirection;
        this.remainingBattery = remainingBattery;
    }

    public void updatePosition(Movement mov) {
        this.position.offsetPositionCartesian(mov.getStopPos());
    }

    public <T extends Movement<T>> void updatePosition(ActualMovement<T> mov) {
        mov.getMovement().ifPresent(this::updatePosition);
    }

    /**
     * @return the position
     */
    public Position getPosition() {
        return position;
    }

    /**
     * @param position the position to set
     */
    public void setPosition(Position position) {
        this.position = position;
    }

    /**
     * @return the facingDirection
     */
    public double getFacingDirection() {
        return facingDirection;
    }

    /**
     * @param facingDirection the facingDirection to set
     */
    public void setFacingDirection(double facingDirection) {
        this.facingDirection = facingDirection;
    }

    /**
     * @return the remainingBattery
     */
    public double getRemainingBattery() {
        return remainingBattery;
    }

    /**
     * @param remainingBattery the remainingBattery to set
     */
    public void setRemainingBattery(double remainingBattery) {
        this.remainingBattery = remainingBattery;
    }

}
