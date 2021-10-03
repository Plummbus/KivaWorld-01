
import edu.duke.Point;

/**
 * Write a description of Kiva here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Kiva {

    public Point currentLocation;
    public FacingDirection directionFacing;
    public FloorMap map;
    public boolean carryingPod;
    public boolean successfulyDropped;
    //private motorLifetime;

    public Kiva(FloorMap map) {
        this.currentLocation = new Point(2, 3); //default initial location
        this.directionFacing = FacingDirection.UP;
        this.carryingPod = false;
        this.successfulyDropped = false;
        this.map = map;
    }
    
    public Kiva(FloorMap map, Point currentLocation) {
        this.directionFacing = FacingDirection.UP;
        this.carryingPod = false;
        this.successfulyDropped = false;
        this.map = map;
        this.currentLocation = currentLocation;
    }
    
    
    public void move(KivaCommand command) {
        switch (command.getDirectionKey()) {
            case 'F':
                moveForward();
                break;
            case 'L':
                moveLeft();
                break;
            default:
                break;
            }
    }
    
    public void moveForward() {
        System.out.println("we got an F!");
    }
    
    //need a switch statement because TURN_LEFT will change the Kiva's facing direction, ending direction dependant on starting direction
    public void moveLeft() {
        switch (this.getDirectionFacing()) {
            case UP:
                this.directionFacing = FacingDirection.LEFT;
                break;
            case LEFT:
                this.directionFacing = FacingDirection.DOWN;
                break;
            case DOWN:
                this.directionFacing = FacingDirection.RIGHT;
                break;
            case RIGHT:
                this.directionFacing = FacingDirection.UP;
                break;
            
        }
        System.out.println("going left!");
    }
    
    
    public boolean isCarryingPod() {
        return this.carryingPod;
    }
    
    public boolean isSuccessfullyDropped() {
        return this.successfulyDropped;
    }
    
    public Point getCurrentLocation() {
        return this.currentLocation;
    }
    
    public FacingDirection getDirectionFacing() {
        return this.directionFacing;
    }
    
}
