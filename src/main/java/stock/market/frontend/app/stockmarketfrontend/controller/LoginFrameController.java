package stock.market.frontend.app.stockmarketfrontend.controller;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import stock.market.frontend.app.stockmarketfrontend.StockMarketApp;

import java.io.IOException;

public class LoginFrameController {


    @FXML
    private Button exitButton;

    @FXML
    private Text headerText;

    @FXML
    private Button loginButton;

    @FXML
    private TextField loginInput;

    @FXML
    private Text loginText;

    @FXML
    private Pane mainFrame;

    @FXML
    private Pane maneFrameHeader;

    @FXML
    private PasswordField passwordInput;

    @FXML
    private Text passwordText;

    @FXML
    private void handleLoginButton(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(StockMarketApp.class.getResource("menu.fxml"));
        Scene scene = new Scene(loader.load(), 1145, 675);
        MenuFrameController menuController = loader.getController();

        Stage stage = new Stage();
        stage.setTitle("Stock Market App");
        stage.setScene(scene);

        stage.setResizable(false);
        stage.show();

        menuController.initialize();

        // Закрываем главное окно
        Stage primaryStage = (Stage) loginButton.getScene().getWindow();
        primaryStage.close();


    }

}


