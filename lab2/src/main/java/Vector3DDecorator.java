public class Vector3DDecorator implements IVector {
    private IVector srcVector;
    private double z;

    public Vector3DDecorator(IVector srcVector, double z) {
        this.srcVector = srcVector;
        this.z = z;
    }

    @Override
    public double abs() {
        double[] components = this.getComponents();
        return Math.sqrt(components[0] * components[0] + components[1] * components[1] + components[2] * components[2]);
    }

    @Override
    public double cdot(IVector param) {
        double[] a = this.getComponents();
        double[] b = param.getComponents();
        return a[0] * b[0] + a[1] * b[1] + a[2] * b[2];
    }

    @Override
    public double[] getComponents() {
        double[] components = srcVector.getComponents();
        double[] newComponents = new double[components.length + 1];
        System.arraycopy(components, 0, newComponents, 0, components.length);
        newComponents[components.length] = this.z;
        return newComponents;
    }

    public double[] cross(IVector param) {
        double[] a = this.getComponents();
        double[] b = param.getComponents();
        double x = a[1] * b[2] - a[2] * b[1];
        double y = a[2] * b[0] - a[0] * b[2];
        double z = a[0] * b[1] - a[1] * b[0];
        return new double[]{x, y, z};
    }

    public IVector getSrv() {
        return srcVector;
    }
}
