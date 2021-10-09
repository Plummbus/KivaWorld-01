
import edu.duke.Point;

/**
 * Test class for the methods in the <tt>Kiva</tt> class. Tests are focused on verifying in move methods take Kiva to the correct location and orientation, as 
 * well as verifying if non-movement actions affect <tt>Kiva</tt>'s class fields in the expected way. There is also exception throwing testing for both
 * movement and non-movement actions the <tt>Kiva</tt> can perform.
 * 
 * @author Franz Jacob Hernandez (herfrn@amazon.com) 
 * @version 0.14
 * @since 10-03-2021
 */
public class KivaMoveTest {
    
    private String defaultLayout = ""
                            + "-------------\n"
                            + "        P   *\n"
                            + "   **       *\n"
                            + "   **       *\n"
                            + "  K       D *\n"
                            + " * * * * * **\n"
                            + "-------------\n";

    private String openLayout = ""
                        + "-------------|\n"
                        + "        P   *|\n"
                        + "   ---      -|\n"
                        + "   |*|      *|\n"
                        + "  K---    D -|\n"
                        + " *     * *  *|\n"
                        + "-------------|\n";

    //default initial location is Point(2, 4)
    //left-most position is Point(0, 0), for reference
    FloorMap defaultMap = new FloorMap(defaultLayout);
    FloorMap openMap = new FloorMap(openLayout);
    
    /**
     * Method to help understand how the map's dimensions are setup. Displays a the dimensions to the user via the console.
     * 
     * @see FloorMap#getMinRowNum
     * @see FloorMap#getMinColNum
     * @see FloorMap#getMaxRowNum
     * @see FloorMap#getMaxColNum
     */
    public void showMapDimensions() {
        Kiva kiva = new Kiva(defaultMap);
        int minY = kiva.getMap().getMinRowNum();
        int minX = kiva.getMap().getMinColNum();
        int maxY = kiva.getMap().getMaxRowNum();
        int maxX = kiva.getMap().getMaxColNum();
        
        String message = String.format("Map Dimensions:\nmin row: %d max row: %d \nmin col: %d max col: %d", minY, maxY, minX, maxX);
        System.out.print(message);
    }
    
    /**
     * Method to help understand what values getDelta() returns. Displays the values of each direction facing to the user via the console.
     * 
     * @see FacingDirection#getDelta
     */
    public void testDeltas() {
        Kiva kiva = new Kiva(defaultMap);
        System.out.println(kiva.getDirectionFacing().getDelta());   //(0, 1)  starts at UP
        
        kiva.move(KivaCommand.TURN_LEFT);
        System.out.println(kiva.getDirectionFacing().getDelta());   //(-1, 0)
        
        kiva.move(KivaCommand.TURN_LEFT);
        System.out.println(kiva.getDirectionFacing().getDelta());   //(0, -1)
        
        kiva.move(KivaCommand.TURN_LEFT);
        System.out.println(kiva.getDirectionFacing().getDelta());   //(1, 0)
    }
    
    /**
     * Tests </tt>Kiva</tt>'s forward movement from the <tt>UP</tt> orientation. Main field(s) to be verified are the location and direction it is facing. Displays a message to the user
     * via the console, verification determines which message is displayed.
     * 
     * @see Kiva#moveForward
     */
    public void testForwardFromUp() {
        Kiva kiva = new Kiva(defaultMap);
        kiva.move(KivaCommand.FORWARD);
        
        verifyKivaState("testForwardFromUp", kiva, new Point(2, 3), FacingDirection.UP, false, false);
    }
    
    /**
     * Tests </tt>Kiva</tt>'s ability to turn left. Main field(s) to be verified are the location and direction it is facing. Displays a message to the user
     * via the console, verification determines which message is displayed. <tt>moveLeft()</tt> is called once.
     * 
     * @see Kiva#moveLeft
     *
     */
    public void testTurnLeftFromUp() {
        Kiva kiva = new Kiva(defaultMap);
        
        kiva.move(KivaCommand.TURN_LEFT);
        
        verifyKivaState("testTurnLeftFromUp", kiva, new Point(2, 4), FacingDirection.LEFT, false, false);
    }
    
