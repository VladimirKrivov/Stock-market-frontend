package stock.market.frontend.app.stockmarketfrontend.controller;

import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import stock.market.frontend.app.stockmarketfrontend.models.HistoryDto;
import stock.market.frontend.app.stockmarketfrontend.models.HistoryElemDto;

import java.util.List;

/**
 * Контроллер окна "Просмотр запроса"
 */
public class ViewHistoryFrameController {

    private HistoryDto historyDtoLocal;
    private String username;

    @FXML
    private Text daysText;

    @FXML
    private Text froms;

    @FXML
    private Pane mainFrame;

    @FXML
    private Text resultFieldText;

    @FXML
    private Text tills;

    @FXML
    private TableView<HistoryElemDto> viewCalcincResultTable;

    /**
     * * Метод инициализации окна "Просмотр истории". Задействуется во время запуска окна
     * @param username имя пользователя
     */
    public void initialize(String username, HistoryDto selectDto) {
        historyDtoLocal = selectDto;
        this.username = username;

        initTableColumn();
        initTable();
    }

    /**
     * Инициализация полей окна и строк таблицы
     */
    private void initTable() {
        List<HistoryElemDto> elems = historyDtoLocal.getHistoryElemDto();
        viewCalcincResultTable.getItems().setAll(elems);
        froms.setText(historyDtoLocal.getFrom());
        tills.setText(historyDtoLocal.getTill());
        daysText.setText(String.valueOf(historyDtoLocal.getDaysCalendar()));
        resultFieldText.setText(historyDtoLocal.getResult());
    }

    /**
     * Инициализация таблицы колонками
     */
    private void initTableColumn() {
        TableColumn<HistoryElemDto, String> secIdColumn = new TableColumn<>("Date");
        secIdColumn.setCellValueFactory(new PropertyValueFactory<>("date"));

        TableColumn<HistoryElemDto, String> shortNameColumn = new TableColumn<>("Short Name");
        shortNameColumn.setCellValueFactory(new PropertyValueFactory<>("shortName"));

        TableColumn<HistoryElemDto, String> growthColumn = new TableColumn<>("Growth");
        growthColumn.setCellValueFactory(new PropertyValueFactory<>("growth"));

        viewCalcincResultTable.getColumns().setAll(secIdColumn, shortNameColumn, growthColumn);
    }
}
