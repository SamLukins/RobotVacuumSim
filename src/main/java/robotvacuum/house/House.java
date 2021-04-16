package robotvacuum.house;

import robotvacuum.collision.CollisionRectangle;
import robotvacuum.collision.Position;

import java.io.Serializable;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Austen Seidler
 */

public class House implements Serializable {
    //TODO: When doors are added to the Room class, addRoom will need to be updated
    //to let the user specify door location.

    //constants
    //TODO: move to enum
    private static final double WALL_THICKNESS = 2;
    private static final double MIN_ROOM_SIZE = 4;
    private static final double BASE_ROOM_X = 0;
    private static final double BASE_ROOM_Y = 0;

    //variables
    private FlooringType floorCovering;
    private final Map<Position, Room> rooms;
    double houseWidth, houseHeight;

    //old constructor
    public House(Map<Position, Room> rooms, FlooringType ft) {
        this.rooms = new HashMap<>(rooms);
        floorCovering = ft;
    }

    public void addRoom(final Position pos, final Room r) {
        //TODO: Add bounds checks
        rooms.put(pos, r);
    }

    /**
     * Adds a new room to the house.
     * Fails if the room is smaller than 4 sq ft,
     * if the room is outside the base room of the house,
     * if the room intersects another room without being entirely inside it,
     * or if the room's walls intersect furniture.
     *
     * @param originPointX the x-coordinate of the top left corner of the room to be added
     * @param originPointY the y-coordinate of the top left corner of the room to be added
     * @param roomWidth    the given width of the room to be added
     * @param roomHeight   the given height of the room to be added
     */
    public void addRoom(double originPointX, double originPointY, double roomWidth, double roomHeight) {
        //create room
        Room newRoom = new Room(
                Map.of(new Position(originPointX, originPointY), new Wall(new CollisionRectangle(WALL_THICKNESS, (roomHeight - WALL_THICKNESS))),  //left wall
                        new Position((originPointX - WALL_THICKNESS), originPointY), new Wall(new CollisionRectangle((roomWidth - WALL_THICKNESS), WALL_THICKNESS)),   //top wall
                        new Position((originPointX + roomWidth - WALL_THICKNESS), (originPointY - WALL_THICKNESS)), new Wall(new CollisionRectangle(WALL_THICKNESS, (roomHeight - WALL_THICKNESS))),    //right wall
                        new Position(originPointX, (originPointY + roomHeight - WALL_THICKNESS)), new Wall(new CollisionRectangle((roomWidth - WALL_THICKNESS), WALL_THICKNESS))));  //bottom wall
        //check eligibility
        if (roomWidth * roomHeight < MIN_ROOM_SIZE) {
            System.out.println("Invalid size: room cannot be smaller than 4 sq ft.");
            return;
        }

        if (originPointX < BASE_ROOM_X || originPointY < BASE_ROOM_Y
                || originPointX > (BASE_ROOM_X + houseWidth)
                || originPointY > (BASE_ROOM_Y + houseHeight)) {
            System.out.println("Invalid location: out of bounds.");
            return;
        }
        //if room intersects another room without being entirely inside it
        //if room's walls intersect furniture

        //add room to house
        rooms.put(new Position(originPointX, originPointY), newRoom);
        System.out.println("Room added.");

    }

    /**
     * Removes a room from the house.
     *
     * @param pos the room to be removed from the house
     */
    public void removeRoom(Position pos) {
        rooms.remove(pos);
    }

    /**
     * Changes the floor covering of the house.
     *
     * @param ft the flooring type
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
     * @param pos the position of the room to return
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
