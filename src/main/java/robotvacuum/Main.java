package robotvacuum;

import robotvacuum.house.*;
import robotvacuum.io.*;

/**
 *
 * @author SamL
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        //Demonstration of using house and file manager functions.
        //Final product will obviously use GUI but for now system output will do.
        FileManager manager = new FileManager();
        
        House h = manager.readHouseFromFile("test");
        h.addRoom(40, 40, 1, 1); //fails due to being too small, intentional use to demonstrate failure
        h.addRoom(40, 40, 10, 10);
        h.addFurniture(2, 70, 70, 4, 4); //fails due to being outside house, intentional use
        h.addFurniture(2, 44, 44, 4, 4);
        h.setWidth(40); //fails due to not containing a room, intentional use
        h.setHeight(80);
        h.setFloorCovering(4);
        h.removeFurniture(h.getFurniture(11, 6));
        h.listRooms();
        h.listFurniture();
        manager.writeHouseToFile(h, "test2");
    }
}
