package stock.market.frontend.app.stockmarketfrontend.models;

import java.util.List;
import java.util.Objects;


public class HistoryDto {
    public String userName;

    public String from;
    public String till;
    public String create;
    public Integer daysCalendar;

    public String result;

    public List<HistoryElemDto> historyElemDto;



    public HistoryDto() {
    }

    public HistoryDto(String userName, String from, String till, String create, Integer daysCalendar, String result, List<HistoryElemDto> historyElemDto) {
        this.userName = userName;
        this.from = from;
        this.till = till;
        this.create = create;
        this.daysCalendar = daysCalendar;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        HistoryDto that = (HistoryDto) o;
        return Objects.equals(userName, that.userName) && Objects.equals(from, that.from) && Objects.equals(till, that.till) && Objects.equals(create, that.create) && Objects.equals(daysCalendar, that.daysCalendar) && Objects.equals(result, that.result) && Objects.equals(historyElemDto, that.historyElemDto);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userName, from, till, create, daysCalendar, result, historyElemDto);
    }

    public Integer getDaysCalendar() {
        return daysCalendar;
    }

    public void setDaysCalendar(Integer daysCalendar) {
        this.daysCalendar = daysCalendar;
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
