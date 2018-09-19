package problem.component;

// 这个类用来存储一个航班信息，它扩展了 {@code FlightRecord} 类，增加了
// 航班的登机口信息和关联旅客人数

/**
 * This class is derived from the class {@code FlightRecord}. It adds the
 * type of the relative airport station and the number of the relative passenger
 * numbers of the flight.
 */
public class FlightRecordWithStationType extends FlightRecord {

    /* The type of the relative airport station. The possible value is "RT",
    "NT", "RU", "NU". "R" stands for the number of the relative passengers of
     the flight is more than 0 and "N" stands for 0 relative passenger; "T"
     stands for the flight must stay at a "T" type airport station and "U"
     stands for the flight might stay at a "T" type airport station or a "S"
     type airport station */
    private String stationType;

    /* The number of the relative passengers of the flight */
    private int relativePassengers;

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
     * @param stationType
     * @param relativePassengers
     */
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
