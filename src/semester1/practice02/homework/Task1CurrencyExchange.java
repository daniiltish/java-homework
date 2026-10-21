package semester1.practice02.homework;

public class Task1CurrencyExchange {
    public static void main(String[] args) {
        double usdAmount = 250.0;
        final double EXCHANGE_RATE = 41.25;
        final double COMMISSION_PERCENT = 1.5;
        double uah = usdAmount * EXCHANGE_RATE;
        double commissionuah = uah * COMMISSION_PERCENT / 100;
        double finalUAH = uah - commissionuah;
        long roundedUAH = Math.round (finalUAH);
        System.out.println("\n\n\n=============(КВИТАНЦІЯ ОБМІНУ)==============");
        System.out.println("сума в доларах:\t\t\t\t" + usdAmount + "      USD");
        System.out.println("курс в грн:\t\t\t\t\t" + EXCHANGE_RATE + "    UAH/USD");
        System.out.println("сума до комісії:\t\t\t" + uah + "    UAH");
        System.out.println("комісія:\t\t\t\t\t" + COMMISSION_PERCENT + "%" + "       ---");
        System.out.println("сума комісій:\t\t\t    " + commissionuah + "   UAH");
        System.out.println("сума після комісії:\t\t\t" + finalUAH + " UAH");
        System.out.println("---------------------------------------------");
        System.out.println("до видачі:\t\t\t\t\t" + roundedUAH + "      UAH");
        System.out.println("=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=");
    }
}
