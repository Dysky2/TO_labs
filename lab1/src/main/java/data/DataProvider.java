package data;

import org.springframework.http.MediaType;
import org.springframework.web.client.RestClient;

public record DataProvider(String url) implements RateProvider {

    static RestClient client = RestClient.create();

    public String getData() {
       return client.get()
               .uri(url)
               .accept(MediaType.TEXT_XML)
               .retrieve()
               .body(String.class);
    }

}
