//2253206  韩明洋
package Food;

public class FriedChicken extends Food {
    private int spiciness;

    public FriedChicken(String name, double weight, double price, String features, int calories, String image, int spiciness) {
        super(name, weight, price, features, calories, image);
        this.spiciness = spiciness;
    }

    public int getSpiciness() {
        return spiciness;
    }

    @Override
    public String toString() {
        return super.toString() + ", spiciness=" + spiciness + '}';
    }
}
