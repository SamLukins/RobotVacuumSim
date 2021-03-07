package robotvacuum.house;

import java.util.ArrayList;
import robotvacuum.house.furniture.*;
import java.awt.Rectangle;

/**
 *
 * @author Austen Seidler
 */

public class House extends Rectangle {
    //TODO: When doors are added to the Room class, addRoom will need to be updated
    //to let the user specify door location.
    //constants
    public static final int DEFAULT_X = 0;
    public static final int DEFAULT_Y = 0;
    public static final int DEFAULT_WIDTH = 50;
    public static final int DEFAULT_HEIGHT = 40;
    
    //variables
    int floorCovering; //1 = hard, 2 = loop pile, 3 = cut pile, 4 = frieze cut pile
    ArrayList<Room> rooms;
    ArrayList<Furniture> furniture;
    
    //constructor
    public House() {
        rooms = new ArrayList<>();
        furniture = new ArrayList<>();
        x = DEFAULT_X;
        y = DEFAULT_Y;
        width = DEFAULT_WIDTH;
        height = DEFAULT_HEIGHT;
        floorCovering = 1;
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
        Room newRoom = new Room(originPointX, originPointY, roomWidth, roomHeight);
        if (newRoom.getWidth()*newRoom.getHeight() < 4) {
            System.out.println("Invalid size: room cannot be smaller than 4 sq ft.");
            return;
        }
        if (!(this.contains(newRoom))) {
            System.out.println("Invalid location: room cannot extend outside house");
            return;
        }
        for (Room r : rooms) {
            if (newRoom.intersects(r) && !(newRoom.contains(r) || r.contains(newRoom))) {
                System.out.println("Invalid location: room wall cannot intersect other room wall");
                return;
            }
        }
        for (Furniture f : furniture) {
            if (newRoom.intersects(f) && !(newRoom.contains(f))) {
                System.out.println("Invalid location: room wall cannot intersect furniture");
                return;
            }
        }
        rooms.add(newRoom);
        System.out.println("New room added: " + newRoom.toString());
    }
    
    /**
     * Removes a room from the house.
     * 
     * @param roomToRemove the room to be removed from the house
     */
    public void removeRoom(Room roomToRemove) {
        rooms.remove(roomToRemove);
        System.out.println("Room removed: " + roomToRemove.toString());
    }
    
    /**
     * Adds a new furniture item to the house.
     * Fails if the furniture item extends outside the house,
     * if the furniture item intersects a room's wall,
     * or if the furniture item intersects another furniture item.
     * 
     * @param furnitureType   whether the furniture item is a chair (1), table (2), or chest (other)
     * @param originPointX    the x-coordinate of the top left corner of the furniture item to be added
     * @param originPointY    the y-coordinate of the top left corner of the furniture item to be added
     * @param furnitureWidth  the given width of the furniture item to be added
     * @param furnitureHeight the given Height of the furniture item to be added
     */
    public void addFurniture(int furnitureType, int originPointX, int originPointY, int furnitureWidth, int furnitureHeight) {
        Furniture newFurniture;
        switch (furnitureType) {
            case 1:  newFurniture = new Chair(originPointX, originPointY, furnitureWidth, furnitureHeight);
                     break;
            case 2:  newFurniture = new Table(originPointX, originPointY, furnitureWidth, furnitureHeight);
                     break;
            default: newFurniture = new Chest(originPointX, originPointY, furnitureWidth, furnitureHeight);
                     break;
        }
        if (!(this.contains(newFurniture))) {
            System.out.println("Invalid location: furniture cannot extend outside house");
            return;
        }
        for (Room r : rooms) {
            if (!(r.contains(newFurniture)) && newFurniture.intersects(r)) {
                System.out.println("Invalid location: furniture cannot intersect room wall");
                return;
            }
        }
        for (Furniture f : furniture) {
            if (newFurniture.intersects(f)) {
                System.out.println("Invalid location: furniture cannot intersect other furniture");
                return;
            }
        }
        furniture.add(newFurniture);
        System.out.println("New furniture added: " + newFurniture.toString());
    }
    
    /**
     * Removes a furniture item from the house.
     * 
     * @param furnitureToRemove the room to be removed from the house
     */
    public void removeFurniture(Furniture furnitureToRemove) {
        furniture.remove(furnitureToRemove);
        System.out.println("Furniture removed: " + furnitureToRemove.toString());
    }
    
