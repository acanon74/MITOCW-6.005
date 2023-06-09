package tutorial;

public class lecture2 {
    public static void main(String[] args) {
        AnotherMethod();
        printSquare(55);
        times(5, 8);
        System.out.println(Square3(99));

        int x = 5;
        System.out.println("main x=" + x);
        printSquare2(x);
        System.out.println("main x=" + x);

        test(4);
        test(5);
        test(6);

        int result = assignment(7.5, 35);
        System.out.println(result);
    }

    public static void AnotherMethod() {
        System.out.println("This is another method!");
    }

    public static void printSquare(int x) {
        System.out.println(x * x);
    }

    public static void times(double a, double b) {
        System.out.println(a * b);
    }

    public static double Square3(double x) {
        return x * x;
    }

    public static void printSquare2(int x) {
        System.out.println("printSquare x=" + x);
        x = x * x;
        System.out.println("printSquare x=" + x);
    }

    public static void test(int x) {
        if (x > 5) {
            System.out.println(x + " is > 5");
        } else if (x == 5) {
            System.out.println(x + " is equal to 5");
        } else {
            System.out.println(x + " is not > 5");
        }
    }

    public static int assignment(double pay, int hours) {

        if (pay < 8.0) {
            return -1;
        } else if (hours >= 60) {
            return -1;
        } else {

            int overtimeHours = 0;
            if (hours > 40) {
                overtimeHours = hours - 40;
                hours = 40;
            }

            int salary = (int) (hours * pay + overtimeHours * pay * 1.5);
            return salary;
        }
    }

}
