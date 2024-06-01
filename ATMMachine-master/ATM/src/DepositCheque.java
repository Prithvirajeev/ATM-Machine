
import java.util.Map;
/**
 * It handles the interface which is shown to the customer when he decides to deposit a cheque
 * @author ARCHIT
 *
 */
public class DepositCheque extends Transaction{
    ConsoleManager console;
    CashManager cash;
    public DepositCheque(SessionInfo session) {
        super(session);
        console = new ConsoleManager();
    }
    
    @Override
    public int getCustomerRequest() {
        int depositCheque = console.setScreen("Please Insert the Cheque and also enter the amount", true);
        return depositCheque;
    }

    @Override
    protected boolean displayMessage(Map<String, Integer> message, int transid) {
        if(message.get("isSuccess") == 1) {
            console.setScreen("Successfully deposited the cheque", false);
            return true;
        } else {
            console.setScreen("Some error while submitting the cheque", false);
            return false;
        }
    }
}
