package semester1.practice06.homework;

public class Task2QuadraticEquation {

    public static void main(String[] args) {

        QuadraticEquation equation1 = new QuadraticEquation();
        equation1.setCoefficients(1, -5, 6);

        System.out.println("=== Рівняння 1 ===");
        equation1.printSolution();

        System.out.println();

        QuadraticEquation equation2 = new QuadraticEquation();
        equation2.setCoefficients(1, 2, 1);

        System.out.println("=== Рівняння 2 ===");
        equation2.printSolution();

        System.out.println();

        QuadraticEquation equation3 = new QuadraticEquation();
        equation3.setCoefficients(1, 2, 5);

        System.out.println("=== Рівняння 3 ===");
        equation3.printSolution();
    }
}
