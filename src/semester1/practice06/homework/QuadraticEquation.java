package semester1.practice06.homework;

public class QuadraticEquation {

    private double a;
    private double b;
    private double c;

    public void setCoefficients(double a, double b, double c) {

        if (a == 0) {
            System.out.println("Помилка: коефіцієнт a не може дорівнювати 0");
            return;
        }

        this.a = a;
        this.b = b;
        this.c = c;
    }

    public double getDiscriminant() {

        return b * b - 4 * a * c;
    }

    public boolean hasRealRoots() {

        return getDiscriminant() >= 0;
    }

    public double getRoot1() {

        double discriminant = getDiscriminant();

        return (-b + Math.sqrt(discriminant)) / (2 * a);
    }

    public double getRoot2() {

        double discriminant = getDiscriminant();

        return (-b - Math.sqrt(discriminant)) / (2 * a);
    }

    public void printSolution() {

        double discriminant = getDiscriminant();

        System.out.println("Дискримінант: " + discriminant);

        if (discriminant > 0) {

            System.out.println("x1 = " + getRoot1());
            System.out.println("x2 = " + getRoot2());

        } else if (discriminant == 0) {

            System.out.println("x = " + getRoot1());

        } else {

            System.out.println("Дійсних коренів немає");
        }
    }
}
