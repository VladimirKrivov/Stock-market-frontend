package stock.market.frontend.app.stockmarketfrontend.models;



public class HistoryElemDto {

    public String date;
    public String shortName;
    public String growth;


    public HistoryElemDto() {
    }

    public HistoryElemDto(String date, String shortName, String growth) {
        this.date = date;
        this.shortName = shortName;
        this.growth = growth;
    }


    @Override
    public String toString() {
        return "HistoryElemDto{" +
                "date='" + date + '\'' +
                ", shortName='" + shortName + '\'' +
                ", growth='" + growth + '\'' +
                '}';
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    public String getGrowth() {
        return growth;
    }

    public void setGrowth(String growth) {
        this.growth = growth;
    }
}
