import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class IngatlanScraper {

    final String baseUrl = "https://ingatlan.com";
    final String url = "/szukites/elado+lakas+uj-epitesu+ujszeru+felujitott+jo-allapotu+nem-berleti-jog+terulet-szerint-csokkeno+budapest+7-15-mFt+10-m2-felett+foldszint-10-felett-emelet?page=";

    // link : list of data
    Map<String, List<String>> scrapedData = new HashMap<>();

    public void scrapeBasicData() {
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
                    List<String> data = new ArrayList<>();

                    final String link = baseUrl+element.select("a.listing__link.js-listing-active-area").attr("href");
                    final String price = element.select("div.price").text();
                    final String m2 = element.select("div.listing__parameter.listing__data--area-size").text();
                    final String m2Price = element.select("div.price--sqm").text();

                    data.add(price);
                    data.add(m2);
                    data.add(m2Price);
                    scrapedData.put(link, data);
                }

                page += 1;

            } catch (Exception ex) {
                ex.printStackTrace();
            }

        }

        System.out.println(scrapedData.size());

    }

    public void scrapeDetailedData() {

        for (Map.Entry<String, List<String>> entry : scrapedData.entrySet()) {
            try {
                String link = entry.getKey();
                Document document = Jsoup.connect(link).get();

                String header = document.select("h1.js-listing-title").text();
                String district = header.split("\\.")[0];
                entry.getValue().add(district);

                String floor = document.select("div.paramterers:nth-child(2) table:nth-child(1) tbody:nth-child(1) tr:nth-child(5) > td:nth-child(2)").text();
                entry.getValue().add(floor);

                String description = document.select("div.long-description").text();
                entry.getValue().add(description);

                System.out.println(entry.getValue());

            } catch (Exception ex) {
                ex.printStackTrace();
            }

        }
    }

}
