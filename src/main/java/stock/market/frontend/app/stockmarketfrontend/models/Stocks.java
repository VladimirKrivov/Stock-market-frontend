package stock.market.frontend.app.stockmarketfrontend.models;

import com.google.gson.Gson;

import java.util.Objects;


public class Stocks {
    private String secId;
    private String shortname;
    private String regNumber;
    private String name;
    private String isin;


    public Stocks() {
    }

    public Stocks(String secId, String shortname, String regNumber, String name, String isin) {
        this.secId = secId;
        this.shortname = shortname;
        this.regNumber = regNumber;
        this.name = name;
        this.isin = isin;
    }

    public String getSecId() {
        return secId;
    }

    public void setSecId(String secId) {
        this.secId = secId;
    }

    public String getShortname() {
        return shortname;
    }

    public void setShortname(String shortname) {
        this.shortname = shortname;
    }

    public String getRegNumber() {
        return regNumber;
    }

    public void setRegNumber(String regNumber) {
        this.regNumber = regNumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIsin() {
        return isin;
    }

    public void setIsin(String isin) {
        this.isin = isin;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Stocks stocks = (Stocks) o;
        return Objects.equals(secId, stocks.secId) && Objects.equals(shortname, stocks.shortname) && Objects.equals(regNumber, stocks.regNumber) && Objects.equals(name, stocks.name) && Objects.equals(isin, stocks.isin);
    }

    @Override
    public int hashCode() {
        return Objects.hash(secId, shortname, regNumber, name, isin);
    }
}