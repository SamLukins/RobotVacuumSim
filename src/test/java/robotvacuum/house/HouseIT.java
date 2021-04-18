package robotvacuum.house;

import org.junit.jupiter.api.Test;
import robotvacuum.collision.Position;
import robotvacuum.io.Serializer;
import robotvacuum.house.furniture.*;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

/**
 * @author SamL and Austen Seidler
 */
public class HouseIT {
    
//    @Test
//    public void testCreateHouseSingleRoom() {
//        Wall w1 = new Wall(new CollisionRectangle(5.0, 50.0));
//        Wall w2 = new Wall(new CollisionRectangle(50.0, 5.0));
//        Wall w3 = new Wall(w1);
//        Wall w4 = new Wall(w2);
//
//        Room r1 = new Room(
//                Map.of(new Position(0.0, 0.0), w1,
//                        new Position(5.0, 0.0), w2,
//                        new Position(55.0, 0.0), w3,
//                        new Position(5.0, 45.0), w4)
//        );
//
//        House h = new House(Map.of(new Position(0.0, 0.0), r1), FlooringType.HARD);
//
//
//        assertEquals(h.getFloorCovering(), FlooringType.HARD);
//    }

    @Test
    public void testSerializer() throws IOException, ClassNotFoundException {
        House h1 = new House(20, 25, FlooringType.FRIEZE);
        Serializer s = new Serializer();
        h1.addRoom(5, 5, 4, 4);
        h1.addRoom(2.5, 3, 2, 1.5);
        s.serializeHouse(h1, "sTest");
        House h2 = s.deserializeHouse("sTest");
        assertNotNull(h2.getRoom(new Position(5, 5)));
        assertNotNull(h2.getRoom(new Position(2.5, 3)));
        assertNull(h2.getRoom(new Position(6, 18)));
        assertEquals(h2.getFloorCovering(), FlooringType.FRIEZE);
    }
    
    @Test
    public void testRemoveRoom() {
        House h1 = new House(20, 25, FlooringType.HARD);
        h1.addRoom(5, 5, 4, 4);
        assertNotNull(h1.getRoom(new Position(5, 5)));
        h1.removeRoom(new Position(5, 5));
        assertNull(h1.getRoom(new Position(5, 5)));
    }
    
    @Test
    public void testAddDoor() {
        House h1 = new House(20, 25, FlooringType.HARD);
        h1.addRoom(5, 5, 4, 4);
        //System.out.println("Old width: " + h1.getRoom(new Position(5, 5)).getWall(new Position(5, 5)).getcRect().getHeight());
        h1.getRoom(new Position(5, 5)).addDoor(new Position(5, 5), 100);
        assertNull(h1.getRoom(new Position(5, 5)).getWall(new Position(5, 7.35)));
        h1.getRoom(new Position(5, 5)).addDoor(new Position(5, 5), 1);
        assertNotNull(h1.getRoom(new Position(5, 5)).getWall(new Position(5, 5)));
        //System.out.println("New width: " + h1.getRoom(new Position(5, 5)).getWall(new Position(5, 5)).getcRect().getHeight());
        assertNotNull(h1.getRoom(new Position(5, 5)).getWall(new Position(5, 7.35)));
    }
    
//    @Test
//    public void oldTestRemoveFurniture() {
//        House h1 = new House(60, 80, FlooringType.HARD);
//        h1.addChestToRoom(h1.getRoom(new Position(0, 0)), 20, 20, 10, 10);
//        assertNotNull(h1.getFurnitureFromRoom(h1.getRoom(new Position(0, 0)), new Position(20, 20)));
//        h1.removeFurnitureFromRoom(h1.getRoom(new Position(0, 0)), new Position(20, 20));
//        assertNull(h1.getFurnitureFromRoom(h1.getRoom(new Position(0, 0)), new Position(20, 20)));
//    }
    
    @Test
    public void testRemoveChest() {
        House h1 = new House(20, 25, FlooringType.HARD);
        h1.getRoom(new Position(0, 0)).addChest(5, 5, 4, 4);
        assertNotNull(h1.getRoom(new Position(0, 0)).getFurniture(new Position(5, 5)));
        h1.getRoom(new Position(0, 0)).removeFurniture(new Position(5, 5));
        assertNull(h1.getRoom(new Position(0, 0)).getFurniture(new Position(5, 5)));
    }
    
    @Test
    public void testRemoveTable() {
        double radius = 0.2;
        House h1 = new House(20, 25, FlooringType.HARD);
        h1.getRoom(new Position(0, 0)).addTable(5, 5, 4, 4);
        assertNotNull(h1.getRoom(new Position(0, 0)).getFurniture(new Position(5, 5)));
        Table t = (Table)h1.getRoom(new Position(0, 0)).getFurniture(new Position(5, 5));
        assertNotNull(t.getLeg(new Position(5 + radius, 5 + radius)));
        assertNotNull(t.getLeg(new Position(9 - radius, 5 + radius)));
        assertNotNull(t.getLeg(new Position(5 + radius, 9 - radius)));
        assertNotNull(t.getLeg(new Position(9 - radius, 9 - radius)));
        assertNull(t.getLeg(new Position(5, 5)));
        h1.getRoom(new Position(0, 0)).removeFurniture(new Position(5, 5));
        assertNull(h1.getRoom(new Position(0, 0)).getFurniture(new Position(5, 5)));
    }

//    /**
//     * Test of addRoom method, of class House.
//     */
//    @Test
//    public void testAddRoom() {
////        System.out.println("addRoom");
////        int originPointX = 0;
////        int originPointY = 0;
////        int roomWidth = 0;
////        int roomHeight = 0;
////        House instance = new House();
////        instance.addRoom(originPointX, originPointY, roomWidth, roomHeight);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of removeRoom method, of class House.
//     */
//    @Test
//    public void testRemoveRoom() {
////        System.out.println("removeRoom");
////        Room roomToRemove = null;
////        House instance = new House();
////        instance.removeRoom(roomToRemove);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of setFloorCovering method, of class House.
//     */
//    @Test
//    public void testSetFloorCovering() {
////        System.out.println("setFloorCovering");
////        int inputFloor = 0;
////        House instance = new House();
////        instance.setFloorCovering(inputFloor);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of getFloorCovering method, of class House.
//     */
//    @Test
//    public void testGetFloorCovering() {
////        System.out.println("getFloorCovering");
////        House instance = new House();
////        int expResult = 0;
////        int result = instance.getFloorCovering();
////        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of getRoom method, of class House.
//     */
//    @Test
//    public void testGetRoom() {
////        System.out.println("getRoom");
////        int x = 0;
////        int y = 0;
////        House instance = new House();
////        Room expResult = null;
////        Room result = instance.getRoom(x, y);
////        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of getAllRooms method, of class House.
//     */
//    @Test
//    public void testGetRooms() {
////        System.out.println("getAllRooms");
////        House instance = new House();
////        ArrayList<Room> expResult = null;
////        ArrayList<Room> result = instance.getAllRooms();
////        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
}
