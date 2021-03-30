package robotvacuum.collision;

import java.io.Serializable;
import java.util.Objects;

/**
 *
 * @author SamL
 */
public class CollisionTestData implements Serializable {
    private final Position pos;
    private final CollisionShape cShape;

    public CollisionTestData(Position pos, CollisionShape cShape) {
        this.pos = pos;
        this.cShape = cShape;
    }

    /**
     * @return the pos
     */
    public Position getPos() {
        return pos;
    }

    /**
     * @return the cShape
     */
    public CollisionShape getcShape() {
        return cShape;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 37 * hash + Objects.hashCode(this.pos);
        hash = 37 * hash + Objects.hashCode(this.cShape);
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
        final CollisionTestData other = (CollisionTestData) obj;
        if (!Objects.equals(this.pos, other.pos)) {
            return false;
        }
        if (!Objects.equals(this.cShape, other.cShape)) {
            return false;
        }
        return true;
    }
}
