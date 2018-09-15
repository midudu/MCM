package problem.component;

public class FlightRecord {

    // unit: min
    private int arrivalTime;

    // unit: min
    private int leftTime;

    private String arrivalFlightName;

    private String leftFlightName;

    private String arrivalType;

    private String leftType;

    private String planeType;

    public FlightRecord(int arrivalTime, int leftTime,
                        String arrivalFlightName, String leftFlightName,
                        String arrivalType, String leftType, String planeType) {

        this.arrivalTime = arrivalTime;
        this.leftTime = leftTime;
        this.arrivalFlightName = arrivalFlightName;
        this.leftFlightName = leftFlightName;
        this.arrivalType = arrivalType;
        this.leftType = leftType;
        this.planeType = planeType;
    }

    public int getArrivalTime() {
        return arrivalTime;
    }

    public int getLeftTime() {
        return leftTime;
    }

    public String getArrivalFlightName() {
        return arrivalFlightName;
    }

    public String getLeftFlightName() {
        return leftFlightName;
    }

    public String getArrivalType() {
        return arrivalType;
    }

    public String getLeftType() {
        return leftType;
    }

    public String getPlaneType() {
        return planeType;
    }
}
