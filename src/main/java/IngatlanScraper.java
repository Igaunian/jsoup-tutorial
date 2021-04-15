import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

public class IngatlanScraper {

    final String baseUrl = "https://ingatlan.com";
    final String url = "/szukites/elado+lakas+uj-epitesu+ujszeru+felujitott+jo-allapotu+nem-berleti-jog+terulet-szerint-csokkeno+budapest+7-15-mFt+10-m2-felett+foldszint-10-felett-emelet?page=";

    List<String> links = new ArrayList<>();

    public void scrapeLinks() {
        int page = 1;

        while (true) {
            try {
                final Document document = Jsoup.connect(baseUrl+url+page).get();
                Elements pagination = document.select("a.pagination__button.button--flat.button--small");
                if (pagination.isEmpty()) {
                    break;
                }

                Elements elements = document.select("div.listing__card");

                for (Element element : elements) {
                    final String link = baseUrl+element.select("a.listing__link.js-listing-active-area").attr("href");
//                    final String price = element.select("div.price").text();
//                    final String m2 = element.select("div.listing__parameter.listing__data--area-size").text();
//                    final String m2Price = element.select("div.price--sqm").text();

                    links.add(link);
                }

                page += 1;

            } catch (Exception ex) {
                ex.printStackTrace();
            }

        }

        System.out.println(links.size());

    }

    public void scrapeData() {

        for (String link : links) {
            try {
                Document document = Jsoup.connect(link).get();

                String header = document.select("h1.js-listing-title").text();
                String district = header.split("\\.")[0];
                System.out.println(district);


            } catch (Exception ex) {
                ex.printStackTrace();
            }

        }
    }

}
