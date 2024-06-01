import java.util.Map;

public class Withdraw extends Transaction {
    ConsoleManager console;
    CashManager cash;

    public Withdraw(SessionInfo session) {
        super(session);
        console = new ConsoleManager();
    }

    @Override
    public int getCustomerRequest() {
        int withdrawMoney = console.setScreen("Enter the Amount you want to withdraw", true);
        cash = new CashManager();
        if (cash.cashAvailable(withdrawMoney)) {
            return withdrawMoney;
        }
        return -1;
    }

    @Override
    protected boolean displayMessage(Map<String, Integer> message, int transid) {
        if (message.get("isSuccess") == 1) {
            console.setScreen("Please Collect Your Cash.", false);
            return true;
        } else {
            console.setScreen("Your Account doesn't have sufficient Balance or some error came up", false);
            return false;
        }
    }

}
