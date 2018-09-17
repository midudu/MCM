package problem.component;

public class FlightRecordWithStationType extends FlightRecord {

    private String stationType;

    public FlightRecordWithStationType(
            int id, int arrivalTime, int leftTime,
            String arrivalFlightName, String leftFlightName,
            String arrivalType, String leftType, String planeType,
            String stationType) {

        super(id, arrivalTime, leftTime, arrivalFlightName, leftFlightName,
                arrivalType, leftType, planeType);

        this.stationType = stationType;
    }
}
