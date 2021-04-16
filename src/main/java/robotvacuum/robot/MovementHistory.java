package robotvacuum.robot;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MovementHistory<M extends Movement<M>> {

    private List<M> movements = new ArrayList<>();

    public void addMovement(M m) {
        movements.add(m);
    }

    public List<M> getMovements() {
        return Collections.unmodifiableList(movements);
    }
}
