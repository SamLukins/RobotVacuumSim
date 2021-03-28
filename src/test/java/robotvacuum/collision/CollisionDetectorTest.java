package robotvacuum.collision;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

class CollisionDetectorTest {

    final CollisionDetector collisionDetector = new CollisionDetector();
    final double root2 = Math.sqrt(2.0);

    @Test
    void detectStaticCollisionBetweenRectanglesNoCollision() {
        CollisionTestData ctd1Mock = generateMockCollisionRectangleTestData(1.0, 1.0, 1.0, 1.0);
        CollisionTestData ctd2Mock = generateMockCollisionRectangleTestData(5.0, 5.0, 1.0, 1.0);

        Optional<Collision> collision = collisionDetector.detectStaticCollisionBetweenRectangles(ctd1Mock, ctd2Mock);

        assertTrue(collision.isEmpty(), "Detected a collision when there should be none");
    }

    @Test
    void detectStaticCollisionBetweenRectanglesNoCollisionXOverlap() {
        CollisionTestData ctd1Mock = generateMockCollisionRectangleTestData(1.0, 1.0, 1.0, 1.0);
        CollisionTestData ctd2Mock = generateMockCollisionRectangleTestData(1.0, 5.0, 1.0, 1.0);

        Optional<Collision> collision = collisionDetector.detectStaticCollisionBetweenRectangles(ctd1Mock, ctd2Mock);

        assertTrue(collision.isEmpty(), "Detected a collision when there should be none");
    }

    @Test
    void detectStaticCollisionBetweenRectanglesNoCollisionYOverlap() {
        CollisionTestData ctd1Mock = generateMockCollisionRectangleTestData(1.0, 1.0, 1.0, 1.0);
        CollisionTestData ctd2Mock = generateMockCollisionRectangleTestData(5.0, 1.0, 1.0, 1.0);

        Optional<Collision> collision = collisionDetector.detectStaticCollisionBetweenRectangles(ctd1Mock, ctd2Mock);

        assertTrue(collision.isEmpty(), "Detected a collision when there should be none");
    }

    @Test
    void detectStaticCollisionBetweenRectanglesLeftCollision() {
        CollisionTestData ctd1Mock = generateMockCollisionRectangleTestData(2.0, 1.0, 1.0, 1.0);
        CollisionTestData ctd2Mock = generateMockCollisionRectangleTestData(1.0, 1.0, 1.0, 1.0);

        Optional<Collision> optCollision = collisionDetector.detectStaticCollisionBetweenRectangles(ctd1Mock, ctd2Mock);

        assertTrue(optCollision.isPresent(), "There should be a collision");
        Collision collision = optCollision.get();
        assertEquals(new Position(1.5, 1.0), collision.getCollisionPosition(), "collision position is not correct");
        assertEquals(Math.PI, collision.getCollisionDirection(), "collision direction is not correct");
    }

    @Test
    void detectStaticCollisionBetweenRectanglesRightCollision() {
        CollisionTestData ctd1Mock = generateMockCollisionRectangleTestData(1.0, 1.0, 1.0, 1.0);
        CollisionTestData ctd2Mock = generateMockCollisionRectangleTestData(2.0, 1.0, 1.0, 1.0);

        Optional<Collision> optCollision = collisionDetector.detectStaticCollisionBetweenRectangles(ctd1Mock, ctd2Mock);

        assertTrue(optCollision.isPresent(), "There should be a collision");
        Collision collision = optCollision.get();
        assertEquals(new Position(1.5, 1.0), collision.getCollisionPosition(), "collision position is not correct");
        assertEquals(0, collision.getCollisionDirection(), "collision direction is not correct");
    }

    @Test
    void detectStaticCollisionBetweenRectanglesTopCollision() {
        CollisionTestData ctd1Mock = generateMockCollisionRectangleTestData(1.0, 1.0, 1.0, 1.0);
        CollisionTestData ctd2Mock = generateMockCollisionRectangleTestData(1.0, 2.0, 1.0, 1.0);

        Optional<Collision> optCollision = collisionDetector.detectStaticCollisionBetweenRectangles(ctd1Mock, ctd2Mock);

        assertTrue(optCollision.isPresent(), "There should be a collision");
        Collision collision = optCollision.get();
        assertEquals(new Position(1.0, 1.5), collision.getCollisionPosition(), "collision position is not correct");
        assertEquals(Math.PI / 2, collision.getCollisionDirection(), "collision direction is not correct");
    }

    @Test
    void detectStaticCollisionBetweenRectanglesBottomCollision() {
        CollisionTestData ctd1Mock = generateMockCollisionRectangleTestData(1.0, 2.0, 1.0, 1.0);
        CollisionTestData ctd2Mock = generateMockCollisionRectangleTestData(1.0, 1.0, 1.0, 1.0);

        Optional<Collision> optCollision = collisionDetector.detectStaticCollisionBetweenRectangles(ctd1Mock, ctd2Mock);

        assertTrue(optCollision.isPresent(), "There should be a collision");
        Collision collision = optCollision.get();
        assertEquals(new Position(1.0, 1.5), collision.getCollisionPosition(), "collision position is not correct");
        assertEquals(-Math.PI / 2, collision.getCollisionDirection(), "collision direction is not correct");
    }

