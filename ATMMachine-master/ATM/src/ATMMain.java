import java.io.IOException;
import java.net.InetAddress;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
/**
 * This class is Handling the implemetation of the interface which is inturn communicating with the transaction class
 * @author ARCHIT
 *
 */
class ATMImpl {
    public ReceiveMessageInterface rmiServer;
    private Registry registry;
    private String serverAddress, serverPort = "3232";
    public boolean isServerOK = true;

    protected ATMImpl() {
        try {
            // get the address of this host.
            serverAddress = InetAddress.getLocalHost().getHostAddress();
        } catch (Exception e) {
            System.out.println("Unable to determine host. Please give the server address manually");
        }
        try {
            // get the registry
            registry = LocateRegistry.getRegistry(serverAddress, (new Integer(serverPort)).intValue());
            // look up the remote object
            rmiServer = (ReceiveMessageInterface) (registry.lookup("bankServer"));
        } catch (RemoteException | NotBoundException e) {
            System.out.println(e.getMessage());
            isServerOK = false;
        }
    }

    public void requestTransaction(SessionInfo session, int option) throws RemoteException {
        Transaction currentTransaction = null;
        currentTransaction = Transaction.initiateTransaction(session, option);
        if (currentTransaction != null) {
            currentTransaction.performTransaction(session, rmiServer, option);
            currentTransaction = null;
        } else {

        }

    }

    public String verifyPIN(String cardNumber, int pin) throws RemoteException {
        return rmiServer.verifyPIN(cardNumber, pin);
    }
};
/**
 * Main class containing the interface and handling the job of interacting with the client
 *
 */
public class ATMMain {
    public static ATMImpl atmImpl = new ATMImpl(); // Object to ATM implementation
    public static ATMMain atm = new ATMMain();
    public static ConsoleManager console = new ConsoleManager();

    static public void main(String args[]) throws IOException {
        sessionManager();
    }

    static void sessionManager() throws RemoteException {
        boolean isAuthenticated = false;
        int pin = 0;
        String cardNumber = null;
        SessionInfo session = null;
        if (atmImpl.isServerOK) {
            while (true) {
                if (!isAuthenticated) {
                    cardNumber = "1234567890123456"; // Inplace of card-reader, hardcoded
                    pin = console.setScreen("Welcome Customer to HDFC Bank ATM : \nPlease enter your PIN: ", true);
                    String cardHolderName = atmImpl.verifyPIN(cardNumber, pin);
                    if (cardHolderName != null) {
                        session = new SessionInfo(cardHolderName, cardNumber);
                        isAuthenticated = true;
                    } else {
                        console.setScreen("Authentication failed\n", false);
                    }
                } else {
                    String cardHolderName = session.getCardHolderName();
                    int option = 0;
                    String text = "Welcome " + cardHolderName + " to HDFC Bank. Select One of the following services."
                            + "\n1) Money Withdrawal\n2) Deposit Cheque\n3) Balance Inquiry\n4) Change PIN\n0) Exit";
                    option = console.setScreen(text, true);
                    if (option != 0) {
                        atmImpl.requestTransaction(session, option);
                    } else {
                        isAuthenticated = false;
                        session.destroySession();
                    }
                }
            }
        } else {
            console.setScreen("Server Down. Please Try Again Later", false);
        }

    }
};
