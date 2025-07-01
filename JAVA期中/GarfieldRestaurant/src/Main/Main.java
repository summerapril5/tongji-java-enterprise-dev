//2253206  韩明洋
package Main;

import Data.DataRetriever;
import Display.FoodDisplay;
import Food.Food;

import java.io.IOException;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        try {
            // 从数据文件中检索食物数据
            List<Food> foods = DataRetriever.retrieveData(".//food.html");

            // 创建 FoodDisplay 实例
            FoodDisplay foodDisplay = new FoodDisplay(foods);

            // 显示食物列表
            System.out.println("显示所有食物列表：");
            foodDisplay.displayFoodList();

            // 显示排序后的食物列表
            System.out.println("显示排序后的食物列表：");
            foodDisplay.displaySortedFoodsMenu();

            // 创建订单
            System.out.println("创建订单：");
            foodDisplay.createOrder();

        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("读取数据文件时发生错误。");
        }
    }
}
