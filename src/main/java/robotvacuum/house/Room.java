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
    
    //constants
    //All constants are in meters or sq meters
    //TODO: move to enum
    private static final double TABLE_LEG_RADIUS = 0.2; //~0.67 ft
    
    //variables
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
    
    /**
     * Adds a chest to the room.
     * Fails if the chest goes outside the room
     * or intersects a wall or furniture (WIP)
     * 
     * @param originPointX      the x-coordinate of the top left corner of the chest
     * @param originPointY      the y-coordinate of the top left corner of the chest
     * @param furnitureWidth    the given width of the chest
     * @param furnitureHeight   the given height of the chest
     */
    public void addChest(double originPointX, double originPointY, double furnitureWidth, double furnitureHeight) {
        Chest newChest = new Chest(new CollisionTestData(new Position(originPointX, originPointY),
                                    new CollisionRectangle(furnitureWidth, furnitureHeight)));
        //TODO: fail if furniture outside room or intersecting wall or furniture
        furniture.add(newChest);
        System.out.println("Chest added.");
    }
    
    /**
     * Adds a table to the room.
     * Fails if the table goes outside the room
     * or intersects a wall or furniture (WIP)
     * 
     * @param originPointX      the x-coordinate of the top left corner of the table
     * @param originPointY      the y-coordinate of the top left corner of the table
     * @param furnitureWidth    the given width of the table
     * @param furnitureHeight   the given height of the table
     */
    public void addTable(double originPointX, double originPointY, double furnitureWidth, double furnitureHeight) {
        Map<Position, Leg> legs = new HashMap<>();
        //if table cannot fix
        if (furnitureWidth < TABLE_LEG_RADIUS*4 || furnitureHeight < TABLE_LEG_RADIUS*4) {
            System.out.println("Error: Given size is too small; table cannot fit all legs under it.");
            return;
        }
        //Place each leg such that the distance between the center
        //and the table's edge equals the radius of the leg
        //Legs should be circular and have fixed diameter according to the requirements
        legs.put(new Position((originPointX + TABLE_LEG_RADIUS), (originPointY + TABLE_LEG_RADIUS)), new Leg(new CollisionCircle(TABLE_LEG_RADIUS)));    //top left leg
        legs.put(new Position((originPointX + furnitureWidth - TABLE_LEG_RADIUS), (originPointY + TABLE_LEG_RADIUS)), new Leg(new CollisionCircle(TABLE_LEG_RADIUS)));    //top right leg
        legs.put(new Position((originPointX + TABLE_LEG_RADIUS), (originPointY + furnitureHeight - TABLE_LEG_RADIUS)), new Leg(new CollisionCircle(TABLE_LEG_RADIUS)));    //bottom left leg
        legs.put(new Position((originPointX + furnitureWidth - TABLE_LEG_RADIUS), (originPointY + furnitureHeight - TABLE_LEG_RADIUS)), new Leg(new CollisionCircle(TABLE_LEG_RADIUS)));    //bottom right leg
        Table newTable = new Table(legs);
        //TODO: fail if furniture outside room or intersecting wall or furniture
        furniture.add(newTable);
        System.out.println("Table added.");
    }
    
    /**
     * Removes a piece of furniture at the given position.
     * 
     * @param pos   the position of the furniture
     */
    public void removeFurniture(Position pos) {
        for (Furniture f : furniture) {
            if (f instanceof Chest) {
                if (((Chest) f).getcData().getPos().equals(pos)) {
                    furniture.remove(f);
                    System.out.println("Chest removed.");
                    return;
                }
            }
            else if (f instanceof Table) {
                Position pos2 = new Position(pos.getX() + TABLE_LEG_RADIUS, pos.getY() + TABLE_LEG_RADIUS);
                for (Position p : ((Table) f).getLegs().keySet()) {
                    if (p.equals(pos2)) {
                        furniture.remove(f);
                        System.out.println("Table removed.");
                        return;
                    }
                }
            }
        }
        System.out.println("Furniture not found.");
    }
    
    public void addDoor(Position pos, double doorWidth) {
        Position doorWallPos = null;
        for (Position p : walls.keySet()) {
            if (p.equals(pos)) {
                doorWallPos = p;
            }
        }
        if (doorWallPos == null) {
            System.out.println("Wall not found.");
            return;
        }
        double wallWidth = walls.get(doorWallPos).getcRect().getWidth();
        double wallHeight = walls.get(doorWallPos).getcRect().getHeight();
        if (wallWidth > wallHeight) { //if wall is wider than tall, it's horizontal
            if (doorWidth > wallWidth) {
                System.out.println("Error: door too wide.");
                return;
            }
            walls.remove(doorWallPos);
            walls.put(doorWallPos, new Wall(new CollisionRectangle((wallWidth - doorWidth)/2, wallHeight)));
            Position newP = new Position((doorWallPos.getX() + ((wallWidth - doorWidth)/2) + doorWidth), doorWallPos.getY());
            walls.put(newP, new Wall(new CollisionRectangle((wallWidth - doorWidth)/2, wallHeight)));
            System.out.println("Door added.");
        }
        else {  //else it's vertical
            if (doorWidth > wallHeight) {
                System.out.println("Error: door too wide.");
                return;
            }
            walls.remove(doorWallPos);
            walls.put(doorWallPos, new Wall(new CollisionRectangle(wallWidth, (wallHeight - doorWidth)/2)));
            Position newP = new Position(doorWallPos.getX(), (doorWallPos.getY() + ((wallHeight - doorWidth)/2) + doorWidth));
            walls.put(newP, new Wall(new CollisionRectangle(wallWidth, (wallHeight - doorWidth)/2)));
            System.out.println("Door added.");
        }
    }

    /**
     * Returns every wall in the room.
     * 
     * @return  the walls
     */
    public Map<Position, Wall> getWalls() {
        return walls;
    }
    
    /**
     * Returns the wall with the given position.
     * 
     * @param pos   the position of the wall
     * @return      the wall at the given position
     */
    public Wall getWall(Position pos) {
        for (Position p : walls.keySet()) {
            if (p.equals(pos)) {
                return walls.get(p);
            }
        }

        return null;
    }

    /**
     * Returns whether the room is the base room.
     * 
     * @return  whether the room is the base room
     */
    public boolean getIsBaseRoom() {
        return isBaseRoom;
    }
    
    /**
     * Returns a piece of furniture at a given position.
     * 
     * @param pos   the position of the furniture
     * @return      the furniture at the given position
     */
    public Furniture getFurniture(Position pos) {
        for (Furniture f : furniture) {
            if (f instanceof Chest) {
                if (((Chest) f).getcData().getPos().equals(pos)) {
                    return f;
                }
            }
            else if (f instanceof Table) {
                Position pos2 = new Position(pos.getX() + TABLE_LEG_RADIUS, pos.getY() + TABLE_LEG_RADIUS);
                for (Position p : ((Table) f).getLegs().keySet()) {
                    if (p.equals(pos2)) {
                        return f;
                    }
                }
            }
        }
        
        return null;
    }
    
    /**
     * Returns every furniture item in the room.
     * 
     * @return 
     */
    public Set<Furniture> getAllFurniture() {
        return furniture;
    }

    /**
     * Sets whether the room is the base room.
     * 
     * @param   isBaseRoom whether the room is the base room
     */
    public void setIsBaseRoom(boolean isBaseRoom) {
        this.isBaseRoom = isBaseRoom;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 41 * hash + Objects.hashCode(this.walls);
        hash = 41 * hash + Objects.hashCode(this.furniture);
        hash = 41 * hash + (this.isBaseRoom ? 1 : 0);
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
