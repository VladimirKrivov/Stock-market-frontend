package stock.market.frontend.app.stockmarketfrontend.models;

import java.util.List;

public class ResponseToCalcDto {
    public String userName;

    public String from;
    public String till;

    public List<Stocks> stocksList;


    public ResponseToCalcDto() {
    }

    public ResponseToCalcDto(String userName, String from, String till, List<Stocks> stocksList) {
        this.userName = userName;
        this.from = from;
        this.till = till;
        this.stocksList = stocksList;
    }

    @Override
    public String toString() {
        return "ResponseToCalcDto{" +
                "userName='" + userName + '\'' +
                ", from='" + from + '\'' +
                ", till='" + till + '\'' +
                ", stocksList=" + stocksList +
                '}';
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTill() {
        return till;
    }

    public void setTill(String till) {
        this.till = till;
    }

    public List<Stocks> getStocksList() {
        return stocksList;
    }

    public void setStocksList(List<Stocks> stocksList) {
        this.stocksList = stocksList;
    }
}
