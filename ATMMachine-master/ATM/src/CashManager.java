

public class CashManager {
    private int cashAvailable;

    public CashManager() {
        cashAvailable = 10000; // In real with physical devices available we
                               // will get the total cash
                               // available through hardware device something
                               // like getTotalCash();
    }
    /**
     * Checks if 
     * @param desiredAmount
     * @return if cash is available or not in the atm machine
     */
    public boolean cashAvailable(int desiredAmount) {
        if (possibleOrNot(desiredAmount)) {
            return true;
        } else {
            return false;
        }
    }
    /**
     * This function will check if the desired amount can be served with the
     * available denominations or not But for now to keep the logic side
     * simple and simply checking if desiredAmount<=CashAvailable
     */
    public boolean possibleOrNot(int desiredAmount) {
        if (desiredAmount <= cashAvailable) {
            return true;
        } else {
            return false;
        }
    }
};
