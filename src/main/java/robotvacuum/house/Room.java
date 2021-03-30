package robotvacuum.house;

import robotvacuum.collision.*;
import robotvacuum.house.furniture.*;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.Set;
import java.util.HashSet;
import java.util.Objects;

/**
 * @author Austen Seidler
 */

public class Room implements Serializable {
    //TODO: add chairs and tables to rooms
    private final Map<Position, Wall> walls;
    private final Set<Furniture> furniture;
    boolean isBaseRoom;

    public Room(Map<Position, Wall> walls) {
        this.walls = new HashMap<>(walls);
        furniture = new HashSet<>();
        isBaseRoom = false;
    }

    public Room(Room room) {
        this.walls = room.getWalls().entrySet().stream()
                .collect(Collectors.toMap(
                        e -> new Position(e.getKey()),
                        e -> new Wall(e.getValue())));
        this.isBaseRoom = room.isBaseRoom;
        this.furniture = room.furniture;
    }
    
    public void addChest(double originPointX, double originPointY, double furnitureWidth, double furnitureHeight) {
        Chest newChest = new Chest(new CollisionTestData(new Position(originPointX, originPointY),
                                    new CollisionRectangle(furnitureWidth, furnitureHeight)));
        //fail if furniture outside room or intersecting wall or furniture
        furniture.add(newChest);
        System.out.println("Furniture added.");
    }
    
    public void removeFurniture(Position pos) {
        for (Furniture f : furniture) {
            if (f instanceof Chest) {
                if (((Chest) f).getcData().getPos().equals(pos)) {
                    furniture.remove(f);
                    System.out.println("Chest removed.");
                    return;
                }
            }
            //if f is a chair or table
        }
        System.out.println("Furniture not found.");
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
    
    public Furniture getFurniture(Position pos) {
        for (Furniture f : furniture) {
            if (f instanceof Chest) {
                if (((Chest) f).getcData().getPos().equals(pos)) {
                    return f;
                }
            }
            //if f is a chair or table
        }
        
        return null;
    }

    /**
     * @param isBaseRoom whether the room is the base room
     */
    public void setIsBaseRoom(boolean isBaseRoom) {
        this.isBaseRoom = isBaseRoom;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 97 * hash + Objects.hashCode(this.walls);
        hash = 97 * hash + Objects.hashCode(this.furniture);
        hash = 97 * hash + (this.isBaseRoom ? 1 : 0);
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
        final Room other = (Room) obj;
        if (this.isBaseRoom != other.isBaseRoom) {
            return false;
        }
        if (!Objects.equals(this.walls, other.walls)) {
            return false;
        }
        if (!Objects.equals(this.furniture, other.furniture)) {
            return false;
        }
        return true;
    }
}
