
import edu.duke.Point;

/**
 * Write a description of KivaMoveTest here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class KivaMoveTest {
    
    String defaultLayout = ""
                            + "-------------\n"
                            + "        P   *\n"
                            + "   **       *\n"
                            + "   **       *\n"
                            + "  K       D *\n"
                            + " * * * * * **\n"
                            + "-------------\n";

    //default initial location is Point(2, 3)
    //left-most position is Point(0, 0), for reference
    FloorMap defaultMap = new FloorMap(defaultLayout);
    
    //move forward once from UP facing direction, should end facing UP and at point(2, 2)
    public void testForwardFromUp() {
        Kiva kiva = new Kiva(defaultMap);
        
        kiva.move(KivaCommand.FORWARD);
        
        verifyKivaState("testForwardFromUp", kiva, new Point(2, 2), FacingDirection.UP, false, false);
    }
    
    //turn left from UP facing direction, should end up facing LEFT and be at initial position
    public void testTurnLeftFromUp() {
        Kiva kiva = new Kiva(defaultMap);
        
        kiva.move(KivaCommand.TURN_LEFT);
        
        verifyKivaState("testTurnLeftFromUp", kiva, new Point(2, 3), FacingDirection.LEFT, false, false);
    }
    
    //turn left TWICE from UP facing direction, should end up facing DOWN and be at initial position
    public void testTurnLeftFromLeft() {
        Kiva kiva = new Kiva(defaultMap);
        
        kiva.move(KivaCommand.TURN_LEFT);
        kiva.move(KivaCommand.TURN_LEFT);
        
        verifyKivaState("testTurnLeftFromUp", kiva, new Point(2, 3), FacingDirection.DOWN, false, false);
    }
    
    //turn left THRICE from UP facing direction, should end up facing RIGHT and be at initial position
    public void testTurnLeftFromDown() {
        Kiva kiva = new Kiva(defaultMap);
        
        kiva.move(KivaCommand.TURN_LEFT);
        kiva.move(KivaCommand.TURN_LEFT);
        kiva.move(KivaCommand.TURN_LEFT);
        
        verifyKivaState("testTurnLeftFromUp", kiva, new Point(2, 3), FacingDirection.RIGHT, false, false);
    }
    
    //turn left 4 TIMES from UP facing direction, should end up facing UP and be at initial position
    public void testTurnLeftFromRight() {
        Kiva kiva = new Kiva(defaultMap);
        
        kiva.move(KivaCommand.TURN_LEFT);
        kiva.move(KivaCommand.TURN_LEFT);
        kiva.move(KivaCommand.TURN_LEFT);
        kiva.move(KivaCommand.TURN_LEFT);
        
        verifyKivaState("testTurnLeftFromUp", kiva, new Point(2, 3), FacingDirection.UP, false, false);
    }
    
    //turn left ONCE and then move forward ONCE, should be facing LEFT and be at Point(1, 3);
    public void testForwardWhileFacingLeft() {
        Kiva kiva  = new Kiva(defaultMap);
        
        kiva.move(KivaCommand.TURN_LEFT);
        kiva.move(KivaCommand.FORWARD);
        
        verifyKivaState("testForwadWhileFacingLeft", kiva, new Point(1, 3), FacingDirection.LEFT, false, false);
    }
    
    //turn left TWICE and the move forward ONCE, should be facing DOWN and be at Point(2, 4)
    public void testForwardWhileFacingDown() {
        Kiva kiva = new Kiva(defaultMap);
        
        kiva.move(KivaCommand.TURN_LEFT);
        kiva.move(KivaCommand.TURN_LEFT);
        kiva.move(KivaCommand.FORWARD);
        
        verifyKivaState("testForwardWhileFacingDown", kiva, new Point(2, 4), FacingDirection.DOWN, false, false);
    }
    
    //turn left THRICE and the move forward ONCE, should be facing RIGHT and be at Point(3, 3)
    public void testForwardWhileFacingRight() {
        Kiva kiva = new Kiva(defaultMap);
        
        kiva.move(KivaCommand.TURN_LEFT);
        kiva.move(KivaCommand.TURN_LEFT);
        kiva.move(KivaCommand.TURN_LEFT);
        kiva.move(KivaCommand.FORWARD);
        
        verifyKivaState("testForwardWhileFacingRight", kiva, new Point(3, 3), FacingDirection.RIGHT, false, false);
    }
    
    //turn right ONCE, should be facing RIGHT and at initial position
    public void testTurnRightFromUp() {
        Kiva kiva = new Kiva(defaultMap);
        
        kiva.move(KivaCommand.TURN_RIGHT);
        
        verifyKivaState("testTurnRightFromUp", kiva, new Point(2, 3), FacingDirection.RIGHT, false, false);
    }
    
    //turn left ONCE followed by right ONCE, should be facing UP and at initial position
    public void testTurnRightFromLeft() {
        Kiva kiva = new Kiva(defaultMap);
        
        kiva.move(KivaCommand.TURN_LEFT);
        kiva.move(KivaCommand.TURN_RIGHT);
        
        verifyKivaState("testTurnRightFromUp", kiva, new Point(2, 3), FacingDirection.UP, false, false);
    }
    
    //turn left TWICE followed by right ONCE, should be facing LEFT and at initial position
    public void testTurnRightFromDown() {
        Kiva kiva = new Kiva(defaultMap);
        
        kiva.move(KivaCommand.TURN_LEFT);
        kiva.move(KivaCommand.TURN_LEFT);
        kiva.move(KivaCommand.TURN_RIGHT);
        
        verifyKivaState("testTurnRightFromUp", kiva, new Point(2, 3), FacingDirection.LEFT, false, false);
    }
    
    //turn left THRICE followed by right ONCE, should be facing DOWN and at initial position
    public void testTurnRightFromRight() {
        Kiva kiva = new Kiva(defaultMap);
        
        kiva.move(KivaCommand.TURN_LEFT);
        kiva.move(KivaCommand.TURN_LEFT);
        kiva.move(KivaCommand.TURN_LEFT);
        kiva.move(KivaCommand.TURN_RIGHT);
        
        verifyKivaState("testTurnRightFromUp", kiva, new Point(2, 3), FacingDirection.DOWN, false, false);
    }
    
    //move forward 3 TIMES, turn right ONCE, move forward 6 TIMES, and PICK UP POD, should be facing RIGHT at Point(8, 0)
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
        
        verifyKivaState("testTakeOnPod", kiva, new Point(8, 0), FacingDirection.RIGHT, true, false);
    }
    
    //move forward 3 TIMES, turn right ONCE, move forward 6 TIMES, PICK UP POD, move to drop zone -> Point(10, 3), then DROP POD.
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
        kiva.move(KivaCommand.TAKE);    //Point(8, 0), PICK UP POD
        
        //to drop zone
        kiva.move(KivaCommand.FORWARD);
        kiva.move(KivaCommand.FORWARD); //Point(10, 0)
        kiva.move(KivaCommand.TURN_RIGHT);
        kiva.move(KivaCommand.FORWARD);
        kiva.move(KivaCommand.FORWARD);
        kiva.move(KivaCommand.FORWARD); //Point(10,3)
        kiva.move(KivaCommand.DROP);    //DROP POD
        
        verifyKivaState("testDropOnDropZone", kiva, new Point(10, 3), FacingDirection.DOWN, false, true);
    }
    
    //start at Point(2, 3) and move up 4 times until OoB, at which time an exception is thrown
    public void testOutOfBoundsMove() {
        Kiva kiva = new Kiva(defaultMap);
        
        kiva.move(KivaCommand.FORWARD);
        kiva.move(KivaCommand.FORWARD);
        kiva.move(KivaCommand.FORWARD);
        kiva.move(KivaCommand.FORWARD);
    }
    
    //start at Point(2, 3) and attempt to move to Point(3, 2) to collide with obstacle, at which time an exception is thrown
    public void testHitObstacle() {
        Kiva kiva = new Kiva(defaultMap);
        
        kiva.move(KivaCommand.FORWARD);
        kiva.move(KivaCommand.TURN_RIGHT);
        kiva.move(KivaCommand.FORWARD);
    }
    
    //kiva object starts with carryingPod = true
    //start at Point(2, 3) and attempt to move to Point(8, 0) to collide with pod, at which time an exception is thrown
    public void testHoldPodMoveToPod() {
        Kiva kiva = new Kiva(defaultMap);
        
        //kiva.move(KivaCommand.TURN_LEFT);
        kiva.move(KivaCommand.FORWARD);
        kiva.move(KivaCommand.FORWARD);
        kiva.move(KivaCommand.FORWARD);
        kiva.move(KivaCommand.FORWARD);
        /*
        kiva.move(KivaCommand.FORWARD);
        kiva.move(KivaCommand.FORWARD);
        kiva.move(KivaCommand.FORWARD);
        kiva.move(KivaCommand.FORWARD); //Point(2, 0)
        kiva.move(KivaCommand.TURN_RIGHT);
        kiva.move(KivaCommand.FORWARD);
        kiva.move(KivaCommand.FORWARD);
        kiva.move(KivaCommand.FORWARD);
        kiva.move(KivaCommand.FORWARD);
        kiva.move(KivaCommand.FORWARD);
        kiva.move(KivaCommand.FORWARD); //Point(8, 0)
        */
    }
    
    private boolean sameLocation(Point a, Point b) {
        return a.getX() == b.getX() && a.getY() == b.getY();
    }
    
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

