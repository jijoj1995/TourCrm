package controller.History;

import db.BillingService;
import db.CustomerService;
import dto.Customer;
import dto.Item;
import dto.PaymentHistoryList;
import dto.SupplierPaymentHistoryList;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
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

public class PaymentItemHistory implements Initializable {

    @FXML
    AnchorPane outerPane;
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
    TextField customerName = new TextField();
    @FXML
    TextField customerPhone = new TextField();
    @FXML
    TextField customerEmail = new TextField();

    @FXML
    TextArea customerAddress = new TextArea();
    String customerPaymentId ="";
    @FXML
    ObservableList<Item> data;


    Customer customer;
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        initializeDefaultLayout();
        customerName.setDisable(true);
        customerPhone.setDisable(true);
        customerEmail.setDisable(true);
        customerAddress.setDisable(true);

        TableColumn name = new TableColumn("Name");
        TableColumn unit_price = new TableColumn("Price Per Unit");

        TableColumn enter_quantity = new TableColumn("Enter Quantity");
        TableColumn price = new TableColumn("Price");

        table.getColumns().addAll(name, unit_price, enter_quantity, price);

        name.setCellValueFactory(
                new PropertyValueFactory<Item, String>("name")
        );
        unit_price.setCellValueFactory(
                new PropertyValueFactory<Item, String>("rate")
        );


        enter_quantity.setCellValueFactory(
                new PropertyValueFactory<Item, String>("qty")
        );
        price.setCellValueFactory(
                new PropertyValueFactory<Item, String>("price")
        );

        table.setItems(data);
    }

    public void initialize(PaymentHistoryList paymentHistoryListDto) {

        try{
            customer= new CustomerService().getCustomer(paymentHistoryListDto.getCustomerId());
        }
        catch (Exception e){
            e.printStackTrace();
        }
        customerName.setText(customer.getName());
        customerPhone.setText(customer.getMobileNumber());
        customerEmail.setText(customer.getEmail());
        customerAddress.setText(customer.getAddress());
        totalBillAmount.setText(String.valueOf(paymentHistoryListDto.getTotalPrice()));
        billPayedAmount.setText(String.valueOf(paymentHistoryListDto.getPayedAmount()));
        billCreatedDate.setText(paymentHistoryListDto.getCreatedOn());

        try {
            data = FXCollections.observableArrayList(new BillingService().getItemList(paymentHistoryListDto.getPaymentId()));
        }
        catch (Exception e){
            e.printStackTrace();
        }
        table.setItems(data);
    }

    @FXML
    private void showPaymentHistoryPage() throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/view/History/PaymentHistory.fxml"));
        outerPane.getChildren().setAll(root);
    }
    private void initializeDefaultLayout(){
        outerPane.setPrefWidth(Main.WIDTH-Main.SIDE_BAR_WIDTH);
        outerPane.setPrefHeight(Main.HEIGHT-30);
    }
}
