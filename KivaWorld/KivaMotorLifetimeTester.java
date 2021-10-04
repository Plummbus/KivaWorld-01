
import edu.duke.Point;

/**
 * Write a description of KivaMotorLifetimeTester here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class KivaMotorLifetimeTester {

    private String defaultLayout = ""
                                + "-----\n"
                                + "|K D|\n"
                                + "| P |\n"
                                + "|* *|\n"
                                + "-----\n";

    private FloorMap defaultMap = new FloorMap(defaultLayout);
                                
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
    
    //testing to see what the coordinates are
    public void showMapDimensions() {
        Kiva kiva = new Kiva(defaultMap);
        int minY = kiva.getMap().getMinRowNum();
        int minX = kiva.getMap().getMinColNum();
        int maxY = kiva.getMap().getMaxRowNum();
        int maxX = kiva.getMap().getMaxColNum();
        
        String message = String.format("Map Dimensions:\nmin row: %d max row: %d \nmin col: %d max col: %d", minY, maxY, minX, maxX);
        System.out.print(message);
    }
    
}
