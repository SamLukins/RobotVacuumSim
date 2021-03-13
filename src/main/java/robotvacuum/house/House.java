package robotvacuum.house;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import robotvacuum.space.Position;

/**
 *
 * @author Austen Seidler
 */

public class House {
    //TODO: When doors are added to the Room class, addRoom will need to be updated
    //to let the user specify door location.
    
    //variables
    private FlooringType floorCovering; //1 = hard, 2 = loop pile, 3 = cut pile, 4 = frieze cut pile
    private final Map<Position, Room> rooms;

    public House(Map<Position, Room> rooms, FlooringType ft) {
        this.rooms = new HashMap<>(rooms);
        floorCovering = ft;
    }
    
    /**
    * Adds a new room to the house.
    * Fails if the room is smaller than 4 sq ft,
    * if the room extends outside the house,
    * if the room's walls intersects another room's walls,
    * or if the room's walls intersect furniture.
    * 
    * @param originPointX the x-coordinate of the top left corner of the room to be added
    * @param originPointY the y-coordinate of the top left corner of the room to be added
    * @param roomWidth    the given width of the room to be added
    * @param roomHeight   the given height of the room to be added
    */
    public void addRoom(int originPointX, int originPointY, int roomWidth, int roomHeight) {
        throw new UnsupportedOperationException("Not implemented yet. Yell at Team 4 Robot Vacuum.");
//        Room newRoom = new Room(originPointX, originPointY, roomWidth, roomHeight);
//        if (newRoom.getWidth()*newRoom.getHeight() < 4) {
//            System.out.println("Invalid size: room cannot be smaller than 4 sq ft.");
//            return;
//        }
//        if (!(this.contains(newRoom))) {
//            System.out.println("Invalid location: room cannot extend outside house");
//            return;
//        }
//        for (Room r : rooms) {
//            if (newRoom.intersects(r) && !(newRoom.contains(r) || r.contains(newRoom))) {
//                System.out.println("Invalid location: room wall cannot intersect other room wall");
//                return;
//            }
//        }
//        for (Furniture f : furniture) {
//            if (newRoom.intersects(f) && !(newRoom.contains(f))) {
//                System.out.println("Invalid location: room wall cannot intersect furniture");
//                return;
//            }
//        }
//        rooms.add(newRoom);
//        System.out.println("New room added: " + newRoom.toString());
    }
    
    /**
     * Removes a room from the house.
     * 
     * @param roomPosToRemove the room to be removed from the house
     */
    public void removeRoom(Position roomPosToRemove) {
        rooms.remove(roomPosToRemove);
        System.out.println("Room removed: " + roomPosToRemove.toString());
    }
    
    /**
     * Changes the floor covering of the house.
     * 
     * @param ft
     */
    public void setFloorCovering(FlooringType ft) {
        floorCovering = ft;
        System.out.println("House floor changed to " + floorCovering);
    }
    
    /**
     * Returns the floor covering used by the house.
     * 
     * @return floor covering ID (1 = hard, 2 = loop pile, 3 = cut pile, 4 = frieze cut pile)
     */
    public FlooringType getFloorCovering() {
        return floorCovering;
    }
    
    /**
     * Returns the room with the given values for x and y.
     * 
     * @param pos
     * @return the room at those coordinates (or null if no room at those coordinates)
     */
    public Room getRoom(Position pos) {
        return this.rooms.get(pos);
    }
    
    /**
     * Returns every room in the house.
     * 
     * @return A list of every room
     */
    public Map<Position, Room> getRooms() {
        return Collections.unmodifiableMap(rooms);
    }
}
