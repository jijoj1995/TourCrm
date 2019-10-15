package controller.suppliers;

import db.SupplierService;
import dto.SupplierList;
import dto.Supplier;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import main.Main;
import org.apache.log4j.Logger;
import service.Toast;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ListSuppliers implements Initializable {

    @FXML
    private AnchorPane supplierListOuterPane;
    @FXML
    private TextField filterField;
    @FXML
    private Button add;
    @FXML
    private TableView<SupplierList> supplierTable;
    @FXML
    private TableColumn<SupplierList, Number> idColumn;
    @FXML
    private TableColumn<SupplierList, SupplierList> nameColumn;
    @FXML
    private TableColumn<SupplierList, String> addressColumn;
    @FXML
    private TableColumn<SupplierList, String> mobileColumn;
    @FXML
    private TableColumn<SupplierList, SupplierList> pendingAmountColumn;

    private ObservableList<SupplierList> masterData = FXCollections.observableArrayList();
    private Logger logger=Logger.getLogger(ListSuppliers.class);


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initializeDefaultLayout();
        masterData.addAll( new SupplierService().getSupplierList());

        // 0. Initialize the columns.
        nameColumn.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue()));
        nameColumn.setCellFactory(param -> new TableCell<SupplierList, SupplierList>() {
            private final Button payButton = new Button();

            @Override
            protected void updateItem(SupplierList supplierList, boolean empty) {
                super.updateItem(supplierList, empty);
                if (supplierList == null) {
                    setGraphic(null);
                    return;
                }
                payButton.setStyle("-fx-text-fill: #006464;\n" +
                        "    -fx-background-color: #b8ffb0;\n" +
                        "    -fx-border-radius: 20;\n" +
                        "    -fx-background-radius: 20;\n" +
                        "    -fx-padding: 5;-fx-min-width:100");
                payButton.setText(supplierList.getName());
                setGraphic(payButton);
                payButton.setOnAction(event -> {
                    FXMLLoader Loader = new FXMLLoader();
                    Loader.setLocation(getClass().getResource("/view/suppliers/SupplierBilling.fxml"));
                    try {
                        Loader.load();

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    SupplierBilling supplierBillingController = Loader.getController();
                    Supplier supplier = new Supplier(supplierList.getSupplierId(),supplierList.getName(), supplierList.getAddress(), supplierList.getMobileNumber());
                    supplier.setSupplierId(supplierList.getSupplierId());
                   supplierBillingController.setMainSupplier(supplier);
                    Parent p = Loader.getRoot();
                    supplierListOuterPane.getChildren().setAll(p);
                });
            }
        });
        idColumn.setCellValueFactory(cellData -> cellData.getValue().supplierIdProperty());
        addressColumn.setCellValueFactory(cellData -> cellData.getValue().addressProperty());
        mobileColumn.setCellValueFactory(cellData -> cellData.getValue().mobileNumberProperty());
        pendingAmountColumn.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue()));
        pendingAmountColumn.setCellFactory(param -> new TableCell<SupplierList, SupplierList>() {
            private final Button payButton = new Button();

            @Override
            protected void updateItem(SupplierList SupplierList, boolean empty) {
                super.updateItem(SupplierList, empty);
                if (SupplierList == null) {
                    setGraphic(null);
                    return;
                }
                payButton.setText(SupplierList.getPendingAmount());
                if (SupplierList.getPendingAmount().equalsIgnoreCase("0.0") || SupplierList.getPendingAmount().equalsIgnoreCase("0")) {
                    payButton.setDisable(true);
                }
                payButton.setStyle("-fx-text-fill: #006464;\n" +
                        "    -fx-background-color: #DFB951;\n" +
                        "    -fx-border-radius: 20;\n" +
                        "    -fx-background-radius: 20;\n" +
                        "    -fx-padding: 5;-fx-min-width:100");
                setGraphic(payButton);
                payButton.setOnAction(event -> {
                    FXMLLoader Loader = new FXMLLoader();
                    Loader.setLocation(getClass().getResource("/view/suppliers/supplierPaymentList.fxml"));
                    try {
                        Loader.load();

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    SupplierPaymentList supplierPaymentListDto = Loader.getController();
                    Supplier mainSupplier = new Supplier(SupplierList.getName(), SupplierList.getAddress(), SupplierList.getMobileNumber());
                    mainSupplier.setSupplierId(SupplierList.getSupplierId());
                    supplierPaymentListDto.setSupplierDetails(mainSupplier, SupplierList.getPendingAmount());
                    Parent p = Loader.getRoot();
                    supplierListOuterPane.getChildren().setAll(p);

                });
            }
        });


        // 1. Wrap the ObservableList in a FilteredList (initially display all data).
        FilteredList<SupplierList> filteredData = new FilteredList<>(masterData, p -> true);

        // 2. Set the filter Predicate whenever the filter changes.
        filterField.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(person -> {
                // If filter text is empty, display all persons.
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }

                // Compare first name and last name of every person with filter text.
                String lowerCaseFilter = newValue.toLowerCase();

                if (person.getName().toLowerCase().indexOf(lowerCaseFilter) != -1) {
                    return true; // Filter matches first name.
                } else // Filter matches mobile.
                    if (person.getAddress().toLowerCase().indexOf(lowerCaseFilter) != -1) {
                        return true; // Filter matches address.
                    }  else // Filter matches mobile.
                            if (person.getPendingAmount().toLowerCase().indexOf(lowerCaseFilter) != -1) {
                                return true; // Filter matches address.
                            } else return person.getMobileNumber().toLowerCase().indexOf(lowerCaseFilter) != -1;
            });
        });

        // 3. Wrap the FilteredList in a SortedList.
        SortedList<SupplierList> sortedData = new SortedList<>(filteredData);

        // 4. Bind the SortedList comparator to the TableView comparator.
        // 	  Otherwise, sorting the TableView would have no effect.
        sortedData.comparatorProperty().bind(supplierTable.comparatorProperty());

        // 5. Add sorted (and filtered) data to the table.
        supplierTable.setItems(sortedData);

    }

    @FXML
    private void addSupplierPage(){
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/view/suppliers/SupplierBilling.fxml"));
            supplierListOuterPane.getChildren().setAll(root);
        } catch (IOException ex) {
            logger.warn("error while opening page "+ex.getMessage());
        }
    }

    private void initializeDefaultLayout(){
        supplierListOuterPane.setPrefWidth(Main.WIDTH-Main.SIDE_BAR_WIDTH);
        supplierListOuterPane.setPrefHeight(Main.HEIGHT-30);
    }


}

