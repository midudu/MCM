package problem.component;

import java.util.Comparator;

public class FlightRecord implements Comparable<FlightRecord> {

    private int id;

    // unit: min
    private int arrivalTime;

    // unit: min
    private int leftTime;

    private String arrivalFlightName;

    private String leftFlightName;

    private String arrivalType;

    private String leftType;

    private String planeType;

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

    public int compareTo(FlightRecord o) {

        if (leftTime != o.leftTime) {
            return Integer.compare(this.leftTime, o.leftTime);

        } else if (this.arrivalTime != o.arrivalTime) {
            return Integer.compare(this.arrivalTime, o.arrivalTime);

        } else {

            return Integer.compare(this.id, o.id);
        }


    }
}
