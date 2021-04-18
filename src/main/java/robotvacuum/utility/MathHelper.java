package robotvacuum.utility;

public class MathHelper {

    private final double TWO_PI = 2 * Math.PI;

    public double normalizeAngle(double angle) {
        return angle - TWO_PI * Math.floor((angle + Math.PI) / TWO_PI);
    }
}
