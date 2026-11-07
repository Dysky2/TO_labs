package vector;

public class Vector2D implements IVector {

    private double x;
    private double y;

    public Vector2D(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public Vector2D() {
        this.x = 0;
        this.y = 0;
    }

    @Override
    public double[] getComponents() {
        return new double[]{this.x, this.y};
    }

    @Override
    public double abs() {
        return Math.sqrt(this.x * this.x + this.y * this.y);
    }

    @Override
    public double cdot(IVector param) {
        double[] components = param.getComponents();
        return this.x * components[0] + this.y * components[1];
    }
}