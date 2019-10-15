package controller.suppliers;

import com.jfoenix.controls.JFXButton;
import constants.InventoryConstants;
import controller.billing.CustomerBillingSummary;
import db.BillingService;
import db.ReportService;
import db.SupplierService;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import dto.Item;
import dto.Product;
import dto.ReportData;
import dto.Supplier;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import main.AutoCompleteComboBoxListener;
import main.FxUtilTest;
import main.Main;
import org.apache.log4j.Logger;
import service.Toast;
import service.Validator;

import java.io.InputStream;
import java.net.URISyntaxException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.ResourceBundle;

public class SupplierBilling implements Initializable {


    private Logger logger=Logger.getLogger(SupplierBilling.class.getName());
    Supplier mainSupplier;
    Product selectedProduct=null;
    private Parent parent;
    private Scene scene;
    @FXML
    TableView table;
    @FXML
    private DatePicker billingDate;

    @FXML
    private FontAwesomeIconView addItemButton;
    @FXML
    Button  payAndPrintButton, saveOnlyButton;

    @FXML
    Label totalAmount;


    @FXML
    TextField payedAmount;

    @FXML
    TextField pricePerUnit, totalStockQuantity, quantitySelling, finalItemPrice, name, phone;

    @FXML
    TextArea address;
    @FXML
    private AnchorPane supplierBillingOuterPane;

    private Stage stage;
    @FXML
    ComboBox<Product> productComboBox;
    ObservableList<Item> data;
    BillingService BService = new BillingService();
    int addedItemIndex=0;
    InputStream media1 ;
    InputStream file;
    ArrayList<Item> itemsList = new ArrayList<>();
    int payment_id=0;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        initializeDefaultLayout();
        media1 =this.getClass().getResourceAsStream("/resource/images/logo.jpg");
        file =this.getClass().getResourceAsStream("/resource/reports/billingReport.jasper");
        payAndPrintButton.setDisable(true);
        saveOnlyButton.setDisable(true);

        productComboBox.getItems().addAll(BService.getStockList());
        FxUtilTest.autoCompleteComboBoxPlus(productComboBox, (typedText, itemToCompare) -> itemToCompare.getName().toLowerCase().contains(typedText.toLowerCase()) || itemToCompare.getCategory().toLowerCase().contains(typedText.toLowerCase()));
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

        phone.setOnKeyTyped(event ->{Validator.checkNumberEntered(event);});
        payedAmount.setOnKeyTyped(event ->{Validator.checkNumberEntered(event);});
        quantitySelling.setOnKeyTyped(event ->{Validator.checkNumberEntered(event);});
        pricePerUnit.setOnKeyTyped(event ->{Validator.checkNumberEntered(event);});

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
                finalItemPrice.setText(quantitySelling.getText().length() == 0 ? "0" : String.valueOf(Integer.parseInt(quantitySelling.getText()) * Integer.parseInt(pricePerUnit.getText())));
               // quantitySelling.setText(FxUtilTest.getComboBoxValue(productComboBox).getUnit());
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
            Stage stage = (Stage) supplierBillingOuterPane.getScene().getWindow();

            // no items added
            if(data.isEmpty()){
                Toast.makeText(stage, "No items added", 3000, 500, 500);
                return;
            }
            //fetch all the items added to the list
             itemsList = new ArrayList<>();
            for (Item a : data) {
                itemsList.add(a);
            }
            //fetch billing Date
            LocalDate ld = billingDate.getValue();
            SimpleDateFormat sdf = new SimpleDateFormat(InventoryConstants.databaseDateTimeFormat);
            Date paymentBillingDate = Date.from(ld.atStartOfDay(ZoneId.systemDefault()).toInstant());
            String billingDateValue=sdf.format(paymentBillingDate);


            //set customer object before saving
            Supplier supplier=new Supplier(name.getText().trim(),address.getText().trim(),phone.getText().trim());


            supplier.setSupplierId(mainSupplier == null ? 0:mainSupplier.getSupplierId());

            //going to save the transactions
            HashMap<String,Object> processResult =null;
           processResult=  new SupplierService().addPaymentDetails(itemsList, supplier, payedAmount.getText().trim(), totalAmount.getText().trim(),billingDateValue);


            //check whether process was successful
            if(!(boolean)processResult.get("isSuccessful")){

                Toast.makeText(stage, "Some Error while saving data. Please check the values entered", 1000, 500, 500);
                return;
            }
            else                            //save payment id for next summary page
                payment_id=(int)processResult.get("paymentId");
            //save updated supplier
                mainSupplier=(Supplier) processResult.get("updatedSupplier");

