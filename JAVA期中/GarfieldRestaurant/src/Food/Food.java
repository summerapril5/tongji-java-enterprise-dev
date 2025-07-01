//2253206  韩明洋
package Food;

public abstract class Food {
    private String name;
    private double weight;
    private double price;
    private String features;
    private int calories;
    private String imageUrl;

    public Food(String name, double weight, double price, String features, int calories, String imageUrl) {
        this.name = name;
        this.weight = weight;
        this.price = price;
        this.features = features;
        this.calories = calories;
        this.imageUrl = imageUrl;
    }

    public String getName() {
        return name;
    }

    public double getWeight() {
        return weight;
    }

    public double getPrice() {
        return price;
    }

    public String getFeatures() {
        return features;
    }

    public int getCalories() {
        return calories;
    }

    public String getImage() {
        return imageUrl;
    }

    @Override
    public String toString() {
        return "Food.Food{" +
                "name='" + name + '\'' +
                ", weight=" + weight +
                ", price=" + price +
                ", features='" + features + '\'' +
                ", calories=" + calories +
                ", image='" + imageUrl + '\'' +
                '}';
    }
}
