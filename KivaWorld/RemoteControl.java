import edu.duke.FileResource;
import java.util.Arrays;
import java.util.Scanner;

/**
 * The <tt>RemoteControl</tt> contains the <i>run()</i> method, the method we call to run the program. The program prompts the user to select a <tt>.txt</tt> file,
 * then using <tt>FileResource</tt>, another window will open up to display the files the user can choose from. Upon selecting a valid file, the program
 * prints diagnostic information to the console. This includes data about the <tt>Kiva</tt>'s initial location, the status of its boolean fields, and where
 * the Pod and Drop Zones are. The user will be prompted to enter in a series of commands. These commands are taken in as a single string and are then converted
 * into an array of enums that the <tt>Kiva</tt> can interpret. A success, failure, or error message is displayed after processing the commands.
 * 
 * @author Franz Jacob Hernandez (herfrn@amazon.com) 
 * @version 0.04
 * @since 10-03-2021
 */
public class RemoteControl {
    
    /**
     * Accesses user input data from the console.
     * This is a class included with the Duke library that gathers data typed by the keyboard via the console, similar to <tt>Scanner</tt>.
     */
    private KeyboardResource keyboardResource;

    /**
     * Builds a new RemoteControl.
     * The constructor is only called for use in test classes, since the program runs through the run() method call within this class.
     * @see #run
     */
    public RemoteControl() {
        keyboardResource = new KeyboardResource();  //easier way to access user input without using Scanner, provided by our friends at Duke
    }

    /**
     * Executes the <tt>Kiva</tt> program.
     * Prompts the user for the floor map to load, displays the map, and asks the user for commands the <tt>Kiva</tt> can execute.
     * Calls helper functions to display diagnostic information and to display specific statements at the end of the commands.
     * Has <tt>Kiva</tt> traverse through an array of <tt>KivaCommand</tt> in order to perform moves/actions.
     * 
     * @see #convertToKivaCommands
     * @see #displayDiagnostics
     * @see #outputPrintStatement
     *
     */
    public void run() {
        System.out.println("Please select a map file.");
        System.out.println("Legal inputs are: \"sample_floor_map1.txt\", \"sample_floor_map2.txt\", and \"sample_floor_map3.txt\"");
        FileResource fileResource = new FileResource();
        
        String inputMap = fileResource.asString();
        FloorMap floorMap = new FloorMap(inputMap);
        Kiva kiva = new Kiva(floorMap);
        System.out.println(floorMap);
        
        displayDiagnostics(kiva, floorMap);

        System.out.println("Please enter the directions for the Kiva Robot to take.");
        String directions = keyboardResource.getLine();
        System.out.println("Directions that you typed in: " + directions);
        KivaCommand[] commands = convertToKivaCommands(directions);
        //System.out.println(Arrays.toString(commands));
        for (KivaCommand command : commands) {
            kiva.move(command);
        }
        
        outputPrintStatement(kiva, commands);
    }
    
    /**
     * Displays a message to the user upon completion of program execution.
     * The <tt>Kiva</tt> successfully dropping the Pod at the correct Drop Zone is the determing factor for what message gets printed to the console.
     */
    private void outputPrintStatement(Kiva kiva, KivaCommand[] commands) {
        if (!kiva.isSuccessfullyDropped()) {
            System.out.println("I'm sorry. The Kiva Robot did not pick up the pod and then drop it off in the right place.");
        }
        if (kiva.isSuccessfullyDropped() && commands[commands.length - 1] == KivaCommand.DROP) {
            System.out.println("Successfully picked up the pod and dropped it off. Thank you!");
        }
        if (kiva.isSuccessfullyDropped() && commands[commands.length - 1] != KivaCommand.DROP) {
            System.out.println("I'm sorry. The Kiva Robot did not pick up the pod and then drop it off in the right place.");
        }
    }
    
    /**
     * Prints diagnostic information to console.
     * After a <tt>.txt</tt> file is chosen by the user, information regarding the <tt>KivaM</tt> and the <tt>FloorMap</tt> are shown to the user
     * so they can make an informed decision when inputting commands.
     */
    private void displayDiagnostics(Kiva kiva, FloorMap map) {
        String diagnosticLocation = String.format("Starting location of Kiva: Point(%d, %d)", kiva.getCurrentLocation().getX(), kiva.getCurrentLocation().getY());
        String diagnosticDirection = String.format("Starting direction facing of Kiva: %s", kiva.getDirectionFacing().name());
        String diagnosticTake = String.format("Kiva has TAKEN Pod: %s", Boolean.toString(kiva.isCarryingPod()));
        String diagnosticDrop = String.format("Kiva has DROPPED Pod: %s", Boolean.toString(kiva.isSuccessfullyDropped()));
        String diagnosticPod = String.format("Location of Pod: Point(%d, %d)", map.getPodLocation().getX(), map.getPodLocation().getY());
        String diagnosticDropZone = String.format("Location of Drop Zone: Point(%d, %d)", map.getDropZoneLocation().getX(), map.getDropZoneLocation().getY());
        System.out.println(diagnosticLocation);
        System.out.println(diagnosticDirection);
        System.out.println(diagnosticTake);
        System.out.println(diagnosticDrop);
        System.out.println(diagnosticPod);
        System.out.println(diagnosticDropZone);
    }
    
    /**
     * Takes in a <tt>String</tt> from the user and converts the <String> into data the <tt>Kiva</tt> can interpret.
     * Each <tt>char</tt> of the given <tt>String</tt>, if it has an analagous value in <tt>KivaCommand</tt>, gets turned into an enum of that class and stored in
     * an array. Later, the <tt>Kiva</tt> will traverse that array and attempt to execute each command. Each value in this array will be read by <tt>move()</tt>
     * 
     * @param input     The <tt>String</tt> of commands the user inputted in the console.
     *                  Should be all uppercase, without whitespace, and only
     *                  contain <tt>char</tt>'s listed in <tt>KivaCommand</tt>
     *                  
     * 
     * @return a collection of <tt>KivaCommand</tt> values for the <tt>Kiva</tt> to execute 
     * @see #run
     * @see Kiva#move
     */
    public KivaCommand[] convertToKivaCommands(String input) {
        char[] tempArray = input.toCharArray();
        KivaCommand[] finalArray = new KivaCommand[tempArray.length];
        for (int i = 0; i < tempArray.length; i++) {
            //char value = Character.toUpperCase(tempArray[i]);     //commenting this out, ATA outline never states they want us to validate for case-sensitivity
            char value = tempArray[i];
            switch (value) {
                case 'F':
                    finalArray[i] = KivaCommand.FORWARD;
                    continue;
                case 'R':
                    finalArray[i] = KivaCommand.TURN_RIGHT;
                    continue;
                case 'L':
                    finalArray[i] = KivaCommand.TURN_LEFT;
                    continue;
                case 'T':
                    finalArray[i] = KivaCommand.TAKE;
                    continue;
                case 'D':
                    finalArray[i] = KivaCommand.DROP;
                    continue;
                default:
                    String message = String.format("User input of \"%c\" is illegal. Legal inputs are \"F\", \"R\", \"L\", \"T\", and \"D\"."
                                                    + "\nLoop failed to pass a legal value to array on i = %d."
                                                    + "\nCurrent KivaCommand[]: %s", tempArray[i], i, Arrays.toString(finalArray));
                    throw new IllegalArgumentException(message);
            }
        }
        
        return finalArray;
    }
}
