package robotvacuum.robot;


import robotvacuum.collision.Collision;
import robotvacuum.utility.MathHelper;

import java.util.Collection;
import java.util.Random;
import java.io.Serializable;

public class RandomVacuumStrategy implements VacuumStrategy, Serializable {

    private final double defaultVacuumDistance;
    private double previousDirection = 0.0;
    private Random random;
    private final MathHelper mathHelper;

    public RandomVacuumStrategy(long seed, double defaultVacuumDistance) {
        this.defaultVacuumDistance = defaultVacuumDistance;
        this.random = new Random(seed);
        this.mathHelper = new MathHelper();
    }

    public RandomVacuumStrategy(long seed, double defaultVacuumDistance, double startingDirection) {
        this(seed, defaultVacuumDistance);
        this.previousDirection = startingDirection;
    }

    @Override
    public ProposedMovement vacuum(RobotSimulationState rSimState, Collection<Collision> previousCollisions) {
        if (previousCollisions.isEmpty()) {
            return new ProposedMovement(
                    new MovementLineSegment(
                            rSimState.getPosition(),
                            rSimState.getPosition().offsetPositionPolar(previousDirection, defaultVacuumDistance)));
        } else {
            double minPossibleDirection =
                    previousCollisions.stream().mapToDouble(Collision::getCollisionDirection).map(x -> x + Math.PI / 2).max().getAsDouble();

            double maxPossibleDirection =
                    previousCollisions.stream().mapToDouble(Collision::getCollisionDirection).map(x -> x - Math.PI / 2).min().getAsDouble()
                            + 2 * Math.PI;

            double direction = mathHelper.normalizeAngle((random.nextDouble() * (maxPossibleDirection - minPossibleDirection)) + minPossibleDirection);

            this.previousDirection = direction;

            return new ProposedMovement(
                    new MovementLineSegment(
                            rSimState.getPosition(),
                            rSimState.getPosition().offsetPositionPolar(direction, defaultVacuumDistance)));
        }
    }

}
