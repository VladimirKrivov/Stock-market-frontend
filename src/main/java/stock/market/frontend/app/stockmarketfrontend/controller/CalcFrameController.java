package stock.market.frontend.app.stockmarketfrontend.controller;


import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import stock.market.frontend.app.stockmarketfrontend.models.HistoryDto;
import stock.market.frontend.app.stockmarketfrontend.models.HistoryElemDto;
import stock.market.frontend.app.stockmarketfrontend.models.ResponseToCalcDto;
import stock.market.frontend.app.stockmarketfrontend.models.Stocks;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Контроллер окна "Расчет доходности"
 */
public class CalcFrameController {
    private static final Logger logger = LogManager.getLogger(CalcFrameController.class);
    private static final String GET_ALL_STOCK_IN_USER = "http://localhost:8080/api/v1/stock/find/all?name=";
    private String username;
    private List<Stocks> stocksListTableItemCalc = new ArrayList<>();
    private List<Stocks> stockCalItems = new ArrayList<>();
    private HistoryDto historyDtoLocale = new HistoryDto();

    @FXML
    private Button addToCalcBut;

    @FXML
    private Button calcButt;

    @FXML
    private TableView<HistoryElemDto> calcincResultTable;

    @FXML
    private Text daysText;

    @FXML
    private TextField inputDateFrom;

    @FXML
    private TextField inputDateTill;

    @FXML
    private Pane mainFrame;

    @FXML
    private Button removeToCalcBut;

    @FXML
    private Text resultFieldText;

    @FXML
    private TableView<Stocks> stocksInCalcTable;

    @FXML
    private TableView<Stocks> stocksInTable;


    @FXML
    private Text froms;

    @FXML
    private Text tills;

    @FXML
    private Button closeBut;


    @FXML
    void handleCloseBut(ActionEvent event) {
        Stage stage = (Stage) closeBut.getScene().getWindow();
        stage.close();
    }

    @FXML
    void handleAddToCalcBut(ActionEvent event) {
        addToCalcStock();
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
                showAlert("Ошибка", "Не удалось выполнить расчет по выбранным акция");
                logger.error("Ошибка при обработке запроса расчета темпа роста акций!!");
                e.printStackTrace();
            }
        }
    }

    /**
     * Метод необходимый для сбора информации для расчета, отправки запроса на сервер, обработки ответа и заполнения
     * таблицы данными
     * @param from начала периода
     * @param till конец периода
     * @throws IOException пробросить исключение
     */
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
            HistoryDto historyDto = gson.fromJson(response.toString(), HistoryDto.class);

            historyDtoLocale = historyDto;


            calcincResultTable.getItems().setAll(historyDtoLocale.getHistoryElemDto());

            froms.setText(historyDtoLocale.getFrom());
            tills.setText(historyDtoLocale.getTill());
            daysText.setText(String.valueOf(historyDtoLocale.getDaysCalendar()));
            resultFieldText.setText(historyDtoLocale.getResult());


        }
    }

    /**
     * Метод с помощью которого осуществляется перемещение из таблицы "Список акций к расчету" в таблицу
     * "Список акций доступных к расчету"
     */
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

    /**
     * Метод с помощью которого осуществляется перемещение из таблицы "Список акций доступных к расчету"
     * в таблицу "Список акций к расчету"
     */
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

    /**
     * Метод инициализации окна "Расчет доходности"
     * @param username
     */
    public void initialize(String username) {
        this.username = username;
        inputDateFrom.setText("2024-01-18");
        inputDateTill.setText("2024-01-25");
        createColumnA();
        createColumnB();
        createColumnC();
        getAllStock();
    }

    /**
     * Метод, который запрашивает список акций портфеля конкретного пользователя
     */
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

    /**
     * Метод с помощью которого преобразуется ответ от сервера в формате Json в список объектов Stocks
     * @param response json в виде строки
     * @return List<Stocks> список акций полученный из json
     */
    public static List<Stocks> parseResponseToList(String response) {
        Gson gson = new Gson();
        Type stocksListType = new TypeToken<List<Stocks>>() {
        }.getType();
        List<Stocks> stocksList = gson.fromJson(response, stocksListType);
        return stocksList;
    }

    /**
     * Отобразить alert
     * @param title заголовок окна
     * @param text текст окна
     */
    public void showAlert(String title, String text) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(text);
        alert.showAndWait();
    }

    /**
     * Инициализация колонками таблицы "Список акций к расчету"
     */
    public void createColumnA() {
        TableColumn<Stocks, String> secIdColumn = new TableColumn<>("SEC ID");
        secIdColumn.setCellValueFactory(new PropertyValueFactory<>("secId"));

        TableColumn<Stocks, String> shortNameColumn = new TableColumn<>("Short Name");
        shortNameColumn.setCellValueFactory(new PropertyValueFactory<>("shortname"));

        stocksInCalcTable.getColumns().setAll(secIdColumn, shortNameColumn);
    }
    /**
     * Инициализация колонками таблицы "Список акций доступных к расчету"
     */
    public void createColumnB() {
        TableColumn<Stocks, String> secIdColumn = new TableColumn<>("SEC ID");
        secIdColumn.setCellValueFactory(new PropertyValueFactory<>("secId"));

        TableColumn<Stocks, String> shortNameColumn = new TableColumn<>("Short Name");
        shortNameColumn.setCellValueFactory(new PropertyValueFactory<>("shortname"));
        stocksInTable.getColumns().setAll(secIdColumn, shortNameColumn);
    }

    /**
     * Инициализация колонками таблицы "Результат расчета"
     */
    private void createColumnC() {
        TableColumn<HistoryElemDto, String> secIdColumn = new TableColumn<>("Date");
        secIdColumn.setCellValueFactory(new PropertyValueFactory<>("date"));

        TableColumn<HistoryElemDto, String> shortNameColumn = new TableColumn<>("Short Name");
        shortNameColumn.setCellValueFactory(new PropertyValueFactory<>("shortName"));

        TableColumn<HistoryElemDto, String> growthColumn = new TableColumn<>("Growth");
        growthColumn.setCellValueFactory(new PropertyValueFactory<>("growth"));

        calcincResultTable.getColumns().setAll(secIdColumn, shortNameColumn, growthColumn);
    }


}