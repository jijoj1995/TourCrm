package controller.billing;

import constants.InventoryConstants;
import db.BillingService;
import db.ReportService;
import dto.Customer;
import dto.Item;
import dto.Payment;
import dto.ReportData;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import org.apache.log4j.Logger;
import service.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.ResourceBundle;


public class PaymentSingle implements Initializable {
    Logger logger=Logger.getLogger(PaymentSingle.class.getName());
    @FXML
    TableView table = new TableView();

    @FXML
    Button pay;

    @FXML
    Label amountpayed = new Label();

    @FXML
    TextField name = new TextField();
    @FXML
    TextField phone = new TextField();
    @FXML
    TextField email = new TextField();

    @FXML
    TextArea address = new TextArea();
    @FXML
    TextField amount= new TextField();
    @FXML
    private CheckBox printReportCheck;
    @FXML
            private AnchorPane paymentSingleOuterPane,paymentSingleInnerPane;
Customer customer;
    ReportService reportService=new ReportService();

    @FXML
    ObservableList<Item> data;
    BillingService BService = new BillingService();
    Payment paymentobj;
 InputStream media1 ;
  InputStream file;
  
    @Override
    public void initialize(URL url, ResourceBundle rb) {
                printReportCheck.setSelected(false);
                 media1 =this.getClass().getResourceAsStream("/resource/images/logo.jpg");
                 file =this.getClass().getResourceAsStream("/resource/reports/billingReport.jasper");
        pay.setDisable(true);
        amount.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.length() == 0) {
                pay.setDisable(true);
            } else if (newValue.matches("[0-9]*[a-zA-Z]+[0-9]*")) {
                pay.setDisable(true);
            } else if (Integer.parseInt(newValue) <= Integer.parseInt(amountpayed.getText())) {
                pay.setDisable(false);
            } else if (Integer.parseInt(newValue) >= Integer.parseInt(amountpayed.getText())) {
                amount.setText(amountpayed.getText());
                pay.setDisable(false);
            } else {
                pay.setDisable(true);

            }

        });
        name.setDisable(true);
        phone.setDisable(true);
        email.setDisable(true);
        address.setDisable(true);
        pay.setOnAction(event -> {
            Stage stage=(Stage) paymentSingleInnerPane.getScene().getWindow();

            //saving transaction
            if(BService.paysingle(paymentobj,Integer.parseInt(amount.getText()))){
                //transaction successful
                Toast.makeText(stage,"Payment Successful.",1000,500,500);
            }
            else {
                Toast.makeText(stage,"Unable to save Payment.",1000,500,500);
                return;
            }
                //check if user wants report
            if(printReportCheck.isSelected()) {
                try {
                    //going to print Report
                    if (printReport()) {  //ifSuccessful
                        // Toast.makeText(stage," Printing Successful",1000,500,500);
                        logger.info("payment and printing successful. now going back to customer list");
                        showCustomerList();
                    } else {
                        Toast.makeText(stage, "Payment Successful but Unable to print Report", 1000, 500, 500);
                    }
                } catch (Exception ex) {
                    logger.warn("error while printing report " + ex.getMessage());

                }
            }
            else {//go back to customer list
                showCustomerList();
            }
        });
        TableColumn name = new TableColumn("Name");
        TableColumn unit_price = new TableColumn("Price Per Unit");

        TableColumn enter_quantity = new TableColumn("Purchased Quantity");
        TableColumn price = new TableColumn("Total Price");

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

    public void initialize(Customer cst, String Payed, Payment payment) {
        customer=cst;
        name.setText(cst.getName());
        phone.setText(cst.getMobileNumber());
        email.setText(cst.getEmail());
        address.setText(cst.getAddress());
        amountpayed.setText(Payed);
        paymentobj=payment;
        data = FXCollections.observableArrayList(BService.getItemList(payment.getPayment_id()));
        table.setItems(data);
    }



    private boolean printReport() throws URISyntaxException{
        boolean isSuccessful=false;


        ArrayList<ReportData> dataList=new ArrayList<ReportData>();
        for(Item item :data){
            ReportData row=new ReportData(item.getStockId().toString(), item.getName(), item.getRate().toString(),
                    item.getQty(), item.getPrice().toString());
            dataList.add(row);
        }
        InputStream targetStream=null;
        HashMap<String,Object> prameter= new HashMap<>();
        try {
       
   
     targetStream= media1;

        }catch (Exception e){
            e.printStackTrace();
        }
        prameter.put("image_Logo",targetStream);
        prameter.put("titile",InventoryConstants.companyName);
        prameter.put("address1",InventoryConstants.companyAddress1);
        prameter.put("address2",InventoryConstants.companyAddress2);
        prameter.put("address3",InventoryConstants.companyAddress3);
        prameter.put("customerid",customer.getCustomerId());
        prameter.put("customername",customer.getName());
        prameter.put("mobileno",customer.getMobileNumber());
        prameter.put("email",customer.getEmail());
        prameter.put("address",customer.getAddress());
        prameter.put("totalamount",paymentobj.getPrice());
        prameter.put("payedamount",String.valueOf(Integer.valueOf(amount.getText())+Integer.valueOf(paymentobj.getPayed())));
        prameter.put("paymentid",String.valueOf(paymentobj.getPayment_id()));
        prameter.put("pendingamount",String.valueOf(Integer.parseInt(paymentobj.getPrice())-(Integer.valueOf(amount.getText())+Integer.valueOf(paymentobj.getPayed()))));
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(InventoryConstants.dateTimeFormat);
        prameter.put("date",simpleDateFormat.format(new Date()));

       if( reportService.printReport(dataList,prameter,file)){
           isSuccessful=true;
       }

        return isSuccessful;
    }

    @FXML
    private void showCustomerList() {
        try {
            paymentSingleInnerPane = FXMLLoader.load(getClass().getResource("/view/customer/listCustomer.fxml"));
            paymentSingleOuterPane.getChildren().setAll(paymentSingleInnerPane);
        } catch (Exception e) {
            Stage stage = (Stage) paymentSingleInnerPane.getScene().getWindow();
            logger.warn("error while opeing page"+e.getMessage());
            Toast.makeText(stage, "Some Error while opening customer page.Please restart the application", 1000, 500, 500);
        }
    }
}