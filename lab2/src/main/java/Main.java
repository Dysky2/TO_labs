public class Main {
    public static void main(String[] args) {
        Vector2D base2D = new Vector2D(2, -3);
        Vector3DDecorator v0 = new Vector3DDecorator(base2D, 0);
        Vector3DInheritance v1 = new Vector3DInheritance(1, 2, 3);
        Vector3DDecorator v2 = new Vector3DDecorator(new Vector2D(4, -1), 5);
        Vector3DDecorator v3 = new Vector3DDecorator(new Polar2DAdapter(new Vector2D(-2, 3)), 1);

        IVector[] vectors = {v0, v1, v2, v3};

        for (int i = 0; i < vectors.length; i++) {
            printVector("v" + i, vectors[i]);
        }

        System.out.println("\nIloczyn skalarny oraz wektorowy dla wszystkich kombinacji:");
        for (int i = 0; i < vectors.length; i++) {
            for (int j = i + 1; j < vectors.length; j++) {
                IVector a = vectors[i];
                IVector b = vectors[j];

                double dot = a.cdot(b);
                double[] cross = crossVector(a, b);

                System.out.printf("v%d . v%d = %.4f%n", i, j, dot);
                System.out.printf("v%d x v%d = %s%n", i, j, formatComponents(cross));
            }
        }
    }

    private static void printVector(String name, IVector vector) {
        double[] components = vector.getComponents();
        double angle = components.length >= 2 ? Math.atan2(components[1], components[0]) : 0.0;
        double radius = components.length >= 2 ? Math.hypot(components[0], components[1]) : Math.abs(components[0]);

        System.out.println(name + ":");
        System.out.println("  kartezjanski: " + formatComponents(components));
        System.out.println("  biegunowy:    r = " + String.format("%.4f", radius)
                + ", fi = " + String.format("%.4f", angle) + " rad");
    }

    private static double[] crossVector(IVector a, IVector b) {
        if (a instanceof Vector3DDecorator) {
            return ((Vector3DDecorator) a).cross(b);
        }
        if (a instanceof Vector3DInheritance) {
            return ((Vector3DInheritance) a).cross(b);
        }

        double[] va = normalizeTo3D(a.getComponents());
        double[] vb = normalizeTo3D(b.getComponents());
        double x = va[1] * vb[2] - va[2] * vb[1];
        double y = va[2] * vb[0] - va[0] * vb[2];
        double z = va[0] * vb[1] - va[1] * vb[0];
        return new double[]{x, y, z};
    }

    private static double[] normalizeTo3D(double[] components) {
        if (components.length == 3) {
            return components;
        }

        double[] normalized = new double[3];
        System.arraycopy(components, 0, normalized, 0, Math.min(components.length, 2));
        return normalized;
    }

    private static String formatComponents(double[] components) {
        StringBuilder builder = new StringBuilder();
        builder.append("(");
        for (int i = 0; i < components.length; i++) {
            if (i > 0) {
                builder.append(", ");
            }
            builder.append(String.format("%.4f", components[i]));
        }
        builder.append(")");
        return builder.toString();
    }
}