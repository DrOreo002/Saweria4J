package co.saweria4j;

public interface SaweriaEventHandler {

    /**
     * Called when a donation is received
     *
     * @param donation Array of {@link SaweriaDonation}(s)
     */
    void onDonationReceived(SaweriaDonation[] donation);

    /**
     * Called when connection is opened to Saweria api
     */
    void onOpen();

    /**
     * Called when connection is closed
     */
    void onClosed();
}
