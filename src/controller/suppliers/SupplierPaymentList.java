package controller.suppliers;

import db.BillingService;
import db.SupplierService;
import dto.Customer;
import dto.Payment;
import dto.PaymentList;
import dto.Supplier;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import service.Toast;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class SupplierPaymentList implements Initializable {

    @FXML
    TableView table;

    @FXML
    Button pay;

    @FXML
    Label totalamount;

    @FXML
    TextField payedamount;

    @FXML
    TextField name, phone;

    @FXML
    TextArea address;
    @FXML
    public AnchorPane outerPane;
    Payment payment;
    @FXML
    private AnchorPane innerPane;


    Supplier mainSupplier;
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
        name.setDisable(true);
        phone.setDisable(true);
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
                  /*  FXMLLoader Loader = new FXMLLoader();
                    Loader.setLocation(getClass().getResource("/view/billing/singlepaymentUpdate.fxml"));
                    try {
                        Loader.load();
                    } catch (Exception e) {
                        e.printStackTrace();
                        return;
                    }
*/
               /*     PaymentSingle paymentSingle = Loader.getController();
                    paymentSingle.initialize(mainSupplier, paymentList.getPendingAmount(), new Payment(Integer.parseInt(paymentList.getPaymentId()), Integer.parseInt(mainSupplier.getSupplierId()),
                            paymentList.getTotalPrice(),paymentList.getPayedAmount(),
                            paymentList.getPendingAmount(),paymentList.getCreatedOn()));
                    Parent p = Loader.getRoot();
                    outerPane.getChildren().setAll(p);*/

                    Stage stage=(Stage) innerPane.getScene().getWindow();

                    //saving transaction
                    if(new SupplierService().paySingleTransaction(paymentList,Integer.parseInt(paymentList.getPendingAmount()),mainSupplier)){
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
            //payment = supplierService.getPaymentObject(paymentList.get(0).getPaymentId());
            //save all transactions
            if(new SupplierService().payAllAmount(paymentList, Integer.parseInt(payedamount.getText()), mainSupplier)){
                //if successful
                Toast.makeText(stage, "PAYMENT SUCCESSFUL.", 3000, 500, 500);
                showSupplierList();
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

    public void setSupplierDetails(Supplier supplier, String pendingAmount) {
        mainSupplier = supplier;
        name.setText(mainSupplier.getName());
        phone.setText(mainSupplier.getMobileNumber());
        address.setText(mainSupplier.getAddress());
        totalamount.setText(pendingAmount);
        masterData.addAll(new SupplierService().getPaymentRecordsList(supplier.getSupplierId()));
        table.setItems(masterData);
    }

    @FXML
    private void showSupplierList()  {
        try {
            innerPane = FXMLLoader.load(getClass().getResource("/view/suppliers/listSuppliers.fxml"));
            outerPane.getChildren().setAll(innerPane);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
