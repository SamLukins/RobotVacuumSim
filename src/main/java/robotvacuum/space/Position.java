package robotvacuum.space;

/**
 *
 * @author SamL
 */
public class Position {
    private final double x;
    private final double y;

    public Position(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public Position(Position pos) {
        this(pos.getX(), pos.getY());
    }

    /**
     * @return the x
     */
    public double getX() {
        return x;
    }

    /**
     * @return the y
     */
    public double getY() {
        return y;
    }

}
