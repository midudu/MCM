package problem.problemOne;

import problem.Problem;
import problem.component.FlightRecord;

import java.util.HashMap;
import java.util.TreeSet;

public class ProblemOne extends Problem {

    private TreeSet<FlightRecord> DDN = new TreeSet<>();
    private TreeSet<FlightRecord> DDW = new TreeSet<>();
    private TreeSet<FlightRecord> DIN = new TreeSet<>();
    private TreeSet<FlightRecord> DIW = new TreeSet<>();
    private TreeSet<FlightRecord> IDN = new TreeSet<>();
    private TreeSet<FlightRecord> IDW = new TreeSet<>();
    private TreeSet<FlightRecord> IIN = new TreeSet<>();
    private TreeSet<FlightRecord> IIW = new TreeSet<>();

    private void generateFlightRecordMap() {


        for (FlightRecord flightRecord: this.flightRecordArrayList) {

            String arrivalType = flightRecord.getArrivalType();
            String leftType = flightRecord.getLeftType();
            String planeType = flightRecord.getPlaneType();

            String totalString = arrivalType + leftType + planeType;
            if (totalString.length() != 3) {
                throw new RuntimeException();
            }

            switch (totalString) {
                case "DDN":{
                    DDN.add(flightRecord);
                    break;
                }
                case "DDW":{
                    DDW.add(flightRecord);
                    break;
                }
                case "DIN":{
                    DIN.add(flightRecord);
                    break;
                }
                case "DIW":{
                    DIW.add(flightRecord);
                    break;
                }
                case "IIN":{
                    IIN.add(flightRecord);
                    break;
                }
                case "IIW":{
                    IIW.add(flightRecord);
                    break;
                }
                case "IDN":{
                    IDN.add(flightRecord);
                    break;
                }
                case "IDW":{
                    IDW.add(flightRecord);
                    break;
                }
                default:{
                    throw new RuntimeException();
                }
            }
        }
    }

    // Below is for test
    public static void main(String[] args) {

        ProblemOne problemOne = new ProblemOne();
        problemOne.getFlightRecordArrayListFromExcel();

        problemOne.generateFlightRecordMap();

        System.out.println("haha");
    }
}
