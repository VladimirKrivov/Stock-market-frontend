package stock.market.frontend.app.stockmarketfrontend.models;


public class StockDto {
    private String secId;
    private String shortname;
    private String regNumber;
    private String name;
    private String isin;
    private String emitEntTitle;

    public StockDto() {
    }

    public StockDto(String secId, String shortname, String regNumber, String name, String isin, String emitEntTitle) {
        this.secId = secId;
        this.shortname = shortname;
        this.regNumber = regNumber;
        this.name = name;
        this.isin = isin;
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

    public String getEmitEntTitle() {
        return emitEntTitle;
    }

    public void setEmitEntTitle(String emitEntTitle) {
        this.emitEntTitle = emitEntTitle;
    }
}