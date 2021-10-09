
import edu.duke.Point;

/**
 * Test class for the <tt>motorLifetime</tt> class field within the <tt>Kiva</tt> class.
 * Has a small map for the <tt>Kiva</tt> to perform small operations within, and then prints diagnostic information to the console.
 * 
 * @author Franz Jacob Hernandez (herfrn@amazon.com) 
 * @version 0.10
 * @since 10-03-2021
 */
public class KivaMotorLifetimeTester {

    private String defaultLayout = ""
                                + "-----\n"
                                + "|K D|\n"
                                + "| P |\n"
                                + "|* *|\n"
                                + "-----\n";

    private FloorMap defaultMap = new FloorMap(defaultLayout);
    
    /**
     * Puts the <tt>Kiva</tt> in a small 3x3 map and has it perform some actions/movement. After resolving a command, the lifetime of the motor is printed
     * to the console to check whether or not the correct amount had been added to the class field. The value of the motor begins at 0, and performing certain
     * actions increases the value by 1000. The motor reaches the end of its life when it hits 72,000,000,000.
     */
    public void testIncrement() {
        Kiva kiva = new Kiva(defaultMap, new Point(1, 1));
        
        System.out.println(kiva.getMotorLifetime());
        kiva.move(KivaCommand.TURN_RIGHT);
        System.out.println(kiva.getMotorLifetime());
        kiva.move(KivaCommand.FORWARD);
        System.out.println(kiva.getMotorLifetime());
        kiva.move(KivaCommand.TURN_RIGHT);
        System.out.println(kiva.getMotorLifetime());
        kiva.move(KivaCommand.FORWARD);
        System.out.println(kiva.getMotorLifetime());
        kiva.move(KivaCommand.TAKE);
        System.out.println(kiva.getMotorLifetime());
    }
    
    //Method used just for testing. Helped clear the confusion I had about how the coordinates worked with the maps.
    private void showMapDimensions() {
        Kiva kiva = new Kiva(defaultMap);
        int minY = kiva.getMap().getMinRowNum();
        int minX = kiva.getMap().getMinColNum();
        int maxY = kiva.getMap().getMaxRowNum();
        int maxX = kiva.getMap().getMaxColNum();
        
        String message = String.format("Map Dimensions:\nmin row: %d max row: %d \nmin col: %d max col: %d", minY, maxY, minX, maxX);
        System.out.print(message);
    }
    
}
