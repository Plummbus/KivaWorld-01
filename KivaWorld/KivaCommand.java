
/**
 * State of the current command the <tt>Kiva</tt> is attempting to perform.
 * 
 * The <tt>Kiva</tt> will receive commands as values of this enum. Each value corresponds to a specific action/movement the <tt>Kiva</tt> can take.
 * 
 * @author Franz Jacob Hernandez (herfrn@amazon.com) 
 * @version 0.10
 * @since 10-03-2021
 */
public enum KivaCommand {

    FORWARD('F'), TURN_LEFT('L'), TURN_RIGHT('R'), TAKE('T'), DROP('D');
    
    private final char label;
    
    /**
     * Private class constructor sets label of enum.
     * Creates instance of this class and assigns one of the 5 values to the singular class field <tt>label</tt>. Since this is a private constructor, its use
     * is restricted to all places except for inside this class itself.
     * 
     * @param label     char that corresponds with a value in the enum.
     */
    private KivaCommand(char label) {
        this.label = label;
    }

    /**
     * A representaion of an action/movement the <tt>Kiva</tt> can perform.
     * 
     * @see Kiva#move
     * @return enum value for an action/move
     */
    public char getDirectionKey() {
        return this.label;
    }
    
}
