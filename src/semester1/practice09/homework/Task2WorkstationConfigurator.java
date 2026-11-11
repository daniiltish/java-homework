package semester1.practice09.homework;

import java.util.Arrays;

public class Task2WorkstationConfigurator {

    public static void main(String[] args) {

        Motherboard motherboard =
                new Motherboard("ASUS ROG B650", "AM5", 4);

        PowerSupplyUnit psu =
                new PowerSupplyUnit("Seasonic 750W Gold", 750, 0.90);

        WorkstationPC pc =
                new WorkstationPC(
                        "DeepLearning-Rig",
                        motherboard,
                        psu
                );

        ProcessorUnit badCpu =
                new ProcessorUnit(
                        "Intel Core i9",
                        25000,
                        "LGA1700",
                        24,
                        5.8,
                        200
                );

        ProcessorUnit goodCpu =
                new ProcessorUnit(
                        "AMD Ryzen 9 7950X",
                        28000,
                        "AM5",
                        16,
                        5.7,
                        170
                );

        GraphicsAccelerator gpu =
                new GraphicsAccelerator(
                        "NVIDIA RTX 4080 Super",
                        45000,
                        16,
                        320,
                        320
                );

        int[] speedSamples = {7200, 7350, 7450};

        NvmeSsdDrive ssd =
                new NvmeSsdDrive(
                        "Samsung 990 Pro 2TB",
                        5900,
                        2000,
                        7450,
                        speedSamples
                );

        System.out.println(
                "================ КОНФІГУРАТОР РОБОЧОЇ СТАНЦІЇ ================"
        );

        System.out.println("\n[ПЕРЕВІРКА СУМІСНОСТІ]:");

        pc.installComponent(badCpu);
        pc.installComponent(goodCpu);
        pc.installComponent(gpu);
        pc.installComponent(ssd);

        speedSamples[0] = 1;

        System.out.println();
        System.out.println(pc);

        System.out.println(
                "\nПеревірка захисного копіювання SSD:"
        );

        System.out.println(
                Arrays.toString(ssd.getSpeedSamples())
        );
    }
}


class Motherboard {

    private final String model;
    private final String socketType;
    private final int maxExpansionSlots;

    public Motherboard(
            String model,
            String socketType,
            int maxExpansionSlots
    ) {
        this.model = model;
        this.socketType = socketType;

        this.maxExpansionSlots =
                Math.max(
                        1,
                        Math.min(8, maxExpansionSlots)
                );
    }

    public String getModel() {
        return model;
    }

    public String getSocketType() {
        return socketType;
    }

    public int getMaxExpansionSlots() {
        return maxExpansionSlots;
    }

    public boolean isSocketCompatible(
            String cpuSocket
    ) {
        return cpuSocket != null
                && socketType.equalsIgnoreCase(cpuSocket);
    }

    @Override
    public String toString() {
        return model + " (Socket: " + socketType + ")";
    }
}


class PowerSupplyUnit {

    private final String model;
    private final int maxOutputWatts;
    private final double efficiencyCoefficient;

    public PowerSupplyUnit(
            String model,
            int maxOutputWatts,
            double efficiencyCoefficient
    ) {
        this.model = model;
        this.maxOutputWatts =
                Math.max(1, maxOutputWatts);

        this.efficiencyCoefficient =
                Math.max(
                        0.80,
                        Math.min(0.96, efficiencyCoefficient)
                );
    }

    public int getSafeContinuousWatts() {
        return (int) (maxOutputWatts * 0.85);
    }

    public double calculateWallPowerDraw(
            int internalLoadWatts
    ) {
        return internalLoadWatts
                / efficiencyCoefficient;
    }

    public double getEfficiencyCoefficient() {
        return efficiencyCoefficient;
    }

    @Override
    public String toString() {
        return model;
    }
}


abstract class AbstractHardwareComponent {

    private final String modelName;
    private final double priceUah;

    public AbstractHardwareComponent(
            String modelName,
            double priceUah
    ) {
        this.modelName = modelName;
        this.priceUah =
                Math.max(0, priceUah);
    }

    public String getModelName() {
        return modelName;
    }

    public double getPriceUah() {
        return priceUah;
    }

    public abstract int getPowerWatts();

    public abstract double getBenchmarkScore();

    public abstract String getCategory();

    @Override
    public String toString() {
        return String.format(
                "[%s] %s (%d Вт | Score: %.2f)",
                getCategory(),
                modelName,
                getPowerWatts(),
                getBenchmarkScore()
        );
    }
}


class ProcessorUnit
        extends AbstractHardwareComponent {

    private final String socket;
    private final int cores;
    private final double boostClockGhz;
    private final int tdpWatts;

    public ProcessorUnit(
            String modelName,
            double priceUah,
            String socket,
            int cores,
            double boostClockGhz,
            int tdpWatts
    ) {
        super(modelName, priceUah);

        this.socket = socket;
        this.cores = Math.max(1, cores);
        this.boostClockGhz =
                Math.max(0, boostClockGhz);
        this.tdpWatts =
                Math.max(0, tdpWatts);
    }

    public String getSocket() {
        return socket;
    }

    @Override
    public int getPowerWatts() {
        return tdpWatts;
    }

    @Override
    public double getBenchmarkScore() {
        return cores
                * boostClockGhz
                * 120.0;
    }

    @Override
    public String getCategory() {
        return "CPU";
    }
}


