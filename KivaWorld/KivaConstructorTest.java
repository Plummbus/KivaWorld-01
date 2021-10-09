
import edu.duke.Point;
import java.util.Random;

/**
 * Test File to check if the <tt>Kiva</tt> class constructors work properly. The base and overloaded class constructors are called using a default map layout,
 * and then a message is printed to the console based on whether or not the current object possess the values we expect it to have.
 * 
 * @author Franz Jacob Hernandez (herfrn@amazon.com) 
 * @version 0.06
 * @since 10-03-2021
 */
public class KivaConstructorTest {
    
    private static Random rand = new Random();  //for 2 param constructor test, checking a new random coordinate everytime.
    private String defaultLayout = ""
                            + "-------------\n"
                            + "        P   *\n"
                            + "   **       *\n"
                            + "   **       *\n"
                            + "  K       D *\n"
                            + " * * * * * **\n"
                            + "-------------\n";

    private FloorMap defaultMap = new FloorMap(defaultLayout);
    
    /**
     * Tests if base <tt>Kiva</tt> constructor works as intended.
     * Test is passed a <tt>FloorMap</tt> object, and prints a message to the console based on whether or not the <tt>Kiva</tt> instance has the values it is
     * expected to have.
     * 
     * @see Kiva#Kiva(FloorMap, Point)
     */
    public void testSingleArgumentConstructor() {
        Kiva kiva = new Kiva(defaultMap);

        Point initialLocation = kiva.getCurrentLocation();
        Point expectedLocation = defaultMap.getInitialKivaLocation();
        
        if (sameLocation(initialLocation, expectedLocation)) {
            System.out.println("testSingleArgumentConstructor SUCCESS");
        } else {
            System.out.println(String.format("testSingleArgumentConstructor FAIL: %s != (2, 4)!", initialLocation));
        }
    }
        
    /**
     * Helper function for the constructor tests.
     * Lets us know if two Points are the same Point.
     * 
     * @param a     the first Point you wish to compare.
     * @param b     the second Point you wish to compare.
     * 
     * @return true if parameters are equal, false if not
     */
    private boolean sameLocation(Point a, Point b) {
        return a.getX() == b.getX() && a.getY() == b.getY();
    }
    
    /**
     * Tests if overloaded <tt>Kiva</tt> constructor works as intended.
     * Test is passed a <tt>FloorMap</tt> object and a Point that is not the initial <tt>Kiva</tt> starting location based on the map. It then prints a message
     * to the console based on whether or not the <tt>Kiva</tt> instance has the values it is expected to have.
     * 
     * @see Kiva#Kiva
     */
    public void testTwoArgumentConstructor() {
        //initially the Kiva will have its inital location be whatever it is on the defaultMap...
        int xRand = rand.nextInt(defaultMap.getMaxColNum()); //nextInt(n) give values between 0(inclusive) and n(exclusive).
        int yRand = rand.nextInt(defaultMap.getMaxRowNum());
        Kiva kiva = new Kiva(defaultMap, new Point(xRand, yRand));  //...but the overloaded constructor assigns a new value for Kiva location on the second line of its method AFTER the default constructor is called.
        Point initialLocation = kiva.getCurrentLocation();
        Point expectedLocation = new Point(xRand, yRand);   //thought the test would be more robust if it checked any combination of coordinates within the dimenions of the map
                                                    //with each test, rather than just a hardcoded one. Ultimately, it doesn't matter if the starting location in this 
                                                    //test makes sense in the context of the map and its obstacles since it is just a test
                                                    //(and doesn't do any exception throwing).
        //System.out.printf("\nX-coord: %d, Y-coord: %d\n", xRand, yRand);  //this is just here to show that new values are being tested each method call
        if (sameLocation(initialLocation, expectedLocation)) {
            System.out.println("testTwoArgumentConstructor SUCCESS");
        } else {
            System.out.println(String.format("testTwoArgumentConstructor FAIL: Kiva's (%d, %d) != Expected (%d, %d)!", 
                initialLocation.getX(), initialLocation.getY(), expectedLocation.getX(), expectedLocation.getY()));
        }
    }
        
}

