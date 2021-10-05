
import java.util.Arrays;

/**
 * Write a description of RemoteControlTest here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class RemoteControlTest {

    private String correctInput = "FFFTRF";
    private String lowercaseInput = "ffftrf";
    private String wrongInput = "B";
    private String mixedInput = "FFFTBF";
    
    private RemoteControl rc = new RemoteControl();
    
    public void testCorrectInput() {
        KivaCommand[] kc = rc.convertToKivaCommands(correctInput);
        System.out.println(Arrays.toString(kc));
    }
    
    public void testLowercaseInput() {
        KivaCommand[] kc = rc.convertToKivaCommands(lowercaseInput);
        System.out.println(Arrays.toString(kc));
    }
    
    public void testWrongInput() {
        KivaCommand[] kc = rc.convertToKivaCommands(wrongInput);
        System.out.println(Arrays.toString(kc));
    }
    
    public void testMixedInput() {
        KivaCommand[] kc = rc.convertToKivaCommands(mixedInput);
        System.out.println(Arrays.toString(kc));
    }
}
