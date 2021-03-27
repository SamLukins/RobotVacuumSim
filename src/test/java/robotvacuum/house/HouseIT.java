package robotvacuum.house;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import robotvacuum.collision.CollisionRectangle;
import robotvacuum.collision.Position;
import robotvacuum.io.Serializer;

import java.io.IOException;
import java.util.Map;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

/**
 * @author SamL
 */
public class HouseIT {

    public HouseIT() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    @Test
    public void testCreateHouseSingleRoom() {
        Wall w1 = new Wall(new CollisionRectangle(5.0, 50.0));
        Wall w2 = new Wall(new CollisionRectangle(50.0, 5.0));
        Wall w3 = new Wall(w1);
        Wall w4 = new Wall(w2);

        Room r1 = new Room(
                Map.of(new Position(0.0, 0.0), w1,
                        new Position(5.0, 0.0), w2,
                        new Position(55.0, 0.0), w3,
                        new Position(5.0, 45.0), w4)
        );

        House h = new House(Map.of(new Position(0.0, 0.0), r1), FlooringType.HARD);

        assertThat(h.getFloorCovering(), is(FlooringType.HARD));
    }

    @Test
    public void testSerializer() throws IOException, ClassNotFoundException {
        House h1 = new House(60, 80, FlooringType.FRIEZE);
        Serializer s = new Serializer();
        h1.addRoom(20, 20, 10, 10);
        h1.addRoom(2, 10, 5, 5);
        s.serializeHouse(h1, "sTest");
        House h2 = s.deserializeHouse("sTest");
        assertNotNull(h2.getRoom(new Position(20, 20)));
        assertNotNull(h2.getRoom(new Position(2, 10)));
        assertNull(h2.getRoom(new Position(52, 47)));
        assertThat(h2.getFloorCovering(), is(FlooringType.FRIEZE));
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
