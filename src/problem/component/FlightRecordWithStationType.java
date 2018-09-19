package problem.component;

// 这个类用来存储一个航班信息，它扩展了 {@code FlightRecord} 类，增加了
// 航班的登机口信息和关联旅客人数
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
