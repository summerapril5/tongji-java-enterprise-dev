//2253206  韩明洋
package Food;

public class Pizza extends Food {
    private double radius;

    public Pizza(String name, double weight, double price, String features, int calories, String image, double radius) {
        super(name, weight, price, features, calories, image);
        this.radius = radius;
    }

    public double getRadius() {
        return radius;
    }

    @Override
    public String toString() {
        return super.toString() + ", radius=" + radius + '}';
    }
}