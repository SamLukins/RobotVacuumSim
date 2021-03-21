package robotvacuum.house;

import java.util.Map;
import java.util.HashMap;
import java.util.stream.Collectors;
import robotvacuum.space.Position;

/**
 *
 * @author Austen Seidler
 */

public class Room {
    //TODO: add doorways to rooms
    private final Map<Position, Wall> walls;
    boolean isBaseRoom;
    
    public Room(Map<Position, Wall> walls) {
        this.walls = new HashMap<>(walls);
        isBaseRoom = false;
    }

    public Room(Room room) {
        this.walls = room.getWalls().entrySet().stream()
                .collect(Collectors.toMap(
                        e -> new Position(e.getKey()),
                        e -> new Wall(e.getValue())));
        isBaseRoom = false;
    }

    /**
     * @return the walls
     */
    public Map<Position, Wall> getWalls() {
        return walls;
    }
    
    public boolean getIsBaseRoom() {
        return isBaseRoom;
    }
    
    public void setIsBaseRoom(boolean isBaseRoom) {
        this.isBaseRoom = isBaseRoom;
    }
}
