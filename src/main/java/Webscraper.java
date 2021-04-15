import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class Webscraper {

    public static void main(String[] args) {
        final String url = "https://ingatlan.com/szukites/elado+lakas+uj-epitesu+ujszeru+felujitott+jo-allapotu+nem-berleti-jog+terulet-szerint-csokkeno+budapest+7-15-mFt+10-m2-felett+foldszint-10-felett-emelet?page=";
        int page = 1;

        while (true) {
            try {
                final Document document = Jsoup.connect(url+page).get();
                Elements pagination = document.select("a.pagination__button.button--flat.button--small");
                if (pagination.isEmpty()) {
                    break;
                }

                Elements elements = document.select("div.listing__card");

                for (Element element : elements) {
                    final String price = element.select("div.price").text();
                    final String m2 = element.select("div.listing__parameter.listing__data--area-size").text();
                    final String m2Price = element.select("div.price--sqm").text();

                    System.out.println(price);
                    System.out.println(m2);
                    System.out.println(m2Price);
                    System.out.println("next");
                }

                page += 1;

            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }


    }
}
