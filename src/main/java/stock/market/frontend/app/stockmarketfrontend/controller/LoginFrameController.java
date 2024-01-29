package stock.market.frontend.app.stockmarketfrontend.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import stock.market.frontend.app.stockmarketfrontend.StockMarketApp;
import stock.market.frontend.app.stockmarketfrontend.models.ShortUserDto;
import stock.market.frontend.app.stockmarketfrontend.models.UserDto;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class LoginFrameController {
    // Контроллер окна авторизации
    private static final String LOGIN_URL = "http://localhost:8080/api/v1/auth/login";
    private static final Logger logger = LogManager.getLogger(LoginFrameController.class);

    private String authUserName;


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

    // Метод обработки нажатия на кнопку авторизации
    @FXML
    private void handleLoginButton(ActionEvent event) {

        ShortUserDto userDto = null;
        try {
            userDto = login(loginInput.getText(), passwordInput.getText());
            authUserName = userDto.getName();



            openMenuFrame();
        } catch (NullPointerException e) {
            logger.error("Failed to receive a response from the server");
        }catch (Exception e) {
            logger.error("Unexpected server error");
            e.printStackTrace();
        }


    }

    // Метод открытия основной формы приложения
    private void openMenuFrame() {
        FXMLLoader loader = new FXMLLoader(StockMarketApp.class.getResource("menu.fxml"));
        Scene scene = null;
        try {
            scene = new Scene(loader.load(), 1145, 675);
        } catch (IOException e) {
            logger.error("Frame failed to load");
        }
        MenuFrameController menuController = loader.getController();

        Stage stage = new Stage();
        stage.setTitle("Добавить акцию в портфель");
        stage.setScene(scene);

        stage.setResizable(false);
        stage.show();

        menuController.initialize(authUserName);

        // Закрываем главное окно
        Stage primaryStage = (Stage) loginButton.getScene().getWindow();
        primaryStage.close();
    }

    // Действие при нажатии кнопки регистрации
    @FXML
    void handleRegButton(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(StockMarketApp.class.getResource("register-menu.fxml"));
        Scene scene = new Scene(loader.load(), 500, 350);
        RegistrationFrameController registrationFrameController = loader.getController();

        Stage stage = new Stage();
        stage.setTitle("Регистрация");
        stage.setScene(scene);

        stage.setResizable(false);
        stage.show();

    }

    // Показать Алерт
    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    // Метод обраюотки авторизации
    private ShortUserDto login(String login, String password) throws Exception {
        HttpURLConnection connection = getHttpURLConnection(login, password);

        String res = String.valueOf(connection.getResponseCode());

        if (res.equals("200")) {
            showAlert("Успешная авторизация", "Авторизация прошла успешно");
        } else if (res.equals("409")) {
            showAlert("Авторизация не удалась", "Проверьте правильность ввода пароля");
        } else {
            showAlert("Авторизация не удалась", "Не предвиденная ошибка сервера!");
        }

        try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(connection.getInputStream(), "utf-8"))) {
            StringBuilder response = new StringBuilder();
            String responseLine;
            while ((responseLine = bufferedReader.readLine()) != null) {
                response.append(responseLine);
            }

            String name = String.valueOf(response);
            name = name.replace("{\"name\":\"", "");
            name = name.replace("\"}", "");

            connection.disconnect();

            return new ShortUserDto(name);
        }

    }

    // Метод отправки запроса авторизации к сервера
    private static HttpURLConnection getHttpURLConnection(String login, String password) throws IOException {
        UserDto userDto = new UserDto(login, password);
        URL url = new URL(LOGIN_URL);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("POST");
        connection.setDoOutput(true);
        connection.setRequestProperty("Content-Type", "application/json");

        String jsonInputString = "{\"name\": \"" + userDto.getName() + "\", \"password\": \"" + userDto.getPassword() + "\"}";
        try (OutputStream outputStream = connection.getOutputStream()) {
            byte[] input = jsonInputString.getBytes("utf-8");
            outputStream.write(input, 0, input.length);
        }
        return connection;
    }

}


