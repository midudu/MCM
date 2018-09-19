package problem.component;

import java.util.LinkedList;

// 这个类存储了一个登机口信息，内部含有一个{@code LinkedList}类型的字段
// 用于存储当前登机口接待的航班
public class Gate {

    private int id;

    private String hallType;

    private String location;

    private String arrivalType;

    private String leftType;

    private String planeType;

    private LinkedList<FlightRecordWithStationType> flightRecords;

    public Gate(int id, String hallType,
                String location, String arrivalType,
                String leftType, String planeType) {

        this.id = id;
        this.hallType = hallType;
        this.location = location;
        this.arrivalType = arrivalType;
        this.leftType = leftType;
        this.planeType = planeType;

        flightRecords = new LinkedList<>();
    }

    public int getId() {
        return id;
    }

    public String getHallType() {
        return hallType;
    }

    public String getLocation() {
        return location;
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

    public LinkedList<FlightRecordWithStationType> getFlightRecords() {
        return flightRecords;
    }

    public void clear() {

        this.flightRecords.clear();
    }
}
