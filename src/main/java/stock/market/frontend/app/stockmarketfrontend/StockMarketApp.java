package stock.market.frontend.app.stockmarketfrontend;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;


public class StockMarketApp extends Application {

    public static void main(String[] args) {
        launch();
    }

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(StockMarketApp.class.getResource("login-menu.fxml"));

        Scene scene = new Scene(fxmlLoader.load(), 500, 300);
        stage.setTitle("Stock MarketFX");
        stage.setScene(scene);
        stage.show();
        stage.setResizable(false);

    }

}