    /**
     * Tests </tt>Kiva</tt>'s ability to turn left. Main field(s) to be verified are the location and direction it is facing. Displays a message to the user
     * via the console, verification determines which message is displayed. <tt>moveLeft()</tt> is called twice.
     * 
     * @see Kiva#moveLeft
     *
     */
    public void testTurnLeftFromLeft() {
        Kiva kiva = new Kiva(defaultMap);
        
        kiva.move(KivaCommand.TURN_LEFT);
        kiva.move(KivaCommand.TURN_LEFT);
        
        verifyKivaState("testTurnLeftFromUp", kiva, new Point(2, 4), FacingDirection.DOWN, false, false);
    }
    
    /**
     * Tests </tt>Kiva</tt>'s ability to turn left. Main field(s) to be verified are the location and direction it is facing. Displays a message to the user
     * via the console, verification determines which message is displayed. <tt>moveLeft()</tt> is called thrice.
     * 
     * @see Kiva#moveLeft
     *
     */
    public void testTurnLeftFromDown() {
        Kiva kiva = new Kiva(defaultMap);
        
        kiva.move(KivaCommand.TURN_LEFT);
        kiva.move(KivaCommand.TURN_LEFT);
        kiva.move(KivaCommand.TURN_LEFT);
        
        verifyKivaState("testTurnLeftFromUp", kiva, new Point(2, 4), FacingDirection.RIGHT, false, false);
    }
    
    /**
     * Tests </tt>Kiva</tt>'s ability to turn left. Main field(s) to be verified are the location and direction it is facing. Displays a message to the user
     * via the console, verification determines which message is displayed. <tt>moveLeft()</tt> is called 4 times.
     * 
     * @see Kiva#moveLeft
     *
     */
    public void testTurnLeftFromRight() {
        Kiva kiva = new Kiva(defaultMap);
        
        kiva.move(KivaCommand.TURN_LEFT);
        kiva.move(KivaCommand.TURN_LEFT);
        kiva.move(KivaCommand.TURN_LEFT);
        kiva.move(KivaCommand.TURN_LEFT);
        
        verifyKivaState("testTurnLeftFromUp", kiva, new Point(2, 4), FacingDirection.UP, false, false);
    }
    
    /**
     * Tests </tt>Kiva</tt>'s forward movement from the <tt>LEFT</tt> orientation. Main field(s) to be verified are the location and direction it is facing. Displays a message to the user
     * via the console, verification determines which message is displayed. <tt>moveLeft()</tt> is called once.
     * 
     * @see Kiva#moveForward
     * @see Kiva#moveLeft
     */
    public void testForwardWhileFacingLeft() {
        Kiva kiva  = new Kiva(defaultMap);
        
        kiva.move(KivaCommand.TURN_LEFT);
        kiva.move(KivaCommand.FORWARD);
        
        verifyKivaState("testForwadWhileFacingLeft", kiva, new Point(1, 4), FacingDirection.LEFT, false, false);
    }
    
    /**
     * Tests </tt>Kiva</tt>'s forward movement from the <tt>LEFT</tt> orientation. Main field(s) to be verified are the location and direction it is facing. Displays a message to the user
     * via the console, verification determines which message is displayed. <tt>moveLeft()</tt> is called twice.
     * 
     * @see Kiva#moveForward
     * @see Kiva#moveLeft
     */
    public void testForwardWhileFacingDown() {
        Kiva kiva = new Kiva(defaultMap);
        
        kiva.move(KivaCommand.TURN_LEFT);
        kiva.move(KivaCommand.TURN_LEFT);
        kiva.move(KivaCommand.FORWARD);
        
        verifyKivaState("testForwardWhileFacingDown", kiva, new Point(2, 5), FacingDirection.DOWN, false, false);
    }
    
    /**
     * Tests </tt>Kiva</tt>'s forward movement from the <tt>LEFT</tt> orientation. Main field(s) to be verified are the location and direction it is facing. Displays a message to the user
     * via the console, verification determines which message is displayed. <tt>moveLeft()</tt> is called thrice.
     * 
     * @see Kiva#moveForward
     * @see Kiva#moveLeft
     */
    public void testForwardWhileFacingRight() {
        Kiva kiva = new Kiva(defaultMap);
        
        kiva.move(KivaCommand.TURN_LEFT);
        kiva.move(KivaCommand.TURN_LEFT);
        kiva.move(KivaCommand.TURN_LEFT);
        kiva.move(KivaCommand.FORWARD);
        
        verifyKivaState("testForwardWhileFacingRight", kiva, new Point(3, 4), FacingDirection.RIGHT, false, false);
    }
    
