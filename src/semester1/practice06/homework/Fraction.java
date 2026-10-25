package semester1.practice06.homework;

public class Fraction {

    private int numerator;
    private int denominator;

    public void setValues(int num, int den) {

        if (den == 0) {
            System.out.println("Помилка: знаменник не може дорівнювати 0");
            return;
        }

        if (den < 0) {
            num = -num;
            den = -den;
        }

        numerator = num;
        denominator = den;
    }

    private int gcd(int a, int b) {

        a = Math.abs(a);
        b = Math.abs(b);

        while (b != 0) {
            int temp = b;
            b = a % b;
            a = temp;
        }

        return a;
    }

    public void simplify() {

        int divisor = gcd(numerator, denominator);

        numerator = numerator / divisor;
        denominator = denominator / divisor;
    }

    public double toDecimal() {

        return (double) numerator / denominator;
    }

    public Fraction add(Fraction other) {

        Fraction result = new Fraction();

        int newNumerator =
                numerator * other.denominator
                        + other.numerator * denominator;

        int newDenominator =
                denominator * other.denominator;

        result.setValues(newNumerator, newDenominator);
        result.simplify();

        return result;
    }

    public void printFraction() {

        System.out.println(numerator + "/" + denominator);
    }
}
