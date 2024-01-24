package stock.market.frontend.app.stockmarketfrontend.models;


class Stocks {
    private String secId;
    private String shortname;
    private String regNumber;
    private String name;
    private String isin;

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

    public String getShortname() {
        return shortname;
    }

    public String getRegNumber() {
        return regNumber;
    }

    public String getName() {
        return name;
    }

    public String getIsin() {
        return isin;
    }
}