    /**
     * Tests </tt>Kiva</tt>'s ability to turn right. Main field(s) to be verified are the location and direction it is facing. Displays a message to the user
     * via the console, verification determines which message is displayed. <tt>moveRight()</tt> is called once.
     * 
     * @see Kiva#moveRight
     */
    public void testTurnRightFromUp() {
        Kiva kiva = new Kiva(defaultMap);
        
        kiva.move(KivaCommand.TURN_RIGHT);
        
        verifyKivaState("testTurnRightFromUp", kiva, new Point(2, 4), FacingDirection.RIGHT, false, false);
    }
    
    /**
     * Tests </tt>Kiva</tt>'s ability to turn right. Main field(s) to be verified are the location and direction it is facing. Displays a message to the user
     * via the console, verification determines which message is displayed. After <tt>moveLeft()</tt> is called once, <tt>moveRight()</tt> is called once.
     * 
     * @see Kiva#moveRight
     * @see Kiva#moveLeft
     */
    public void testTurnRightFromLeft() {
        Kiva kiva = new Kiva(defaultMap);
        
        kiva.move(KivaCommand.TURN_LEFT);
        kiva.move(KivaCommand.TURN_RIGHT);
        
        verifyKivaState("testTurnRightFromUp", kiva, new Point(2, 4), FacingDirection.UP, false, false);
    }
    
    /**
     * Tests </tt>Kiva</tt>'s ability to turn right. Main field(s) to be verified are the location and direction it is facing. Displays a message to the user
     * via the console, verification determines which message is displayed. After <tt>moveLeft()</tt> is called twice, <tt>moveRight()</tt> is called once.
     * 
     * @see Kiva#moveRight
     * @see Kiva#moveLeft
     */
    public void testTurnRightFromDown() {
        Kiva kiva = new Kiva(defaultMap);
        
        kiva.move(KivaCommand.TURN_LEFT);
        kiva.move(KivaCommand.TURN_LEFT);
        kiva.move(KivaCommand.TURN_RIGHT);
        
        verifyKivaState("testTurnRightFromUp", kiva, new Point(2, 4), FacingDirection.LEFT, false, false);
    }
    
    /**
     * Tests </tt>Kiva</tt>'s ability to turn right. Main field(s) to be verified are the location and direction it is facing. Displays a message to the user
     * via the console, verification determines which message is displayed. After <tt>moveLeft()</tt> is called thrice, <tt>moveRight()</tt> is called once.
     * 
     * @see Kiva#moveRight
     * @see Kiva#moveLeft
     */
    public void testTurnRightFromRight() {
        Kiva kiva = new Kiva(defaultMap);
        
        kiva.move(KivaCommand.TURN_LEFT);
        kiva.move(KivaCommand.TURN_LEFT);
        kiva.move(KivaCommand.TURN_LEFT);
        kiva.move(KivaCommand.TURN_RIGHT);
        
        verifyKivaState("testTurnRightFromUp", kiva, new Point(2, 4), FacingDirection.DOWN, false, false);
    }
    
    /**
     * Tests </tt>Kiva</tt>'s ability to pick up a Pod. Main functionality that is being tested is <tt>Kiva</tt>'s takePod() method.
     * Displays a message to the user based on whether or not the <tt>Kiva</tt> was able to pick up a Pod.
     * 
     * @see Kiva#takePod
     */
    public void testTakeOnPod() {
        Kiva kiva = new Kiva(defaultMap);
        
        kiva.move(KivaCommand.FORWARD);
        kiva.move(KivaCommand.FORWARD);
        kiva.move(KivaCommand.FORWARD);
        kiva.move(KivaCommand.TURN_RIGHT);
        kiva.move(KivaCommand.FORWARD);
        kiva.move(KivaCommand.FORWARD);
        kiva.move(KivaCommand.FORWARD);
        kiva.move(KivaCommand.FORWARD);
        kiva.move(KivaCommand.FORWARD);
        kiva.move(KivaCommand.FORWARD);
        kiva.move(KivaCommand.TAKE);    //PICK UP POD
        
        verifyKivaState("testTakeOnPod", kiva, new Point(8, 1), FacingDirection.RIGHT, true, false);
    }
    
