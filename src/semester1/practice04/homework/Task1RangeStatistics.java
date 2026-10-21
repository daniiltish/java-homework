package semester1.practice04.homework;

import java.util.Scanner;

public class Task1RangeStatistics {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        int n;

        do {
            System.out.print("Введіть N: ");
            n = scanner.nextInt();

            if (n <= 0) {
                System.out.println("Помилка: N має бути більше 0!");
            }

        } while (n <= 0);

        int totalSum = 0;
        int evenSum = 0;
        int oddSum = 0;
        int evenCount = 0;
        int oddCount = 0;

        for (int i = 1; i <= n; i++) {

            totalSum += i;

            if (i % 2 == 0) {
                evenSum += i;
                evenCount++;
            } else {
                oddSum += i;
                oddCount++;
            }
        }

        double average = (double) totalSum / n;

        System.out.println();
        System.out.println("=== РЕЗУЛЬТАТИ ===");
        System.out.println("Загальна сума: " + totalSum);
        System.out.println("Сума парних: " + evenSum);
        System.out.println("Кількість парних: " + evenCount);
        System.out.println("Сума непарних: " + oddSum);
        System.out.println("Кількість непарних: " + oddCount);
        System.out.println("Середнє арифметичне: " + average);

        scanner.close();
    }
}
