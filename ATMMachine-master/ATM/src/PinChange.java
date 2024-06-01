
import java.util.Map;
/**
 * Screen displayed to the customer while pin change process
 * @author ARCHIT
 *
 */
public class PinChange extends Transaction {
    ConsoleManager console;
    CashManager cash;

    public PinChange(SessionInfo session) {
        super(session);
        console = new ConsoleManager();
    }

    public int getCustomerRequest() {
        int newPIN = console.setScreen("Enter the new PIN", true);
        int newPINAgain = console.setScreen("Please Again Enter the new PIN", true);
        if (newPIN == newPINAgain) {
            return newPIN;
        } else {
            return -1;
        }
    }

    @Override
    protected boolean displayMessage(Map<String, Integer> message, int transid) {
        if (message != null) {
            console.setScreen("PIN Successfully Changed!", false);
            return true;
        } else {
            console.setScreen("PIN Doesn't match. Please try Again.", false);
            return false;
        }
    }
}
