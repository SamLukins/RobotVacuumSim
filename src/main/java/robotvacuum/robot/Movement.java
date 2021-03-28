package robotvacuum.robot;

import robotvacuum.collision.Position;

/**
 *
 * @author SamL
 */
public interface Movement {

    /**
     *
     * @param percent the percent along the movement
     * @return the position that is percent along the movement from the start
     * @throws RuntimeException if the percent is less than 0 or greater than 1
     */
    public Position linearInterpolatedPosition(double percent) throws Exception;

    /**
     *
     * @param distance distance from start position along movement in meters
     * @return the position distance from the start along the path
     * @throws RuntimeException if the distance is greater than the total movement distance
     */
    public Position fixedDistancePosition(double distance) throws Exception;

    /**
     *
     * @param percent percent of the original movement that the new movement will cover
     * @param <T> The actual type of the movement
     * @return A new movement (or subclass) object that covers percent amount of the original movement
     * @throws Exception if the percent is less than 0 or greater than 1
     */
    public <T extends Movement> T partialLinearInterpolatedMovement(double percent) throws Exception;

    /**
     *
     * @param distance distance of the original movement that the new movement will cover in meters
     * @param <T> The actual type of the movement
     * @return A new movement (or subclass) object that covers distance amount of the original movement
     * @throws Exception if the distance is less than 0 or greater than the total distance
     */
    public <T extends Movement> T partialFixedDistanceMovement(double distance) throws Exception;

    /**
     *
     * @return the distance travelled from the center of the start position along the movement to the final position
     */
    public double totalTravelDistance();
}
