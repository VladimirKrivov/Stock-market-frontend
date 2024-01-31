package stock.market.frontend.app.stockmarketfrontend.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import stock.market.frontend.app.stockmarketfrontend.models.UserDto;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class RegistrationFrameController {
    /**
     * Контроллер окна регистрации
     */
    private static final String REG_URL = "http://localhost:8080/api/v1/auth/register";

    private static final Logger logger = LogManager.getLogger(RegistrationFrameController.class);

    @FXML
    private PasswordField confirmPasswordInput;

    @FXML
    private Text headerText;

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
    private Text passwordText1;

    @FXML
    private Button registerButton;


    /**
     * Действие при нажатии кнопки регистрации
     */
    @FXML
    void handleRegisterButton(ActionEvent event) {
        if (loginInput.getText().length() < 3) {
            showAlert("Регистрация не удалась","Логин должен быть не менее 3 символов");
        } else {
            if (!confirmPasswordInput.getText().equals(passwordInput.getText())) {
                showAlert("Регистрация не удалась","Введенные пароли не совпадают!");

            } else if (confirmPasswordInput.getText().length() < 5) {
                showAlert("Регистрация не удалась","Введенный пароль должен быть не менее 5 символов!");
            } else {
                try {
                    register(loginInput.getText(), passwordInput.getText());
                    loginInput.setText("");
                    passwordInput.setText("");
                    confirmPasswordInput.setText("");
                } catch (Exception e) {
                    logger.error("Unexpected server error");
                }
            }

        }


    }

    /**
     * Отобразить alert
     * @param title заголовок окна
     * @param message текст окна
     */
    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    /**
     * Метод осуществляющий регистрацию
     * @param login логин пользователя
     * @param password пароль пользователя
     */
    private void register(String login, String password) throws Exception {
        HttpURLConnection connection = getHttpURLConnection(login, password);

        String res = String.valueOf(connection.getResponseCode());

        if (res.equals("200")) {
            showAlert("Успешная регистрация", "Регистрация прошла успешно");
            Stage stage = (Stage) registerButton.getScene().getWindow();
            stage.close();
        } else if (res.equals("409")) {
            showAlert("Регистрация не удалась", "Пользователь с таким именем уже существует!");
        } else {
            showAlert("Регистрация не удалась", "Не предвиденная ошибка сервера!");
        }
        connection.disconnect();
    }

    /**
     * Метод осуществляющий запрос на сервер для регистрации
     * @param login логин пользователя
     * @param password пароль пользователя
     */
    private static HttpURLConnection getHttpURLConnection(String login, String password) throws IOException {
        UserDto userDto = new UserDto(login, password);

        URL url = new URL(REG_URL);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("POST");

        connection.setDoOutput(true);

        connection.setRequestProperty("Content-Type", "application/json");
        // Преобразование объекта UserDto в строку JSON
        String jsonInputString = "{\"name\": \"" + userDto.getName() + "\", \"password\": \"" + userDto.getPassword() + "\"}";

        // Построение запроса и отправка данных на сервер
        try (DataOutputStream outputStream = new DataOutputStream(connection.getOutputStream())) {
            byte[] input = jsonInputString.getBytes(StandardCharsets.UTF_8);
            outputStream.write(input, 0, input.length);
        }

        return connection;
    }
}


