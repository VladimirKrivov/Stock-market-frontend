package stock.market.frontend.app.stockmarketfrontend;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.io.IOException;


public class StockMarketApp extends Application {

    public static void main(String[] args) {
        launch();
    }

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(StockMarketApp.class.getResource("login-menu.fxml"));



        Scene scene = new Scene(fxmlLoader.load(), 487, 289);
        stage.setTitle("Stock Market App");
        stage.setScene(scene);
        stage.show();
        stage.setResizable(false);

    }
}