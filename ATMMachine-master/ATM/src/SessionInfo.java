/**
 * Retains all the session information of the customer which has successfully logged in to atm machone
 * This class object is sent along with all the transaction to precheck about the customer details
 * @author ARCHIT
 *
 */
public class SessionInfo {

    private String cardNumber;
    private String cardHolderName;

    protected SessionInfo(String cardHolderName, String cardNumber) {
        this.cardNumber = cardNumber;
        this.cardHolderName = cardHolderName;
    }

    protected String getCardHolderName() {
        return cardHolderName;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    protected void destroySession() {
        // this.cardNumber = null;
        this.cardHolderName = null;

    }

}
