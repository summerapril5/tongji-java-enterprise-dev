//2253206  韩明洋
package Data;
import Food.Food;
import Food.ConcreteFood;
import Food.FriedChicken;
import Food.FrenchFries;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.File;
import java.io.IOException;

import java.util.ArrayList;
import java.util.List;

public class DataRetriever {
    public static List<Food> retrieveData(String filePath) throws IOException {
        // 读取文件
        File input = new File(filePath);
        System.out.println("正在读取文件: " + filePath);

        Document doc = Jsoup.parse(input, "UTF-8");
        Elements foodElements = doc.select(".pizza, .french-fries, .fried-chicken");
        // 遍历每个元素
        List<Food> foods = new ArrayList<>();
        for (Element element : foodElements) {
            String name = element.select("h2").text();
            int weight = Integer.parseInt(element.select("p:contains(Weight)").text().replace("Weight: ", "").replace("g", "").trim());
            double price = Double.parseDouble(element.select("p.price").text().replace("$", "").trim());
            String features = element.select("p.features").text().replace("Features: ", "").trim();
            int calories = Integer.parseInt(element.select("p:contains(Calories)").text().replace("Calories: ", "").replace("kcal", "").trim());
            String image = element.select("img").attr("src");

            ConcreteFood food = new ConcreteFood(name, weight, price, features, calories, image);
            foods.add(food);

            System.out.println("读取到食物: " + food.getName() + " - 重量: " + food.getWeight() + "g - 价格: " + food.getPrice() + "元 - 特征: " + food.getFeatures() + " - 卡路里: " + food.getCalories() + "卡 - 图片: " + food.getImage());
        }

        System.out.println("共读取到 " + foods.size() + " 种食物。");
        return foods;
    }
}





