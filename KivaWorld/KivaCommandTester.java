
/**
 * Write a description of KivaCommandTester here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class KivaCommandTester {
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
