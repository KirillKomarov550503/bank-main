package printEntities;

import dev3.bank.entity.GeneralNews;

public class PrintEntity {

    public static void printNews(GeneralNews generalNews) {
        System.out.println("" +
                "Date: " + generalNews.getDate() + "\t" +
                "Title: " + generalNews.getTitle() + "\t" +
                "Text: " + generalNews.getText());
    }
}
