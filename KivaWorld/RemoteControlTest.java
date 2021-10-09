
import java.util.Arrays;

/**
 * Test class for checking if the <tt>convertToKivaCommands(String input)</tt> method from class <tt>RemoteControl</tt> works as intended.
 * 
 * 
 * @author Franz Jacob Hernandez (herfrn@amazon.com) 
 * @version 0.02
 * @since 10-03-2021
 */
public class RemoteControlTest {

    private String correctInput = "FFFTRF";
    private String lowercaseInput = "ffftrf";
    private String wrongInput = "B";
    private String mixedInput = "FFFTBF";
    
    private RemoteControl rc = new RemoteControl();
    
    /**
     * Tests if an uppercase <tt>String</tt> containing only valid <tt>char</tt> values is interpreted as a legal input for
     * the convertToKivaCommands(String input) method.
     * 
     * @see RemoteControl#convertToKivaCommands
     */
    public void testCorrectInput() {
        KivaCommand[] kc = rc.convertToKivaCommands(correctInput);
        System.out.println(Arrays.toString(kc));
    }
    
    /**
     * Tests if a lowercase <tt>String</tt> containing only valid <tt>char</tt> values is interpreted as a legal input for
     * the convertToKivaCommands(String input) method.
     * 
     * @see RemoteControl#convertToKivaCommands
     */
    public void testLowercaseInput() {
        KivaCommand[] kc = rc.convertToKivaCommands(lowercaseInput);
        System.out.println(Arrays.toString(kc));
    }
    
    /**
     * Tests if a <tt>String</tt> containing only invalid <tt>char</tt> values is interpreted as an illegal input for
     * the convertToKivaCommands(String input) method.
     * 
     * @see RemoteControl#convertToKivaCommands
     */
    public void testWrongInput() {
        KivaCommand[] kc = rc.convertToKivaCommands(wrongInput);
        System.out.println(Arrays.toString(kc));
    }
    
    /**
     * Tests if an uppercase <tt>String</tt> containing only a mix of valid and invalid <tt>char</tt> values is interpreted as an illegal input for
     * the convertToKivaCommands(String input) method.
     * 
     * @see RemoteControl#convertToKivaCommands
     */
    public void testMixedInput() {
        KivaCommand[] kc = rc.convertToKivaCommands(mixedInput);
        System.out.println(Arrays.toString(kc));
    }
}
