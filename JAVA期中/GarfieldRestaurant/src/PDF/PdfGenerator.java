//2253206  韩明洋
package PDF;
import Food.Food;
import Order.Order;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;

import java.io.IOException;

public class PDFBoxPdfGenerator implements PdfGenerator {

    @Override
    public void generateOrderReport(Order order, String filePath) throws IOException {
        try (PDDocument document = new PDDocument()) {
            PDPage page = new PDPage();
            document.addPage(page);

            try (PDPageContentStream contentStream = new PDPageContentStream(document, page)) {
                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 18);
                contentStream.beginText();
                contentStream.newLineAtOffset(100, 750);
                contentStream.showText("Order Report");
                contentStream.endText();

                contentStream.setFont(PDType1Font.HELVETICA, 12);
                contentStream.beginText();
                contentStream.newLineAtOffset(100, 700);
                contentStream.showText("Order Details:");
                contentStream.endText();

                double totalPrice = 0.0;
                int totalCalories = 0;
                for (Food item : order.getItems()) {
                    contentStream.beginText();
                    contentStream.newLineAtOffset(100, 680 - (order.getItems().indexOf(item) * 20));
                    contentStream.showText(item.getName() + " - 价格: " + item.getPrice() + " 元 - 卡路里: " + item.getCalories() + " 卡");
                    contentStream.endText();
                    totalPrice += item.getPrice();
                    totalCalories += item.getCalories();
                }

                contentStream.beginText();
                contentStream.newLineAtOffset(100, 660 - (order.getItems().size() * 20));
                contentStream.showText("Discount Plan Applied: " + order.getDiscountPlan().getName());
                contentStream.endText();

                contentStream.beginText();
                contentStream.newLineAtOffset(100, 640 - (order.getItems().size() * 20));
                contentStream.showText("Total Price: " + totalPrice + " 元");
                contentStream.endText();

                contentStream.beginText();
                contentStream.newLineAtOffset(100, 620 - (order.getItems().size() * 20));
                contentStream.showText("Total Calories: " + totalCalories + " 卡");
                contentStream.endText();

                contentStream.beginText();
                contentStream.newLineAtOffset(100, 600 - (order.getItems().size() * 20));
                contentStream.showText("Detailed Descriptions:");
                contentStream.endText();

                for (Food item : order.getItems()) {
                    contentStream.beginText();
                    contentStream.newLineAtOffset(100, 580 - ((order.getItems().size() + 1) * 20 + order.getItems().indexOf(item) * 20));
                    contentStream.showText("Name: " + item.getName());
                    contentStream.newLineAtOffset(0, -20);
                    contentStream.showText("Weight: " + item.getWeight() + "g");
                    contentStream.newLineAtOffset(0, -20);
                    contentStream.showText("Price: " + item.getPrice() + " 元");
                    contentStream.newLineAtOffset(0, -20);
                    contentStream.showText("Calories: " + item.getCalories() + " 卡");
                    contentStream.newLineAtOffset(0, -20);
                    contentStream.showText("Features: " + item.getFeatures());
                    contentStream.newLineAtOffset(0, -20);
                    contentStream.showText("Image: " + item.getImage());
                    contentStream.newLineAtOffset(0, -20);
                    contentStream.showText(" ");
                    contentStream.endText();
                }
            }

            document.save(filePath);
        }
    }
}

