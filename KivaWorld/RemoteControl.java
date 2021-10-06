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
    
    public void outputPrintStatement(Kiva kiva, KivaCommand[] commands) {
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
    
    public void displayDiagnostics(Kiva kiva, FloorMap map) {
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
