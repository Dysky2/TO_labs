package data;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.StringReader;
import java.util.Currency;
import java.util.HashMap;
import java.util.List;

public class NbpXmlRateProvider implements RateParser{

    HashMap<String, Currency> rates;

    public NbpXmlRateProvider() {
        rates = new HashMap<>();
    }

    public HashMap<String, Currency> parse(String data) {

        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder;

        try {
             builder = factory.newDocumentBuilder();
        } catch (ParserConfigurationException e) {
            throw new RuntimeException(e);
        }

        InputSource inputSource = new InputSource(new StringReader(data));

        Document document;
        try {
            document = builder.parse(inputSource);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        NodeList nodeList = document.getElementsByTagName("nazwa_waluty");

        for(int i =0; i < nodeList.getLength(); i++) {
            Node node = nodeList.item(i);
            String currencyName = node.getTextContent();
            System.out.println(currencyName);
        }

        XmlMapper xmlMapper = new XmlMapper();

        try {
            List<Currency> currencies = xmlMapper.readValue(data, new TypeReference<List<Currency>>() {});

            for(Currency currency : currencies) {
                System.out.println(currency.getDisplayName());
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }


        return rates;

    }
}
