import edu.duke.FileResource;
import java.util.Arrays;
import java.util.Scanner;

/**
 * This is the class that controls Kiva's actions. Implement the <code>run()</code>
 * method to deliver the pod and avoid the obstacles.
 *
 * This is starter code that may or may not work. You will need to update the code to
 * complete the project.
 */
public class RemoteControl {
    KeyboardResource keyboardResource;

    /**
     * Build a new RemoteControl.
     */
    public RemoteControl() {
        keyboardResource = new KeyboardResource();
    }

    /**
     * The controller that directs Kiva's activity. Prompts the user for the floor map
     * to load, displays the map, and asks the user for the commands for Kiva to execute.
     *
     * [Here's the method you'll execute from within BlueJ. It may or may not run successfully
     * as-is, but you'll definitely need to add more to complete the project.]
     */
    public void run() {
        System.out.println("Please select a map file.");
        System.out.println("Legal inputs are: \"sample_floor_map1.txt\", \"sample_floor_map2.txt\", and \"sample_floor_map3.txt\"");
        Scanner sc = new Scanner(System.in);
        String file = sc.nextLine();
        FileResource fileResource = new FileResource(file);
        
        String inputMap = fileResource.asString();
        FloorMap floorMap = new FloorMap(inputMap);
        Kiva kiva = new Kiva(floorMap);
        System.out.println(floorMap);
        
        

        System.out.println("Please enter the directions for the Kiva Robot to take.");
        String directions = keyboardResource.getLine();
        System.out.println("Directions that you typed in: " + directions);
        KivaCommand[] commands = convertToKivaCommands(directions);
        //System.out.println(Arrays.toString(commands));
        for (KivaCommand command : commands) {
            kiva.move(command);
        }
    }
    
    public void displayDiagnostics(Kiva kiva) {
        String diagnosticLocation = String.format("Start location of kiva: Point(%d, %d)", kiva.getCurrentLocation().getX(), kiva.getCurrentLocation().getY());
        String diagnosticDirection = String.format("Start direction facing of kiva: %s", kiva.getDirectionFacing().name());
        System.out.println(diagnosticLocation);
        System.out.println(diagnosticDirection);
    }
    
    public KivaCommand[] convertToKivaCommands(String input) {
        char[] tempArray = input.toCharArray();
        KivaCommand[] finalArray = new KivaCommand[tempArray.length];
        for (int i = 0; i < tempArray.length; i++) {
            char value = Character.toUpperCase(tempArray[i]);
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
                                                    + "\nLoop failed on i = %d. Current KivaCommand[]: %s", tempArray[i], i, Arrays.toString(finalArray));
                    throw new IllegalArgumentException(message);
            }
        }
        
        return finalArray;
    }
}
