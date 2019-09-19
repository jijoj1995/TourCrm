package controller.History;

import db.BillingService;
import db.SupplierService;
import dto.PaymentHistoryList;
import dto.SupplierPaymentHistoryList;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import main.Main;
import service.Toast;
import service.Validator;

import java.net.URL;
import java.util.ResourceBundle;

public class SupplierPaymentHistory implements Initializable {

    @FXML
    TableView table;

    BillingService BService = new BillingService();
    @FXML
    private TableColumn<SupplierPaymentHistoryList, String> createdOnDate;
    @FXML
    private TableColumn<SupplierPaymentHistoryList, String> nameColumn;
    @FXML
    private TableColumn<SupplierPaymentHistoryList, Number> totalAmountColumn;
    @FXML
    private TableColumn<SupplierPaymentHistoryList, Number> payedAmountColumn;
    @FXML
    private TableColumn<SupplierPaymentHistoryList, SupplierPaymentHistoryList> paymentIdColumn;
    @FXML
    private TableColumn<SupplierPaymentHistoryList, Number> pendingAmountColumn;
    @FXML
    private TableColumn<SupplierPaymentHistoryList, String> billingDateColumn;
    @FXML
    private TextField filterField,rowNumberField;

    @FXML
    private AnchorPane paymentHistoryMainPane;
    @FXML
    Stage stage;
    private ObservableList<SupplierPaymentHistoryList> masterData = FXCollections.observableArrayList();

    @FXML
    private Button rowsUpdateButton;
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        initializeDefaultLayout();
        rowNumberField.setOnKeyTyped(new EventHandler<KeyEvent>()
        {                       //allow only numbers
            public void handle(final KeyEvent keyEvent)
            {
                Validator.checkNumberEntered(keyEvent);
            }
        });

        masterData.addAll(new SupplierService().getSupplierPaymentHistoryList(100));
        nameColumn.setCellValueFactory(cellData -> cellData.getValue().supplierNameProperty());
        createdOnDate.setCellValueFactory(cellData -> cellData.getValue().createdOnProperty());
        totalAmountColumn.setCellValueFactory(cellData -> cellData.getValue().totalPriceProperty());
        payedAmountColumn.setCellValueFactory(cellData -> cellData.getValue().payedAmountProperty());
        pendingAmountColumn.setCellValueFactory(cellData -> cellData.getValue().pendingAmountProperty());
        billingDateColumn.setCellValueFactory(cellData -> cellData.getValue().billingDateProperty());

        paymentIdColumn.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue()));
        paymentIdColumn.setCellFactory(param -> new TableCell<SupplierPaymentHistoryList, SupplierPaymentHistoryList>() {
            private final Button payButton = new Button();

            @Override
            protected void updateItem(SupplierPaymentHistoryList supplierPaymentHistoryList, boolean empty) {
                super.updateItem(supplierPaymentHistoryList, empty);
                if (supplierPaymentHistoryList == null) {
                    setGraphic(null);
                    return;
                }
                payButton.setText(String.valueOf(supplierPaymentHistoryList.getPaymentId()));

                payButton.setStyle("-fx-text-fill: #006464;\n" +
                        "    -fx-background-color: #DFB951;\n" +
                        "    -fx-border-radius: 20;\n" +
                        "    -fx-background-radius: 20;\n" +
                        "    -fx-padding: 5;-fx-min-width:100");
                setGraphic(payButton);
                payButton.setOnAction(event -> {
                    FXMLLoader Loader = new FXMLLoader();
                    Loader.setLocation(getClass().getResource("/view/History/SupplierPaymentItemsHistory.fxml"));
                    try {
                        Loader.load();

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    SupplierPaymentItemsHistory supplierPaymentItemsHistory = Loader.getController();
                    supplierPaymentItemsHistory.initialize(supplierPaymentHistoryList);

                    Parent p = Loader.getRoot();
                    paymentHistoryMainPane.getChildren().setAll(p);

                });
            }
        });

        FilteredList<SupplierPaymentHistoryList> filteredData = new FilteredList<>(masterData, p -> true);

        // 2. Set the filter Predicate whenever the filter changes.
        filterField.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(payment -> {
                // If filter text is empty, display all persons.
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }

                // Compare first supplierName and last supplierName of every person with filter text.
                String lowerCaseFilter = newValue.toLowerCase();

                if (String.valueOf(payment.getPaymentId()).toLowerCase().indexOf(lowerCaseFilter) != -1) {
                    return true; // Filter matches first supplierName.
                } else if (payment.getSupplierName().toLowerCase().indexOf(lowerCaseFilter) != -1) {
                    return true; // Filter matches first supplierName.
                }   else // Filter matches mobile.
                    // Filter matches supplierAddress.
                    return false;
            });
        });

        // 3. Wrap the FilteredList in a SortedList.
        SortedList<SupplierPaymentHistoryList> sortedData = new SortedList<>(filteredData);

        // 4. Bind the SortedList comparator to the TableView comparator.
        // 	  Otherwise, sorting the TableView would have no effect.
        sortedData.comparatorProperty().bind(table.comparatorProperty());

        // 5. Add sorted (and filtered) data to the table.
        table.setItems(sortedData);
    }

    @FXML
    private void updatePaymentRows(){
        stage=(Stage) paymentHistoryMainPane.getScene().getWindow();

        //going to update table row number

        //check if user input is empty
        if(rowNumberField.getText().equals("")||rowNumberField.getText().equals("0")){
            Toast.makeText(stage,"Enter Number of rows",1000,500,500);
            return;
        }
        Integer rowNumber=Integer.parseInt(rowNumberField.getText());//number of rows to be shown

        //first remove all rows to show new rows, else it gets added on
        masterData.removeAll(masterData.sorted());

        //add rows from payment History table
        masterData.addAll(new SupplierService().getSupplierPaymentHistoryList(rowNumber));
        Toast.makeText(stage,"Rows Updated",500,500,500);
    }
    private void initializeDefaultLayout(){
        paymentHistoryMainPane.setPrefWidth(Main.WIDTH-Main.SIDE_BAR_WIDTH);
        paymentHistoryMainPane.setPrefHeight(Main.HEIGHT-30);
    }
}
