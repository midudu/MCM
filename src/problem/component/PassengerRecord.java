package problem.component;

/**
 * An object of this class stores a passenger record from the original data.
 */
public class PassengerRecord {

    /* The number of the passengers in the current record */
    private int passengerNumbers;

    /* The type of the arrival flight. The possible value is "I" or "D". See
    {@code FlightRecord} for details */
    private String arrivalFlightType;

    /* The type of the left flight. The possible value is "I" or "D". See
    {@code FlightRecord} for details */
    private String leftFlightType;

    /* The id of the arrival flight */
    private int arrivalFlightId;

    /* The id of the left flight*/
    private int leftFlightId;

    /**
     * Constructor.
     *
     * @param passengerNumbers
     * @param arrivalFlightType
     * @param leftFlightType
     * @param arrivalFlightId
     * @param leftFlightId
     */
    public PassengerRecord(int passengerNumbers,
                           String arrivalFlightType, String leftFlightType,
                           int arrivalFlightId, int leftFlightId) {

        this.passengerNumbers = passengerNumbers;
        this.arrivalFlightType = arrivalFlightType;
        this.leftFlightType = leftFlightType;
        this.arrivalFlightId = arrivalFlightId;
        this.leftFlightId = leftFlightId;
    }

    public int getArrivalFlightId() {
        return arrivalFlightId;
    }

    public int getLeftFlightId() {
        return leftFlightId;
    }

    public int getPassengerNumbers() {
        return passengerNumbers;
    }

    public String getArrivalFlightType() {
        return arrivalFlightType;
    }

    public String getLeftFlightType() {
        return leftFlightType;
    }
}
