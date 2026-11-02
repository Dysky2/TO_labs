public class PolarInheritance2D extends Vector2D {

    public PolarInheritance2D(double x, double y) { super(x, y); }
    public PolarInheritance2D() { super(); }

    public double getAngle() {
        double[] components = this.getComponents();
        return Math.atan2(components[1], components[0]);
    }

}
