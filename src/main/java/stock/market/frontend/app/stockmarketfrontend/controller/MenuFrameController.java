package stock.market.frontend.app.stockmarketfrontend.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;


public class MenuFrameController {

//    private ObservableList<Stocks> data = FXCollections.observableArrayList(
//            new Stocks("SEC1", "Shortname1", "RegNum1", "Name1", "ISIN1"),
//            new Stocks("SEC2", "Shortname2", "RegNum2", "Name2", "ISIN2"),
//            new Stocks("SEC3", "Shortname3", "RegNum3", "Name3", "ISIN3"),
//            new Stocks("SEC4", "Shortname4", "RegNum4", "Name4", "ISIN4"),
//            new Stocks("SEC5", "Shortname5", "RegNum5", "Name5", "ISIN5")
//    );


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


//        tableContent.getColumns().setAll(secIdCol, shortnameCol, regNumberCol, nameCol, isinCol);
        tableContent.getColumns().add(secIdColumn);
        tableContent.getColumns().add(shortNameColumn);
        tableContent.getColumns().add(regNumberColumn);
        tableContent.getColumns().add(nameColumn);
        tableContent.getColumns().add(isinColumn);
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



}



