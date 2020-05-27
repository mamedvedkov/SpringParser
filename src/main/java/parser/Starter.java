package parser;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Component;
import springConfig.SpringConfig;

@Component
public class Starter {

    public static void main(String[] args) throws Throwable {

        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(SpringConfig.class);

        DataBaseHandler dataBaseHandler = context.getBean("dataBaseHandler", DataBaseHandler.class);
        //Crawler crawler = context.getBean("crawler", Crawler.class);
        StarterUrl url = context.getBean("starterUrl", StarterUrl.class);

        Crawler.processPage(url.getUrl(), dataBaseHandler);

    }

}
