public class Vector3DInheritance extends Vector2D {
    private double z;

    public Vector3DInheritance(double x, double y, double z) {
        super(x, y);
        this.z = z;
    }

    @Override
    public double abs() {
        double[] components = this.getComponents();
        return Math.sqrt(components[0] * components[0] + components[1] * components[1] + components[2] * components[2]);
    }

    @Override
    public double cdot(IVector param) {
        double[] a = normalizeTo3D(this.getComponents());
        double[] b = normalizeTo3D(param.getComponents());
        return a[0] * b[0] + a[1] * b[1] + a[2] * b[2];
    }

    @Override
    public double[] getComponents() {
        double[] components = super.getComponents();
        return new double[]{components[0], components[1], this.z};
    }

    public double[] cross(IVector param) {
        double[] a = normalizeTo3D(this.getComponents());
        double[] b = normalizeTo3D(param.getComponents());
        double x = a[1] * b[2] - a[2] * b[1];
        double y = a[2] * b[0] - a[0] * b[2];
        double z = a[0] * b[1] - a[1] * b[0];
        return new double[]{x, y, z};
    }

    private double[] normalizeTo3D(double[] components) {
        if (components.length == 3) {
            return components;
        }

        double[] normalized = new double[3];
        System.arraycopy(components, 0, normalized, 0, Math.min(components.length, 2));
        return normalized;
    }

    public IVector getSrv() {
        return this;
    }
}
