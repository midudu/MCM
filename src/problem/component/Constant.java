package problem.component;

import java.util.HashMap;

public class Constant {

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
}
