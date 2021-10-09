
/**
 * Test class for checking if the <tt>KivaCommand</tt> class is setup properly.
 * 
 * 
 * @author Franz Jacob Hernandez (herfrn@amazon.com) 
 * @version 0.10
 * @since 10-03-2021
 */
public class KivaCommandTester {
    
    /**
     * Creates instances of <tt>KivaCommand</tt>, one for each constant. Then it prints that constant and its associated value to the console.
     * 
     * @see KivaCommand
     * @see KivaCommand#getDirectionKey
     */
    public void testForward() {
        KivaCommand command1 = KivaCommand.FORWARD;
        System.out.println(command1);
        System.out.println(command1.getDirectionKey());
        
        KivaCommand command2 = KivaCommand.TURN_LEFT;
        System.out.println(command2);
        System.out.println(command2.getDirectionKey());
        
        KivaCommand command3 = KivaCommand.TURN_RIGHT;
        System.out.println(command3);
        System.out.println(command3.getDirectionKey());
        
        KivaCommand command4 = KivaCommand.TAKE;
        System.out.println(command4);
        System.out.println(command4.getDirectionKey());
        
        KivaCommand command5 = KivaCommand.DROP;
        System.out.println(command5);
        System.out.println(command5.getDirectionKey());
        
        
    }
}
