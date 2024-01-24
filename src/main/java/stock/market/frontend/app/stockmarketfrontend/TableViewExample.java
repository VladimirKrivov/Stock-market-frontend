package stock.market.frontend.app.stockmarketfrontend;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import static javafx.application.Application.launch;

public class TableViewExample extends Application {

    private ObservableList<Stocks> data = FXCollections.observableArrayList(
            new Stocks("SEC1", "Shortname1", "RegNum1", "Name1", "ISIN1"),
            new Stocks("SEC2", "Shortname2", "RegNum2", "Name2", "ISIN2"),
            new Stocks("SEC3", "Shortname3", "RegNum3", "Name3", "ISIN3"),
            new Stocks("SEC4", "Shortname4", "RegNum4", "Name4", "ISIN4"),
            new Stocks("SEC5", "Shortname5", "RegNum5", "Name5", "ISIN5")
    );

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) {
        TableView<Stocks> tableView = new TableView<>();

        TableColumn<Stocks, String> secIdCol = new TableColumn<>("SecId");
        secIdCol.setCellValueFactory(new PropertyValueFactory<>("secId"));

        TableColumn<Stocks, String> shortnameCol = new TableColumn<>("Shortname");
        shortnameCol.setCellValueFactory(new PropertyValueFactory<>("shortname"));

        TableColumn<Stocks, String> regNumberCol = new TableColumn<>("RegNumber");
        regNumberCol.setCellValueFactory(new PropertyValueFactory<>("regNumber"));

        TableColumn<Stocks, String> nameCol = new TableColumn<>("Name");
        nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));

        TableColumn<Stocks, String> isinCol = new TableColumn<>("ISIN");
        isinCol.setCellValueFactory(new PropertyValueFactory<>("isin"));

        tableView.getColumns().addAll(secIdCol, shortnameCol, regNumberCol, nameCol, isinCol);

        tableView.setItems(data);

        Scene scene = new Scene(tableView);
        stage.setScene(scene);
        stage.show();
    }


//    Вложенный класс Stocks
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