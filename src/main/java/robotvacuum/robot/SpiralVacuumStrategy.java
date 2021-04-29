package robotvacuum.robot;

import robotvacuum.collision.Collision;
import robotvacuum.collision.Position;
import robotvacuum.utility.MathHelper;

import java.io.Serializable;
import java.util.Collection;
import java.util.Random;

public class SpiralVacuumStrategy implements VacuumStrategy, Serializable {

    private final double minSpiralWallDistance;
    private final double maxSpiralWallDistance;
    private final double collisionSearchDistance;
    private final double previousDirection;
    private SpiralState spiralState;
    private final Random random;
    private final MathHelper mathHelper;

    public SpiralVacuumStrategy(final double minSpiralWallDistance, final double maxSpiralWallDistance,
                                final double collisionSearchDistance, final double startingDirection, final long seed) {
        this.minSpiralWallDistance = minSpiralWallDistance;
        this.maxSpiralWallDistance = maxSpiralWallDistance;
        this.collisionSearchDistance = collisionSearchDistance;
        this.previousDirection = startingDirection;
        this.random = new Random(seed);
        this.mathHelper = new MathHelper();
        this.spiralState = SpiralState.LINE_FOR_COLLISION;
    }

    public SpiralVacuumStrategy(final double minSpiralWallDistance, final double maxSpiralWallDistance,
                                final double collisionSearchDistance, final long seed) {
        this(minSpiralWallDistance, maxSpiralWallDistance, collisionSearchDistance, 0, seed);
    }

    public SpiralVacuumStrategy(final double minSpiralWallDistance, final double maxSpiralWallDistance,
                                final double collisionSearchDistance) {
        this(minSpiralWallDistance, maxSpiralWallDistance, collisionSearchDistance, 0, 0);
    }

    @Override
    public ProposedMovement vacuum(RobotSimulationState rSimState, Collection<Collision> previousCollisions) {
        if (this.spiralState == SpiralState.LINE_FOR_COLLISION) {
            if (previousCollisions.isEmpty()) {
                return new ProposedMovement(
                        new MovementLineSegment(
                                rSimState.getPosition(),
                                rSimState.getPosition().offsetPositionPolar(previousDirection, collisionSearchDistance)));
            } else {
                this.spiralState = SpiralState.LINE_FOR_SPIRAL;
                return this.vacuum(rSimState, previousCollisions);

            }
        } else if (this.spiralState == SpiralState.LINE_FOR_SPIRAL) {
            double minPossibleDirection =
                    previousCollisions.stream().mapToDouble(Collision::getCollisionDirection).map(x -> x + Math.PI / 2).max().getAsDouble();

            double maxPossibleDirection =
                    previousCollisions.stream().mapToDouble(Collision::getCollisionDirection).map(x -> x - Math.PI / 2).min().getAsDouble()
                            + 2 * Math.PI;

            double direction = mathHelper.normalizeAngle((random.nextDouble() * (maxPossibleDirection - minPossibleDirection)) + minPossibleDirection);
            double distance = (random.nextDouble() * (maxSpiralWallDistance - minSpiralWallDistance)) + minSpiralWallDistance;

            return new ProposedMovement(
                    new MovementLineSegment(
                            rSimState.getPosition(),
                            rSimState.getPosition().offsetPositionPolar(direction, distance)));
        } else if (this.spiralState == SpiralState.SPIRAL) {
            if (previousCollisions.isEmpty()) {

                Position spiralCenter = rSimState.getPosition().offsetPositionPolar(rSimState.getFacingDirection() + Math.PI / 2, 0.1);

                return new ProposedMovement(
                        new MovementSpiral(
                                rSimState.getPosition(), spiralCenter, Math.PI * 2, 0.15, CircleDirection.CLOCKWISE));
            }
        }

        throw new UnsupportedOperationException("not implemented");
    }
}

enum SpiralState {
    LINE_FOR_SPIRAL,
    LINE_FOR_COLLISION,
    SPIRAL
}