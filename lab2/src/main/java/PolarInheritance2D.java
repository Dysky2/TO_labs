public class PolarInheritance2D extends Vector2D {

    public double getAngle() {
        double[] components = this.getComponents();
        return Math.atan2(components[1], components[0]);
    }

}
