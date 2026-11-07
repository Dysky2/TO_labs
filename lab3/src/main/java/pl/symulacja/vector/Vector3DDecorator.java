package pl.symulacja.vector;

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
        double[] a = getComponents();
        double[] b = to3D(param.getComponents());
        return a[0]*b[0] + a[1]*b[1] + a[2]*b[2];
    }

    @Override
    public double[] getComponents() {
        double[] c = srcVector.getComponents();
        return new double[]{c[0], c[1], z};
    }

    public double[] cross(IVector param) {
        double[] a = getComponents();
        double[] b = to3D(param.getComponents());
        double[][] m = {
                {0,    -a[2],  a[1]},
                {a[2],  0,    -a[0]},
                {-a[1], a[0],  0   }
        };
        double[] r = new double[3];
        for (int i = 0; i < 3; i++)
            for (int j = 0; j < 3; j++)
                r[i] += m[i][j] * b[j];
        return r;
    }

    public IVector getSrcV() {
        return srcVector;
    }

    private static double[] to3D(double[] c) {
        double[] r = new double[3];
        System.arraycopy(c, 0, r, 0, Math.min(c.length, 3));
        return r;
    }
}