    /**
     * Tests </tt>Kiva</tt>'s ability to drop off a Pod. Main functionality that is being tested is <tt>Kiva</tt>'s dropPod() method.
     * Displays a message to the user based on whether or not the <tt>Kiva</tt> was able to drop up a Pod.
     * 
     * @see Kiva#dropPod
     */
    public void testDropOnDropZone() {
        Kiva kiva = new Kiva(defaultMap);
        
        //to drop pod
        kiva.move(KivaCommand.FORWARD);
        kiva.move(KivaCommand.FORWARD);
        kiva.move(KivaCommand.FORWARD);
        kiva.move(KivaCommand.TURN_RIGHT);
        kiva.move(KivaCommand.FORWARD);
        kiva.move(KivaCommand.FORWARD);
        kiva.move(KivaCommand.FORWARD);
        kiva.move(KivaCommand.FORWARD);
        kiva.move(KivaCommand.FORWARD);
        kiva.move(KivaCommand.FORWARD);
        kiva.move(KivaCommand.TAKE);    //Point(8, 1), PICK UP POD
        
        //to drop zone
        kiva.move(KivaCommand.FORWARD);
        kiva.move(KivaCommand.FORWARD); //Point(10, 1)
        kiva.move(KivaCommand.TURN_RIGHT);
        kiva.move(KivaCommand.FORWARD);
        kiva.move(KivaCommand.FORWARD);
        kiva.move(KivaCommand.FORWARD); //Point(10,4)
        kiva.move(KivaCommand.DROP);    //DROP POD
        
        verifyKivaState("testDropOnDropZone", kiva, new Point(10, 4), FacingDirection.DOWN, false, true);
    }
    
    /**
     * Tests </tt>Kiva</tt>'s exception handling for dropping Pods. Main functionality that is being tested is <tt>Kiva</tt>'s dropPod() method.
     * An IllegalDropZoneLocation exception should be thrown along with a diagnostic message.
     * 
     * @see Kiva#dropPod
     * @see IllegalDropZoneLocation
     */
    public void testDropPodOnEmpty() {
        Kiva kiva = new Kiva(defaultMap);
        
        //to drop pod
        kiva.move(KivaCommand.FORWARD);
        kiva.move(KivaCommand.FORWARD);
        kiva.move(KivaCommand.FORWARD);
        kiva.move(KivaCommand.TURN_RIGHT);
        kiva.move(KivaCommand.FORWARD);
        kiva.move(KivaCommand.FORWARD);
        kiva.move(KivaCommand.FORWARD);
        kiva.move(KivaCommand.FORWARD);
        kiva.move(KivaCommand.FORWARD);
        kiva.move(KivaCommand.FORWARD);
        kiva.move(KivaCommand.TAKE);    //Point(8, 1), PICK UP POD
        
        //to empty location
        kiva.move(KivaCommand.FORWARD);
        kiva.move(KivaCommand.FORWARD); //Point(10, 1)
        kiva.move(KivaCommand.DROP);    //DROP POD
    }

    /**
     * Tests <tt>Kiva</tt>'s exception handling for moving to coordinates outside the bounds of the map dimenions. Main functionality being tested is <tt>Kiva</tt>'s
     * validMove(int a, int b) method. There is an IllegalMoveException that should be thrown, but before the message's code segment is reached, an
     * InvalidFloorMapLocationException is thrown instead.
     * 
     * @see Kiva#validMove(int, int)
     * @see InvalidFloorMapLocationException
     * @see IllegalMoveException
     */
    public void testOutOfBoundsMove() {
        Kiva kiva = new Kiva(openMap);
        
        kiva.move(KivaCommand.TURN_LEFT);
        kiva.move(KivaCommand.FORWARD);
        kiva.move(KivaCommand.FORWARD);
        kiva.move(KivaCommand.FORWARD);
    }
    
    /**
     * Tests <tt>Kiva</tt>'s exception handling for moving into spaces occupied by an obstacle. Main functionality being tested is <tt>Kiva</tt>'s
     * validMove(int a, int b) method. IllegalMoveException should be thrown along with a diagnostic message.
     * 
     * @see Kiva#validMove(int, int)
     * @see IllegalMoveException
     */
    public void testHitObstacle() {
        Kiva kiva = new Kiva(defaultMap);
        
        kiva.move(KivaCommand.FORWARD);
        kiva.move(KivaCommand.TURN_RIGHT);
        kiva.move(KivaCommand.FORWARD);
    }
    
