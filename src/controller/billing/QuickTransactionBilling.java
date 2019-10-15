package controller.billing;

import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXTextField;
import constants.InventoryConstants;
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
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
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
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.ResourceBundle;

public class QuickTransactionBilling implements Initializable {
    private Logger logger=Logger.getLogger(QuickTransactionBilling.class);
    @FXML
    private AnchorPane outerPane;
    @FXML
    private ComboBox<Product> productComboBox;
    @FXML
    private JFXTextField pricePerUnit,totalStockQuantity,quantitySelling,finalItemPrice;
    @FXML
    private TableView table;
    @FXML
    Button payAndPrintButton, saveOnlyButton;
    @FXML
    Label totalAmount;
    @FXML
    private FontAwesomeIconView addItemButton;
    @FXML
    private TextField payedAmount;
    private BillingService billingService = new BillingService();
    private ObservableList<Item> data;
    private InputStream media1 ;
    private InputStream file;
    private ReportService reportService=new ReportService();
    private JFXCheckBox isPrintEnabled;
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initializeDefaultLayout();
        media1 =this.getClass().getResourceAsStream("/resource/images/logo.jpg");
        file =this.getClass().getResourceAsStream("/resource/reports/billingReport.jasper");

        productComboBox.getItems().addAll(billingService.getStockList());
        FxUtilTest.autoCompleteComboBoxPlus(productComboBox, (typedText, itemToCompare) -> itemToCompare.getName().toLowerCase().contains(typedText.toLowerCase()) || itemToCompare.getCategory().toString().equals(typedText));
        FxUtilTest.getComboBoxValue(productComboBox);
        payAndPrintButton.setDisable(true);
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
            Stage stage = (Stage) outerPane.getScene().getWindow();

            // no items added
            if(data.isEmpty()){
                Toast.makeText(stage, "No items added", 3000, 500, 500);
                return;
            }
            //fetch all the items added to the list
            ArrayList<Item> itemsList = new ArrayList<>();
                itemsList.addAll(data);
            //going to save the transactions
            HashMap<String,Object> processResult =  billingService.addQuickPaymentTransaction(itemsList,totalAmount.getText());
            Integer payment_id=(int) processResult.get("paymentId");

            //check whether process was successful
            if(!(boolean)processResult.get("isSuccessful")){
                Toast.makeText(stage, "Some Error while saving data. Please check the values entered", 1000, 500, 500);
                return;
            }
            else {
                try {
                   if (isPrintEnabled.isSelected()) {
                       printReport(itemsList, payment_id);
                   }
                   else {
                       Toast.makeText(stage, "Data Saved Successfully", 1000, 500, 500);
                   }
                reInitializeScreen();
                }
                catch (Exception e){
                    e.printStackTrace();
                }
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
        table.setItems(data);
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

    private boolean printReport(ArrayList<Item> itemsList,Integer payment_id) throws URISyntaxException {
        boolean isSuccessful=false;
        logger.info("going to print report");
        ArrayList<ReportData> dataList=new ArrayList<ReportData>();
        for(Item item : itemsList){
            ReportData row=new ReportData(item.getStockId().toString(), item.getName(), item.getRate().toString(),
                    item.getQty(), item.getPrice().toString());
            dataList.add(row);
        }
        InputStream targetStream=null;
        HashMap<String,Object> reportParameter= new HashMap<>();
        try {
            targetStream= media1;
        }catch (Exception e){
            e.printStackTrace();
        }
        reportParameter.put("image_Logo",targetStream);
        reportParameter.put("titile", InventoryConstants.companyName);
        reportParameter.put("address1",InventoryConstants.companyAddress1);
        reportParameter.put("address2",InventoryConstants.companyAddress2);
        reportParameter.put("address3",InventoryConstants.companyAddress3);
        reportParameter.put("customerid","0");
        reportParameter.put("customername","Quick Transaction");
        reportParameter.put("mobileno","N/A");
        reportParameter.put("email","N/A");
        reportParameter.put("address","N/A");
        reportParameter.put("totalamount",totalAmount.getText());
        reportParameter.put("payedamount",totalAmount.getText());
        reportParameter.put("paymentid",payment_id);
        reportParameter.put("pendingamount",String.valueOf(Integer.parseInt(totalAmount.getText())-Integer.parseInt(payedAmount.getText())));
        reportParameter.put("date",new Date());
        if( reportService.printReport(dataList,reportParameter,file)){
            isSuccessful=true;
            logger.info("printing report successful");
        }
        return isSuccessful;
    }

    private void reInitializeScreen(){
        table.refresh();
        totalAmount.setText("0");
        payedAmount.setText("0");
        totalStockQuantity.setDisable(true);
        finalItemPrice.setDisable(true);
        addItemButton.setDisable(true);
        quantitySelling.setDisable(true);
        pricePerUnit.setDisable(true);
    }
    private void initializeDefaultLayout(){
        outerPane.setPrefWidth(Main.WIDTH-Main.SIDE_BAR_WIDTH);
        outerPane.setPrefHeight(Main.HEIGHT-30);
    }
}
