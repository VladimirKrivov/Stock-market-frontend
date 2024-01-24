package stock.market.frontend.app.stockmarketfrontend.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import stock.market.frontend.app.stockmarketfrontend.models.StockDto;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;


public class MenuFrameController {

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
//        tableContent.getColumns().add(secIdColumn);
//        tableContent.getColumns().add(shortNameColumn);
//        tableContent.getColumns().add(regNumberColumn);
//        tableContent.getColumns().add(nameColumn);
//        tableContent.getColumns().add(isinColumn);
        // Создание списка элементов для таблицы
        ObservableList<Stocks> stocksList = FXCollections.observableArrayList();

        // Добавление 5 элементов в список
        stocksList.add(new Stocks("1", "Stock 1", "12345", "Stock 1 Name", "ABC123"));
        stocksList.add(new Stocks("2", "Stock 2", "56789", "Stock 2 Name", "DEF456"));
        stocksList.add(new Stocks("3", "Stock 3", "98765", "Stock 3 Name", "GHI789"));
        stocksList.add(new Stocks("4", "Stock 4", "43210", "Stock 4 Name", "JKL012"));
        stocksList.add(new Stocks("5", "Stock 5", "67890", "Stock 5 Name", "MNO345"));


        // Установка списка элементов для таблицы
        tableContent.setItems(stocksList);
    }


    public static class Stocks {
        private String secId;
        private String shortname;
        private String regNumber;
        private String name;
        private String isin;

        public Stocks(String secId, String shortname, String regNumber, String name, String isin) {
            this.secId = secId;
            this.shortname = shortname;
            this.regNumber = regNumber;
            this.name = name;
            this.isin = isin;
        }

        public String getSecId() {
            return secId;
        }

        public String getShortname() {
            return shortname;
        }

        public String getRegNumber() {
            return regNumber;
        }

        public String getName() {
            return name;
        }

        public String getIsin() {
            return isin;
        }
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
//                StockDto stockDto = parseStockDto(response.toString());
                // Дальнейшая обработка объекта stockDto...

            } else {
                System.out.println("Ошибка при выполнении запроса. Код ошибки: " + responseCode);
            }

            connection.disconnect();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private StockDto parseStockDto(String json) {
        // Здесь нужно реализовать код для парсинга JSON и создания объекта StockDto
        // Например, можно использовать библиотеку GSON: https://github.com/google/gson
        return null;
    }

}



