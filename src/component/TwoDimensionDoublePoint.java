package component;

public class TwoDimensionDoublePoint {

    private double x;
    private double y;

    public TwoDimensionDoublePoint(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public TwoDimensionDoublePoint() {

        this(0.0,0.0);
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public void setX(double x) {
        this.x = x;
    }

    public void setY(double y) {
        this.y = y;
    }
}
