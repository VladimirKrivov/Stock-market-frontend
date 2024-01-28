package stock.market.frontend.app.stockmarketfrontend.models;

public class ShortUserDto {
    private String name;


    public ShortUserDto() {
    }

    public ShortUserDto(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}