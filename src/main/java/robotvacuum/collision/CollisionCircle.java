package robotvacuum.collision;

/**
 *
 * @author SamL
 */
public class CollisionCircle extends CollisionShape {

    private final double radius;

    public CollisionCircle(double radius) {
        super(Shape.CIRCLE);
        this.radius = radius;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 47 * hash + (int) (Double.doubleToLongBits(this.radius) ^ (Double.doubleToLongBits(this.radius) >>> 32));
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final CollisionCircle other = (CollisionCircle) obj;
        return Double.doubleToLongBits(this.radius) == Double.doubleToLongBits(other.radius);
    }


    /**
     * Get the value of radius
     *
     * @return the value of radius
     */
    public double getRadius() {
        return radius;
    }

}
