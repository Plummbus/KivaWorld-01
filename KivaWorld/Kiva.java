
import edu.duke.Point;

/**
 * <tt>Kiva</tt> represents an industrial-sized roomba that travels orthoganally on AR floors and interacts with Pods that contain product for picking/stowing.
 * The Kiva class holds information about its current position, the direction it is facing, the environemt it itself is standing on,
 * the lifespan of its motor, if it currently is holding a pod and whether or not it has dropped off that pod at the correct location.<br><br>
 * 
 * The <tt>Kiva</tt> can interpret commands from the user one command at a time, and thus, can only validate commands as it recieves them. Changing
 * the orientation of the <tt>Kiva</tt> does not require error-checking. All orientation changes occur within the current location the <tt>Kiva</tt> resides in.
 * Taking actions that require error-checking are: <b>1)</b> moving to a new location on the map,
 * <b>2)</b> taking a pod, and <b>3)</b> dropping a pod.<br><br>
 * 
 * The <tt>Kiva</tt> can only move forward, meaning it must change its orientation to face the direction the user wants it to travel. When it attempts to move
 * it first has to check if that movement is to an "empty" location. For the sake of the <tt>Kiva</tt> locations marked as having a POD or a DROP_ZONE are
 * considered to be empty. If it attempts to move into a space containing an OBSTACLE or that is outside the the dimensions of the map, an exception will be thrown.
 * Holding a Pod while moving into a POD location similarly throws an exception. If a move action is successful, it affects the lifetime of the motor.<br><br>
 * 
 * Taking and dropping Pods require checking the <tt>FloorMapObject</tt> at the <tt>Point</tt> the <tt>Kiva</tt> is currently at. <tt>Kiva</tt>s cannot TAKE Pods
 * from non-POD locations. Following this logic, performing a DROP action at non-DROP_ZONE locations or when it is not holding a Pod is also illegal. These
 * conditions throw exceptions. If these actions are successful, they do not affect the lifetime of the motor.<br><br>
 * 
 * 
 * @author Franz Jacob Hernandez (herfrn@amazon.com) 
 * @version 0.10
 * @since 10-03-2021
 */
public class Kiva {

    private Point currentLocation;              //The current coordinates of the Kiva.
    private FacingDirection directionFacing;    //The facing direction of the Kiva.    
    private FloorMap map;                       //The map the Kiva will be traversing.
    private boolean carryingPod;                //State for whether or not the Kiva is holding a Pod.
    private boolean successfulyDropped;         //State for whether or not Kiva has dropped the Pod in a Drop Zone.
    private long motorLifetime;                 //The lifetime of the Kiva motor. 
                                                //20,000 hours = 72b milliseconds <- this will be the limit, though the project doesn't
                                                //specify to error check for hitting this limit

    /**
     * Class Constructor.
     * Initializes class fields.
     * 
     * @param map   Takes a FloorMap object.
     *              This is needed to assign values to other class fields.
     */
    public Kiva(FloorMap map) {
        this.currentLocation = map.getInitialKivaLocation();
        this.directionFacing = FacingDirection.UP;
        this.carryingPod = false;
        this.successfulyDropped = false;
        this.map = map;
        this.motorLifetime = 0L;
    }
    
    /**
     * Overloaded Class Constructor.
     * Initializes class fields by calling the other class constructor with the map parameter.
     * All things are the same as the other constructor, but the user can specify where on the map the Kiva starts.
     * 
     * @param map               Takes a FloorMap object.
     *                          This is needed to assign values to other class fields.
     * @param currentLocation   Custom initial starting location.
     */
    public Kiva(FloorMap map, Point currentLocation) {
        this(map);
        this.currentLocation = currentLocation;
    }
    
    /**
     * Makes the Kiva perform actions based on the given command.
     * Acts as a traffic controller for the user's commands. Calls different helper functions based on the enum that is passed as a parameter.
     * If all checks pass and the move is successful, the field motorLifetime is incremented.
     * 
     * @see #moveForward
     * @see #moveLeft
     * @see #moveRight
     * @see #takePod
     * @see #dropPod
     * @see #incrementMotorLifetime
     * 
     * @param command   a KivaCommand enum.
     *                  Must be the enum itself, not the char value associated with it.
     */
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
    