    /**
     * Tests <tt>Kiva</tt>'s exception handling for moving into spaces that contain a Pod while also holding a Pod. Main functionality be tested is <tt>Kiva</tt>'s
     * validMove(int a, int b) method. IllegalMoveException should be thrown along with a dianostic message.
     * 
     * @see Kiva#validMove(int, int)
     * @see IllegalMoveException
     */
    public void testHoldPodMoveToPod() {
        Kiva kiva = new Kiva(defaultMap);

        kiva.move(KivaCommand.FORWARD);
        kiva.move(KivaCommand.FORWARD);
        kiva.move(KivaCommand.FORWARD); //Point(2, 1)
        kiva.move(KivaCommand.TURN_RIGHT);
        kiva.move(KivaCommand.FORWARD);
        kiva.move(KivaCommand.FORWARD);
        kiva.move(KivaCommand.FORWARD);
        kiva.move(KivaCommand.FORWARD);
        kiva.move(KivaCommand.FORWARD);
        kiva.move(KivaCommand.FORWARD); //Point(8, 0) where the Pod is
        kiva.move(KivaCommand.TAKE);
        kiva.move(KivaCommand.FORWARD);//moves off the Pod spot to Point(9,0)
        kiva.move(KivaCommand.TURN_RIGHT);
        kiva.move(KivaCommand.TURN_RIGHT);//turn to face LEFT back towards Pod
        kiva.move(KivaCommand.FORWARD);//move into Pod spot, should throw exception
        
    }
    
    /**
     * Tests <tt>Kiva</tt>'s exception handling for dropping a Pod when it is not carrying a Pod. Main functionality being tested is <tt>Kiva</tt>'s 
     * dropPod() method. A NoPodException should be thrown along with a diagnostic message.
     * 
     * @ Kiva#dropPod();
     */
    public void testDropWhenNoPod() {
        Kiva kiva = new Kiva(defaultMap);
        
        kiva.move(KivaCommand.DROP);
    }
    
    /**
     * Tests <tt>Kiva</tt>'s exception handling for picking up a Pod from a location that does not have a Pod. Main functionality being tested is <tt>Kiva</tt>'s
     * takePod() method. A NoPodException should be thrown along with a diagnostic message.
     */
    public void testTakeFromEmpty() {
        Kiva kiva = new Kiva(defaultMap);
        kiva.move(KivaCommand.TAKE);
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
     * Verifies the if the current instance of <tt>Kiva</tt> has the correct/expected information via a console message.
     * 
     * @param testname          The name of the test, can be anything.
     * @param actual            The <tt>Kiva</tt> instance that is being tested
     * @param expectLocation    The (x, y) coordinates the <tt>Kiva</tt> should be located at
     * @param expectDirection   The orientation the <tt>Kiva</tt> should be facing
     * @param expectCarry       Whether or not the <tt>Kiva</tt> is holding a Pod
     * @param expectDropped     Whether or not the <tt>Kiva</tt> has successfully dropped a Pod at a Drop Zone
     */
    private void verifyKivaState(String testName, Kiva actual, Point expectLocation, FacingDirection expectDirection, boolean expectCarry, boolean expectDropped) {
        Point actualLocation = actual.getCurrentLocation();
        if (sameLocation(actualLocation, expectLocation)) {
            System.out.println(String.format("%s: current location SUCCESS", testName));
        }
        else {
            System.out.println(String.format("%s: current location FAIL!", testName));
            System.out.println(String.format("Expected %s, got %s", expectLocation, actualLocation));
        }
        
        FacingDirection actualDirection = actual.getDirectionFacing();
        if (actualDirection == expectDirection) {
            System.out.println(String.format("%s: facing direction SUCCESS", testName));
        }
        else {
            System.out.println(String.format("%s: facing direction FAIL!",testName));
            System.out.println(String.format("Expected %s, got %s", expectDirection, actualDirection));
        }
        
        
        boolean actualCarry = actual.isCarryingPod();
        if (actualCarry == expectCarry) {
            System.out.println(String.format("%s: carrying pod SUCCESS", testName));
        }
        else {
            System.out.println(String.format("%s: carrying pod FAIL", testName));
            System.out.println(String.format("Expected %s, got %s", expectCarry, actualCarry));
        }
        
        boolean actualDropped = actual.isSuccessfullyDropped();
        if (actualDropped == expectDropped) {
            System.out.println(String.format("%s: successfully dropped SUCCESS", testName));
        }
        else {
            System.out.println(String.format("%s: successfully dropped FAIL", testName));
            System.out.println(String.format("Expected %s, got %s", expectDropped, actualDropped));
        }
    }
}

