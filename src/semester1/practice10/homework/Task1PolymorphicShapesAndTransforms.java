package semester1.practice10.homework;

public class Task1PolymorphicShapesAndTransforms {

    public static void main(String[] args) {

        AbstractShape2D[] shapes = {
                new CircleShape("Коло-Лінза", 0, 0, 5),
                new RectangleShape("Рамка-А", 2, 3, 4, 6),
                new ImmutableReferenceSquare("Еталон-Квадрат", 10, 10, 5)
        };

        System.out.println("================ ПОЛІМОРФНА ГЕОМЕТРІЯ ================");
        System.out.println("[ДО ТРАНСФОРМАЦІЇ]");

        for (AbstractShape2D shape : shapes) {
            System.out.println(shape);
        }

        System.out.printf("Сумарна площа: %.2f%n", calculateTotalArea(shapes));

        transformScene(shapes, 2.0, 45.0);

        System.out.println("\n[ПІСЛЯ ТРАНСФОРМАЦІЇ]");

        for (AbstractShape2D shape : shapes) {
            System.out.println(shape);
        }

        System.out.printf("Нова сумарна площа: %.2f%n", calculateTotalArea(shapes));
    }

    public static double calculateTotalArea(AbstractShape2D[] shapes) {
        double total = 0;

        for (AbstractShape2D shape : shapes) {
            total += shape.getArea();
        }

        return total;
    }

    public static void transformScene(
            AbstractShape2D[] shapes,
            double scaleFactor,
            double rotateDeg
    ) {
        for (AbstractShape2D shape : shapes) {

            if (shape instanceof Scalable2D scalable) {
                scalable.scale(scaleFactor);
            }

            if (shape instanceof Rotatable2D rotatable) {
                rotatable.rotate(rotateDeg);
            }
        }
    }
}


interface Scalable2D {

    double MIN_SCALE_FACTOR = 0.1;

    void scale(double factor);
}


interface Rotatable2D {

    void rotate(double deltaDegrees);

    double getRotationAngle();
}


abstract class AbstractShape2D {

    private final String name;
    private double centerX;
    private double centerY;

    public AbstractShape2D(
            String name,
            double centerX,
            double centerY
    ) {
        this.name = name;
        this.centerX = centerX;
        this.centerY = centerY;
    }

    public String getName() {
        return name;
    }

    public abstract double getArea();

    public abstract double getPerimeter();

    public final double getIsoperimetricQuotient() {
        double p = getPerimeter();

        if (p == 0) {
            return 0;
        }

        return 4 * Math.PI * getArea() / (p * p);
    }

    @Override
    public String toString() {
        return String.format(
                "%s['%s', center=(%.1f; %.1f) | S=%.2f, P=%.2f, Q=%.4f]",
                getClass().getSimpleName(),
                name,
                centerX,
                centerY,
                getArea(),
                getPerimeter(),
                getIsoperimetricQuotient()
        );
    }
}


class CircleShape extends AbstractShape2D implements Scalable2D {

    private double radius;

    public CircleShape(
            String name,
            double centerX,
            double centerY,
            double radius
    ) {
        super(name, centerX, centerY);
        this.radius = radius > 0 ? radius : 1;
    }

    @Override
    public void scale(double factor) {
        factor = Math.max(factor, MIN_SCALE_FACTOR);
        radius *= factor;
    }

    @Override
    public double getArea() {
        return Math.PI * radius * radius;
    }

    @Override
    public double getPerimeter() {
        return 2 * Math.PI * radius;
    }

    @Override
    public String toString() {
        return String.format(
                "CircleShape['%s', r=%.2f | S=%.2f, P=%.2f, Q=%.4f]",
                getName(),
                radius,
                getArea(),
                getPerimeter(),
                getIsoperimetricQuotient()
        );
    }
}


class RectangleShape extends AbstractShape2D
        implements Scalable2D, Rotatable2D {

    private double width;
    private double height;
    private double rotationAngle;

    public RectangleShape(
            String name,
            double centerX,
            double centerY,
            double width,
            double height
    ) {
        super(name, centerX, centerY);

        this.width = width > 0 ? width : 1;
        this.height = height > 0 ? height : 1;
    }

    @Override
    public void scale(double factor) {
        factor = Math.max(factor, MIN_SCALE_FACTOR);

        width *= factor;
        height *= factor;
    }

    @Override
    public void rotate(double deltaDegrees) {
        rotationAngle =
                (rotationAngle + deltaDegrees) % 360;

        if (rotationAngle < 0) {
            rotationAngle += 360;
        }
    }

    @Override
    public double getRotationAngle() {
        return rotationAngle;
    }

    @Override
    public double getArea() {
        return width * height;
    }

    @Override
    public double getPerimeter() {
        return 2 * (width + height);
    }

    @Override
    public String toString() {
        return String.format(
                "RectangleShape['%s', %.2fx%.2f, angle=%.1f° | S=%.2f, P=%.2f, Q=%.4f]",
                getName(),
                width,
                height,
                rotationAngle,
                getArea(),
                getPerimeter(),
                getIsoperimetricQuotient()
        );
    }
}


final class ImmutableReferenceSquare extends AbstractShape2D {

    private final double side;

    public ImmutableReferenceSquare(
            String name,
            double centerX,
            double centerY,
            double side
    ) {
        super(name, centerX, centerY);
        this.side = side > 0 ? side : 1;
    }

    @Override
    public double getArea() {
        return side * side;
    }

    @Override
    public double getPerimeter() {
        return 4 * side;
    }

    @Override
    public String toString() {
        return String.format(
                "ImmutableReferenceSquare['%s', side=%.2f | S=%.2f, P=%.2f, Q=%.4f]",
                getName(),
                side,
                getArea(),
                getPerimeter(),
                getIsoperimetricQuotient()
        );
    }
}
