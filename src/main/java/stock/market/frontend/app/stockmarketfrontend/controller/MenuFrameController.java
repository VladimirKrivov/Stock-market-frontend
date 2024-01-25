package stock.market.frontend.app.stockmarketfrontend.controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import stock.market.frontend.app.stockmarketfrontend.models.Stocks;
import stock.market.frontend.app.stockmarketfrontend.service.StocksAdapter;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


public class MenuFrameController {

    List<Stocks> stocksListTableItem = new ArrayList<>();


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
    void ClickHistory(ActionEvent event) {


        // Установка списка элементов для таблицы

    }

    @FXML
    void deletCollumn(ActionEvent event) {
        // Получаем выбранную строку
//        TableView<Stocks> tableView = (TableView<Stocks>) event.getSource();
        ObservableList<Stocks> selectedItems = tableContent.getSelectionModel().getSelectedItems();


        // Удаляем выбранные строки
        tableContent.getItems().removeAll(selectedItems);


    }


    @FXML
    private void handleAddStockButt() {
        String searchValue = inputSearch.getText();
        String url = "http://localhost:8080/api/v1/stock/find?name=" + searchValue;

        try {
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
                    System.out.println(response);
                }
                responseReader.close();

                System.out.println(response);

                // Обработка полученного JSON
                Stocks stocks = parseJson(response.toString());

                if (!tableContent.getItems().contains(stocks)) {
                    stocksListTableItem.add(stocks);
                    tableContent.getItems().setAll(stocksListTableItem);

                } else {
                    showAlert("Error", "Акция: \"" + stocks.getName() +"\", по запросу: " + searchValue + " уже есть в списке!" );
                }

            } else {
                System.out.println("Ошибка при выполнении запроса. Код ошибки: " + responseCode);
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

    public void initialize() {
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


    public Stocks parseJson(String response) {
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(Stocks.class, new StocksAdapter())
                .create();

        return gson.fromJson(response, Stocks.class);

    }

}