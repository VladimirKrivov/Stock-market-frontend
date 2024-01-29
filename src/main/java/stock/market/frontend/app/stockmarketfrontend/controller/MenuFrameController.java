package stock.market.frontend.app.stockmarketfrontend.controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import stock.market.frontend.app.stockmarketfrontend.StockMarketApp;
import stock.market.frontend.app.stockmarketfrontend.models.Stocks;
import stock.market.frontend.app.stockmarketfrontend.service.StocksAdapter;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;


public class MenuFrameController {

    // Контроллер главного меню в котором производится добавляение акции в портфель пользователя

    private static final Logger logger = LogManager.getLogger(MenuFrameController.class);
    private String username;
    private List<Stocks> stocksListTableItem = new ArrayList<>();


    private static final String ADD_STOCK_IN_USER_URL = "http://localhost:8080/api/v1/stock/find?company=";
    private static final String GET_ALL_STOCK_IN_USER = "http://localhost:8080/api/v1/stock/find/all?name=";
    private static final String DELETE_STOCK_URL = "http://localhost:8080/api/v1/stock/find/delete?name=";


    @FXML
    private Button addStockButt;

    @FXML
    private Button authorButt;

    @FXML
    private Button delStockButt;

    @FXML
    private Button exitButton;

    @FXML
    private Text headerText2;

    @FXML
    private TextField inputSearch;

    @FXML
    private Pane mainFrame;

    @FXML
    private Pane maneFrameHeader;

    @FXML
    private Button openHistoryButt;

    @FXML
    private Button rasButton;

    @FXML
    private TableView<Stocks> tableContent;

    @FXML
    void handleExit(ActionEvent event) {
        Platform.exit();
    }

    // Открыть окно истории запросов
    @FXML
    void ClickHistory(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(StockMarketApp.class.getResource("historyCalc.fxml"));
        Scene scene = null;
        try {
            scene = new Scene(loader.load(), 790, 576);
        } catch (IOException e) {
            logger.error("Frame failed to load");
        }
        HistoryFrameController historyFrameController = loader.getController();

        Stage stage = new Stage();
        stage.setTitle("История запросов");
        stage.setScene(scene);

        stage.setResizable(false);
        stage.show();

        historyFrameController.initialize(username);

    }

    // Удаляем акцию из портфеля пользователя
    @FXML
    void deletCollumn(ActionEvent event) {
        // Получаем выбранную строку
        ObservableList<Stocks> selectedItems = tableContent.getSelectionModel().getSelectedItems();

        deleteStockInUser(selectedItems.get(0).getSecId(), username);
        getAllStock();
    }

    // Добавить акцию в портфель пользователя
    @FXML
    private void handleAddStockButt() {
        String searchValue = inputSearch.getText();
        inputSearch.setText("");

        HttpURLConnection connection = null;
        try {
            connection = addStockToUser(searchValue);
        } catch (IOException e) {
            logger.error("Ошибка при выполнении запроса. Добавление акции по имени: {}", searchValue);
        }
        connection.disconnect();
    }

    // Открыть окно в котором производится расчет акций
    @FXML
    void handleOpenCalcFrame(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(StockMarketApp.class.getResource("calcStock.fxml"));
        Scene scene = new Scene(loader.load(), 1267, 802);
        CalcFrameController calcFrameController = loader.getController();

        Stage stage = new Stage();
        stage.setTitle("Расчет доходности");
        stage.setScene(scene);

        calcFrameController.initialize(username);

        stage.setResizable(false);
        stage.show();
    }

    // Открыть окно справка/автор
    @FXML
    void handleAuthorButton(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(StockMarketApp.class.getResource("author.fxml"));
        Scene scene = new Scene(loader.load(), 819, 563);

        Stage stage = new Stage();
        stage.setTitle("Справка");
        stage.setScene(scene);

        stage.setResizable(false);
        stage.show();
    }


