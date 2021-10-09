
import edu.duke.Point;

/**
 * Write a description of KivaMoveTest here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
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
    
    //testing to see what the coordinates are
    public void showMapDimensions() {
        Kiva kiva = new Kiva(defaultMap);
        int minY = kiva.getMap().getMinRowNum();
        int minX = kiva.getMap().getMinColNum();
        int maxY = kiva.getMap().getMaxRowNum();
        int maxX = kiva.getMap().getMaxColNum();
        
        String message = String.format("Map Dimensions:\nmin row: %d max row: %d \nmin col: %d max col: %d", minY, maxY, minX, maxX);
        System.out.print(message);
    }
    
    public void testDeltas() {
        Kiva kiva = new Kiva(defaultMap);
        System.out.println(kiva.getDirectionFacing().getDelta());   //UP
        
        kiva.move(KivaCommand.TURN_LEFT);
        System.out.println(kiva.getDirectionFacing().getDelta());
        
        kiva.move(KivaCommand.TURN_LEFT);
        System.out.println(kiva.getDirectionFacing().getDelta());
        
        kiva.move(KivaCommand.TURN_LEFT);
        System.out.println(kiva.getDirectionFacing().getDelta());
    }
    
    //move forward once from UP facing direction, should end facing UP and at point(2, 3)
    public void testForwardFromUp() {
        Kiva kiva = new Kiva(defaultMap);
        kiva.move(KivaCommand.FORWARD);
        
        verifyKivaState("testForwardFromUp", kiva, new Point(2, 3), FacingDirection.UP, false, false);
    }
    
    //turn left from UP facing direction, should end up facing LEFT and be at initial position
    public void testTurnLeftFromUp() {
        Kiva kiva = new Kiva(defaultMap);
        
        kiva.move(KivaCommand.TURN_LEFT);
        
        verifyKivaState("testTurnLeftFromUp", kiva, new Point(2, 4), FacingDirection.LEFT, false, false);
    }
    
    //turn left TWICE from UP facing direction, should end up facing DOWN and be at initial position
    public void testTurnLeftFromLeft() {
        Kiva kiva = new Kiva(defaultMap);
        
        kiva.move(KivaCommand.TURN_LEFT);
        kiva.move(KivaCommand.TURN_LEFT);
        
        verifyKivaState("testTurnLeftFromUp", kiva, new Point(2, 4), FacingDirection.DOWN, false, false);
    }
    
    //turn left THRICE from UP facing direction, should end up facing RIGHT and be at initial position
    public void testTurnLeftFromDown() {
        Kiva kiva = new Kiva(defaultMap);
        
        kiva.move(KivaCommand.TURN_LEFT);
        kiva.move(KivaCommand.TURN_LEFT);
        kiva.move(KivaCommand.TURN_LEFT);
        
        verifyKivaState("testTurnLeftFromUp", kiva, new Point(2, 4), FacingDirection.RIGHT, false, false);
    }
    
    //turn left 4 TIMES from UP facing direction, should end up facing UP and be at initial position
    public void testTurnLeftFromRight() {
        Kiva kiva = new Kiva(defaultMap);
        
        kiva.move(KivaCommand.TURN_LEFT);
        kiva.move(KivaCommand.TURN_LEFT);
        kiva.move(KivaCommand.TURN_LEFT);
        kiva.move(KivaCommand.TURN_LEFT);
        
        verifyKivaState("testTurnLeftFromUp", kiva, new Point(2, 4), FacingDirection.UP, false, false);
    }
    
    //turn left ONCE and then move forward ONCE, should be facing LEFT and be at Point(1, 3);
    public void testForwardWhileFacingLeft() {
        Kiva kiva  = new Kiva(defaultMap);
        
        kiva.move(KivaCommand.TURN_LEFT);
        kiva.move(KivaCommand.FORWARD);
        
        verifyKivaState("testForwadWhileFacingLeft", kiva, new Point(1, 4), FacingDirection.LEFT, false, false);
    }
    
    //turn left TWICE and the move forward ONCE, should be facing DOWN and be at Point(2, 4)
    public void testForwardWhileFacingDown() {
        Kiva kiva = new Kiva(defaultMap);
        
        kiva.move(KivaCommand.TURN_LEFT);
        kiva.move(KivaCommand.TURN_LEFT);
        kiva.move(KivaCommand.FORWARD);
        
        verifyKivaState("testForwardWhileFacingDown", kiva, new Point(2, 5), FacingDirection.DOWN, false, false);
    }
    
    //turn left THRICE and the move forward ONCE, should be facing RIGHT and be at Point(3, 3)
    public void testForwardWhileFacingRight() {
        Kiva kiva = new Kiva(defaultMap);
        
        kiva.move(KivaCommand.TURN_LEFT);
        kiva.move(KivaCommand.TURN_LEFT);
        kiva.move(KivaCommand.TURN_LEFT);
        kiva.move(KivaCommand.FORWARD);
        
        verifyKivaState("testForwardWhileFacingRight", kiva, new Point(3, 4), FacingDirection.RIGHT, false, false);
    }
    
    //turn right ONCE, should be facing RIGHT and at initial position
    public void testTurnRightFromUp() {
        Kiva kiva = new Kiva(defaultMap);
        
        kiva.move(KivaCommand.TURN_RIGHT);
        
        verifyKivaState("testTurnRightFromUp", kiva, new Point(2, 4), FacingDirection.RIGHT, false, false);
    }
    
    //turn left ONCE followed by right ONCE, should be facing UP and at initial position
    public void testTurnRightFromLeft() {
        Kiva kiva = new Kiva(defaultMap);
        
        kiva.move(KivaCommand.TURN_LEFT);
        kiva.move(KivaCommand.TURN_RIGHT);
        
        verifyKivaState("testTurnRightFromUp", kiva, new Point(2, 4), FacingDirection.UP, false, false);
    }
    
    //turn left TWICE followed by right ONCE, should be facing LEFT and at initial position
    public void testTurnRightFromDown() {
        Kiva kiva = new Kiva(defaultMap);
        
        kiva.move(KivaCommand.TURN_LEFT);
        kiva.move(KivaCommand.TURN_LEFT);
        kiva.move(KivaCommand.TURN_RIGHT);
        
        verifyKivaState("testTurnRightFromUp", kiva, new Point(2, 4), FacingDirection.LEFT, false, false);
    }
    
    //turn left THRICE followed by right ONCE, should be facing DOWN and at initial position
    public void testTurnRightFromRight() {
        Kiva kiva = new Kiva(defaultMap);
        
        kiva.move(KivaCommand.TURN_LEFT);
        kiva.move(KivaCommand.TURN_LEFT);
        kiva.move(KivaCommand.TURN_LEFT);
        kiva.move(KivaCommand.TURN_RIGHT);
        
        verifyKivaState("testTurnRightFromUp", kiva, new Point(2, 4), FacingDirection.DOWN, false, false);
    }
    
    //move forward 3 TIMES, turn right ONCE, move forward 6 TIMES, and PICK UP POD, should be facing RIGHT at Point(8, 1)
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
    
    //move forward 3 TIMES, turn right ONCE, move forward 6 TIMES, PICK UP POD, move to drop zone -> Point(10, 4), then DROP POD.
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
    
    //similar code to method above, but now dropping pod on an EMPTY location, should throw IllegalDropZoneException
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
    
    //this test uses the openMap layout, so we should be able to attempt to go left to a -1 x position without hitting an OBSTACLE
    //start at Point(2, 4), turn left ONCE and then move forward 3 TIMES, should throw an IllegalMoveException
    //FloorMap has its own exception that will throw before this, though
    public void testOutOfBoundsMove() {
        Kiva kiva = new Kiva(openMap);
        
        kiva.move(KivaCommand.TURN_LEFT);
        kiva.move(KivaCommand.FORWARD);
        kiva.move(KivaCommand.FORWARD);
        kiva.move(KivaCommand.FORWARD);
    }
    
    //start at Point(2, 4) and attempt to move to Point(3, 3) to collide with obstacle, should throw an IllegalMoveException
    public void testHitObstacle() {
        Kiva kiva = new Kiva(defaultMap);
        
        kiva.move(KivaCommand.FORWARD);
        kiva.move(KivaCommand.TURN_RIGHT);
        kiva.move(KivaCommand.FORWARD);
    }
    
    //kiva object starts with carryingPod = true
    //start at Point(2, 4) and attempt to move to Point(8, 1) to collide with pod, should throw an IllegalMoveException
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
    
    //kiva object attempts to drop a pod when carryingPod = false, should throw a NoPodException
    public void testDropWhenNoPod() {
        Kiva kiva = new Kiva(defaultMap);
        
        kiva.move(KivaCommand.DROP);
    }
    
    //start at Point(2, 4), attempt to pick up POD while on an EMPTY location
    //should print to console a message
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

