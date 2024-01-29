package stock.market.frontend.app.stockmarketfrontend.controller;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import stock.market.frontend.app.stockmarketfrontend.StockMarketApp;
import stock.market.frontend.app.stockmarketfrontend.models.HistoryDto;
import stock.market.frontend.app.stockmarketfrontend.models.Stocks;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class HistoryFrameController {

    private static final Logger logger = LogManager.getLogger(HistoryFrameController.class);
    private String username;
    private List<HistoryDto> historyDtoLocal = new ArrayList<>();
    private HistoryDto selectDto = null;

//    @FXML
//    private Button deleteSelectHistory;

    @FXML
    private TableView<HistoryDto> historyTable;

    @FXML
    private Pane mainFrame;

    @FXML
    private Button openSelectHistory;

    @FXML
    void handleDeleteSelectHistory(ActionEvent event) {

    }

    // Открыть форму для просмотра выбранного запроса из истории
    @FXML
    void handleOpenSelectHistory(ActionEvent event) {
        openSelectHistory();
    }

    private void openSelectHistory() {
        ObservableList<HistoryDto> selectedItems = historyTable.getSelectionModel().getSelectedItems();

        selectDto = selectedItems.get(0);

        if (selectDto != null) {
            openViewHistoryFrom(selectDto);
        }


    }

    // открыть форму просмотра конкретного выбранного запроса
    private void openViewHistoryFrom(HistoryDto selectDto) {
        FXMLLoader loader = new FXMLLoader(StockMarketApp.class.getResource("viewHistory.fxml"));
        Scene scene = null;
        try {
            scene = new Scene(loader.load(), 892, 570);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        ViewHistoryFrameController viewHistoryFrameController = loader.getController();

        Stage stage = new Stage();
        stage.setTitle("Просмотр истории");
        stage.setScene(scene);

        viewHistoryFrameController.initialize(username, selectDto);

        stage.setResizable(false);
        stage.show();
    }


    // Метод инициализации. Запускается во время открытия текущей формы
    public void initialize(String userName) {
        this.username = userName;

        // Создание столбцов таблицы
        TableColumn<HistoryDto, String> userNameCol = new TableColumn<>("Username");
        userNameCol.setCellValueFactory(new PropertyValueFactory<>("userName"));

        TableColumn<HistoryDto, String> fromCol = new TableColumn<>("From");
        fromCol.setCellValueFactory(new PropertyValueFactory<>("from"));

        TableColumn<HistoryDto, String> tillCol = new TableColumn<>("Till");
        tillCol.setCellValueFactory(new PropertyValueFactory<>("till"));

        TableColumn<HistoryDto, String> createCol = new TableColumn<>("Create");
        createCol.setCellValueFactory(new PropertyValueFactory<>("create"));

        TableColumn<HistoryDto, Integer> daysCalendarCol = new TableColumn<>("Calendar Days");
        daysCalendarCol.setCellValueFactory(new PropertyValueFactory<>("daysCalendar"));

        TableColumn<HistoryDto, Integer> resultCol = new TableColumn<>("Result");
        resultCol.setCellValueFactory(new PropertyValueFactory<>("result"));


        historyTable.getColumns().setAll(userNameCol, fromCol, tillCol, createCol, daysCalendarCol, resultCol);


        try {
            historyDtoLocal = getHistoryStocks();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        addHistoryToTable();
    }

    // Заполнить таблицу данными
    private void addHistoryToTable() {
        historyTable.getItems().setAll(historyDtoLocal);
    }



    // сделать запрос к серверному приложению и получить данные
    private List<HistoryDto> getHistoryStocks() throws IOException {
        String url = "http://localhost:8080/api/v1/calc/stocks/history/" + username;
        HttpURLConnection connection = null;
        BufferedReader reader = null;

        try {
            // Создание объекта URL на основе адреса
            URL apiUrl = new URL(url);

            // Создание подключения
            connection = (HttpURLConnection) apiUrl.openConnection();

            // Установка метода запроса
            connection.setRequestMethod("GET");

            // Установка заголовков
            connection.setRequestProperty("Accept", "application/json");

            // Проверка кода ответа
            int responseCode = connection.getResponseCode();
            if (responseCode != 200) {
                throw new IOException("Unexpected response code: " + responseCode);
            }

            // Создание потока чтения для получения ответа
            reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            StringBuilder response = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                response.append(line);
            }

            // Использование Gson для парсинга JSON-ответа
            Gson gson = new Gson();
            Type historyListType = new TypeToken<List<HistoryDto>>() {
            }.getType();
            List<HistoryDto> historyData = gson.fromJson(response.toString(), historyListType);

            return historyData;

        } finally {
            // Закрытие ридера и подключения
            if (reader != null) {
                reader.close();
            }
            if (connection != null) {
                connection.disconnect();
            }
        }
    }


}
