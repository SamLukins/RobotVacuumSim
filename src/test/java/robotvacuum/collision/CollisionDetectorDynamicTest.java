package robotvacuum.collision;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import robotvacuum.robot.ActualMovement;
import robotvacuum.robot.MovementLineSegment;
import robotvacuum.robot.ProposedMovement;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

class CollisionDetectorDynamicTest {

    final CollisionDetector collisionDetector = new CollisionDetector();

    @Test
    void detectDynamicCollisionBetweenCircleAndRectangleLineMovement() {
        CollisionTestData robotTestData = generateMockCollisionCircleTestData(1.0, 1.0, 1.0);
        CollisionTestData wallTestData = generateMockCollisionRectangleTestData(4.0, 1.0, 2.0, 2.0);
        final ProposedMovement<MovementLineSegment> proposedMovement =
                new ProposedMovement<>(new MovementLineSegment(robotTestData.getPos(), new Position(5.0, 1.0)));

        ActualMovement<MovementLineSegment> actualMovement =
                collisionDetector.detectDynamicCollision(robotTestData, wallTestData, proposedMovement);

        assertFalse(actualMovement.getCollisions().isEmpty());
        Optional<MovementLineSegment> optMovement = actualMovement.getMovement();
        assertTrue(optMovement.isPresent());
        Optional<Collision> optCollision = actualMovement.getCollisions().stream().findFirst();
        assertTrue(optCollision.isPresent());

        MovementLineSegment movement = optMovement.orElseThrow();
        Collision collision = optCollision.orElseThrow();
        assertEquals(2.0, movement.getStopPos().getX(), 0.00001);
        assertEquals(1.0, movement.getStopPos().getY(), 0.00001);
        assertEquals(3.0, collision.getCollisionPosition().getX(), 0.00001);
        assertEquals(1.0, collision.getCollisionPosition().getY(), 0.00001);
    }

    private CollisionTestData generateMockCollisionRectangleTestData(double x, double y, double width, double height) {
        CollisionRectangle rectMock = Mockito.mock(CollisionRectangle.class);
        when(rectMock.getHeight()).thenReturn(height);
        when(rectMock.getWidth()).thenReturn(width);
        when(rectMock.getShape()).thenReturn(Shape.RECTANGLE);
        Position pos = new Position(x, y);
        CollisionTestData ctdMock = Mockito.mock(CollisionTestData.class);
        when(ctdMock.getcShape()).thenReturn(rectMock);
        when(ctdMock.getPos()).thenReturn(pos);
        return ctdMock;
    }

    private CollisionTestData generateMockCollisionCircleTestData(double x, double y, double radius) {
        CollisionCircle circleMock = Mockito.mock(CollisionCircle.class);
        when(circleMock.getRadius()).thenReturn(radius);
        when(circleMock.getShape()).thenReturn(Shape.CIRCLE);
        Position pos = new Position(x, y);
        CollisionTestData ctdMock = Mockito.mock(CollisionTestData.class);
        when(ctdMock.getcShape()).thenReturn(circleMock);
        when(ctdMock.getPos()).thenReturn(pos);
        return ctdMock;
    }
}