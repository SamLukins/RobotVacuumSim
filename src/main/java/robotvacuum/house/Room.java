package robotvacuum.house;

import java.util.Map;
import java.util.HashMap;
import java.util.stream.Collectors;
import robotvacuum.space.Position;
import java.io.Serializable;

/**
 *
 * @author Austen Seidler
 */

public class Room implements Serializable {
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
        this.isBaseRoom = room.isBaseRoom;
    }

    /**
     * @return the walls
     */
    public Map<Position, Wall> getWalls() {
        return walls;
    }
    
    /**
     * @return whether the room is the base room
     */
    public boolean getIsBaseRoom() {
        return isBaseRoom;
    }
    
    /**
     * @param isBaseRoom whether the room is the base room
     */
    public void setIsBaseRoom(boolean isBaseRoom) {
        this.isBaseRoom = isBaseRoom;
    }
}
