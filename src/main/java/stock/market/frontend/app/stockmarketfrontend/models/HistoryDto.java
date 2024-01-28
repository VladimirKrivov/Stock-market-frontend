package stock.market.frontend.app.stockmarketfrontend.models;

import java.util.List;


public class HistoryDto {
    public String userName;

    public String from;
    public String till;
    public String create;

    public String result;

    public List<HistoryElemDto> historyElemDto;


    public HistoryDto() {
    }

    public HistoryDto(String userName, String from, String till, String create, String result, List<HistoryElemDto> historyElemDto) {
        this.userName = userName;
        this.from = from;
        this.till = till;
        this.create = create;
        this.result = result;
        this.historyElemDto = historyElemDto;
    }

    @Override
    public String toString() {
        return "HistoryDto{" +
                "userName='" + userName + '\'' +
                ", from='" + from + '\'' +
                ", till='" + till + '\'' +
                ", create='" + create + '\'' +
                ", result='" + result + '\'' +
                ", historyElemDto=" + historyElemDto +
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

    public String getCreate() {
        return create;
    }

    public void setCreate(String create) {
        this.create = create;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public List<HistoryElemDto> getHistoryElemDto() {
        return historyElemDto;
    }

    public void setHistoryElemDto(List<HistoryElemDto> historyElemDto) {
        this.historyElemDto = historyElemDto;
    }
}
