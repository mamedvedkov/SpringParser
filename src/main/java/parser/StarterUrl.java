package parser;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class StarterUrl {
    @Value("${crawlingURL.value}")
    private String url;

    public String getUrl() {
        return url;
    }
}
