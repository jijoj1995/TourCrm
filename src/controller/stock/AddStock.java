package controller.stock;

import constants.InventoryConstants;
import db.StockService;
import dto.Stock;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import main.Main;
import org.apache.log4j.Logger;
import service.ExcelExporter;
import service.InventoryReader;
import service.Toast;
import service.Validator;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.ResourceBundle;


public class AddStock implements Initializable {
    private static Logger log=Logger.getLogger(AddStock.class.getName());
    DateFormat fileNameDateFormat=new SimpleDateFormat(InventoryConstants.FileNamedateTimeFormat);
    @FXML
    private TextField nameInput;

    @FXML
    private TextField categoryInput;
    @FXML
    private TextField stockLeftInput;
    @FXML
    private TextField sellingPriceInput;
    @FXML
    private TextField supplierInput;
    @FXML
    private TextField originalPriceInput;
    @FXML
    private AnchorPane addStockAnchorPane;
    @FXML
    private Label fileNameLabel;
    @FXML
    private CheckBox sellingPriceCheckBox;
    @FXML
    private Button uploadFileButton,saveStockButton,listStockButton,fileChooserButton;
    private File stockFile;
    private Stock mainStockDto;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        initializeDefaultLayout();
        sellingPriceInput.setDisable(true);
        sellingPriceCheckBox.setSelected(true);

        sellingPriceCheckBox.setOnAction(event -> {
            if(sellingPriceCheckBox.isSelected()){
                sellingPriceInput.setDisable(true);
                sellingPriceInput.setText(originalPriceInput.getText());
            }
            else {
                sellingPriceInput.setDisable(false);
            }
        });
        originalPriceInput.textProperty().addListener((observable, oldValue, newValue) -> {
             if (newValue.matches("[0-9]*")){
                originalPriceInput.setText(newValue);
                if(sellingPriceCheckBox.isSelected()) sellingPriceInput.setText(newValue);
            }else{
                 originalPriceInput.setText(oldValue);
                 if(sellingPriceCheckBox.isSelected()) sellingPriceInput.setText(oldValue);
             }
        });


        sellingPriceInput.setOnKeyTyped(new EventHandler<KeyEvent>()
        {
            public void handle(final KeyEvent keyEvent)
            {
                checkNumberEntered(keyEvent);
            }
        });

        stockLeftInput.setOnKeyTyped(new EventHandler<KeyEvent>()
        {
            public void handle(final KeyEvent keyEvent)
            {
                checkNumberEntered(keyEvent);
            }
        });


