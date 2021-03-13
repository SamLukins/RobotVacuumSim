package robotvacuum.house;

import java.util.Objects;
import robotvacuum.collision.CollisionRectangle;

/**
 *
 * @author SamL
 */
public class Wall {

    private CollisionRectangle cRect;

    public Wall(CollisionRectangle cRect) {
        this.cRect = cRect;
    }

    public Wall(Wall wall) {
        this(wall.getcRect());
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 97 * hash + Objects.hashCode(this.cRect);
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
        final Wall other = (Wall) obj;
        return Objects.equals(this.cRect, other.cRect);
    }

    /**
     * @return the cRect
     */
    public CollisionRectangle getcRect() {
        return cRect;
    }

}
