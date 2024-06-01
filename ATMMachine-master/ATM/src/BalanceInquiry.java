import java.util.Map;

public class BalanceInquiry extends Transaction {
    ConsoleManager console;
    CashManager cash;
    
    public BalanceInquiry(SessionInfo session) {
        super(session);
        console = new ConsoleManager();
    }

    public int getCustomerRequest() {
        return 0;
    }

    @Override
    protected boolean displayMessage(Map<String, Integer> message, int transid) {
        if(message.get("isSuccess") == 1) {
            console.setScreen("Your Account Balance is: " + String.valueOf(message.get("balance")), false);
            return true;
        } else {
            console.setScreen("Couldn't retrieve the balance", false);
            return false;
        }
}    
}

