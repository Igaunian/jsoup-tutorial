public class Main {

    public static void main(String[] args) {
        IngatlanScraper ingatlanScraper = new IngatlanScraper();
        ingatlanScraper.scrapeBasicData();
        ingatlanScraper.scrapeDetailedData();
    }
}
