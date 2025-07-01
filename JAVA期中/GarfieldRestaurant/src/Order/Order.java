//2253206  韩明洋
package Order;

import Food.Food;

import java.util.ArrayList;
import java.util.List;

public class Order {
    private List<Food> items;
    private double totalPrice;

    public Order() {
        this.items = new ArrayList<>();
        this.totalPrice = 0.0;
    }

    public void addItem(Food item) {
        items.add(item);
        totalPrice += item.getPrice();
    }

    public void removeItem(Food item) {
        if (items.remove(item)) {
            totalPrice -= item.getPrice();
        }
    }

    public List<Food> getItems() {
        return items;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public List<Food> getOrderedFoods() {
        return new ArrayList<>(items); // 返回一个新的列表，避免外部修改原始列表
    }
}