           try {
              if(printReport()){
                  logger.info("Printing report also successful");
                  showSupplierList();
              }
               else{
                  Toast.makeText(stage, "Some Error while generating report. Please check the values entered", 1000, 500, 500);
               }
           }
           catch (Exception e){
               logger.warn("unable to print report",e);
               Toast.makeText(stage, "Some Error while generating report. Please check the values entered", 1000, 500, 500);
           }
        });

        saveOnlyButton.setOnAction(event -> {
            Stage stage = (Stage) supplierBillingOuterPane.getScene().getWindow();
            logger.info("going to save transaction w/o report option");
            //check if any data in list ot be saved
            if(data.isEmpty()){
                Toast.makeText(stage, "No items to be saved", 1000, 500, 500);
                return;
            }

            itemsList = new ArrayList<>();
            for (Item a : data) {
                itemsList.add(a);
            }

            //fetching billing Date
            LocalDate ld = billingDate.getValue();
            SimpleDateFormat sdf =
                    new SimpleDateFormat(InventoryConstants.databaseDateTimeFormat);
            Date paymentBillingDate = Date.from(ld.atStartOfDay(ZoneId.systemDefault()).toInstant());
            String billingDateValue=sdf.format(paymentBillingDate);

            //setting customer object for next process
            Supplier supplier = new Supplier(name.getText().trim(), address.getText().trim(), phone.getText().trim());
            supplier.setSupplierId(mainSupplier != null ? mainSupplier.getSupplierId() : 0);

            //saving all customer and payment details
            HashMap<String,Object> processResult =null;
            processResult=  new SupplierService().addPaymentDetails(itemsList, supplier, payedAmount.getText().trim(), totalAmount.getText(),billingDateValue);

            //if process not successful
            if(!(boolean)processResult.get("isSuccessful")){
                Toast.makeText(stage, "Some Error while saving data. Please check the values entered", 1000, 500, 500);
            }
            else { //process Successfull. goback to customerList page
                logger.info("Process completed sucessfully");
                showSupplierList();
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
                    comodata.setUnit(String.valueOf(Integer.parseInt(comodata.getUnit()) - Integer.parseInt(item.getQty())));
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

        table.setItems(data);
    }

    public void setMainSupplier(Supplier mainSupplier) {
        this.mainSupplier = mainSupplier;
        name.setText(this.mainSupplier.getName());
        phone.setText(this.mainSupplier.getMobileNumber());
        address.setText(this.mainSupplier.getAddress());
    }

    @FXML
    private void showSupplierList() {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/view/suppliers/listSuppliers.fxml"));
            supplierBillingOuterPane.getChildren().setAll(root);
        } catch (Exception e) {
            Stage stage = (Stage) supplierBillingOuterPane.getScene().getWindow();
            Toast.makeText(stage, "Some Error while opening supplier page.Please restart the application", 1000, 500, 500);
        }
    }

    @FXML
    private void saveSupplierOnly(){
        Stage stage = (Stage) supplierBillingOuterPane.getScene().getWindow();
        Supplier supplier = new Supplier(name.getText(), address.getText(), phone.getText());

        //if old supplier need to save id to new object
        supplier.setSupplierId(this.mainSupplier != null ? this.mainSupplier.getSupplierId() : 0);

        //going to save only mainSupplier details
        if(new SupplierService().saveSupplierOnly(supplier)){ //if process successful, show a message
            Toast.makeText(stage, "Supplier Details saved successfully", 1000, 500, 500);
        }
        else
            Toast.makeText(stage, "Some Error while saving Supplier details.Please check the values entered or restart the application", 1000, 500, 500);
    }

    @FXML
    private void showSupplierDetailsPage(){
        FXMLLoader Loader = new FXMLLoader();
        Loader.setLocation(getClass().getResource("/view/suppliers/supplierPaymentRecords.fxml"));
        try {
            Loader.load();

        } catch (Exception e) {
            e.printStackTrace();
        }
        SupplierPaymentRecords customerPaymentRecords = Loader.getController();
        customerPaymentRecords.initializeSupplier(mainSupplier);
        Parent p = Loader.getRoot();
        supplierBillingOuterPane.getChildren().setAll(p);
    }


    private boolean printReport() throws URISyntaxException {
        boolean isSuccessful=false;
        logger.info("going to print report");
        ArrayList<ReportData> dataList=new ArrayList<ReportData>();
        for(Item item :itemsList){
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
        prameter.put("customerid",mainSupplier.getSupplierId());
        prameter.put("customername",mainSupplier.getName());
        prameter.put("mobileno",mainSupplier.getMobileNumber());
        prameter.put("email","N/A");
        prameter.put("address",mainSupplier.getAddress().replaceAll("\n"," "));
        prameter.put("totalamount",totalAmount.getText());
        prameter.put("payedamount",payedAmount.getText());
        prameter.put("paymentid",payment_id);
        prameter.put("pendingamount",String.valueOf(Integer.parseInt(totalAmount.getText())-Integer.parseInt(payedAmount.getText())));
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(InventoryConstants.dateTimeFormat);
        prameter.put("date",!billingDate.getValue().toString().isEmpty()? billingDate.getValue():simpleDateFormat.format(new Date()));
        if( new ReportService().printReport(dataList,prameter,file)){
            isSuccessful=true;
            logger.info("printing report successful");
        }
        return isSuccessful;
    }

    @FXML
    private void addItemToTable(){
        data.add(new Item(FxUtilTest.getComboBoxValue(productComboBox).getStockId(), FxUtilTest.getComboBoxValue(productComboBox).getName(), FxUtilTest.getComboBoxValue(productComboBox).getUnit(), Integer.parseInt(pricePerUnit.getText()), Integer.parseInt(finalItemPrice.getText()), quantitySelling.getText(), productComboBox.getSelectionModel().getSelectedIndex()));
        totalAmount.setText(String.valueOf(Integer.parseInt(totalAmount.getText()) + Integer.parseInt(finalItemPrice.getText())));
        FxUtilTest.getComboBoxValue(productComboBox).setUnit(String.valueOf(Integer.parseInt(FxUtilTest.getComboBoxValue(productComboBox).getUnit()) + Integer.parseInt(quantitySelling.getText())));
        productComboBox.getSelectionModel().select(-1);
        pricePerUnit.setText("");
        quantitySelling.setText("");
        finalItemPrice.setText("");
        totalStockQuantity.setText("");
        quantitySelling.setDisable(true);
        pricePerUnit.setDisable(true);
    }
    private void initializeDefaultLayout(){
        supplierBillingOuterPane.setPrefWidth(Main.WIDTH-Main.SIDE_BAR_WIDTH);
        supplierBillingOuterPane.setPrefHeight(Main.HEIGHT-30);
    }
}