        fileChooserButton.setOnAction(event -> {
            onFileChooserButton();
        });
        uploadFileButton.setOnAction(event -> {
            onUploadFileButton();
        });

    }


    public void saveButtonProcess(){
        boolean isUpdateProcess = false;
        Stage stage=(Stage) addStockAnchorPane.getScene().getWindow();
        log.info("in saving stock method");

        //fetching values of stock input
        Stock stockDto=new Stock();

            if(!validateStockInput()){
                log.info("Validation Failed");
                return;
            }
        //checking old stock or adding new entry
        if(mainStockDto!=null && mainStockDto.getStockId()!=null){
            stockDto.setStockId(mainStockDto.getStockId());
            isUpdateProcess = true;
        }

        stockDto.setName(nameInput.getText());

        stockDto.setCategory(categoryInput.getText());
        stockDto.setOriginalPrice(Integer.valueOf(originalPriceInput.getText()));
        stockDto.setStockLeft(Integer.valueOf(stockLeftInput.getText()));
        stockDto.setSellingPrice(Integer.valueOf(sellingPriceInput.getText()));
        stockDto.setSupplier((supplierInput.getText()));
        //adding new stock and checking result
        if(new StockService().addStock(stockDto)){
            Toast.makeText(stage,"Stock Inserted Successfully",1000,500,500);
            resetValues();
            if (isUpdateProcess) {
                //return back to stock List page
                onStockCancelButton();
            }
        }
        else{
            Toast.makeText(stage,"Some Error while adding stock. Please check the values or database connection",3000,500,500);
        }
    }

    @FXML
    private void onStockCancelButton() {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/view/stock/listStock.fxml"));
            addStockAnchorPane.getChildren().setAll(root);
        } catch (IOException ex) {
            Stage stage = (Stage) addStockAnchorPane.getScene().getWindow();
            Toast.makeText(stage, "Some Error while showing stockList page.", 3000, 500, 500);
        }
    }

    @FXML
    private void onFileChooserButton(){
        FileChooser fileChooser = new FileChooser();
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("Excel Files (*.xlsx)", "*.xlsx","*.xls","*.csv");
        fileChooser.getExtensionFilters().add(extFilter);
        Stage stage=(Stage) addStockAnchorPane.getScene().getWindow();

        stockFile = fileChooser.showOpenDialog(stage);
        System.out.println(stockFile);
        if(stockFile==null){
            Toast.makeText(stage,"No file Selected",1000,500,500);
            fileNameLabel.setVisible(false);
            uploadFileButton.disableProperty().set(true);
            return;
        }

        fileNameLabel.setVisible(true);
        fileNameLabel.setText(stockFile.getName());
        uploadFileButton.disableProperty().set(false);
    }

    @FXML
    private void onUploadFileButton(){

        log.info("Going to upload file "+stockFile);
        Stage stage=(Stage) addStockAnchorPane.getScene().getWindow();
        if(!stockFile.exists()){
            Toast.makeText(stage,"No file Selected",1000,500,500);
            return;
        }

        //show uploading in progress
        if(InventoryReader.uploadStock(stockFile)){
            Toast.makeText(stage,"Upload Successful",5000,500,500);
        } else {
            Toast.makeText(stage, "Error While Uploading sheet. Please check your excel sheet for correct format", 5000, 500, 500);
        }


    }

    public void setStockDto(Stock stock){
        log.info("updating stock from addPage option");
        this.mainStockDto=stock;
        nameInput.setText(stock.getName());
        categoryInput.setText(stock.getCategory());

        originalPriceInput.setText(String.valueOf(stock.getOriginalPrice()));
        sellingPriceInput.setText(String.valueOf(stock.getSellingPrice()));
        stockLeftInput.setText(String.valueOf(stock.getStockLeft()));
        supplierInput.setText(stock.getSupplier());
    }

    private void resetValues(){
        nameInput.setText("");
        categoryInput.setText("");
        originalPriceInput.setText("");
        sellingPriceInput.setText("");
        supplierInput.setText("");
        stockLeftInput.setText("");

    }

    private boolean validateStockInput(){
        Stage stage=(Stage) addStockAnchorPane.getScene().getWindow();
        if(Validator.isNullOrEmpty(nameInput.getText())){
            Toast.makeText(stage," enter name of the stock",1000,500,500);
            return false;
        }
        if(Validator.isNullOrEmpty(categoryInput.getText())){
            Toast.makeText(stage," enter category of the stock",1000,500,500);
            return false;
        }

        if(!Validator.isNumberOnly(originalPriceInput.getText())){
            Toast.makeText(stage,"original Price should be number",1000,500,500);
            return false;
        }
        if(!Validator.isNumberOnly(sellingPriceInput.getText())){
            Toast.makeText(stage,"selling Price should be number",1000,500,500);
            return false;
        }

        if(!Validator.isNumberOnly(stockLeftInput.getText())){
            Toast.makeText(stage,"stockLeft should be number",1000,500,500);
            return false;
        }
        if(Validator.isNullOrEmpty(supplierInput.getText())){
            Toast.makeText(stage,"enter name of the suppliers",1000,500,500);
            return false;
        }
        return true;
    }

    @FXML
    private void checkNumberEntered(KeyEvent e){
        // Consume the event if it is not a digit
            Validator.checkNumberEntered( e);
    }
    @FXML
    private void generateSampleFile(){
        log.info("going to generate sample stock data file");
        Stage stage = (Stage) addStockAnchorPane.getScene().getWindow();
        ArrayList<Stock> stockArrayList = new ArrayList<>();



        FileChooser fileChooser = new FileChooser();
        fileChooser.setInitialFileName("SampleStockFile"+fileNameDateFormat.format(new Date()));
        //Set extension filter for xlsx files
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("Excel files (*.xlsx)", "*.xlsx");
        fileChooser.getExtensionFilters().add(extFilter);

        //Show save file dialog
        File file = fileChooser.showSaveDialog(stage);
        if (file == null) {
            Toast.makeText(stage, "No file Selected", 1000, 500, 500);
            return;
        }

        if(new ExcelExporter().stockDataExporter(file,stockArrayList)){
            Toast.makeText(stage, "Sample Stock File Generated Successfully", 1000, 500, 500);
            log.info("Writing on XLSX file Successful ...");
        }
        else{
            Toast.makeText(stage, "Could not generate sample Stock file. Please check your File or Database Connection", 1000, 500, 500);
            log.warn("Writing on XLSX file failed ...");
        }
    }
    private void initializeDefaultLayout(){
        addStockAnchorPane.setPrefWidth(Main.WIDTH-Main.SIDE_BAR_WIDTH);
        addStockAnchorPane.setPrefHeight(Main.HEIGHT-30);
    }
}
