public class Polar2DAdapter implements IPolar2D, IVector{
    private Vector2D srcVector;

    public Polar2DAdapter(Vector2D srcVector) {
        this.srcVector = srcVector;
    }

    @Override
    public double abs() {
        return this.srcVector.abs();
    }

    @Override
    public double cdot(IVector param) {
        return this.srcVector.cdot(param);
    }

    @Override
    public double[] getComponents() {
        return this.srcVector.getComponents();
    }

    @Override
    public double getAngle() {
        double[] components = this.srcVector.getComponents();
        return Math.atan2(components[1], components[0]);
    }
}
