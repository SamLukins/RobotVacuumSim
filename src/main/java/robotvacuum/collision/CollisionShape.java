package robotvacuum.collision;

import robotvacuum.collision.Shape;

public abstract class CollisionShape {

    private Shape shape;

    protected CollisionShape(Shape shape) {
        this.shape = shape;
    }
}
