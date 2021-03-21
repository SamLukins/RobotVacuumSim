package robotvacuum.house;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import robotvacuum.space.Position;
import robotvacuum.collision.CollisionRectangle;

/**
 *
 * @author Austen Seidler
 */

public class House {
    //TODO: When doors are added to the Room class, addRoom will need to be updated
    //to let the user specify door location.
    
    //constants
    //TODO: move to enum
    private static final double WALL_THICKNESS = 2;
    private static final double BASE_WALL_THICKNESS = 5;
    private static final double MIN_BASE_ROOM_SIZE = 200;
    private static final double MAX_BASE_ROOM_SIZE = 8000;
    private static final double DEFAULT_BASE_ROOM_WIDTH = 50;
    private static final double DEFAULT_BASE_ROOM_HEIGHT = 50;
    private static final double MIN_ROOM_SIZE = 4;
    private static final double BASE_ROOM_X = 0;
    private static final double BASE_ROOM_Y = 0;
    
    //variables
    private FlooringType floorCovering;
    private final Map<Position, Room> rooms;

    //constructor for loading previously made house from file
    public House(Map<Position, Room> rooms, FlooringType ft) {
        this.rooms = new HashMap<>(rooms);
        floorCovering = ft;
    }
    
    //constructor for creating new house from scratch
    public House(double baseRoomWidth, double baseRoomHeight, FlooringType ft) {
        this.rooms = new HashMap<>();
        floorCovering = ft;
        if (baseRoomWidth*baseRoomHeight < MIN_BASE_ROOM_SIZE) {
            System.out.println("Given base room too small, using default parameters instead.");
            baseRoomWidth = DEFAULT_BASE_ROOM_WIDTH;
        }
        else if (baseRoomWidth*baseRoomHeight > MAX_BASE_ROOM_SIZE) {
            System.out.println("Given base room too large, using default parameters instead.");
            baseRoomHeight = DEFAULT_BASE_ROOM_HEIGHT;
        }
        
        Room newRoom = new Room(
                 Map.of(new Position(BASE_ROOM_X, BASE_ROOM_Y), new Wall(new CollisionRectangle(BASE_WALL_THICKNESS, baseRoomHeight)),  //left wall
                        new Position(BASE_ROOM_X, BASE_ROOM_Y), new Wall(new CollisionRectangle(baseRoomWidth, BASE_WALL_THICKNESS)),   //top wall
                        new Position((BASE_ROOM_X + baseRoomWidth - BASE_WALL_THICKNESS), BASE_ROOM_Y), new Wall(new CollisionRectangle(BASE_WALL_THICKNESS, baseRoomHeight)),    //right wall
                        new Position(BASE_ROOM_X, (BASE_ROOM_Y + baseRoomHeight - BASE_WALL_THICKNESS)), new Wall(new CollisionRectangle(baseRoomWidth, BASE_WALL_THICKNESS))));  //bottom wall
                
        newRoom.setIsBaseRoom(true);
        rooms.put(new Position(BASE_ROOM_X, BASE_ROOM_Y), newRoom);
    }
    
    /**
    * Adds a new room to the house.
    * Fails if the room is smaller than 4 sq ft,
    * if the room is outside the base room of the house,
    * if the room intersects another room without being entirely inside it,
    * or if the room's walls intersect furniture.
    * 
    * @param originPointX   the x-coordinate of the top left corner of the room to be added
    * @param originPointY   the y-coordinate of the top left corner of the room to be added
    * @param roomWidth      the given width of the room to be added
    * @param roomHeight     the given height of the room to be added
    */
    public void addRoom(double originPointX, double originPointY, double roomWidth, double roomHeight) {
        //create room
        Room newRoom = new Room( 
                 Map.of(new Position(originPointX, originPointY), new Wall(new CollisionRectangle(WALL_THICKNESS, roomHeight)),  //left wall
                        new Position(originPointX, originPointY), new Wall(new CollisionRectangle(roomWidth, WALL_THICKNESS)),   //top wall
                        new Position((originPointX + roomWidth - WALL_THICKNESS), originPointY), new Wall(new CollisionRectangle(WALL_THICKNESS, roomHeight)),    //right wall
                        new Position(originPointX, (originPointY + roomHeight - WALL_THICKNESS)), new Wall(new CollisionRectangle(roomWidth, WALL_THICKNESS))));  //bottom wall
        //check eligibility
        if (roomWidth*roomHeight < MIN_ROOM_SIZE) {
            System.out.println("Invalid size: room cannot be smaller than 4 sq ft.");
            return; 
        }
        //if room is outside base room
        //if room intersects another room without being entirely inside it
        //if room's walls intersect furniture
        
        //add room to house
        rooms.put(new Position(originPointX, originPointY), newRoom);
        System.out.println("Room added.");
        
//        throw new UnsupportedOperationException("Not implemented yet. Yell at Team 4 Robot Vacuum.");
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
     * @param pos the room to be removed from the house
     */
    public void removeRoom(Position pos) {
        for (Position p : rooms.keySet()) {
            if (p.getX() == pos.getX() && p.getY() == pos.getY()) {
                if (rooms.get(p).getIsBaseRoom()) {
                    System.out.println("Can't remove base room.");
                    return;
                }
                else {
                    rooms.remove(p);
                    System.out.println("Room removed:" + p);
                    return;
                }
            }
        }
        System.out.println("Room not found.");
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
     * @return floor covering
     */
    public FlooringType getFloorCovering() {
        return floorCovering;
    }
    
    /**
     * Returns the room with the given position.
     * 
     * @param pos
     * @return the room at those coordinates
     */
    public Room getRoom(Position pos) {
        for (Position p : rooms.keySet()) {
            if (p.getX() == pos.getX() && p.getY() == pos.getY()) {
                return rooms.get(p);
            }
        }
        
        return null;
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
