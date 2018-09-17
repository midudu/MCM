package problem.component;

public class PassengerRecord {

    private int passengerNumbers;

    private String arrivalFlightType;

    private String leftFlightType;

    private int arrivalFlightId;

    private int leftFlightId;

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
