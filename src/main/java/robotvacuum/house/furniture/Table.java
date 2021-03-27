package robotvacuum.house.furniture;

import robotvacuum.collision.CollisionTestData;
import robotvacuum.collision.Position;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
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
}
