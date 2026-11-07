import java.util.Locale;

public class Main {
    private static final int N = 10;
    private static final int M = 10;

    public static void main(String[] args) {
        for(int i=0; i<N;i++) {
            System.out.print("[");
            for(int j=0; j < M;j++) {
                System.out.print(" [] ");
            }
            System.out.println("]");
        }

    }
}