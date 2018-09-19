package problem.component;

import java.util.LinkedList;


/**
 * An object of this class represents a boarding gate
 */
public class Gate {

    /* The id of the gate */
    private int id;

    /* The hall type of the gate. The possible value is "T" or "S" */
    private String hallType;

    /* The location of the gate. The possible value is "North", "Center",
    "South" and "East" */
    private String location;

    /* The type of the arrival flight. The possible value is "I" or "D". See
    {@code FlightRecord} for details */
    private String arrivalType;

    /* The type of the left flight. The possible value is "I" or "D". See
    {@code FlightRecord} for details */
    private String leftType;

    /* The type of the boarding plane. The possible value is "N" or "W". See
    {@code FlightRecord} for details */
    private String planeType;

    /* A {@code LinkedList} container to store the flight records boarding on
    the current boarding gate
     */
    private LinkedList<FlightRecordWithStationType> flightRecords;

    /**
     * Constructor.
     *
     * @param id
     * @param hallType
     * @param location
     * @param arrivalType
     * @param leftType
     * @param planeType
     */
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

    /**
     * Clear this.flightRecords.
     */
    public void clear() {

        this.flightRecords.clear();
    }
}
