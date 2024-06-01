
import java.rmi.RemoteException;
import java.util.Map;
/**
 * This abstract class is implemented by the various transaction carried out during the customer.
 * 
 * @author ARCHIT
 *
 */
public abstract class Transaction {
    private static int transid = 1;    // Maintaining the transaction id for each request for future purpose
    protected SessionInfo session;
    protected int id;
    
    protected Transaction(SessionInfo session) {
        this.session = session;
        id = transid++;
    }
    /**
     * This method is returning the relevant object for the request 
     * Basically an example of polymorphism showing the single object for all type of transaction
     * @param session
     * @param option
     * @return
     */
    public static Transaction initiateTransaction(SessionInfo session, int option) {
        switch(option) {
        case 1:
            return new Withdraw(session);
        case 2:
            return new DepositCheque(session);
        case 3:
            return new BalanceInquiry(session);
        case 4:
            return new PinChange(session);
        default:
            return null;
           
        }
    }
    /**
     * This method is communicating with the bank server
     * @param session
     * @param rmiServer
     * @param type
     * @return
     * @throws RemoteException
     */
    public boolean performTransaction(SessionInfo session, ReceiveMessageInterface rmiServer, int type) throws RemoteException {
        Map<String, Integer> message = null;
        int val  = getCustomerRequest();
        if(val != -1) {
        message = rmiServer.messagetoPerform(session.getCardNumber(), val, type);
        }
        return displayMessage(message, transid);
    }
    protected abstract int getCustomerRequest();
    protected abstract boolean displayMessage(Map<String, Integer> message, int transid);
};
