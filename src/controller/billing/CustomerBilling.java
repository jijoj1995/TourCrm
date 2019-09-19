package controller.billing;

import com.jfoenix.controls.JFXAutoCompletePopup;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import constants.InventoryConstants;
import controller.customer.CustomerPaymentRecords;
import db.BillingService;
import db.ReportService;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import dto.Customer;
import dto.Item;
import dto.Product;
import dto.ReportData;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import main.AutoCompleteComboBoxListener;
import main.ComboBoxAutoComplete;
import main.FxUtilTest;
import main.Main;
import org.apache.log4j.Logger;
import org.controlsfx.glyphfont.FontAwesome;
import org.controlsfx.glyphfont.Glyph;
import service.Toast;
import service.Validator;

import java.io.InputStream;
import java.net.URISyntaxException;
import java.net.URL;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.ResourceBundle;

public class CustomerBilling implements Initializable {
    private Logger logger=Logger.getLogger(CustomerBilling.class.getName());
    Customer Cst;
    int payment_id=0;
    String billingDateValue=null;
    InputStream media1 ;
    InputStream file;
    ReportService reportService=new ReportService();

    private Parent parent;
    private Scene scene;
    @FXML
    private HBox saveCustomerHbox;
    @FXML private JFXButton saveCustomerButton;
    @FXML
    TableView table;
    @FXML
    private DatePicker billingDate;

    @FXML
    Button  payAndPrintButton, saveOnlyButton;
    @FXML
    private FontAwesomeIconView addItemButton,detailsPageIcon;

    @FXML
    Label totalAmount;


    @FXML
    TextField payedAmount;

    @FXML
    TextField pricePerUnit, totalStockQuantity, quantitySelling, finalItemPrice;
    @FXML
    private JFXTextField  name, phone, email;

    @FXML
    JFXTextArea address;
    @FXML
    private AnchorPane customerBillingInnerPane;
    @FXML
    private AnchorPane customerBillingOuterPane;

