package semester1.practice06.homework;

public class Task1Fraction {

    public static void main(String[] args) {

        Fraction fraction1 = new Fraction();
        fraction1.setValues(8, 12);

        System.out.print("Перша дріб: ");
        fraction1.printFraction();

        fraction1.simplify();

        System.out.print("Після скорочення: ");
        fraction1.printFraction();

        System.out.println(
                "Десяткове значення: "
                        + fraction1.toDecimal()
        );

        System.out.println();

        Fraction fraction2 = new Fraction();
        fraction2.setValues(1, 3);

        System.out.print("Друга дріб: ");
        fraction2.printFraction();

        System.out.println();

        Fraction sum = fraction1.add(fraction2);

        System.out.print("Сума дробів: ");
        sum.printFraction();
    }
}
