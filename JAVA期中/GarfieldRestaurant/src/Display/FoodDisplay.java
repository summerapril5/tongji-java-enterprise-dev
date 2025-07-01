//2253206  韩明洋
package Display;

import Food.Food;
import Order.Order;
import Order.DiscountPlan;
import java.util.*;


public class FoodDisplay {
    private List<Food> foods;

    public FoodDisplay(List<Food> foods) {
        this.foods = foods;
    }
    // 显示所有食物
    public void displayFoodList() {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("请选择要查看的食物类型：");
            System.out.println("1. 所有食物");
            System.out.println("2. 披萨");
            System.out.println("3. 炸鸡");
            System.out.println("4. 薯条");
            System.out.println("5. 退出");
            System.out.print("请输入选项（1-5）：");

            int choice = scanner.nextInt();
            scanner.nextLine(); // 消耗换行符

            switch (choice) {
                case 1:
                    displayAllFoods();
                    break;
                case 2:
                    displayFoodsByType("pizza");
                    break;
                case 3:
                    displayFoodsByType("fried_chicken");
                    break;
                case 4:
                    displayFoodsByType("french_fries");
                    break;
                case 5:
                    System.out.println("退出程序。");
                    return;
                default:
                    System.out.println("无效的选项，请重新输入。");
            }

            System.out.print("是否继续查看其他食物类型？(y/n): ");
            String continueChoice = scanner.nextLine();
            if (!continueChoice.equalsIgnoreCase("y")) {
                break;
            }
        }
    }

    private void displayAllFoods() {
        displaySortedFoods(foods, null);
    }
    // 显示按类型展示
    private void displayFoodsByType(String type) {
        List<Food> filteredFoods = new ArrayList<>();
        for (Food food : foods) {
            if (food.getClass().getSimpleName().toLowerCase().contains(type)) {
                filteredFoods.add(food);
            }
        }
        displaySortedFoods(filteredFoods, null);
    }
    // 显示排序
    private void displaySortedFoods(List<Food> foods, Comparator<Food> comparator) {
        if (comparator != null) {
            Collections.sort(foods, comparator);
        }

        for (int i = 0; i < foods.size(); i++) {
            Food food = foods.get(i);
            System.out.println((i + 1) + ". " + food.getName() + " - 重量: " + food.getWeight() + "g - 价格: " + food.getPrice() + "元 - 特征: " + food.getFeatures() + " - 卡路里: " + food.getCalories() + "卡 - 图片: " + food.getImage());
        }
    }
    // 显示排序菜单
    public void displaySortedFoodsMenu() {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("请选择排序方式：");
            System.out.println("1. 按卡路里升序");
            System.out.println("2. 按卡路里降序");
            System.out.println("3. 按价格升序");
            System.out.println("4. 按价格降序");
            System.out.println("5. 退出");
            System.out.print("请输入选项（1-5）：");

            int choice = scanner.nextInt();
            scanner.nextLine(); // 消耗换行符

            Comparator<Food> comparator = null;
            switch (choice) {
                case 1:
                    comparator = Comparator.comparingInt(Food::getCalories);
                    break;
                case 2:
                    comparator = Comparator.comparingInt(Food::getCalories).reversed();
                    break;
                case 3:
                    comparator = Comparator.comparingDouble(Food::getPrice);
                    break;
                case 4:
                    comparator = Comparator.comparingDouble(Food::getPrice).reversed();
                    break;
                case 5:
                    System.out.println("退出程序。");
                    return;
                default:
                    System.out.println("无效的选项，请重新输入。");
            }

            if (comparator != null) {
                displaySortedFoods(foods, comparator);
            }

            System.out.print("是否继续查看其他排序方式？(y/n): ");
            String continueChoice = scanner.nextLine();
            if (!continueChoice.equalsIgnoreCase("y")) {
                break;
            }
        }
    }
    // 创建订单
    public void createOrder() {
        Scanner scanner = new Scanner(System.in);
        Order order = new Order();
        DiscountPlan discountPlan = new DiscountPlan();

        while (true) {
            System.out.println("请选择要添加到订单的食物（输入编号）：");
            displayAllFoods();
            System.out.print("请输入编号（每次输入一个数字后回车，输入0结束）：");
            int choice = scanner.nextInt();
            scanner.nextLine(); // 消耗换行符

            if (choice == 0) {
                break;
            }

            if (choice > 0 && choice <= foods.size()) {
                Food selectedFood = foods.get(choice - 1);
                order.addItem(selectedFood);
                System.out.println(selectedFood.getName() + " 已添加到订单。");
            } else {
                System.out.println("无效的编号，请重新输入。");
            }
        }
        // 计算订单总价（含折扣）
        double finalPrice = discountPlan.applyDiscount(order);
        System.out.println("订单总价（含折扣）： " + finalPrice + " 元");
    }
}