    /**
     * Validates whether or not the next move the Kiva is attempting to make is a legal one.
     * If it is legal, the Kiva performs the move.
     * If it is illegal, code execution stops and throws an IllegalMoveException.
     * This is a helper function to the moveForward() method.
     * 
     * @see #moveForward
     * @param x     the x-coordinate of the Point Kiva is attempting to move to
     * @param y     the y-coordinate of the Point Kiva is attempting to move to
     * 
     */
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
    
    /**
     * The Kiva attempts to go to a new location based off the current value of directionFacing.
     * LEFT and RIGHT values move the Kiva along the x-axis, while UP and DOWN values move it along the y-axis.
     * validMove() is called within this method to determine if the new location the Kiva is attempting to move to is legal.
     * This is a helper function of the move() method.
     * @see #validMove
     * @see #move
     */
    public void moveForward() {
        int x = currentLocation.getX();
        int y = currentLocation.getY();
        
        switch (this.getDirectionFacing()) {    //I could implement getDelta() from FacingDirection.java here,
                                                //but I think using the getter method for the switch comparison this way makes the cases more readable/user-friendly
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
    
    /**
     * Kiva TURNS LEFT to a new direction based off the current value of class field directionFacing.
     * This does NOT move the Kiva to a new location, only changes the orientation that it is facing within the same location.
     * This is a helper function of the move() method
     * @see #move
     */
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
    
    /**
     * Kiva TURNS RIGHT to a new direction based off the current value of class field directionFacing.
     * This does NOT move the Kiva to a new location, only changes the orientation that it is facing within the same location.
     * This is a helper function of the move() method
     * @see #move
     */
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

    /**
     * Kiva attempts to TAKE a Pod at the coordinates of currentLocation.
     * If successful, the field carryingPod becomes true and code execution continues.
     * Otherwise, a NoPodException is thrown.
     */
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
    
    /**
     * Kiva attempts to DROP a Pod at the coordinates of currentLocation.
     * If successful, the field successfullyDropped becomes true and code execution continues.
     * Otherwise, a NoPodException exception or an IllegalDropZone exception is thrown.
     */
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
    
    /**
     * increases the field motorLifetime by 1000 each time it is called.
     */
    public void incrementMotorLifetime() {
        this.motorLifetime += 1000;
    }
    
    /*
     * GETTERS FOR CLASS FIELDS
     */
    
    /**
     * A state for whether or not the Kiva instance has successfully called the takePod() method on a Pod location.
     * @see #takePod
     * @return boolean value for class field carryingPod
     */
    public boolean isCarryingPod() {
        return this.carryingPod;
    }
    
    /**
     * A state for whether or not the Kiva instance has successfully called the dropPod() method on a Drop location.
     * @see #dropPod
     * @return boolean value for class field successfullyDropped
     */
    public boolean isSuccessfullyDropped() {
        return this.successfulyDropped;
    }
    
    /**
     * The current location of the Kiva, represented by a Point object in (x, y) coordinates.
     * @see <a href="https://www.dukelearntoprogram.com/course2/doc/javadoc/edu/duke/Point.html">edu.duke.Point</a>
     * @return a Point object
     */
    public Point getCurrentLocation() {
        return this.currentLocation;
    }
    
    /**
     * The current direction (enum) that the Kiva is facing: UP, DOWN, LEFT, or RIGHT.
     * @return a direction facing (enum)
     */
    public FacingDirection getDirectionFacing() {
        return this.directionFacing;
    }
    
    /**
     * The map object that the Kiva will be traversing.
     * Also contains information about the Pod, DropZone, and Obstacles.
     * @return a FloorMap object
     */
    public FloorMap getMap() {
        return this.map;
    }
    
    /**
     * The lifetime of the Kiva motor, represented by a (long) value in milliseconds.
     * The motor is rated to last 20,000 hours. which is equivalent to 72 billion milliseconds.
     * @return a long value in milliseconds
     */
    public long getMotorLifetime() {
        return this.motorLifetime;
    }
}
