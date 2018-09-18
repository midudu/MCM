package problem.component;

public class FlightRecordWithStationType extends FlightRecord {

    private String stationType;

    private int relativePassengers;

    public FlightRecordWithStationType(
            int id, int arrivalTime, int leftTime,
            String arrivalFlightName, String leftFlightName,
            String arrivalType, String leftType, String planeType,
            String stationType, int relativePassengers) {

        super(id, arrivalTime, leftTime, arrivalFlightName, leftFlightName,
                arrivalType, leftType, planeType);

        this.stationType = stationType;
        this.relativePassengers = relativePassengers;
    }

    public String getStationType() {
        return stationType;
    }

    public int getRelativePassengers() {
        return relativePassengers;
    }
}
