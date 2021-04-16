package robotvacuum.robot;


import robotvacuum.collision.Collision;

import java.util.Collection;
import java.util.Random;

public class RandomVacuumStrategy implements VacuumStrategy<MovementLineSegment> {

    private final double defaultVacuumDistance;
    private double previousDirection = 0.0;
    private Random random;

    public RandomVacuumStrategy(long seed, double defaultVacuumDistance) {
        this.defaultVacuumDistance = defaultVacuumDistance;
        this.random = new Random(seed);
    }

    public RandomVacuumStrategy(long seed, double defaultVacuumDistance, double startingDirection) {
        this(seed, defaultVacuumDistance);
        this.previousDirection = startingDirection;
    }

    @Override
    public ProposedMovement<MovementLineSegment> vacuum(RobotSimulationState rSimState, Collection<Collision> previousCollisions) {
        if (previousCollisions.isEmpty()) {
            return new ProposedMovement<>(
                    new MovementLineSegment(
                            rSimState.getPosition(),
                            rSimState.getPosition().offsetPositionPolar(previousDirection, defaultVacuumDistance)));
        } else {
            double minPossibleDirection =
                    previousCollisions.stream().mapToDouble(Collision::getCollisionDirection).map(x -> x + Math.PI / 2).max().getAsDouble();

            double maxPossibleDirection =
                    previousCollisions.stream().mapToDouble(Collision::getCollisionDirection).map(x -> x - Math.PI / 2).min().getAsDouble()
                            + 2 * Math.PI;

            double direction = ((random.nextDouble() * (maxPossibleDirection - minPossibleDirection)) + minPossibleDirection) % (2 * Math.PI);

            return new ProposedMovement<>(
                    new MovementLineSegment(
                            rSimState.getPosition(),
                            rSimState.getPosition().offsetPositionPolar(direction, defaultVacuumDistance)));
        }
    }

}
