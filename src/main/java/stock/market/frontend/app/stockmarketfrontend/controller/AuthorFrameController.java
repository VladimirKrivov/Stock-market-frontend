package stock.market.frontend.app.stockmarketfrontend.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;

/**
 * Контроллер окна "Справка"
 */
public class AuthorFrameController {
    @FXML
    private Button closeBut;

    @FXML
    void handleCloseBut(ActionEvent event) {
        Stage stage = (Stage) closeBut.getScene().getWindow();
        stage.close();
    }
}