    @Test
    void detectStaticCollisionBetweenRectanglesCornerCollision() {
        CollisionTestData ctd1Mock = generateMockCollisionRectangleTestData(2.0, 2.0, 1.0, 1.0);
        CollisionTestData ctd2Mock = generateMockCollisionRectangleTestData(1.0, 1.0, 1.0, 1.0);

        Optional<Collision> optCollision = collisionDetector.detectStaticCollisionBetweenRectangles(ctd1Mock, ctd2Mock);

        assertTrue(optCollision.isPresent(), "There should be a collision");
        Collision collision = optCollision.get();
        assertEquals(new Position(1.5, 1.5), collision.getCollisionPosition(), "collision position is not correct");
        assertEquals(-3 * Math.PI / 4, collision.getCollisionDirection(), "collision direction is not correct");
    }

    @Test
    void detectStaticCollisionBetweenRectanglesCompleteOverlap() {
        CollisionTestData ctd1Mock = generateMockCollisionRectangleTestData(1.0, 1.0, 1.0, 1.0);
        CollisionTestData ctd2Mock = generateMockCollisionRectangleTestData(1.0, 1.0, 1.0, 1.0);

        Optional<Collision> optCollision = collisionDetector.detectStaticCollisionBetweenRectangles(ctd1Mock, ctd2Mock);

        assertTrue(optCollision.isPresent(), "There should be a collision");
        Collision collision = optCollision.get();
        assertEquals(new Position(1.0, 1.0), collision.getCollisionPosition(), "collision position is not correct");
        assertEquals(0.0, collision.getCollisionDirection(), "collision direction is not correct");
    }

    @Test
    void detectStaticCollisionBetweenCirclesNoCollision() {
        CollisionTestData ctd1Mock = generateMockCollisionCircleTestData(1.0, 1.0, 1.0);
        CollisionTestData ctd2Mock = generateMockCollisionCircleTestData(5.0, 5.0, 1.0);

        Optional<Collision> optCollision = collisionDetector.detectStaticCollisionBetweenCircles(ctd1Mock, ctd2Mock);

        assertTrue(optCollision.isEmpty(), "Collision detected when there should be none");
    }

    @Test
    void detectStaticCollisionBetweenCirclesLeftCollision() {
        CollisionTestData ctd1Mock = generateMockCollisionCircleTestData(3.0, 1.0, 1.0);
        CollisionTestData ctd2Mock = generateMockCollisionCircleTestData(1.0, 1.0, 1.0);

        Optional<Collision> optCollision = collisionDetector.detectStaticCollisionBetweenCircles(ctd1Mock, ctd2Mock);

        assertTrue(optCollision.isPresent(), "There should be a collision");
        Collision collision = optCollision.get();
        assertEquals(new Position(2.0, 1.0), collision.getCollisionPosition(), "collision position is not correct");
        assertEquals(Math.PI, collision.getCollisionDirection(), "collision direction is not correct");
    }

    @Test
    void detectStaticCollisionBetweenCirclesRightCollision() {
        CollisionTestData ctd1Mock = generateMockCollisionCircleTestData(1.0, 1.0, 1.0);
        CollisionTestData ctd2Mock = generateMockCollisionCircleTestData(3.0, 1.0, 1.0);

        Optional<Collision> optCollision = collisionDetector.detectStaticCollisionBetweenCircles(ctd1Mock, ctd2Mock);

        assertTrue(optCollision.isPresent(), "There should be a collision");
        Collision collision = optCollision.get();
        assertEquals(new Position(2.0, 1.0), collision.getCollisionPosition(), "collision position is not correct");
        assertEquals(0.0, collision.getCollisionDirection(), "collision direction is not correct");
    }

    @Test
    void detectStaticCollisionBetweenCirclesTopCollision() {
        CollisionTestData ctd1Mock = generateMockCollisionCircleTestData(1.0, 1.0, 1.0);
        CollisionTestData ctd2Mock = generateMockCollisionCircleTestData(1.0, 3.0, 1.0);

        Optional<Collision> optCollision = collisionDetector.detectStaticCollisionBetweenCircles(ctd1Mock, ctd2Mock);

        assertTrue(optCollision.isPresent(), "There should be a collision");
        Collision collision = optCollision.get();
        assertEquals(new Position(1.0, 2.0), collision.getCollisionPosition(), "collision position is not correct");
        assertEquals(Math.PI / 2, collision.getCollisionDirection(), "collision direction is not correct");
    }

    @Test
    void detectStaticCollisionBetweenCirclesBottomCollision() {
        CollisionTestData ctd1Mock = generateMockCollisionCircleTestData(1.0, 3.0, 1.0);
        CollisionTestData ctd2Mock = generateMockCollisionCircleTestData(1.0, 1.0, 1.0);

        Optional<Collision> optCollision = collisionDetector.detectStaticCollisionBetweenCircles(ctd1Mock, ctd2Mock);

        assertTrue(optCollision.isPresent(), "There should be a collision");
        Collision collision = optCollision.get();
        assertEquals(new Position(1.0, 2.0), collision.getCollisionPosition(), "collision position is not correct");
        assertEquals(-Math.PI / 2, collision.getCollisionDirection(), "collision direction is not correct");
    }

