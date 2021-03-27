package robotvacuum.robot;

import robotvacuum.collision.Position;

/**
 *
 * @author SamL
 */
public interface Movement {

    public Position linearInterpolatedPosition(double percent);
}
