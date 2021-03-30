package robotvacuum.house.furniture;

import robotvacuum.collision.CollisionTestData;
import robotvacuum.collision.Position;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author Austen Seidler
 */

public class Table implements Furniture, Serializable {

    private final Map<Position, Leg> legs;

    public Table(Map<Position, Leg> legs) {
        this.legs = new HashMap<>(legs);
    }

    /**
     * @return the legs
     */
    public Map<Position, Leg> getLegs() {
        return legs;
    }

    @Override
    public Collection<CollisionTestData> getCollisionTestData() {
        return legs.entrySet().stream().map(e -> new CollisionTestData(e.getKey(), e.getValue().getcShape())).collect(Collectors.toSet());
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 47 * hash + Objects.hashCode(this.legs);
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
        final Table other = (Table) obj;
        if (!Objects.equals(this.legs, other.legs)) {
            return false;
        }
        return true;
    }
}
