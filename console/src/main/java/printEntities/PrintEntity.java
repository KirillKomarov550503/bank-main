package printEntities;

import dev3.bank.entity.News;

public class PrintEntity {

    public static void printNews(News news) {
        System.out.println("" +
                "Date: " + news.getDate() + "\t" +
                "Title: " + news.getTitle() + "\t" +
                "Text: " + news.getText());
    }
}
