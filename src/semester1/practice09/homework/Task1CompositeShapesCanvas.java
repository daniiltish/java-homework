package semester1.practice09.homework;

public class Task1CompositeShapesCanvas {

    public static void main(String[] args) {

        Point2D originalPoint = new Point2D(0, 0);

        Circle2D circle = new Circle2D(
                "Коло-А",
                originalPoint,
                5
        );

        Rectangle2D rectangle = new Rectangle2D(
                "Плита-Б",
                new Point2D(10, 5),
                4,
                6
        );

        RightTriangle2D triangle = new RightTriangle2D(
                "Клин-В",
                new Point2D(-3, 2),
                3,
                4
        );

        GeometryCanvas canvas =
                new GeometryCanvas("Інженерне креслення №1", 5);

        canvas.addShape(circle);
        canvas.addShape(rectangle);
        canvas.addShape(triangle);

        // Проверка защитного копирования
        originalPoint.translate(100, 100);

        System.out.println(canvas);

        AbstractShape2D largest = canvas.findLargestShape();

        if (largest != null) {
            System.out.printf(
                    "Найбільша фігура: %s (S = %.2f)%n",
                    largest.getName(),
                    largest.getArea()
            );
        }

        System.out.println(
                "-> Переміщуємо всі фігури на (+5.0; -2.0)..."
        );

        canvas.moveAllShapes(5, -2);

        System.out.println(
                "Новий центр '" +
                        circle.getName() +
                        "': " +
                        circle.getAnchorPoint()
        );
    }
}


class Point2D {

    private double x;
    private double y;

    public Point2D(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public Point2D(Point2D other) {
        this.x = other.x;
        this.y = other.y;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public void translate(double dx, double dy) {
        x = x + dx;
        y = y + dy;
    }

    public double distanceTo(Point2D other) {
        double dx = other.x - x;
        double dy = other.y - y;

        return Math.sqrt(dx * dx + dy * dy);
    }

    @Override
    public String toString() {
        return String.format(
                "(%.2f; %.2f)",
                x,
                y
        );
    }
}


abstract class AbstractShape2D {

    private final String name;
    private final Point2D anchorPoint;

    public AbstractShape2D(
            String name,
            Point2D anchorPoint
    ) {
        this.name = name;

        this.anchorPoint =
                (anchorPoint != null)
                        ? new Point2D(anchorPoint)
                        : new Point2D(0, 0);
    }

    public String getName() {
        return name;
    }

    public Point2D getAnchorPoint() {
        return new Point2D(anchorPoint);
    }

    public void moveBy(double dx, double dy) {
        anchorPoint.translate(dx, dy);
    }

    public abstract double getArea();

    public abstract double getPerimeter();

    @Override
    public String toString() {
        return String.format(
                "%s[name='%s', center=%s | S=%.2f, P=%.2f]",
                getClass().getSimpleName(),
                name,
                anchorPoint,
                getArea(),
                getPerimeter()
        );
    }
}


class Circle2D extends AbstractShape2D {

    private double radius;

    public Circle2D(
            String name,
            Point2D anchorPoint,
            double radius
    ) {
        super(name, anchorPoint);

        this.radius =
                radius > 0 ? radius : 1.0;
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
                "Circle2D[name='%s', center=%s, r=%.2f | S=%.2f, P=%.2f]",
                getName(),
                getAnchorPoint(),
                radius,
                getArea(),
                getPerimeter()
        );
    }
}


class Rectangle2D extends AbstractShape2D {

    private double width;
    private double height;

    public Rectangle2D(
            String name,
            Point2D anchorPoint,
            double width,
            double height
    ) {
        super(name, anchorPoint);

        this.width =
                width > 0 ? width : 1.0;

        this.height =
                height > 0 ? height : 1.0;
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
                "Rectangle2D[name='%s', center=%s, %.2fx%.2f | S=%.2f, P=%.2f]",
                getName(),
                getAnchorPoint(),
                width,
                height,
                getArea(),
                getPerimeter()
        );
    }
}


class RightTriangle2D extends AbstractShape2D {

    private double legA;
    private double legB;

    public RightTriangle2D(
            String name,
            Point2D anchorPoint,
            double legA,
            double legB
    ) {
        super(name, anchorPoint);

        this.legA =
                legA > 0 ? legA : 1.0;

        this.legB =
                legB > 0 ? legB : 1.0;
    }

    public double getHypotenuse() {
        return Math.sqrt(
                legA * legA + legB * legB
        );
    }

    @Override
    public double getArea() {
        return 0.5 * legA * legB;
    }

    @Override
    public double getPerimeter() {
        return legA +
                legB +
                getHypotenuse();
    }

    @Override
    public String toString() {
        return String.format(
                "RightTriangle2D[name='%s', center=%s, a=%.2f, b=%.2f, c=%.2f | S=%.2f, P=%.2f]",
                getName(),
                getAnchorPoint(),
                legA,
                legB,
                getHypotenuse(),
                getArea(),
                getPerimeter()
        );
    }
}


class GeometryCanvas {

    private final String title;
    private final AbstractShape2D[] shapes;
    private int count;

    public GeometryCanvas(
            String title,
            int capacity
    ) {
        this.title = title;
        this.shapes =
                new AbstractShape2D[capacity];
        this.count = 0;
    }

    public boolean addShape(
            AbstractShape2D shape
    ) {
        if (shape == null ||
                count >= shapes.length) {
            return false;
        }

        shapes[count] = shape;
        count++;

        return true;
    }

    public double getTotalArea() {

        double total = 0;

        for (int i = 0; i < count; i++) {
            total += shapes[i].getArea();
        }

        return total;
    }

    public double getTotalPerimeter() {

        double total = 0;

        for (int i = 0; i < count; i++) {
            total += shapes[i].getPerimeter();
        }

        return total;
    }

    public AbstractShape2D findLargestShape() {

        if (count == 0) {
            return null;
        }

        AbstractShape2D largest =
                shapes[0];

        for (int i = 1; i < count; i++) {

            if (shapes[i].getArea()
                    > largest.getArea()) {

                largest = shapes[i];
            }
        }

        return largest;
    }

    public void moveAllShapes(
            double dx,
            double dy
    ) {
        for (int i = 0; i < count; i++) {
            shapes[i].moveBy(dx, dy);
        }
    }

    @Override
    public String toString() {

        String result =
                "================ ГЕОМЕТРИЧНЕ ПОЛОТНО ================\n";

        result +=
                "Полотно: '" +
                        title +
                        "' (Фігур: " +
                        count +
                        "/" +
                        shapes.length +
                        ")\n";

        for (int i = 0; i < count; i++) {
            result +=
                    (i + 1) +
                            ". " +
                            shapes[i] +
                            "\n";
        }

        result +=
                "Сумарна площа: " +
                        String.format(
                                "%.2f",
                                getTotalArea()
                        ) +
                        "\n";

        result +=
                "Сумарний периметр: " +
                        String.format(
                                "%.2f",
                                getTotalPerimeter()
                        );

        return result;
    }
}