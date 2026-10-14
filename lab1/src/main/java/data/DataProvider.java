package data;

import org.springframework.http.MediaType;
import org.springframework.web.client.RestClient;

public record DataProvider() implements RateProvider {

    static RestClient client = RestClient.create();

    public String getData() {
       return client.get()
               .uri("https://static.nbp.pl/dane/kursy/xml/lastA.xml")
               .accept(MediaType.TEXT_XML)
               .retrieve()
               .body(String.class);
    }

}