    /**
     * Changes the width of the house.
     * Fails if the change would cause the area of the house to exceed 8000 sq ft, 
     * be less than 200 sq ft, or fail to contain a room or furniture item.
     * Use instead of Rectangle's setSize() to ensure size stays within bounds.
     * 
     * @param inputWidth the new width of the house
     */
    public void setWidth(int inputWidth) {
        if (inputWidth*height > 8000) {
            System.out.println("Error: house area too large");
        }
        else if (inputWidth*height < 200) {
            System.out.println("Error: house area too small");
        }
        else {
            int originalWidth = width;
            width = inputWidth;
            for (Room r : rooms) {
                if (!(this.contains(r))) {
                    width = originalWidth;
                    System.out.println("Error: New width fails to completely contain a room.");
                }
            }
            for (Furniture f : furniture) {
                if (!this.contains(f)) {
                    width = originalWidth;
                    System.out.println("Error: new width fails to completely contain a furniture item.");
                }
            }
            
            if (width == inputWidth) {
                System.out.println("House width changed to " + width);
            }
        }
    }
    
    /**
     * Changes the height of the house.
     * Fails if the change would cause the area of the house to exceed 8000 sq ft,
     * be less than 200 sq ft, or fail to contain a room or furniture item.
     * Use instead of Rectangle's setSize() to ensure size stays within bounds.
     * 
     * @param inputHeight the new height of the house
     */
    public void setHeight(int inputHeight) {
        if (inputHeight*width > 8000) {
            System.out.println("Error: house area too large");
        }
        else if (inputHeight*width < 200) {
            System.out.println("Error: house area too small");
        }
        else {
            int originalHeight = height;
            height = inputHeight;
            for (Room r : rooms) {
                if (!(this.contains(r))) {
                    height = originalHeight;
                    System.out.println("Error: New height fails to completely contain a room.");
                }
            }
            for (Furniture f : furniture) {
                if (!this.contains(f)) {
                    height = originalHeight;
                    System.out.println("Error: new height fails to completely contain a furniture item.");
                }
            }
            
            if (height == inputHeight) {
                System.out.println("House height changed to " + height);
            }
        }
    }
    
    /**
     * Changes the floor covering of the house.
     * 
     * @param inputFloor 1 = hard, 2 = loop pile, 3 = cut pile, 4 = frieze cut pile
     */
    public void setFloorCovering(int inputFloor) {
        floorCovering = inputFloor;
        System.out.println("House floor changed to " + floorCovering);
    }
    
    /**
     * Returns the floor covering used by the house.
     * 
     * @return floor covering ID (1 = hard, 2 = loop pile, 3 = cut pile, 4 = frieze cut pile)
     */
    public int getFloorCovering() {
        return floorCovering;
    }
    
    /**
     * Returns the room with the given values for x and y.
     * 
     * @param x the x-coordinate of the room to return
     * @param y the y-coordinate of the room to return
     * @return the room at those coordinates (or null if no room at those coordinates)
     */
    public Room getRoom(int x, int y) {
        for (Room r : rooms) {
            if (r.getX() == x && r.getY() == y) {
                System.out.println("Room retrieved: " + r.toString());
                return r;
            }
        }
        System.out.println("Room not found.");
        return null;
    }
    
    /**
     * Returns every room in the house.
     * 
     * @return A list of every room
     */
    public ArrayList<Room> getAllRooms() {
        return rooms;
    }
    
    /**
     * Returns the furniture item with the given values for x and y.
     * 
     * @param x the x-coordinate of the furniture item to return
     * @param y the y-coordinate of the furniture item to return
     * @return the furniture item at those coordinates (or null if no furniture item at those coordinates)
     */
    public Furniture getFurniture(int x, int y) {
        for (Furniture f : furniture) {
            if (f.getX() == x && f.getY() == y) {
                System.out.println("Furniture retrieved: " + f.toString());
                return f;
            }
        }
        System.out.println("Furniture not found.");
        return null;
    }
    
    /**
     * Returns every furniture item in the house.
     * 
     * @return A list of every furniture item
     */
    public ArrayList<Furniture> getAllFurniture() {
        return furniture;
    }
    
    /**
     * Lists all of the rooms in the house.
     */
    public void listRooms() {
        System.out.println("Rooms:");
        for (Room r : rooms) {
            System.out.println(r.toString());
        }
    }
    
    /**
     * Lists all of the furniture in the house.
     */
    public void listFurniture() {
        System.out.println("Furniture:");
        for (Furniture f : furniture) {
            System.out.println(f.toString());
        }
    }
}
