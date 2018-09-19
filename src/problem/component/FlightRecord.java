package problem.component;

/**
 * This class is to store the flight information. An object of this class stores
 * a flight record in the original data sheet
 */
public class FlightRecord implements Comparable<FlightRecord> {

    /* The id of the flight */
    private int id;

    /* The exact arrival time of the flight (unit: min) */
    private int arrivalTime;

    /* The exact left time of the flight (unit: min) */
    private int leftTime;

    /* The name of the arrival flight */
    private String arrivalFlightName;

    /* The name of the left flight ({@code leftFlightName} is always different
     * from {@code arrivalFlightName} according to the original data) */
    private String leftFlightName;

    /* The type of the arrival flight. The value is either "D" or "I". "D"
    stands for a domestic flight and "I" stands for a international flight */
    private String arrivalType;

    /* The type of the left flight. The value is either "D" or "I". "D"
    stands for a domestic flight and "I" stands for a international flight */
    private String leftType;

    /* The type of the plane. The value is either "N" or "R". "N" stands for a
    narrow type plane and "W" stands for a wide type plane */
    private String planeType;

    /**
     * Constructor
     *
     * @param id
     * @param arrivalTime
     * @param leftTime
     * @param arrivalFlightName
     * @param leftFlightName
     * @param arrivalType
     * @param leftType
     * @param planeType
     */
    public FlightRecord(int id, int arrivalTime, int leftTime,
                        String arrivalFlightName, String leftFlightName,
                        String arrivalType, String leftType, String planeType) {

        this.id = id;
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

    public int getId() {
        return id;
    }


    /**
     * The flight is sorted by the left time (first-key) and the arrival time (second-key)
     *
     * @param anotherFlightRecord another flight record to be compared
     * @return the comparing result
     */
    public int compareTo(FlightRecord anotherFlightRecord) {

        if (leftTime != anotherFlightRecord.leftTime) {
            return Integer.compare(this.leftTime, anotherFlightRecord.leftTime);

        } else if (this.arrivalTime != anotherFlightRecord.arrivalTime) {
            return Integer.compare(this.arrivalTime, anotherFlightRecord.arrivalTime);

        } else {

            return Integer.compare(this.id, anotherFlightRecord.id);
        }
    }
}
