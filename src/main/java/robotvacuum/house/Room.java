package robotvacuum.house;

import java.util.Map;
import java.util.stream.Collectors;
import robotvacuum.collision.Position;

/**
 *
 * @author Austen Seidler
 */

public class Room {
    //TODO: add doorways to rooms
    private Map<Position, Wall> walls;
    
    public Room(Map<Position, Wall> walls) {
        this.walls = walls;
    }

    public Room(Room room) {
        this.walls = room.getWalls().entrySet().stream()
                .collect(Collectors.toMap(
                        e -> new Position(e.getKey()),
                        e -> new Wall(e.getValue())));
    }

    /**
     * @return the walls
     */
    public Map<Position, Wall> getWalls() {
        return walls;
    }

}
