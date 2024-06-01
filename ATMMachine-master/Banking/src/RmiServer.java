import java.net.InetAddress;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.HashMap;
import java.util.Map;
/**
 * This is the main class of the bank and it is communicating with client as well aas the bank database
 * @author ARCHIT
 *
 */
public class RmiServer extends java.rmi.server.UnicastRemoteObject implements ReceiveMessageInterface {

    private static final long serialVersionUID = 1L;
    private static HashMap<String, String[]> bankDB;
    Map<String, Integer> mp = new HashMap<String, Integer>();
    int thisPort;
    String thisAddress;
    Registry registry; // rmi registry for lookup the remote objects.
    BankDatabase bdb = new BankDatabase();

    public RmiServer() throws RemoteException {
        try {
            // get the address of this host.
            thisAddress = InetAddress.getLocalHost().getHostAddress();
        } catch (Exception e) {
            throw new RemoteException("can't get inet address.");
        }
        thisPort = 3232; // this port(registry port)
        System.out.println("this address=" + thisAddress + ",port=" + thisPort);
        try {
            // create the registry and bind the name and object.
            registry = LocateRegistry.createRegistry(thisPort);
            registry.rebind("bankServer", this);
        } catch (RemoteException e) {
            throw e;
        }
        bankDB = bdb.readTextFile("BankCustomerDB");
    }

    static public void main(String args[]) {
        try {
            RmiServer s = new RmiServer();
        } catch (Exception e) {
            System.out.println("Cannot get the Server registered. Exiting" + e.getMessage());
            System.exit(1);
        }
    }

    @Override
    public String verifyPIN(String cardNumber, int pinVal) {
        if (!bankDB.isEmpty() && cardNumber != null) {
            System.out.println(bankDB.get(cardNumber)[1] + "  " + pinVal);
            if (bankDB.get(cardNumber)[0].equals(String.valueOf(pinVal))) {
                return bankDB.get(cardNumber)[1];
            }
        }
        return null;

    }

    @Override
    public boolean changePIN(String cardNumber, int newPIN) {
        System.out.println("card: " + cardNumber);
        String[] values = bankDB.get(cardNumber);
        values[0] = String.valueOf(newPIN);
        bankDB.put(cardNumber, values);
        try {
            if (bdb.updateEntries(bankDB)) {
                return true;
            }
        } catch (Exception e) {

        }
        return false;
    }

    @Override
    public int getBalance(String cardNumber) throws RemoteException {
        if (cardNumber != null && !bankDB.get(cardNumber).equals("")) {
            System.out.println("Balance Info: " + bankDB.get(cardNumber)[2]);
            return Integer.valueOf(bankDB.get(cardNumber)[2]);
        }
        return -1;
    }

    @Override
    public boolean depositCheque(String cardNumber, int depositMoney) throws RemoteException {
        String[] values = bankDB.get(cardNumber);
        int bal = Integer.valueOf(values[2]);
        bal += depositMoney;
        values[2] = String.valueOf(bal);
        bankDB.put(cardNumber, values);
        try {
            if (bdb.updateEntries(bankDB)) {
                return true;
            }
        } catch (Exception e) {
            return false;
        }
        return false;
    }

    @Override
    public boolean withdrawMoney(String cardNumber, int withdrawMoney) throws RemoteException {
        String[] values = bankDB.get(cardNumber);
        int bal = Integer.valueOf(values[2]);
        if (bal >= withdrawMoney) {
            bal -= withdrawMoney;
            values[2] = String.valueOf(bal);
            bankDB.put(cardNumber, values);
            try {
                if (bdb.updateEntries(bankDB)) {
                    return true;
                }
            } catch (Exception e) {
                return false;
            }
        }
        return false;
    }

    @Override
    public Map<String, Integer> messagetoPerform(String cardNumber, int val, int type) throws RemoteException {
        mp.clear();
        switch (type) {
        case 1:
            if (withdrawMoney(cardNumber, val)) {
                mp.put("isSuccess", 1);
            } else {
                mp.put("isSuccess", 0);
            }
            return mp;
        case 2:
            if (depositCheque(cardNumber, val)) {
                mp.put("isSuccess", 1);
            } else {
                mp.put("isSuccess", 0);
            }
            return mp;
        case 3:
            int bal = getBalance(cardNumber);
            if (bal != -1.0) {
                mp.put("isSuccess", 1);
                mp.put("balance", bal);
            } else {
                mp.put("isSuccess", 0);
                mp.put("balance", -1);
            }
            return mp;
        case 4:
            boolean isSuccess = changePIN(cardNumber, val);
            if (isSuccess) {
                mp.put("isSuccess", 1);
            } else {
                mp.put("isSuccess", 0);
            }
            return mp;
        default:
            return mp;
        }
    }
};