    //    Удалить акцию из списка
    private void deleteStockInUser(String secid, String username) {
        String url = DELETE_STOCK_URL + username + "&secid=" + secid;

        System.out.println("Чему равен secId " + secid);


        try {
            URL apiUrl = new URL(url);
            HttpURLConnection connection = (HttpURLConnection) apiUrl.openConnection();
            connection.setRequestMethod("DELETE");

            int responseCode = connection.getResponseCode();
        } catch (IOException e) {
            logger.error("Не удалось удалить Акцию: {} у пользователя: {}", secid, username);
        }

    }


    // Добавить акцию в портфель пользователя
    private HttpURLConnection addStockToUser(String searchValue) throws IOException {
        String url = "http://localhost:8080/api/v1/stock/find?company=" + searchValue + "&name=" + username;

        URL apiUrl = new URL(url);
        HttpURLConnection connection = (HttpURLConnection) apiUrl.openConnection();
        connection.setRequestMethod("GET");

        int responseCode = connection.getResponseCode();
        if (responseCode == HttpURLConnection.HTTP_CREATED) {
            // Чтение ответа от сервера
            BufferedReader responseReader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            StringBuilder response = new StringBuilder();
            String line;
            while ((line = responseReader.readLine()) != null) {
                response.append(line);
            }
            responseReader.close();


            // Обработка полученного JSON
            Stocks stocks = parseJson(response.toString());

            if (!tableContent.getItems().contains(stocks)) {
                stocksListTableItem.add(stocks);
                tableContent.getItems().setAll(stocksListTableItem);

            } else {
                showAlert("Error", "Акция: \"" + stocks.getName() + "\", по запросу: " + searchValue + " уже есть в списке!");
            }

        } else {
            logger.error("Ошибка при выполнении запроса. Код ошибки: {}", responseCode);
        }
        return connection;

    }

    // Вызвать Alert
    public void showAlert(String title, String text) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(text);
        alert.showAndWait();
    }

    //    Метод инизиализации полей текущего окна
    public void initialize(String userName) {
        this.username = userName;
        getAllStock();

        // Создание столбцов таблицы
        TableColumn<Stocks, String> secIdColumn = new TableColumn<>("SEC ID");
        secIdColumn.setCellValueFactory(new PropertyValueFactory<>("secId"));

        TableColumn<Stocks, String> shortNameColumn = new TableColumn<>("Short Name");
        shortNameColumn.setCellValueFactory(new PropertyValueFactory<>("shortname"));

        TableColumn<Stocks, String> regNumberColumn = new TableColumn<>("Reg Number");
        regNumberColumn.setCellValueFactory(new PropertyValueFactory<>("regNumber"));

        TableColumn<Stocks, String> nameColumn = new TableColumn<>("Name");
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));

        TableColumn<Stocks, String> isinColumn = new TableColumn<>("ISIN");
        isinColumn.setCellValueFactory(new PropertyValueFactory<>("isin"));


        tableContent.getColumns().setAll(secIdColumn, shortNameColumn, regNumberColumn, nameColumn, isinColumn);
    }

    // Парсить Json Stock
    public Stocks parseJson(String response) {
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(Stocks.class, new StocksAdapter())
                .create();

        return gson.fromJson(response, Stocks.class);

    }
// Парсить Json List Stock

    public List<Stocks> parseResponseToList(String response) {
        Gson gson = new Gson();
        Type stocksListType = new TypeToken<List<Stocks>>() {
        }.getType();
        List<Stocks> stocksList = gson.fromJson(response, stocksListType);
        return stocksList;
    }


    //    Получить List всех акций портфеля пользователя
    public List<Stocks> getAllStock() {
        String url = "http://localhost:8080/api/v1/stock/find/all?name=" + username;

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

                // Обработка полученного JSON

                stocksListTableItem = parseResponseToList(response.toString());
                tableContent.getItems().setAll(stocksListTableItem);

            } else {
                logger.error("Ошибка при выполнении запроса. Код ошибки: {}", responseCode);
            }

            connection.disconnect();

        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

}