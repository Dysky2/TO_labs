import data.DataProvider;
import data.NbpXmlRateProvider;

public class Main {
    public static void main(String[] args) {
        DataProvider dataProvider = new DataProvider();

        String data = dataProvider.getData();

        NbpXmlRateProvider nbpXmlRateProvider = new NbpXmlRateProvider();

        nbpXmlRateProvider.parse(data);

        System.out.println(dataProvider.getData());
    }
}
