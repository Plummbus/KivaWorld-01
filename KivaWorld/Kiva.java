
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
    private long motorLifetime; //20,000 hours = 72b milliseconds <- this will be our limit,

    public Kiva(FloorMap map) {
        this.currentLocation = map.getInitialKivaLocation();
        this.directionFacing = FacingDirection.UP;
        this.carryingPod = false;
        this.successfulyDropped = false;
        this.map = map;
        this.motorLifetime = 0L;
    }
    
    public Kiva(FloorMap map, Point currentLocation) {
        this(map);
        this.currentLocation = currentLocation;
    }
    
    
    public void move(KivaCommand command) {
        switch (command.getDirectionKey()) {
            case 'F':
                moveForward();
                incrementMotorLifetime();
                break;
            case 'L':
                moveLeft();
                incrementMotorLifetime();
                break;
            case 'R':
                moveRight();
                incrementMotorLifetime();
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
            message = String.format("Illegal move to location with an OBSTACLE. Tried to go to Point(%d, %d)" +
                                            ". Last valid location was Point(%d, %d)", x, y, this.getCurrentLocation().getX(), this.getCurrentLocation().getY());
            throw new IllegalMoveException(message);
        }
        if (floorObject == FloorMapObject.POD && this.isCarryingPod() == true) {
            message = String.format("Illegal move to location with a POD because you are currently carrying a POD. Tried to go to Point(%d, %d)" +
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
    }
    
    //need a switch statement because TURN_LEFT will change the Kiva's facing direction, ending direction dependant on starting direction
    public void moveLeft() {
        switch (getDirectionFacing()) {
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
    }
    
    //same logic as moveLeft(), just a different direction facing
    public void moveRight() {
        switch (getDirectionFacing()) {
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
    }
    
    //if in a POD, pick up, else print message
    //don't have to check if carryPod == true because an IllegalMoveException will happen if
    //user tries to enter space that contains POD while holding POD,
    //I put it in just in case
    public void takePod() {
        Point target = this.getCurrentLocation();
        FloorMapObject mapObject = this.getMap().getObjectAtLocation(target);
        if (mapObject == FloorMapObject.POD && this.isCarryingPod() == false) {
            this.carryingPod = true;
        }
        else {
            String objectName = mapObject.name(); 
            String message = String.format("Current location Point(%d, %d) does not contain a POD, cannot TAKE from: %s", target.getX(), target.getY(), objectName);
            throw new NoPodException(message);
        }
    }
    
    public void dropPod() {
        Point target = this.getCurrentLocation();
        FloorMapObject mapObject = this.getMap().getObjectAtLocation(target);
        if (this.isCarryingPod() == true) {
            if (mapObject == FloorMapObject.DROP_ZONE) {
                this.carryingPod = false;
                this.successfulyDropped = true;
            } else {
            String objectName = mapObject.name();
            String message = String.format("Current location Point(%d, %d) is not a DROP_ZONE, cannot DROP pod in: %s", target.getX(), target.getY(), objectName);
            throw new IllegalDropZoneException(message);
            }
        } else {
            String boolValue = String.valueOf(this.isCarryingPod());
            String message = String.format("DROP at Point(%d, %d) failed! isCarryingPod is currently: %s. Cannot drop what you do not have!", target.getX(), target.getY(), boolValue);
            throw new NoPodException(message);
        }
    }
    
    public void incrementMotorLifetime() {
        this.motorLifetime += 1000;
    }
    
    /*
     * GETTERS FOR CLASS FIELDS
     */
    
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
    
    public long getMotorLifetime() {
        return this.motorLifetime;
    }
    
    /*
     * 
     * These methods (setters) are for TESTING PURPOSES ONLY in KivaConstructor.java and KivaMoveTest.java
     */
    public void setCurrentLocation(Point point) {
        this.currentLocation = point;
    }
    
    public void setCarryingPod(boolean value) {
        this.carryingPod = value;
    }
}
