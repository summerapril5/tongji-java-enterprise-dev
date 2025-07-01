//2253206  韩明洋
package Order;

import Food.Food;

import java.util.List;

public class DiscountPlan {
    public double calculateDiscount(Order order) {
        List<Food> orderedFoods = order.getOrderedFoods();
        double discount = 0.0;

        // 示例折扣规则：如果订单中有超过3个披萨，打9折
        int pizzaCount = 0;
        for (Food food : orderedFoods) {
            if (food.getClass().getSimpleName().toLowerCase().contains("pizza")) {
                pizzaCount++;
            }
        }

        if (pizzaCount > 3) {
            discount = order.getTotalPrice() * 0.1; // 10% 的折扣
        }

        return discount;
    }

    public double applyDiscount(Order order) {
        double discount = calculateDiscount(order);
        double finalPrice = order.getTotalPrice() - discount;
        order.setTotalPrice(finalPrice);
        return finalPrice;
    }
}