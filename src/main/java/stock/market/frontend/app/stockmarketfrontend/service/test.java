package stock.market.frontend.app.stockmarketfrontend.service;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import stock.market.frontend.app.stockmarketfrontend.models.Stocks;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

public class test {

    public static void main(String[] args) {
        getAllStock();
    }

    public static List<Stocks> parseResponseToList(String response) {
        Gson gson = new Gson();
        Type stocksListType = new TypeToken<List<Stocks>>() {
        }.getType();
        List<Stocks> stocksList = gson.fromJson(response, stocksListType);
        return stocksList;
    }


    public static List<Stocks> getAllStock() {
        String url = "http://localhost:8080/api/v1/stock/find/all?name=" + "John";

        try {
            URL apiUrl = new URL(url);
            HttpURLConnection connection = (HttpURLConnection) apiUrl.openConnection();
            connection.setRequestMethod("GET");

            int responseCode = connection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                // Чтение ответа от сервера
                BufferedReader responseReader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                StringBuilder response = new StringBuilder();
                String line;
                while ((line = responseReader.readLine()) != null) {
                    response.append(line);
                }
                responseReader.close();

                System.out.println(response);

// Обработка полученного JSON

                List<Stocks> stocks = parseResponseToList(response.toString());

                System.out.println("Попытка распарсить лист");
                for (Stocks elem : stocks) {
                    System.out.println("elem 1:");
                    System.out.println(elem.getShortname());
                }

            } else {
                System.out.println("Ошибка при выполнении запроса. Код ошибки: " + responseCode);
            }

            connection.disconnect();

        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("HHHHHHHHHHHHHHHHHHHHHHHHHHHH");
        }
        return null;
    }
}