class GraphicsAccelerator
        extends AbstractHardwareComponent {

    private final int vramGb;
    private final int tensorCores;
    private final int boardPowerWatts;

    public GraphicsAccelerator(
            String modelName,
            double priceUah,
            int vramGb,
            int tensorCores,
            int boardPowerWatts
    ) {
        super(modelName, priceUah);

        this.vramGb =
                Math.max(0, vramGb);

        this.tensorCores =
                Math.max(0, tensorCores);

        this.boardPowerWatts =
                Math.max(0, boardPowerWatts);
    }

    @Override
    public int getPowerWatts() {
        return boardPowerWatts;
    }

    @Override
    public double getBenchmarkScore() {
        return vramGb * 250.0
                + tensorCores * 1.5;
    }

    @Override
    public String getCategory() {
        return "GPU";
    }
}


class NvmeSsdDrive
        extends AbstractHardwareComponent {

    private final int capacityGb;
    private final int readSpeedMbSec;
    private final int[] speedSamples;

    public NvmeSsdDrive(
            String modelName,
            double priceUah,
            int capacityGb,
            int readSpeedMbSec,
            int[] speedSamples
    ) {
        super(modelName, priceUah);

        this.capacityGb =
                Math.max(0, capacityGb);

        this.readSpeedMbSec =
                Math.max(0, readSpeedMbSec);

        this.speedSamples =
                speedSamples == null
                        ? new int[0]
                        : Arrays.copyOf(
                        speedSamples,
                        speedSamples.length
                );
    }

    public int[] getSpeedSamples() {
        return Arrays.copyOf(
                speedSamples,
                speedSamples.length
        );
    }

    @Override
    public int getPowerWatts() {
        return 12;
    }

    @Override
    public double getBenchmarkScore() {
        return readSpeedMbSec * 0.25;
    }

    @Override
    public String getCategory() {
        return "NVMe SSD";
    }
}


class WorkstationPC {

    private final String buildName;
    private final Motherboard motherboard;
    private final PowerSupplyUnit psu;

    private final AbstractHardwareComponent[] components;
    private int componentCount;

    public WorkstationPC(
            String buildName,
            Motherboard motherboard,
            PowerSupplyUnit psu
    ) {
        this.buildName = buildName;
        this.motherboard = motherboard;
        this.psu = psu;

        this.components =
                new AbstractHardwareComponent[
                        motherboard.getMaxExpansionSlots()
                        ];

        this.componentCount = 0;
    }

    public boolean installComponent(
            AbstractHardwareComponent comp
    ) {

        if (comp == null) {
            return false;
        }

        if (componentCount
                >= components.length) {

            System.out.println(
                    " Відмова: немає вільних слотів!"
            );

            return false;
        }

        if (comp instanceof ProcessorUnit) {

            ProcessorUnit cpu =
                    (ProcessorUnit) comp;

            if (!motherboard.isSocketCompatible(
                    cpu.getSocket()
            )) {

                System.out.println(
                        " Відмова: Процесор "
                                + cpu.getModelName()
                                + " ("
                                + cpu.getSocket()
                                + ") несумісний із платою "
                                + motherboard.getModel()
                                + " (Socket "
                                + motherboard.getSocketType()
                                + ")!"
                );

                return false;
            }
        }

        int newPower =
                getTotalPowerWatts()
                        + comp.getPowerWatts();

        if (newPower
                > psu.getSafeContinuousWatts()) {

            System.out.println(
                    " Відмова: компонент "
                            + comp.getModelName()
                            + " перевищує безпечний ліміт БЖ!"
            );

            return false;
        }

        components[componentCount] = comp;
        componentCount++;

        System.out.println(
                " Встановлено: " + comp
        );

        return true;
    }

    public int getTotalPowerWatts() {

        int total = 0;

        for (int i = 0;
             i < componentCount;
             i++) {

            total +=
                    components[i]
                            .getPowerWatts();
        }

        return total;
    }

    public double getTotalSystemScore() {

        double total = 0;

        for (int i = 0;
             i < componentCount;
             i++) {

            total +=
                    components[i]
                            .getBenchmarkScore();
        }

        return total;
    }

    public double getTotalBuildPrice() {

        double total = 0;

        for (int i = 0;
             i < componentCount;
             i++) {

            total +=
                    components[i]
                            .getPriceUah();
        }

        return total;
    }

    @Override
    public String toString() {

        return String.format(
                """
                [ПАСПОРТ ЗБІРКИ '%s']:
                Материнська плата: %s
                Блок живлення: %s
                Встановлено компонентів: %d/%d
                Внутрішнє споживання: %d Вт
                Безпечний ліміт БЖ: %d Вт
                Споживання з розетки: %.2f Вт
                Сумарний індекс продуктивності: %.2f
                Загальна вартість конфігурації: %.2f грн
                """,
                buildName,
                motherboard,
                psu,
                componentCount,
                components.length,
                getTotalPowerWatts(),
                psu.getSafeContinuousWatts(),
                psu.calculateWallPowerDraw(
                        getTotalPowerWatts()
                ),
                getTotalSystemScore(),
                getTotalBuildPrice()
        );
    }
}
