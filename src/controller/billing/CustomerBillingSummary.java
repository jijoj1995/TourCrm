package controller.billing;

import constants.InventoryConstants;
import db.BillingService;
import db.ReportService;
import dto.Customer;
import dto.Item;
import dto.ReportData;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import org.apache.log4j.Logger;
import service.Toast;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.ResourceBundle;


public class CustomerBillingSummary implements Initializable {
    private Logger logger=Logger.getLogger(CustomerBillingSummary.class.getName());
    @FXML
    TableView table = new TableView();

    @FXML
    Button print;
    @FXML
    AnchorPane outerPane,innerPane;
    @FXML
    Label totalamount = new Label();
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
    String payment_id="";
    @FXML
    ObservableList<Item> data;
     ArrayList<Item> DataPrint=new ArrayList<>();
    BillingService BService = new BillingService();
    ReportService reportService=new ReportService();
    Customer customer;
    InputStream media1 ;
  InputStream file;
  private String billingDate;
  
    @Override
    public void initialize(URL url, ResourceBundle rb) {

                 media1 =this.getClass().getResourceAsStream("/resource/images/logo.jpg");
                 file =this.getClass().getResourceAsStream("/resource/reports/billingReport.jasper");
        name.setDisable(true);
        phone.setDisable(true);
        email.setDisable(true);
        address.setDisable(true);
        print.setOnAction(event -> {
            Stage stage=(Stage) innerPane.getScene().getWindow();
            try {
                //calling printing method
                if(printReport()){//if successful
    //                   Toast.makeText(stage," Printing Successful",1000,500,500);
                    showCustomerListPage();
                }
                else {
                    Toast.makeText(stage," Unable to Print Report",1000,500,500);
                }
            }
            catch (Exception ex) {
                logger.warn("unable to print report "+ ex.getMessage());
                //Logger.getLogger(CustomerBillingSummary.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        TableColumn name = new TableColumn("Name");
        TableColumn unit_price = new TableColumn("Price Per Unit");
        TableColumn total_quantity = new TableColumn("Total Quantity");
        TableColumn enter_quantity = new TableColumn("Enter Quantity");
        TableColumn price = new TableColumn("Price");

        table.getColumns().addAll(name, unit_price, total_quantity, enter_quantity, price);

        name.setCellValueFactory(
                new PropertyValueFactory<Item, String>("name")
        );
        unit_price.setCellValueFactory(
                new PropertyValueFactory<Item, String>("rate")
        );
        total_quantity.setCellValueFactory(
                new PropertyValueFactory<Item, String>("unit")
        );

        enter_quantity.setCellValueFactory(
                new PropertyValueFactory<Item, String>("qty")
        );
        price.setCellValueFactory(
                new PropertyValueFactory<Item, String>("price")
        );

        table.setItems(data);
    }

    public void initialize(Customer cst, ArrayList<Item> items, String total, String Payed, String payment_id,String billingDate) {
        customer=cst;
        name.setText(cst.getName());
        phone.setText(cst.getMobileNumber());
        email.setText(cst.getEmail());
        address.setText(cst.getAddress());
        totalamount.setText(total);
        amountpayed.setText(Payed);
        DataPrint= items;
        this.payment_id=payment_id;
        data = FXCollections.observableArrayList(items);
        table.setItems(data);
        this.billingDate=billingDate;
    }

    private boolean printReport() throws URISyntaxException{
        boolean isSuccessful=false;
        logger.info("going to print report");
        ArrayList<ReportData> dataList=new ArrayList<ReportData>();
        for(Item item :DataPrint){
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
        prameter.put("titile", InventoryConstants.companyName);
        prameter.put("address1",InventoryConstants.companyAddress1);
        prameter.put("address2",InventoryConstants.companyAddress2);
        prameter.put("address3",InventoryConstants.companyAddress3);
        prameter.put("customerid",customer.getCustomerId());
        prameter.put("customername",customer.getName());
        prameter.put("mobileno",customer.getMobileNumber());
        prameter.put("email",customer.getEmail());
        prameter.put("address",customer.getAddress().replaceAll("\n"," "));
        prameter.put("totalamount",totalamount.getText());
        prameter.put("payedamount",amountpayed.getText());
        prameter.put("paymentid",payment_id);
        prameter.put("pendingamount",String.valueOf(Integer.parseInt(totalamount.getText())-Integer.parseInt(amountpayed.getText())));
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(InventoryConstants.dateTimeFormat);
        prameter.put("date",!billingDate.isEmpty()? billingDate:simpleDateFormat.format(new Date()));
       if( reportService.printReport(dataList,prameter,file)){
           isSuccessful=true;
           logger.info("printing report successful");
       }

        return isSuccessful;
    }

    @FXML
    private void showCustomerListPage() throws Exception{
        innerPane= FXMLLoader.load(getClass().getResource("/view/customer/listCustomer.fxml"));
        outerPane.getChildren().setAll(innerPane);
    }
}
