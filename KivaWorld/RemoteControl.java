import edu.duke.FileResource;
import java.util.Arrays;

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
        FileResource fileResource = null;
        String inputMap = fileResource.asString();
        FloorMap floorMap = new FloorMap(inputMap);
        System.out.println(floorMap);

        System.out.println("Please enter the directions for the Kiva Robot to take.");
        String directions = keyboardResource.getLine();
        System.out.println("Directions that you typed in: " + directions);
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
