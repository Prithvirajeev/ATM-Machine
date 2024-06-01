
import java.rmi.*;
import java.util.Map;
/**
 * Declaration of the function which should be implemented by the bank server.
 * @author ARCHIT
 *
 */
public interface ReceiveMessageInterface extends Remote
{
   String verifyPIN(String cardNumber, int pinVal) throws RemoteException;
   boolean changePIN(String cardNumber, int newPIN) throws RemoteException;
   int getBalance(String cardNumber) throws RemoteException;
   boolean depositCheque(String cardNumber, int val) throws RemoteException;
   boolean withdrawMoney(String cardNumber, int val) throws RemoteException;
   Map<String, Integer> messagetoPerform(String cardNumber, int amount, int type) throws RemoteException;
}