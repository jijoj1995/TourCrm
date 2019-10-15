package controller.billing;

import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import db.BillingService;
import dto.Customer;
import dto.Payment;
import dto.PaymentList;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import main.Main;
import service.Toast;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class PaymentUpdate implements Initializable {

    @FXML
    TableView table;

    @FXML
    Button pay;

    @FXML
    Label totalamount;

    @FXML
    TextField payedamount;

    @FXML
    JFXTextField name, phone, email;

    @FXML
    JFXTextArea address;
    @FXML
    public AnchorPane outerPane;
    Payment payment;
    @FXML
    private AnchorPane innerPane;


    Customer Cst;
    BillingService BService = new BillingService();
    @FXML
    private TableColumn<PaymentList, String> dateColumn;
    @FXML
    private TableColumn<PaymentList, String> billingDate;
    @FXML
    private TableColumn<PaymentList, String> totalAmountColumn;
    @FXML
    private TableColumn<PaymentList, String> payeAmountColumn;
    @FXML
    private TableColumn<PaymentList, String> pendingAmountColumn;
    @FXML
    private TableColumn<PaymentList, PaymentList> actionColumn;
    private ObservableList<PaymentList> masterData = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        initializeDefaultLayout();
        name.setDisable(true);
        phone.setDisable(true);
        email.setDisable(true);
        address.setDisable(true);

        billingDate.setCellValueFactory(cellData -> cellData.getValue().billingDateProperty());
        dateColumn.setCellValueFactory(cellData -> cellData.getValue().createdOnProperty());
        totalAmountColumn.setCellValueFactory(cellData -> cellData.getValue().totalPriceProperty());
        payeAmountColumn.setCellValueFactory(cellData -> cellData.getValue().payedAmountProperty());
        pendingAmountColumn.setCellValueFactory(cellData -> cellData.getValue().pendingAmountProperty());
        actionColumn.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue()));
        actionColumn.setCellFactory(param -> new TableCell<PaymentList, PaymentList>() {
            private final Button payButton = new Button();

            @Override
            protected void updateItem(PaymentList paymentList, boolean empty) {
                super.updateItem(paymentList, empty);
                if (paymentList == null) {
                    setGraphic(null);
                    return;
                }
                payButton.setText("Pay");
                setGraphic(payButton);
                payButton.setOnAction(event -> {
                   /* FXMLLoader Loader = new FXMLLoader();
                    Loader.setLocation(getClass().getResource("/view/billing/singlepaymentUpdate.fxml"));
                    try {
                        Loader.load();
                    } catch (Exception e) {
                        e.printStackTrace();
                        return;
                    }
                    PaymentSingle paymentSingle = Loader.getController();
                    paymentSingle.initialize(Cst, paymentList.getPendingAmount(), new Payment(Integer.parseInt(paymentList.getPaymentId()), Integer.parseInt(Cst.getCustomerId()),
                            paymentList.getTotalPrice(),paymentList.getPayedAmount(),
                            paymentList.getPendingAmount(),paymentList.getCreatedOn(),paymentList.getBillingDate()));
                    Parent p = Loader.getRoot();
                    outerPane.getChildren().setAll(p);
*/
                    Stage stage=(Stage) outerPane.getScene().getWindow();

                    //saving transaction
                    payment = new Payment(Integer.parseInt(paymentList.getPaymentId()), Integer.parseInt(Cst.getCustomerId()),
                            paymentList.getTotalPrice(),paymentList.getPayedAmount(),
                            paymentList.getPendingAmount(),paymentList.getCreatedOn(),paymentList.getBillingDate());
                    if(BService.paysingle(payment,Integer.parseInt(paymentList.getPendingAmount()))){
                        //transaction successful
                        Toast.makeText(stage,"Payment Successful.",1000,500,500);
                        masterData.removeAll(paymentList);
                    }
                    else {
                        Toast.makeText(stage,"Unable to save Payment.",1000,500,500);
                        return;
                    }
                });
            }
        });
        table.setItems(masterData);

        pay.setOnAction(event -> {
            Stage stage = (Stage) innerPane.getScene().getWindow();
            ArrayList<PaymentList> paymentList = new ArrayList<>();
            for (PaymentList data : masterData) {
                paymentList.add(data);
            }
            payment = BService.getPaymentObject(paymentList.get(0).getPaymentId());

            //save all transactions
            if(BService.payAllAmount(paymentList, Integer.parseInt(payedamount.getText()), payment)){
                //if successful
                Toast.makeText(stage, "PAYMENT SUCCESSFUL.", 3000, 500, 500);
                showCustomerList();
            }
            else{
                Toast.makeText(stage, "Some Error while saving transaction.", 3000, 500, 500);
            }

        });

        pay.setDisable(true);
        payedamount.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.length() == 0) {
                pay.setDisable(true);
            } else if (newValue.matches("[0-9]*[a-zA-Z]+[0-9]*")) {
                pay.setDisable(true);
            } else if (Integer.parseInt(newValue) <= Integer.parseInt(totalamount.getText())) {
                pay.setDisable(false);
            } else if (Integer.parseInt(newValue) >= Integer.parseInt(totalamount.getText())) {
                payedamount.setText(totalamount.getText());
                pay.setDisable(false);
            } else {
                pay.setDisable(true);

            }

        });
    }

    public void setCst(Customer cst, String pendingAmount) {
        Cst = cst;
        name.setText(Cst.getName());
        phone.setText(Cst.getMobileNumber());
        address.setText(Cst.getAddress());
        email.setText(Cst.getEmail());
        totalamount.setText(pendingAmount);
        masterData.addAll(BService.getPaymentRecordsList(Integer.parseInt(cst.getCustomerId())));
        table.setItems(masterData);
    }

    @FXML
    private void showCustomerList()  {
        try {
            innerPane = FXMLLoader.load(getClass().getResource("/view/customer/listCustomer.fxml"));
            outerPane.getChildren().setAll(innerPane);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    private void initializeDefaultLayout(){
        outerPane.setPrefWidth(Main.WIDTH-Main.SIDE_BAR_WIDTH);
        outerPane.setPrefHeight(Main.HEIGHT-30);
    }
}
