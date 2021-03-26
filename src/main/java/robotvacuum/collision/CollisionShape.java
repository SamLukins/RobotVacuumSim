package robotvacuum.collision;

import java.io.Serializable;

public abstract class CollisionShape implements Serializable {

    private Shape shape;

    protected CollisionShape(Shape shape) {
        this.shape = shape;
    }
}
