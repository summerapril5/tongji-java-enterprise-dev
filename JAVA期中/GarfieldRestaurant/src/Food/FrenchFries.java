//2253206  韩明洋
package Food;


public class FrenchFries extends Food {
    private double thickness;

    public FrenchFries(String name, double weight, double price, String features, int calories, String image, double thickness) {
        super(name, weight, price, features, calories, image);
        this.thickness = thickness;
    }

    public double getThickness() {
        return thickness;
    }

    @Override
    public String toString() {
        return super.toString() + ", thickness=" + thickness + '}';
    }
}