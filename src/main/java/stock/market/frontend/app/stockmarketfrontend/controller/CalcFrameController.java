package stock.market.frontend.app.stockmarketfrontend.controller;


import com.google.gson.Gson;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import stock.market.frontend.app.stockmarketfrontend.models.HistoryDto;
import stock.market.frontend.app.stockmarketfrontend.models.ResponseToCalcDto;
import stock.market.frontend.app.stockmarketfrontend.models.Stocks;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import static stock.market.frontend.app.stockmarketfrontend.service.test.parseResponseToList;

public class CalcFrameController {
    private static final Logger logger = LogManager.getLogger(CalcFrameController.class);
    private static final String GET_ALL_STOCK_IN_USER = "http://localhost:8080/api/v1/stock/find/all?name=";
    private String username;
    private List<Stocks> stocksListTableItemCalc = new ArrayList<>();
    private List<Stocks> stockCalItems = new ArrayList<>();
    private HistoryDto historyDtoLocale;

    @FXML
    private Button addToCalcBut;

    @FXML
    private Button calcButt;

    @FXML
    private TableView<?> calcincResultTable;

    @FXML
    private Text daysText;

    @FXML
    private TextField inputDateFrom;

    @FXML
    private TextField inputDateTill;

    @FXML
    private Pane mainFrame;

    @FXML
    private Button openMenuBut;

    @FXML
    private Button removeToCalcBut;

    @FXML
    private Text resultFieldText;

    @FXML
    private TableView<Stocks> stocksInCalcTable;

    @FXML
    private TableView<Stocks> stocksInTable;

    @FXML
    void handleAddToCalcBut(ActionEvent event) {
        addToCalcStock();
    }

    @FXML
    void handleCalcBut(ActionEvent event) {
        String from = inputDateFrom.getText();
        String till = inputDateTill.getText();

        if (from.equals("") || till.equals("")) {
            showAlert("Ошибка", "Заполните поля ввода даты");
        } else {

            try {
                calculatingStockDate(from, till);
            } catch (IOException e) {
                logger.error("Ошибка при обработке запроса расчета темпа роста акций!!");
                e.printStackTrace();
            }


        }


    }

    private void calculatingStockDate(String from, String till) throws IOException {
        ResponseToCalcDto responseToCalcDto = new ResponseToCalcDto();

        responseToCalcDto.setUserName(username);
        responseToCalcDto.setFrom(from);
        responseToCalcDto.setTill(till);

        if (stockCalItems.isEmpty()) {
            responseToCalcDto.setStocksList(stocksListTableItemCalc);
        } else
            responseToCalcDto.setStocksList(stockCalItems);

        // Преобразуем объект responseToCalcDto в JSON
        Gson gson = new Gson();
        String json = gson.toJson(responseToCalcDto);

        System.out.println(json);


        // Отправляем POST-запрос
//        String url = "http://localhost:8080/api/v1/calc/stocks"; // Замените на ваш URL


        URL url = new URL("http://localhost:8080/api/v1/calc/stocks");
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("POST");
        connection.setDoOutput(true);
        connection.setRequestProperty("Content-Type", "application/json");

        try (OutputStream outputStream = connection.getOutputStream()) {
            byte[] input = json.getBytes("utf-8");
            outputStream.write(input, 0, input.length);
        }

//        Обработка ответа
        try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(connection.getInputStream(), "utf-8"))) {
            StringBuilder response = new StringBuilder();
            String responseLine;
            while ((responseLine = bufferedReader.readLine()) != null) {
                response.append(responseLine);
            }

            String get = String.valueOf(response);
            System.out.println("Полученный ответ");
            System.out.println(get);
            HistoryDto historyDto = gson.fromJson(response.toString(), HistoryDto.class);
            System.out.println("Распарсенный ответ");
            System.out.println(historyDto);

            historyDtoLocale = historyDto;

        }
    }


    @FXML
    void handleInputDateFrom(ActionEvent event) {

    }

    @FXML
    void handleInputDateTill(ActionEvent event) {

    }

    @FXML
    void handleRemoveToCalcBut(ActionEvent event) {
        removeStockInTableCalc();
    }

    private void removeStockInTableCalc() {
        // Получаем выбранную строку
        ObservableList<Stocks> selectedItems = stocksInCalcTable.getSelectionModel().getSelectedItems();

        Stocks buf = new Stocks();

        try {
            for (Stocks elem : stockCalItems) {
                if (elem.getSecId().equals(selectedItems.get(0).getSecId())) {
                    buf = elem;
                }
            }
        } catch (IndexOutOfBoundsException e) {
            showAlert("Ошибка", "Выбирите элемент в правой таблице");
        }


        stockCalItems.remove(buf);
        stocksListTableItemCalc.add(buf);

        stocksInTable.getItems().setAll(stocksListTableItemCalc);
        stocksInCalcTable.getItems().setAll(stockCalItems);
    }

    private void addToCalcStock() {
        // Получаем выбранную строку
        ObservableList<Stocks> selectedItems = stocksInTable.getSelectionModel().getSelectedItems();

        Stocks buf = new Stocks();

        try {
            for (Stocks elem : stocksListTableItemCalc) {
                if (elem.getSecId().equals(selectedItems.get(0).getSecId())) {
                    buf = elem;
                }
            }
        } catch (IndexOutOfBoundsException e) {
            showAlert("Ошибка", "Выбирите элемент в левой таблице");
        }


        stocksListTableItemCalc.remove(buf);
        stockCalItems.add(buf);

        stocksInTable.getItems().setAll(stocksListTableItemCalc);
        stocksInCalcTable.getItems().setAll(stockCalItems);

    }


    public void initialize(String username) {
        this.username = username;
        inputDateFrom.setText("2024-01-18");
        inputDateTill.setText("2024-01-25");
        createColumnA();
        createColumnB();
        getAllStock();
    }

    private void getAllStock() {
        String url = GET_ALL_STOCK_IN_USER + username;

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

                stocksListTableItemCalc = parseResponseToList(response.toString());
                stocksInTable.getItems().setAll(stocksListTableItemCalc);

            } else {
                logger.error("Ошибка при выполнении запроса. Код ошибки: {}", responseCode);
            }

            connection.disconnect();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void showAlert(String title, String text) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(text);
        alert.showAndWait();
    }

    public void createColumnA() {
        TableColumn<Stocks, String> secIdColumn = new TableColumn<>("SEC ID");
        secIdColumn.setCellValueFactory(new PropertyValueFactory<>("secId"));

        TableColumn<Stocks, String> shortNameColumn = new TableColumn<>("Short Name");
        shortNameColumn.setCellValueFactory(new PropertyValueFactory<>("shortname"));

        stocksInCalcTable.getColumns().setAll(secIdColumn, shortNameColumn);
    }

    public void createColumnB() {
        TableColumn<Stocks, String> secIdColumn = new TableColumn<>("SEC ID");
        secIdColumn.setCellValueFactory(new PropertyValueFactory<>("secId"));

        TableColumn<Stocks, String> shortNameColumn = new TableColumn<>("Short Name");
        shortNameColumn.setCellValueFactory(new PropertyValueFactory<>("shortname"));
        stocksInTable.getColumns().setAll(secIdColumn, shortNameColumn);
    }


}