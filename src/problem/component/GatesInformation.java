package problem.component;

public class GatesInformation {

    private String name;

    private String location;

    private String arrivalType;

    private String leftType;

    private String planeType;

    public GatesInformation(String name, String location,
                            String arrivalType, String leftType, String planeType) {

        this.name = name;
        this.location = location;
        this.arrivalType = arrivalType;
        this.leftType = leftType;
        this.planeType = planeType;
    }
}
