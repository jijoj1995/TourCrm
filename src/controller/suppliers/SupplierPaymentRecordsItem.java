package controller.suppliers;

import db.SupplierService;
import dto.Supplier;
import dto.SupplierPaymentHistoryList;
import dto.SupplierPaymentItem;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import main.Main;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class SupplierPaymentRecordsItem implements Initializable{

    @FXML
    AnchorPane innerPane,outerPane;
    @FXML
    TableView table = new TableView();

    @FXML
    Button print;

    @FXML
    Label totalBillAmount = new Label();
    @FXML
    Label billPayedAmount = new Label();
    @FXML
    Label billCreatedDate = new Label();
    @FXML
    TextField supplierName = new TextField();
    @FXML
    TextField supplierPhone = new TextField();
    @FXML
    TextField customerEmail = new TextField();
    @FXML
    TextArea supplierAddress = new TextArea();
    String customerPaymentId ="";
    @FXML
    ObservableList<SupplierPaymentItem> data;


    Supplier mainSupplier;
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        initializeDefaultLayout();
        supplierName.setDisable(true);
        supplierPhone.setDisable(true);
        supplierAddress.setDisable(true);
        TableColumn name = new TableColumn("Name");
        TableColumn unit_price = new TableColumn("Price Per Unit");
        TableColumn enter_quantity = new TableColumn("Enter Quantity");
        TableColumn price = new TableColumn("Price");

        table.getColumns().addAll(name, unit_price, enter_quantity, price);

        name.setCellValueFactory(
                new PropertyValueFactory<SupplierPaymentItem, String>("itemName")
        );
        unit_price.setCellValueFactory(
                new PropertyValueFactory<SupplierPaymentItem, String>("itemPrice")
        );
        enter_quantity.setCellValueFactory(
                new PropertyValueFactory<SupplierPaymentItem, String>("itemQuantity")
        );
        price.setCellValueFactory(
                new PropertyValueFactory<SupplierPaymentItem, String>("totalAmount")
        );

        table.setItems(data);
    }

    public void initialize(SupplierPaymentHistoryList paymentHistoryListDto) {

        try{
            mainSupplier = new SupplierService().getSupplier(paymentHistoryListDto.getSupplierId());
        }
        catch (Exception e){
            e.printStackTrace();
        }
        supplierName.setText(mainSupplier.getName());
        supplierPhone.setText(mainSupplier.getMobileNumber());
        supplierAddress.setText(mainSupplier.getAddress());
        totalBillAmount.setText(String.valueOf(paymentHistoryListDto.getTotalPrice()));
        billPayedAmount.setText(String.valueOf(paymentHistoryListDto.getPayedAmount()));
        billCreatedDate.setText(paymentHistoryListDto.getCreatedOn());

        try {

            data = FXCollections.observableArrayList(new SupplierService().getItemList(paymentHistoryListDto.getPaymentId()));
        }
        catch (Exception e){
            e.printStackTrace();
        }
        table.setItems(data);
    }

    @FXML
    private void showPaymentRecordsPage(){
        FXMLLoader Loader = new FXMLLoader();
        Loader.setLocation(getClass().getResource("/view/suppliers/supplierPaymentRecords.fxml"));
        try {
            Loader.load();
        } catch (Exception e) {
            e.printStackTrace();
        }
        SupplierPaymentRecords supplierPaymentRecords = Loader.getController();
        supplierPaymentRecords.initializeSupplier(mainSupplier);
        Parent p = Loader.getRoot();
        outerPane.getChildren().setAll(p);
    }

    private void initializeDefaultLayout(){
        outerPane.setPrefWidth(Main.WIDTH-Main.SIDE_BAR_WIDTH);
        outerPane.setPrefHeight(Main.HEIGHT-30);
    }
}