    private Stage stage;
    @FXML
    ComboBox<Product> productComboBox;
    ObservableList<Item> data;
    BillingService BService = new BillingService();
    int addedItemIndex=0;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        initializeDefaultLayout();
        media1 =this.getClass().getResourceAsStream("/resource/images/logo.jpg");
        file =this.getClass().getResourceAsStream("/resource/reports/billingReport.jasper");
        if(Cst==null){
            detailsPageIcon.setDisable(true);
        }
        else{
            detailsPageIcon.setDisable(false);
        }
//        payAndPrintButton.setDisable(true);
  //      saveOnlyButton.setDisable(true);

/*        productComboBox.getItems().addAll(BService.getStockList());
        FxUtilTest.autoCompleteComboBoxPlus(productComboBox, (typedText, itemToCompare) -> itemToCompare.getName().toLowerCase().contains(typedText.toLowerCase()) || itemToCompare.getCategory().toString().equals(typedText));
        FxUtilTest.getComboBoxValue(productComboBox);

        new AutoCompleteComboBoxListener(productComboBox);
        productComboBox.setOnAction(event -> {
            if (productComboBox.getSelectionModel().getSelectedIndex() != -1) {
                pricePerUnit.setText(FxUtilTest.getComboBoxValue(productComboBox).getRate().toString());
                totalStockQuantity.setText(FxUtilTest.getComboBoxValue(productComboBox).getUnit());
                finalItemPrice.setText("");
                quantitySelling.setText("");
                quantitySelling.setDisable(false);
                pricePerUnit.setDisable(false);
            }
        });

        payedAmount.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.length() == 0) {
                payAndPrintButton.setDisable(true);
                saveOnlyButton.setDisable(true);
            } else if (newValue.matches("[0-9]*[a-zA-Z]+[0-9]*")) {
                payAndPrintButton.setDisable(true);
                saveOnlyButton.setDisable(true);
            } else if (Integer.parseInt(newValue) <= Integer.parseInt(totalAmount.getText())) {
                payAndPrintButton.setDisable(false);
                saveOnlyButton.setDisable(false);
            } else if (Integer.parseInt(newValue) >= Integer.parseInt(totalAmount.getText())) {
                payedAmount.setText(totalAmount.getText());
                payAndPrintButton.setDisable(false);
                saveOnlyButton.setDisable(false);
            } else {
                payAndPrintButton.setDisable(true);
                saveOnlyButton.setDisable(true);
            }
        });
        //for only number inputs
        phone.setOnKeyTyped(new EventHandler<KeyEvent>()
        {
            public void handle(final KeyEvent keyEvent)
            {
                Validator.checkNumberEntered(keyEvent);
            }
        });
        payedAmount.setOnKeyTyped(new EventHandler<KeyEvent>()
        {
            public void handle(final KeyEvent keyEvent)
            {
                Validator.checkNumberEntered(keyEvent);
            }
        });
        quantitySelling.setOnKeyTyped(new EventHandler<KeyEvent>()
        {
            public void handle(final KeyEvent keyEvent)
            {
                Validator.checkNumberEntered(keyEvent);
            }
        });
        pricePerUnit.setOnKeyTyped(new EventHandler<KeyEvent>()
        {
            public void handle(final KeyEvent keyEvent)
            {
                Validator.checkNumberEntered(keyEvent);
            }
        });

        totalAmount.setText("0");
        payedAmount.setText("0");
        totalStockQuantity.setDisable(true);
        finalItemPrice.setDisable(true);
        addItemButton.setDisable(true);
        quantitySelling.setDisable(true);
        pricePerUnit.setDisable(true);
        billingDate.setValue(LocalDate.now());

        quantitySelling.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.length() == 0) {
                finalItemPrice.setText("0");
            } else if (newValue.matches("[0-9]*[a-zA-Z]+[0-9]*")) {
                finalItemPrice.setText("0");
            }  else if (Integer.parseInt(newValue) <= Integer.parseInt(FxUtilTest.getComboBoxValue(productComboBox).getUnit())) {
                finalItemPrice.setText(String.valueOf(Integer.parseInt(newValue) * Integer.parseInt(pricePerUnit.getText())));
            } else if (Integer.parseInt(newValue) >= Integer.parseInt(FxUtilTest.getComboBoxValue(productComboBox).getUnit())) {
                finalItemPrice.setText(quantitySelling.getText().length() == 0 ? "0" : String.valueOf(Integer.parseInt(FxUtilTest.getComboBoxValue(productComboBox).getUnit()) * Integer.parseInt(pricePerUnit.getText())));
                quantitySelling.setText(FxUtilTest.getComboBoxValue(productComboBox).getUnit());
            } else {
                finalItemPrice.setText("0");
            }
            if (finalItemPrice.getText().equalsIgnoreCase("0") || finalItemPrice.getText().length() == 0) {
                addItemButton.setDisable(true);
            } else {
                addItemButton.setDisable(false);
            }
        });

        pricePerUnit.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.length() == 0) {
                pricePerUnit.setText("0");
            } else if (!Validator.isNumberOnly(newValue)) {
                pricePerUnit.setText(oldValue);
            }else{
                finalItemPrice.setText(quantitySelling.getText().length() == 0 ? "0" : String.valueOf(Integer.parseInt(quantitySelling.getText()) *Integer.parseInt(newValue)));
            }
        });

        payAndPrintButton.setOnAction(event -> {
            Stage stage = (Stage) customerBillingOuterPane.getScene().getWindow();

            // no items added
            if(data.isEmpty()){
                Toast.makeText(stage, "No items added", 3000, 500, 500);
                return;            }

            //fetch all the items added to the list
            ArrayList<Item> itemsList = new ArrayList<>();
            for (Item a : data) {
                itemsList.add(a);
            }
            //fetch billing Date
            LocalDate ld = billingDate.getValue();
            SimpleDateFormat sdf = new SimpleDateFormat(InventoryConstants.databaseDateTimeFormat);
            Date paymentBillingDate = Date.from(ld.atStartOfDay(ZoneId.systemDefault()).toInstant());
             billingDateValue=sdf.format(paymentBillingDate);


            //set customer object before saving
            Customer customer = new Customer(name.getText(), address.getText(), phone.getText(), email.getText());
            customer.setCustomerId(Cst != null ? Cst.getCustomerId() : null);

            //going to save the transactions
            HashMap<String,Object> processResult =  BService.addPaymentDetails(itemsList, customer, payedAmount.getText(), totalAmount.getText(),billingDateValue);
            payment_id=(int) processResult.get("paymentId");

            //check whether process was successful
            if(!(boolean)processResult.get("isSuccessful")){
                Toast.makeText(stage, "Some Error while saving data. Please check the values entered", 1000, 500, 500);
                return;
            }
            else { //setting new customer object to main customer obj
                Cst=customer;
                try {
                    printReport(itemsList);
                    showCustomerList();
                }
                catch (Exception e){
                    e.printStackTrace();
                }
                //save payment id for next summary page


               *//* FXMLLoader Loader = new FXMLLoader();
                Loader.setLocation(getClass().getResource("/view/billing/customerBillingSummary.fxml"));
                try {
                    Loader.load();

                } catch (Exception e) {
                    e.printStackTrace();
                }
                logger.info("payment complete. now showing billing summary page");
                CustomerBillingSummary CBS = Loader.getController();
                CBS.initialize(customer, itemsList, totalAmount.getText(), payedAmount.getText(), String.valueOf(payment_id), billingDateValue);
                Parent p = Loader.getRoot();
                customerBillingOuterPane.getChildren().setAll(p);*//*
            }
        });

        saveOnlyButton.setOnAction(event -> {
            Stage stage = (Stage) customerBillingOuterPane.getScene().getWindow();
                logger.info("going to save transaction w/o report option");
                            //check if any data in list ot be saved
            if(data.isEmpty()){
                Toast.makeText(stage, "No items to be saved", 1000, 500, 500);
                return;
            }

            ArrayList<Item> items = new ArrayList<>();
            for (Item a : data) {
                items.add(a);
            }

                            //fetching billing Date
            LocalDate ld = billingDate.getValue();
            java.text.SimpleDateFormat sdf =
                    new java.text.SimpleDateFormat(InventoryConstants.databaseDateTimeFormat);
            Date paymentBillingDate = Date.from(ld.atStartOfDay(ZoneId.systemDefault()).toInstant());
            String billingDateValue=sdf.format(paymentBillingDate);

            //setting customer object for next process
            Customer customer = new Customer(name.getText(), address.getText(), phone.getText(), email.getText());
            customer.setCustomerId(Cst != null ? Cst.getCustomerId() : null);

            //saving all customer and payment details
            HashMap<String,Object> processResult =  BService.addPaymentDetails(items, customer, payedAmount.getText(), totalAmount.getText(),billingDateValue);

            //if process not successful
            if(!(boolean)processResult.get("isSuccessful")){
                Toast.makeText(stage, "Some Error while saving data. Please check the values entered", 1000, 500, 500);
            }
            else { //process Successfull. goback to customerList page
                logger.info("Process completed sucessfully");
                showCustomerList();
            }

        });
        TableColumn id = new TableColumn("Stock ID");
        TableColumn name = new TableColumn("Name");
        TableColumn unit_price = new TableColumn("Price Per Unit");
        TableColumn total_quantity = new TableColumn("Total Quantity");
        TableColumn enter_quantity = new TableColumn("Enter Quantity");
        TableColumn price = new TableColumn("Price");
        TableColumn<Item, Item> delete = new TableColumn<>("Action");
        table.getColumns().addAll(id,name, unit_price, total_quantity, enter_quantity, price, delete);
        id.prefWidthProperty().bind(table.widthProperty().divide(7)); // w * 1/4
        name.prefWidthProperty().bind(table.widthProperty().divide(7)); // w * 1/2
        unit_price.prefWidthProperty().bind(table.widthProperty().divide(7));
        total_quantity.prefWidthProperty().bind(table.widthProperty().divide(7)); // w * 1/4
        enter_quantity.prefWidthProperty().bind(table.widthProperty().divide(7)); // w * 1/2
        price.prefWidthProperty().bind(table.widthProperty().divide(7));
        delete.prefWidthProperty().bind(table.widthProperty().divide(7));

        data = FXCollections.observableArrayList(

        );

        id.setCellValueFactory(
                new PropertyValueFactory<Item, String>("stockId")
        );

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
        delete.setCellValueFactory(
                new PropertyValueFactory<Item, Item>("Action")
        );

        delete.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue()));
        delete.setCellFactory(param -> new TableCell<Item, Item>() {
            private final Button deleteButton = new Button("Delete");

            @Override
            protected void updateItem(Item item, boolean empty) {
                super.updateItem(item, empty);
                if (item == null) {
                    setGraphic(null);
                    return;
                }

                setGraphic(deleteButton);
                deleteButton.setOnAction(event -> {
                    totalAmount.setText(String.valueOf(Integer.parseInt(totalAmount.getText()) - item.getPrice()));
                    Product comodata = productComboBox.getItems().get(item.getIndex());
                    comodata.setUnit(String.valueOf(Integer.parseInt(comodata.getUnit()) + Integer.parseInt(item.getQty())));
                    productComboBox.getItems().remove(productComboBox.getItems().get(item.getIndex()));
                    productComboBox.getItems().add(comodata);
                    data.remove(item);
                    productComboBox.getSelectionModel().select(-1);
                    data.remove(item);
                    pricePerUnit.setText("");
                    quantitySelling.setText("");
                    price.setText("");
                    totalStockQuantity.setText("");
                });
            }
        });
        table.setItems(data);*/
    }

    public void setCst(Customer cst) {

        detailsPageIcon.setDisable(false);
        Cst = cst;
        name.setText(Cst.getName());
        phone.setText(Cst.getMobileNumber());
        address.setText(Cst.getAddress());
        email.setText(Cst.getEmail());

    }

    @FXML
    private void showCustomerList() {
        try {
            customerBillingInnerPane = FXMLLoader.load(getClass().getResource("/view/customer/listCustomer.fxml"));
            customerBillingOuterPane.getChildren().setAll(customerBillingInnerPane);
        } catch (Exception e) {
            Stage stage = (Stage) customerBillingInnerPane.getScene().getWindow();
            Toast.makeText(stage, "Some Error while opening customer page.Please restart the application", 1000, 500, 500);
        }
    }

    @FXML
    private void saveCustomerOnly(){
        Stage stage = (Stage) customerBillingOuterPane.getScene().getWindow();
        if(name.getText().isEmpty()){
            Toast.makeText(stage, "Please enter atleast customer Name", 1000, 500, 500);            return;
        }
        Customer customer = new Customer(name.getText(), address.getText(), phone.getText(), email.getText());

        //if old customer need to save id to new object
        customer.setCustomerId(Cst != null ? Cst.getCustomerId() : null);

        //going to save only customer details
        if(BService.saveOnlyCustomerProcess(customer)){ //if process successful, show a message
            Toast.makeText(stage, "Customer Details saved successfully", 1000, 500, 500);
        }
        else
        Toast.makeText(stage, "Some Error while saving customer details.Please check the values entered or restart the application", 1000, 500, 500);
    }
    @FXML
    private void showCustomerListPage(){
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/view/customer/listCustomer.fxml"));
            customerBillingOuterPane.getChildren().setAll(root);
        } catch (Exception e) {
            Stage stage = (Stage) customerBillingOuterPane.getScene().getWindow();
            Toast.makeText(stage, "Some Error while opening customer page.Please restart the application", 1000, 500, 500);
            logger.warn("error while going back from billing page "+e.getMessage());
        }
    }

    @FXML
    private void addItemToTable(){
        data.add(new Item(FxUtilTest.getComboBoxValue(productComboBox).getStockId(), FxUtilTest.getComboBoxValue(productComboBox).getName(), FxUtilTest.getComboBoxValue(productComboBox).getUnit(), Integer.parseInt(pricePerUnit.getText()), Integer.parseInt(finalItemPrice.getText()), quantitySelling.getText(), productComboBox.getSelectionModel().getSelectedIndex()));
        totalAmount.setText(String.valueOf(Integer.parseInt(totalAmount.getText()) + Integer.parseInt(finalItemPrice.getText())));
        FxUtilTest.getComboBoxValue(productComboBox).setUnit(String.valueOf(Integer.parseInt(FxUtilTest.getComboBoxValue(productComboBox).getUnit()) - Integer.parseInt(quantitySelling.getText())));
        productComboBox.getSelectionModel().select(-1);
        pricePerUnit.setText("");
        quantitySelling.setText("");
        finalItemPrice.setText("");
        totalStockQuantity.setText("");
        quantitySelling.setDisable(true);
        pricePerUnit.setDisable(true);
    }

    @FXML
    private void showPaymentDetailsPage(){
        if(Cst==null||Cst.getCustomerId()==null||Cst.getCustomerId().isEmpty()){
            return;
        }
        FXMLLoader Loader = new FXMLLoader();
        Loader.setLocation(getClass().getResource("/view/customer/customerPaymentRecords.fxml"));
        try {
            Loader.load();

        } catch (Exception e) {
            e.printStackTrace();
        }

        CustomerPaymentRecords customerPaymentRecords = Loader.getController();
        customerPaymentRecords.initializeCustomer(Cst);
        Parent p = Loader.getRoot();
        customerBillingOuterPane.getChildren().setAll(p);
    }

    private void initializeDefaultLayout(){
        customerBillingOuterPane.setPrefWidth(Main.WIDTH-Main.SIDE_BAR_WIDTH);
        customerBillingOuterPane.setPrefHeight(Main.HEIGHT-30);
    }

    private boolean printReport(ArrayList<Item> itemsList) throws URISyntaxException {
        boolean isSuccessful=false;
        logger.info("going to print report");
        ArrayList<ReportData> dataList=new ArrayList<ReportData>();
        for(Item item : itemsList){
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
        prameter.put("customerid",Cst.getCustomerId());
        prameter.put("customername",Cst.getName());
        prameter.put("mobileno",Cst.getMobileNumber());
        prameter.put("email",Cst.getEmail());
        prameter.put("address",Cst.getAddress().replaceAll("\n"," "));
        prameter.put("totalamount",totalAmount.getText());
        prameter.put("payedamount",payedAmount.getText());
        prameter.put("paymentid",payment_id);
        prameter.put("pendingamount",String.valueOf(Integer.parseInt(totalAmount.getText())-Integer.parseInt(payedAmount.getText())));
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(InventoryConstants.dateTimeFormat);
        prameter.put("date",!billingDateValue.isEmpty()? billingDate:simpleDateFormat.format(new Date()));
        if( reportService.printReport(dataList,prameter,file)){
            isSuccessful=true;
            logger.info("printing report successful");
        }
        return isSuccessful;
    }
}
