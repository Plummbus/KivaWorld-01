
import edu.duke.Point;

/**
 * Write a description of Kiva here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Kiva {

    private Point currentLocation;
    private FacingDirection directionFacing;
    private FloorMap map;
    private boolean carryingPod;
    private boolean successfulyDropped;
    //private motorLifetime;

    public Kiva(FloorMap map) {
        this.currentLocation = new Point(2, 4); //default initial location
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
            case 'R':
                moveRight();
                break;
            case 'T':
                takePod();
                break;
            case 'D':
                dropPod();
                break;
            default:
                System.out.println("default case for move() was hit!");
                break;
            }
    }
    
    public void validMove(int x, int y) {
        Point target = new Point(x, y);
        FloorMapObject floorObject = this.map.getObjectAtLocation(target);
        String message;
        //FloorMap has a InvalidFloorMapLocationException that will get called before this
        //this is here just in case
        if (x < this.map.getMinColNum() || x > this.map.getMaxColNum() || y < this.map.getMinRowNum() || y > this.map.getMaxRowNum()) {
            message = String.format("Illegal move to out-of-bounds location. Tried to go to Point(%d, %d)" + 
                                            ". Last valid location was Point(%d, %d)", x, y, this.getCurrentLocation().getX(), this.getCurrentLocation().getY());
            throw new IllegalMoveException(message);
        }
        if (floorObject == FloorMapObject.OBSTACLE) {
            System.out.println(floorObject);
            message = String.format("Illegal move to location with an obstacle. Tried to go to Point(%d, %d)" +
                                            ". Last valid location was Point(%d, %d)", x, y, this.getCurrentLocation().getX(), this.getCurrentLocation().getY());
            throw new IllegalMoveException(message);
        }
        if (floorObject == FloorMapObject.POD && this.isCarryingPod() == true) {
            message = String.format("Illegal move to location with a pod, currently carrying a pod. Tried to go to Point(%d, %d)" +
                                            ". Last valid location was Point(%d, %d)", x, y, this.getCurrentLocation().getX(), this.getCurrentLocation().getY());
            throw new IllegalMoveException(message);
        }
        
    }
    
    //since top-left position is Point(0, 0), moving down the map is y++ and moving to the right side is x++
    public void moveForward() {
        int x = currentLocation.getX();
        int y = currentLocation.getY();
        
        switch (this.getDirectionFacing()) {
            case UP:
                y = y - 1;
                validMove(x, y);
                this.currentLocation = new Point(x, y);
                break;
            case LEFT:
                x = x - 1;
                validMove(x, y);
                this.currentLocation = new Point(x, y);
                break;
            case DOWN:
                y = y + 1;
                validMove(x, y);
                this.currentLocation = new Point(x, y);
                break;
            case RIGHT:
                x = x + 1;
                validMove(x, y);
                this.currentLocation = new Point(x, y);
                break;
            default:
                System.out.println("default case for moveForward() was hit!");
                break;
        }
        System.out.println(String.format("Moving to Point(%d, %d)", this.currentLocation.getX(), this.currentLocation.getY()));
        System.out.print(this.map.getObjectAtLocation(new Point(this.currentLocation.getX(), this.currentLocation.getY())));
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
            default:
                System.out.println("default case for moveLeft() was hit!");
                break;
            
        }
        System.out.println("going left!");
    }
    
    //same logic as moveLeft(), just a different direction facing
    public void moveRight() {
        switch (this.getDirectionFacing()) {
            case UP:
                this.directionFacing = FacingDirection.RIGHT;
                break;
            case RIGHT:
                this.directionFacing = FacingDirection.DOWN;
                break;
            case DOWN:
                this.directionFacing = FacingDirection.LEFT;
                break;
            case LEFT:
                this.directionFacing = FacingDirection.UP;
                break;
            default:
                System.out.println("default case for moveRight() was hit!");
                break;
            
        }
        System.out.println("going right!");
    }
    
    //if in a POD, pick up, else print message
    //don't have to check if carryPod == true because an IllegalMoveException will happen if
    //user tries to enter space that contains POD while holding POD
    public void takePod() {
        if (this.getMap().getObjectAtLocation(this.getCurrentLocation()) == FloorMapObject.POD) {
            this.carryingPod = true;
        }
        else {
            System.out.println("No pod at this location!");
        }
    }
    
    public void dropPod() {
        if (isCarryingPod()) {
            this.carryingPod = false;
            this.successfulyDropped = true;
        }
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
    
    public FloorMap getMap() {
        return this.map;
    }
    
    /*
     * 
     * These methods are for testing purposes in KivaConstructor.java and KivaMoveTest.java
     */
    public void setCurrentLocation(Point point) {
        this.currentLocation = point;
    }
    
    public void setCarryingPod(boolean value) {
        this.carryingPod = value;
    }
}
