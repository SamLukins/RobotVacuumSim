package robotvacuum.house.furniture;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;
import robotvacuum.collision.CollisionTestData;
import robotvacuum.space.Position;

/**
 *
 * @author Austen Seidler
 */

public class Chair implements Furniture {
    //TODO: add legs to chairs

    private final Map<Position, Leg> legs;
    
    public Chair(Map<Position, Leg> legs) {
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
