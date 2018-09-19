package problem.component;

import java.util.HashMap;

/**
 * This class is to store some important constants in the problems
 */

public class Constant {

    /* The minimum procedure time between the time when a passenger lands on
    the airport and the time when the passenger transfers to another flight*/
    public static HashMap<String, Integer> minProcedureTime
            = new HashMap<>();

    static {

        minProcedureTime.put("DDTT", 15);
        minProcedureTime.put("DDTS", 20);
        minProcedureTime.put("DDST", 20);
        minProcedureTime.put("DDSS", 15);

        minProcedureTime.put("DITT", 35);
        minProcedureTime.put("DITS", 40);
        minProcedureTime.put("DIST", 40);
        minProcedureTime.put("DISS", 35);

        minProcedureTime.put("IDTT", 35);
        minProcedureTime.put("IDTS", 40);
        minProcedureTime.put("IDST", 40);
        minProcedureTime.put("IDSS", 45);

        minProcedureTime.put("IITT", 20);
        minProcedureTime.put("IITS", 30);
        minProcedureTime.put("IIST", 30);
        minProcedureTime.put("IISS", 20);
    }

    /* The number of times of a passenger must take MRT between the arrival
    airport terminal and the left airport terminal */
    public static HashMap<String, Integer> mrtCount = new HashMap<>();

    static {

        mrtCount.put("DDTT", 0);
        mrtCount.put("DDTS", 1);
        mrtCount.put("DDST", 1);
        mrtCount.put("DDSS", 0);

        mrtCount.put("DITT", 0);
        mrtCount.put("DITS", 1);
        mrtCount.put("DIST", 1);
        mrtCount.put("DISS", 0);

        mrtCount.put("IDTT", 0);
        mrtCount.put("IDTS", 1);
        mrtCount.put("IDST", 1);
        mrtCount.put("IDSS", 2);

        mrtCount.put("IITT", 0);
        mrtCount.put("IITS", 1);
        mrtCount.put("IIST", 1);
        mrtCount.put("IISS", 0);
    }

    /* The time a single MRT needs */
    public final static int mrtTime = 8;

    /* The walking time of the passenger between an airport terminal to another */
    public static HashMap<String, Integer> walkingTime = new HashMap<>();

    static {

        Integer[][] walkingtimeArray
                = {
                {10, 15, 20, 25, 20, 25, 25},
                {15, 10, 15, 20, 15, 20, 20},
                {20, 15, 10, 25, 20, 25, 25},
                {25, 20, 25, 10, 15, 20, 20},
                {20, 15, 20, 15, 10, 15, 15},
                {25, 20, 25, 20, 15, 10, 20},
                {25, 20, 25, 20, 15, 20, 10}};

        String[] stationName = {"TN", "TC", "TS", "SN", "SC", "SS", "SE"};

        for (int i = 0; i < stationName.length; i++) {
            for (int j = 0; j < stationName.length; j++) {

                String key = stationName[i] + stationName[j];
                Integer value = walkingtimeArray[i][j];

                walkingTime.put(key, value);
            }
        }
    }
}