    @Test
    void detectStaticCollisionBetweenCirclesDiagonalCollision() {

        CollisionTestData ctd1Mock = generateMockCollisionCircleTestData(1.0, 1.0, 1.0);
        CollisionTestData ctd2Mock = generateMockCollisionCircleTestData(1.0 + root2, 1.0 + root2, 1.0);

        Optional<Collision> optCollision = collisionDetector.detectStaticCollisionBetweenCircles(ctd1Mock, ctd2Mock);

        assertTrue(optCollision.isPresent(), "There should be a collision");
        Collision collision = optCollision.get();
        assertEquals(new Position(1.0 + (root2 / 2), 1.0 + (root2 / 2)), collision.getCollisionPosition(), "collision position is not correct");
        assertEquals(Math.PI / 4, collision.getCollisionDirection(), "collision direction is not correct");
    }

    @Test
    void detectStaticCollisionBetweenCircleAndRectangleNoCollision() {
        CollisionTestData ctd1Mock = generateMockCollisionCircleTestData(1.0, 1.0, 1.0);
        CollisionTestData ctd2Mock = generateMockCollisionRectangleTestData(5.0, 5.0, 1.0, 1.0);

        Optional<Collision> optCollision = collisionDetector.detectStaticCollisionBetweenCircleAndRectangle(ctd1Mock, ctd2Mock);

        assertTrue(optCollision.isEmpty(), "Collision detected when there should be none");
    }

    @Test
    void detectStaticCollisionBetweenCircleAndRectangleLeftCollision() {
        CollisionTestData ctd1Mock = generateMockCollisionCircleTestData(3.0, 1.0, 1.0);
        CollisionTestData ctd2Mock = generateMockCollisionRectangleTestData(1.0, 1.0, 2.0, 2.0);

        Optional<Collision> optCollision = collisionDetector.detectStaticCollisionBetweenCircleAndRectangle(ctd1Mock, ctd2Mock);

        assertTrue(optCollision.isPresent(), "There should be a collision");
        Collision collision = optCollision.get();
        assertEquals(new Position(2.0, 1.0), collision.getCollisionPosition(), "collision position is not correct");
        assertEquals(Math.PI, collision.getCollisionDirection(), "collision direction is not correct");
    }

    @Test
    void detectStaticCollisionBetweenCircleAndRectangleRightCollision() {
        CollisionTestData ctd1Mock = generateMockCollisionCircleTestData(1.0, 1.0, 1.0);
        CollisionTestData ctd2Mock = generateMockCollisionRectangleTestData(3.0, 1.0, 2.0, 2.0);

        Optional<Collision> optCollision = collisionDetector.detectStaticCollisionBetweenCircleAndRectangle(ctd1Mock, ctd2Mock);

        assertTrue(optCollision.isPresent(), "There should be a collision");
        Collision collision = optCollision.get();
        assertEquals(new Position(2.0, 1.0), collision.getCollisionPosition(), "collision position is not correct");
        assertEquals(0.0, collision.getCollisionDirection(), "collision direction is not correct");
    }

    @Test
    void detectStaticCollisionBetweenCircleAndRectangleTopCollision() {
        CollisionTestData ctd1Mock = generateMockCollisionCircleTestData(1.0, 1.0, 1.0);
        CollisionTestData ctd2Mock = generateMockCollisionRectangleTestData(1.0, 3.0, 2.0, 2.0);

        Optional<Collision> optCollision = collisionDetector.detectStaticCollisionBetweenCircleAndRectangle(ctd1Mock, ctd2Mock);

        assertTrue(optCollision.isPresent(), "There should be a collision");
        Collision collision = optCollision.get();
        assertEquals(new Position(1.0, 2.0), collision.getCollisionPosition(), "collision position is not correct");
        assertEquals(Math.PI / 2, collision.getCollisionDirection(), "collision direction is not correct");
    }

    @Test
    void detectStaticCollisionBetweenCircleAndRectangleBottomCollision() {
        CollisionTestData ctd1Mock = generateMockCollisionCircleTestData(1.0, 3.0, 1.0);
        CollisionTestData ctd2Mock = generateMockCollisionRectangleTestData(1.0, 1.0, 2.0, 2.0);

        Optional<Collision> optCollision = collisionDetector.detectStaticCollisionBetweenCircleAndRectangle(ctd1Mock, ctd2Mock);

        assertTrue(optCollision.isPresent(), "There should be a collision");
        Collision collision = optCollision.get();
        assertEquals(new Position(1.0, 2.0), collision.getCollisionPosition(), "collision position is not correct");
        assertEquals(-Math.PI / 2, collision.getCollisionDirection(), "collision direction is not correct");
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