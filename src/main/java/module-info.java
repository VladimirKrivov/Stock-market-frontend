module stock.market.frontend.app.stockmarketfrontend {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;

    opens stock.market.frontend.app.stockmarketfrontend to javafx.fxml;
    exports stock.market.frontend.app.stockmarketfrontend;
}