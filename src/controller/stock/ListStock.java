package controller.stock;


import com.jfoenix.controls.JFXButton;
import db.StockService;
import dto.Stock;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import main.Main;
import service.Toast;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

@SuppressWarnings("unused")
public class ListStock implements Initializable {

    @FXML
    private AnchorPane stockListAnchorPane;
    @FXML
    private Button addStockButton;
    @FXML
    private Pane stockInnerPane;
    @FXML
    private TextField filterField;
    @FXML
    private TableView<Stock> stockTable;
    @FXML
    private TableColumn<Stock, Number> stockIdColumn;
    @FXML
    private TableColumn<Stock, Stock> stockNameColumn;
    @FXML
    private TableColumn<Stock, String> stockCategoryColumn;
    @FXML
    private TableColumn<Stock, Number> stockLeftColumn;
    @FXML
    private TableColumn<Stock, Number> stockOriginalPriceColumn;
    @FXML
    private TableColumn<Stock, Number> stockSellingPriceColumn;
    @FXML
    private TableColumn<Stock,String> stockSupplierColumn;



    private ObservableList<Stock> masterData = FXCollections.observableArrayList();

    public ListStock(){
        masterData.addAll( new StockService().getStockList());

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initialize();
    }

    @FXML
    private void initialize() {
        initializeDefaultLayout();
        // 0. Initialize the columns.
        // Stage stage=(Stage) stockListAnchorPane.getScene(s).getWindow();
        stockListAnchorPane.prefWidthProperty();
        stockIdColumn.setCellValueFactory(cellData -> cellData.getValue().stockIdProperty());

        stockCategoryColumn.setCellValueFactory(cellData -> cellData.getValue().categoryProperty());
        stockLeftColumn.setCellValueFactory(cellData -> cellData.getValue().stockLeftProperty());
        stockSellingPriceColumn.setCellValueFactory(cellData -> cellData.getValue().sellingPriceProperty());
        stockOriginalPriceColumn.setCellValueFactory(cellData -> cellData.getValue().originalPriceProperty());

        stockSupplierColumn.setCellValueFactory(cellData->cellData.getValue().supplierProperty());
       stockNameColumn.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue()));
        stockNameColumn.setCellFactory(cellData -> new TableCell<Stock,Stock>(){
            private final JFXButton payButton = new JFXButton();

            @Override
            protected void updateItem(Stock stock, boolean empty) {
                super.updateItem(stock, empty);
                if (stock == null) {
                    setGraphic(null);
                    return;
                }
                payButton.setStyle("-fx-text-fill: #006464;\n" +
                        "    -fx-background-color: #b8ffb0;\n" +
                        "    -fx-border-radius: 20;\n" +
                        "    -fx-background-radius: 20;\n" +
                        "    -fx-padding: 5;-fx-min-width:100");
                payButton.setText(stock.getName());
                setGraphic(payButton);
                payButton.setOnAction(event -> {
                    FXMLLoader Loader = new FXMLLoader();
                    Loader.setLocation(getClass().getResource("/view/stock/addStock.fxml"));
                    try {
                        Loader.load();

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    AddStock addStockPage = Loader.getController();
                    Stock tempStock = new Stock(stock.nameProperty(),stock.categoryProperty(),stock.supplierProperty(),stock.stockLeftProperty(),stock.sellingPriceProperty(),stock.originalPriceProperty());
                    tempStock.setStockId(stock.stockIdProperty().getValue());
                    addStockPage.setStockDto(stock);
                    Parent p = Loader.getRoot();
                    stockListAnchorPane.getChildren().setAll(p);

                });
            }
        });

        /* pendingAmountColumn.setCellValueFactory(cellData -> cellData.getValue().pendingAmountProperty());*/

        // 1. Wrap the ObservableList in a FilteredList (initially display all data).
        FilteredList<Stock> filteredData = new FilteredList<Stock>(masterData, p -> true);

        // 2. Set the filter Predicate whenever the filter changes.
        filterField.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(person -> {
                // If filter text is empty, display all persons.
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }

                // Compare first name and last name of every person with filter text.
                String lowerCaseFilter = newValue.toLowerCase();

                // Filter matches cattegory.
                if (person.getName().toLowerCase().indexOf(lowerCaseFilter) != -1) {
                    return true; // Filter matches first name.
                } else return person.getCategory().toLowerCase().indexOf(lowerCaseFilter) != -1;
            });
        });

        // 3. Wrap the FilteredList in a SortedList.
        SortedList<Stock> sortedData = new SortedList<>(filteredData);

        // 4. Bind the SortedList comparator to the TableView comparator.
        // 	  Otherwise, sorting the TableView would have no effect.
        sortedData.comparatorProperty().bind(stockTable.comparatorProperty());

        // 5. Add sorted (and filtered) data to the table.
        stockTable.setItems(sortedData);
    }

    @FXML
    private void showStockAddPage() throws IOException {
        stockInnerPane= FXMLLoader.load(getClass().getResource("/view/stock/addStock.fxml"));
        stockListAnchorPane.getChildren().setAll(stockInnerPane);
    }
    private void initializeDefaultLayout(){
        stockListAnchorPane.setPrefWidth(Main.WIDTH-Main.SIDE_BAR_WIDTH);
        stockListAnchorPane.setPrefHeight(Main.HEIGHT-30);
    }

}


