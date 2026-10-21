package semester1.practice03.homework;

import java.util.Scanner;
public class Task2DeliveryService {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("=== ТАРИФІКАТОР СЛУЖБИ ДОСТАВКИ ===");

        System.out.print("Введіть вагу відправлення (кг): ");
        double weight = scanner.nextDouble();

        System.out.print("Введіть відстань транспортування (км): ");
        int distance = scanner.nextInt();

        System.out.print("Оберіть пункт призначення (1 - Відділення, 2 - Поштомат, 3 - Кур'єр): ");
        int deliveryType = scanner.nextInt();

        scanner.nextLine();

        System.out.print("Чи є у вас карта Premium? (так/ні): ");
        String premiumAnswer = scanner.nextLine();

        if (weight <= 0 || weight > 50) {
            System.out.println("Помилка: вага повинна бути більше 0 і не більше 50 кг.");
            scanner.close();
            return;
        }

        if (distance <= 0) {
            System.out.println("Помилка: відстань повинна бути більше 0 км.");
            scanner.close();
            return;
        }

        double baseTariff = switch (deliveryType) {
            case 1 -> 50.0;

            case 2 -> {
                if (weight > 15.0) {
                    System.out.println("Помилка: поштомат не приймає посилки важчі за 15 кг.");
                    yield -1.0;
                } else {
                    yield 60.0;
                }
            }

            case 3 -> 100.0;

            default -> -1.0;
        };

        if (baseTariff < 0) {
            System.out.println("Помилка: некоректний тип доставки.");
            scanner.close();
            return;
        }

        double distanceFee;

        if (distance <= 50) {
            distanceFee = 0.0;
        } else if (distance <= 200) {
            distanceFee = 35.0;
        } else {
            distanceFee = 80.0;
        }

        boolean isPremium = premiumAnswer.equalsIgnoreCase("так");

        double totalBeforeDiscount = baseTariff + distanceFee;

        double finalPrice = isPremium
                ? totalBeforeDiscount * 0.80
                : totalBeforeDiscount;

        String deliveryName = switch (deliveryType) {
            case 1 -> "Відділення";
            case 2 -> "Поштомат";
            case 3 -> "Кур'єр";
            default -> "Невідомо";
        };

        System.out.println();
        System.out.println("------------- НАКЛАДНА ДОСТАВКИ -------------");
        System.out.println("Тип доставки: " + deliveryName);
        System.out.println("Базовий тариф: " + baseTariff + " грн");
        System.out.println("Доплата за відстань: " + distanceFee + " грн");
        System.out.println("Сума до знижки: " + totalBeforeDiscount + " грн");
        System.out.println("Статус клієнта: " + (isPremium ? "Premium (-20%)" : "Звичайний"));
        System.out.println("---------------------------------------------");
        System.out.println("РАЗОМ ДО СПЛАТИ: " + finalPrice + " грн");

        scanner.close();
    }
}
