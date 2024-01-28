package stock.market.frontend.app.stockmarketfrontend.service;

import javafx.concurrent.Service;
import javafx.concurrent.Task;
import stock.market.frontend.app.stockmarketfrontend.models.UserDto;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class AuthService extends Service<Void> {
    public static final String REGISTER_URL = "http://localhost:8080/api/v1/auth/register";

    private final UserDto user;

    public AuthService(UserDto user) {
        this.user = user;
    }

    @Override
    protected Task<Void> createTask() {
        return new Task<>() {
            @Override
            protected Void call() {
                try {
                    // Создание соединения
                    URL url = new URL(REGISTER_URL);
                    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                    connection.setRequestMethod("POST");
                    connection.setRequestProperty("Content-Type", "application/json");

                    // Преобразование объекта UserDto в строку JSON
                    String jsonInputString = "{\"name\":\"" + user.getName() + "\",\"password\":\"" + user.getPassword() + "\"}";

                    // Построение запроса и отправка данных на сервер
                    connection.setDoOutput(true);
                    try (DataOutputStream outputStream = new DataOutputStream(connection.getOutputStream())) {
                        byte[] input = jsonInputString.getBytes(StandardCharsets.UTF_8);
                        outputStream.write(input, 0, input.length);
                    }

                    // Получение ответа от сервера
                    int responseCode = connection.getResponseCode();

                    if (responseCode != 200) {
                        throw new IOException("Registration Failed with HTTP error code: " + responseCode);
                    }

                    connection.disconnect();

                } catch (IOException e) {
                    e.printStackTrace();
                    throw new RuntimeException(e);
                }
                return null;
            }
        };
    }
}
