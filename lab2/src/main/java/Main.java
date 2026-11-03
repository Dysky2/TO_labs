import java.util.Locale;

public class Main {

    static double[] cross(IVector a, IVector b) {
        if (a instanceof Vector3DDecorator d) return d.cross(b);
        if (a instanceof Vector3DInheritance i) return i.cross(b);
        return new Vector3DDecorator(a, 0).cross(b);
    }

    static String fmt(double[] v) {
        return String.format(Locale.US, "(%.1f, %.1f, %.1f)",
                v[0], v[1], v.length > 2 ? v[2] : 0.0);
    }

    public static void main(String[] args) {
        Locale.setDefault(Locale.US);

        IVector[] v = {
                new Polar2DAdapter(new Vector2D(3, 4)),
                new PolarInheritance2D(1, 2),
                new Vector3DDecorator(new Vector2D(2, -1), 5)
        };
        String[] n = {"A", "B", "C"};
        String[] desc = {"adapter 2D", "dziedziczenie 2D", "dekorator 3D"};

        System.out.println("=== WSPOLRZEDNE ===");
        System.out.printf("%-3s %-18s %-20s %-8s %-10s%n","", "typ", "kartezjanskie", "r", "phi [rad]");
        for (int i = 0; i < v.length; i++) {
            double[] c = v[i].getComponents();
            System.out.printf("%-3s %-18s %-20s %-8.3f %-10.3f%n",
                    n[i], desc[i], fmt(c), v[i].abs(), Math.atan2(c[1], c[0]));
        }

        System.out.println("\n=== ILOCZYN SKALARNY (a . b) ===");
        System.out.printf("%-6s", "a \\ b");
        for (String s : n) System.out.printf("%10s", s);
        System.out.println();
        for (int i = 0; i < v.length; i++) {
            System.out.printf("%-6s", n[i]);
            for (int j = 0; j < v.length; j++)
                System.out.printf("%10.2f", v[i].cdot(v[j]));
            System.out.println();
        }

        System.out.println("\n=== ILOCZYN WEKTOROWY (a x b) ===");
        System.out.printf("%-6s", "a \\ b");
        for (String s : n) System.out.printf("%-20s", s);
        System.out.println();
        for (int i = 0; i < v.length; i++) {
            System.out.printf("%-6s", n[i]);
            for (int j = 0; j < v.length; j++)
                System.out.printf("%-20s", fmt(cross(v[i], v[j])));
            System.out.println();
        }
    }
}