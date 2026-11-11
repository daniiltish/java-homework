package semester1.practice10.homework;

public class Task2SmartPaymentAndAuditGateway {

    public static void main(String[] args) {

        AbstractPaymentTransaction[] ledger = {
                new PremiumCardPayment(
                        "TX-101",
                        20000,
                        "**** 4581",
                        0.03
                ),

                new CryptoUsdtPayment(
                        "TX-102",
                        8500,
                        "0x9f8a...c4",
                        18
                ),

                new TaxTreasuryPayment(
                        "TX-103",
                        5000,
                        "TAX-2026"
                )
        };

        runAudit(ledger);
    }

    public static void runAudit(
            AbstractPaymentTransaction[] ledger
    ) {

        double totalTurnover = 0;
        double totalFees = 0;
        double totalCashback = 0;

        System.out.println(
                "================ ПРОЦЕСИНГОВИЙ ЦЕНТР FINTECH ================"
        );

        for (AbstractPaymentTransaction tx : ledger) {

            if (tx.process()) {

                totalTurnover += tx.getBaseAmountUah();
                totalFees += tx.calculateFee();

                System.out.printf(
                        " [%s | %s] Сума: %.2f грн | Комісія: %.2f грн | Всього: %.2f грн%n",
                        tx.getTxId(),
                        tx.getPaymentChannel(),
                        tx.getBaseAmountUah(),
                        tx.calculateFee(),
                        tx.getTotalCharged()
                );
            }
        }

        System.out.println("\n[АУДИТ МОЖЛИВОСТЕЙ]");

        for (AbstractPaymentTransaction tx : ledger) {

            if (tx instanceof CashbackCapable cashback) {

                double value =
                        cashback.calculateCashback(
                                tx.getBaseAmountUah()
                        );

                totalCashback += value;

                System.out.printf(
                        " Cashback %s: %.2f грн%n",
                        tx.getTxId(),
                        value
                );
            }

            if (tx instanceof BlockchainVerifiable blockchain) {

                System.out.printf(
                        " %s: Hash=%s | Confirmations=%d | Finalized=%s%n",
                        tx.getTxId(),
                        blockchain.getTxHash(),
                        blockchain.getConfirmations(),
                        blockchain.isFinalized()
                );
            }

            if (tx instanceof Refundable refundable) {

                boolean refunded =
                        refundable.requestRefund(
                                "Тестове повернення"
                        );

                System.out.printf(
                        " Refund %s: %s%n",
                        tx.getTxId(),
                        refunded
                );
            }
        }

        System.out.println("\n[ПІДСУМКИ]");

        System.out.printf(
                "Сумарний оборот: %.2f грн%n",
                totalTurnover
        );

        System.out.printf(
                "Сумарна комісія: %.2f грн%n",
                totalFees
        );

        System.out.printf(
                "Сумарний кешбек: %.2f грн%n",
                totalCashback
        );
    }
}


interface CashbackCapable {

    double MAX_CASHBACK_UAH = 500.0;

    double getCashbackRate();

    default double calculateCashback(
            double baseAmount
    ) {
        return Math.min(
                MAX_CASHBACK_UAH,
                baseAmount * getCashbackRate()
        );
    }
}


interface Refundable {

    boolean requestRefund(String reason);

    boolean isRefunded();
}


interface BlockchainVerifiable {

    String getTxHash();

    int getConfirmations();

    default boolean isFinalized() {
        return getConfirmations() >= 12;
    }
}


abstract class AbstractPaymentTransaction {

    private final String txId;
    private final double baseAmountUah;
    private boolean processed;

    public AbstractPaymentTransaction(
            String txId,
            double baseAmountUah
    ) {
        this.txId = txId;
        this.baseAmountUah = baseAmountUah;
    }

    public String getTxId() {
        return txId;
    }

    public double getBaseAmountUah() {
        return baseAmountUah;
    }

    public boolean isProcessed() {
        return processed;
    }

    public abstract double calculateFee();

    public abstract String getPaymentChannel();

    public final boolean process() {

        if (baseAmountUah <= 0 || processed) {
            return false;
        }

        processed = true;
        return true;
    }

    public final double getTotalCharged() {
        return baseAmountUah + calculateFee();
    }
}


class PremiumCardPayment
        extends AbstractPaymentTransaction
        implements CashbackCapable, Refundable {

    private final String maskedPan;
    private final double cashbackRate;
    private boolean refunded;

    public PremiumCardPayment(
            String txId,
            double amount,
            String maskedPan,
            double cashbackRate
    ) {
        super(txId, amount);

        this.maskedPan = maskedPan;
        this.cashbackRate = cashbackRate;
    }

    @Override
    public double calculateFee() {
        return getBaseAmountUah() * 0.01;
    }

    @Override
    public String getPaymentChannel() {
        return "Visa Infinite";
    }

    @Override
    public double getCashbackRate() {
        return cashbackRate;
    }

    @Override
    public boolean requestRefund(String reason) {

        if (isProcessed() && !refunded) {
            refunded = true;
            return true;
        }

        return false;
    }

    @Override
    public boolean isRefunded() {
        return refunded;
    }
}


class CryptoUsdtPayment
        extends AbstractPaymentTransaction
        implements CashbackCapable, BlockchainVerifiable {

    private final String txHash;
    private final int confirmations;

    public CryptoUsdtPayment(
            String txId,
            double amount,
            String txHash,
            int confirmations
    ) {
        super(txId, amount);

        this.txHash = txHash;
        this.confirmations = confirmations;
    }

    @Override
    public double calculateFee() {
        return 40.0;
    }

    @Override
    public String getPaymentChannel() {
        return "USDT TRC-20";
    }

    @Override
    public double getCashbackRate() {
        return 0.01;
    }

    @Override
    public String getTxHash() {
        return txHash;
    }

    @Override
    public int getConfirmations() {
        return confirmations;
    }
}


final class TaxTreasuryPayment
        extends AbstractPaymentTransaction
        implements Refundable {

    private final String taxCode;
    private boolean refunded;

    public TaxTreasuryPayment(
            String txId,
            double amount,
            String taxCode
    ) {
        super(txId, amount);
        this.taxCode = taxCode;
    }

    @Override
    public double calculateFee() {
        return 0.0;
    }

    @Override
    public String getPaymentChannel() {
        return "ДержКазначейство";
    }

    @Override
    public boolean requestRefund(String reason) {

        if (isProcessed() && !refunded) {
            refunded = true;
            return true;
        }

        return false;
    }

    @Override
    public boolean isRefunded() {
        return refunded;
    }
}