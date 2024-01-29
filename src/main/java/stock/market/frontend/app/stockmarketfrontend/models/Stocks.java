package stock.market.frontend.app.stockmarketfrontend.models;

import java.util.Objects;


public class Stocks {
    public String secId;
    public String shortname;
    public String regNumber;
    public String name;
    public String isin;
    public String emitEntTitle;

    public Stocks(String secId, String shortname, String regNumber, String name, String isin, String emitEntTitle) {
        this.secId = secId;
        this.shortname = shortname;
        this.regNumber = regNumber;
        this.name = name;
        this.isin = isin;
        this.emitEntTitle = emitEntTitle;
    }

    public Stocks() {
    }

    @Override
    public String toString() {
        return "Stocks{" +
                "secId='" + secId + '\'' +
                ", shortname='" + shortname + '\'' +
                ", regNumber='" + regNumber + '\'' +
                ", name='" + name + '\'' +
                ", isin='" + isin + '\'' +
                '}';
    }

    public String getEmitEntTitle() {
        return emitEntTitle;
    }

    public void setEmitEntTitle(String emitEntTitle) {
        this.emitEntTitle = emitEntTitle;
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