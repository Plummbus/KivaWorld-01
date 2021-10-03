
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

    FloorMap defaultMap = new FloorMap(defaultLayout);
    
    //move forward once from UP facing direction, should end facing UP
    public void testForwardFromUp() {
        Kiva kiva = new Kiva(defaultMap);
        
        kiva.move(KivaCommand.FORWARD);
        
        verifyKivaState("testForwardFromUp", kiva, new Point(2, 3), FacingDirection.UP, false, false);
    }
    
    //turn left from UP facing direction, should end up facing LEFT
    public void testTurnLeftFromUp() {
        Kiva kiva = new Kiva(defaultMap);
        
        kiva.move(KivaCommand.TURN_LEFT);
        
        verifyKivaState("testTurnLeftFromUp", kiva, new Point(2, 3), FacingDirection.LEFT, false, false);
    }
    
    //turn left TWICE from UP facing direction, should end up facing DOWN
    public void testTurnLeftFromLeft() {
        Kiva kiva = new Kiva(defaultMap);
        
        kiva.move(KivaCommand.TURN_LEFT);
        kiva.move(KivaCommand.TURN_LEFT);
        
        verifyKivaState("testTurnLeftFromUp", kiva, new Point(2, 3), FacingDirection.DOWN, false, false);
    }
    
    //turn left THRICE from UP facing direction, should end up facing RIGHT
    public void testTurnLeftFromDown() {
        Kiva kiva = new Kiva(defaultMap);
        
        kiva.move(KivaCommand.TURN_LEFT);
        kiva.move(KivaCommand.TURN_LEFT);
        kiva.move(KivaCommand.TURN_LEFT);
        
        verifyKivaState("testTurnLeftFromUp", kiva, new Point(2, 3), FacingDirection.RIGHT, false, false);
    }
    
    //turn left 4 TIMES from UP facing direction, should end up facing UP
    public void testTurnLeftFromRight() {
        Kiva kiva = new Kiva(defaultMap);
        
        kiva.move(KivaCommand.TURN_LEFT);
        kiva.move(KivaCommand.TURN_LEFT);
        kiva.move(KivaCommand.TURN_LEFT);
        kiva.move(KivaCommand.TURN_LEFT);
        
        verifyKivaState("testTurnLeftFromUp", kiva, new Point(2, 3), FacingDirection.UP, false, false);
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

