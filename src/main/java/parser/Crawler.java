package parser;

import org.jsoup.Jsoup;
import org.jsoup.UnsupportedMimeTypeException;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

@Component
@Scope("prototype")
public class Crawler {
    static void processPage(String URL, DataBaseHandler db) throws Throwable {
        //check if the given URL is already in database
        String sql = "select * from record where URL = '" + URL + "'";
        ResultSet rs = db.runSql(sql);

        if (isExist(rs)) {
            //System.out.println("Existed link");
        } else if (isValidMime(URL)) {
            //store the URL to database to avoid parsing again
            sql = "INSERT INTO  `Crawler`.`record` " + "(`URL`) VALUES " + "(?);";
            PreparedStatement stmt = db.connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            stmt.setString(1, URL);
            stmt.execute();
            System.out.println("Link added to db: " + URL);

            //get useful information
            Document doc = Jsoup.connect(URL).get();

            //get all links and recursively call the processPage method
            Elements questions = doc.select("a[href]");
            for (Element link : questions) {
                if (link.attr("href").contains("mit.edu"))
                    processPage(link.attr("abs:href"), db);
            }


        }
    }

    private static boolean isExist(ResultSet rs) throws Exception {
        return rs.next();
    }

    private static boolean isValidMime(String URL) {
        try {
            Document doc = Jsoup.connect(URL).get();
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
