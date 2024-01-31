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
import stock.market.frontend.app.stockmarketfrontend.StockMarketApp;
import stock.market.frontend.app.stockmarketfrontend.models.HistoryDto;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Контроллер окна "История запросов"
 */
public class HistoryFrameController {

    private final String GET_HISTORY_URL = "http://localhost:8080/api/v1/calc/stocks/history/";
    private String username;
    private List<HistoryDto> historyDtoLocal = new ArrayList<>();
    private HistoryDto selectDto = null;

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

    /**
     * Метод отслеживает выбранную колонку в таблице и открывает окно "Просмотр истории"
     */
    private void openSelectHistory() {
        ObservableList<HistoryDto> selectedItems = historyTable.getSelectionModel().getSelectedItems();

        selectDto = selectedItems.get(0);

        if (selectDto != null) {
            openViewHistoryFrom(selectDto);
        }


    }

    /**
     * Открыть форму просмотра конкретного выбранного запроса
     * @param selectDto - dto запроса выделенного в таблице
     */
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

    /**
     * Метод инициализации окна "История запросов". Задействуется во время запуска окна
     * @param userName имя пользователя текущей сессии
     */
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

    /**
     * Метод с помощью которого заполняется таблица запросов данными
     */
    private void addHistoryToTable() {
        historyTable.getItems().setAll(historyDtoLocal);
    }

    /**
     * Метод с помощью которого производится запрос на сервер для получения списка истории запросов
     * @return List<HistoryDto> список истории запросов
     * @throws IOException пробрасывается исключение
     */
    private List<HistoryDto> getHistoryStocks() throws IOException {
        String url = GET_HISTORY_URL + username;
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
