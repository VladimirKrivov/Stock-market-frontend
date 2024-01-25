module stock.market.frontend.app.stockmarketfrontend {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.google.gson;

    requires org.controlsfx.controls;

    opens stock.market.frontend.app.stockmarketfrontend to javafx.fxml;
    exports stock.market.frontend.app.stockmarketfrontend;
    exports stock.market.frontend.app.stockmarketfrontend.controller;
    opens stock.market.frontend.app.stockmarketfrontend.controller to javafx.fxml;

    exports stock.market.frontend.app.stockmarketfrontend.models;
    opens stock.market.frontend.app.stockmarketfrontend.models to javafx.fxml;
    exports stock.market.frontend.app.stockmarketfrontend.service;
    opens stock.market.frontend.app.stockmarketfrontend.service to javafx.fxml;

}