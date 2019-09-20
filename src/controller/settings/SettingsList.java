package controller.settings;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXToggleButton;
import constants.InventoryConstants;
import db.CustomerService;
import db.DbBackupService;
import db.StockService;
import db.UserService;
import dto.CustomerList;
import dto.Stock;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.CheckBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TabPane;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import main.InventoryConfig;
import main.Main;
import org.apache.log4j.Logger;
import service.ExcelExporter;
import service.Toast;
import timers.InventoryTimers;

import java.io.File;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.ResourceBundle;

public class SettingsList implements Initializable {
    private static Logger log = Logger.getLogger(SettingsList.class.getName());
    DateFormat fileNameDateFormat = new SimpleDateFormat(InventoryConstants.FileNamedateTimeFormat);
    @FXML
    private AnchorPane settingsAnchorPane;
    @FXML
    private TabPane backupTab;
    @FXML
    private CheckBox showPendingOnlyCheckbox;
    @FXML
    private JFXToggleButton automaticBackupCheckBox;
    @FXML
    private HBox backupDetailsBox;
    @FXML
    private JFXComboBox<DayOfWeek> backupDayOptionList;
    @FXML
    private PasswordField oldPasswordField, newPasswordField, confirmPasswordField;
    InventoryConfig inventoryConfig = InventoryConfig.getInstance();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initializeDefaultLayout();

                                          //fetch backup property enabled from properties file
        Boolean isAutomaticBackupEnabled = false;
        try {
            isAutomaticBackupEnabled = Boolean.parseBoolean(inventoryConfig.getAppProperties().getProperty("automaticBackup"));
        } catch (Exception e) {
            log.warn("exception while fetching automatic backup properties from property file" + e.getMessage());
        }
                                        //set vbox visibility based on backup property
        if (!isAutomaticBackupEnabled) {
            backupDetailsBox.setVisible(false);
            //test log
        }
        automaticBackupCheckBox.setSelected(isAutomaticBackupEnabled);

                                        //fetch next backupDate to show dayOfWeek in dropdown
        Date autoDbBackupDate = new Date();
        try {
            autoDbBackupDate = new SimpleDateFormat(InventoryConstants.databaseDateTimeFormat).parse(inventoryConfig.getAppProperties().getProperty("nextDbBackupDate"));
        } catch (Exception e) {
            log.warn("error while fetching backup data from properties file" + e.getMessage());
        }
        LocalDate localDateDbBackupDate = autoDbBackupDate.toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDate();

        DayOfWeek week = localDateDbBackupDate.getDayOfWeek();

                                            //initialize dropdown with dayOfweek objects
        backupDayOptionList.getItems().addAll(DayOfWeek.MONDAY, DayOfWeek.TUESDAY, DayOfWeek.WEDNESDAY, DayOfWeek.THURSDAY, DayOfWeek.FRIDAY, DayOfWeek.SATURDAY, DayOfWeek.SUNDAY);
        backupDayOptionList.setValue(week);

                                                //on backup toggleButton action
        automaticBackupCheckBox.setOnAction((event) -> {
            ToggleButton but = (ToggleButton) event.getTarget();
            if (but.isSelected()) {
                backupDetailsBox.setVisible(true);
                inventoryConfig.getAppProperties().setProperty("automaticBackup", "true");
            } else {
                backupDetailsBox.setVisible(false);
                inventoryConfig.getAppProperties().setProperty("automaticBackup", "false");
            }
        });
    }

    @FXML
    private void exportStocksData() {

        log.info("going to backup stock data");
        Stage stage = (Stage) settingsAnchorPane.getScene().getWindow();
        ArrayList<Stock> stockArrayList = new StockService().getAscendingStockList();

        //checking whether stock data present or not
        if (stockArrayList.isEmpty()) {
            Toast.makeText(stage, "No data present in stocks", 1000, 500, 500);
            return;
        }

        FileChooser fileChooser = new FileChooser();
        fileChooser.setInitialFileName("StockList_" + fileNameDateFormat.format(new Date()));
        //Set extension filter for xlsx files
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("Excel files (*.xlsx)", "*.xlsx");
        fileChooser.getExtensionFilters().add(extFilter);

        //Show save file dialog
        File file = fileChooser.showSaveDialog(stage);
        if (file == null) {
            Toast.makeText(stage, "No file Selected", 1000, 500, 500);
            return;
        }
        if (new ExcelExporter().stockDataExporter(file, stockArrayList)) {
            Toast.makeText(stage, "Stock Data Exported Successfully", 1000, 500, 500);
            log.info("Writing on XLSX file Successful ...");
        } else {
            Toast.makeText(stage, "Could Not Export Stock Data. Please check your File or Database Connection", 1000, 500, 500);
            log.info("Writing on XLSX file Successful ...");
        }
    }

    @FXML
    private void deleteStocksData() {
        Stage stage = (Stage) settingsAnchorPane.getScene().getWindow();
        if (new StockService().deleteAllStockData()) {
            Toast.makeText(stage, " All Stock Data deleted Successfully", 1000, 500, 500);
        } else {
            Toast.makeText(stage, "Could Not delete Stock Data. Please check your Database Connection", 1000, 500, 500);
        }
    }

    @FXML
    private void backupEntireDatabase() {
        //stage for showing messages
        Stage stage = (Stage) settingsAnchorPane.getScene().getWindow();

        //choose file for saving
        FileChooser fileChooser = new FileChooser();
        fileChooser.setInitialFileName("InventoryBackup_" + fileNameDateFormat.format(new Date()));
        //Set extension filter for xlsx files
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("Sql Files (*.sql)", "*.sql");
        fileChooser.getExtensionFilters().add(extFilter);

        //Show save file dialog

        File file = fileChooser.showSaveDialog(stage);
        if (file == null) {
            Toast.makeText(stage, "No file Selected", 1000, 500, 500);
            return;
        }
        if (new DbBackupService().backupEntireDatabase(file)) {
            Toast.makeText(stage, "Entire Data backed up Successfully", 1000, 500, 500);
        } else {
            Toast.makeText(stage, "Could Not backup Database. Please check your Database Connection", 1000, 500, 500);
        }
    }

    @FXML
    private void restoreEntireDatabase() {
        //stage for showing messages
        Stage stage = (Stage) settingsAnchorPane.getScene().getWindow();
        //choose file for saving
        FileChooser fileChooser = new FileChooser();
        //Set extension filter for xlsx files
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("Sql Files (*.sql)", "*.sql");
        fileChooser.getExtensionFilters().add(extFilter);

        File file = fileChooser.showOpenDialog(stage);
        if (file == null) {
            Toast.makeText(stage, "No file Selected", 1000, 500, 500);
            return;
        }

        if (new DbBackupService().restoreEntireDatabase(file)) {
            Toast.makeText(stage, "Entire Data Restored Successfully", 1000, 500, 500);
        } else {
            Toast.makeText(stage, "Could Not Restore Database. Please check your Database Connection", 1000, 500, 500);
        }
    }

    @FXML
    private void exportCustomerData() {
        //stage for showing messages
        Stage stage = (Stage) settingsAnchorPane.getScene().getWindow();

        //if only customer name and pending amount required to be exported
        boolean isShowOnlyPendingAmount = showPendingOnlyCheckbox.isSelected();

        ArrayList<CustomerList> customerArrayList = new CustomerService().getCustomerList();

        //checking whether stock data present or not
        if (customerArrayList.isEmpty()) {
            Toast.makeText(stage, "No customer data present", 1000, 500, 500);
            return;
        }

        FileChooser fileChooser = new FileChooser();
        fileChooser.setInitialFileName("CustomerData_" + fileNameDateFormat.format(new Date()));
        //Set extension filter for xlsx files
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("Excel files (*.xlsx)", "*.xlsx");
        fileChooser.getExtensionFilters().add(extFilter);

        //Show save file dialog
        File file = fileChooser.showSaveDialog(stage);

        if (file == null) {
            Toast.makeText(stage, "No file Selected", 1000, 500, 500);
            return;
        }
        //write data to excel file
        if (new ExcelExporter().exportCustomerData(file, customerArrayList, isShowOnlyPendingAmount)) {
            Toast.makeText(stage, "Customer Data Exported Successfully", 1000, 500, 500);
            log.info("Writing on XLSX file Successful ...");
        } else {
            Toast.makeText(stage, "Could Not Export Customer Data. Please check your File or Database Connection", 1000, 500, 500);
            log.info("Writing on XLSX file Successful ...");
        }
    }

    @FXML
    private void updatePassword() {
        Stage stage = (Stage) settingsAnchorPane.getScene().getWindow();

        //check for any empty values
        if (oldPasswordField.getText().isEmpty() || newPasswordField.getText().isEmpty() || confirmPasswordField.getText().isEmpty()) {
            Toast.makeText(stage, "Fields cannot be empty", 1000, 500, 500);
            return;
        }
        //check for same new &cnf password
        if (!newPasswordField.getText().equals(confirmPasswordField.getText())) {
            Toast.makeText(stage, "Password does not match.", 1000, 500, 500);
            return;
        }
        //check for valid old Password
        if (!new UserService().validateOldPassword(oldPasswordField.getText())) {
            Toast.makeText(stage, "Old Password is incorrect", 1000, 500, 500);
            return;
        }
        //update Password
        if (new UserService().changePassword(newPasswordField.getText())) {
            Toast.makeText(stage, "Password changed successfully.", 1000, 500, 500);
        } else {
            Toast.makeText(stage, "Unable to change password. Please check Database Connection", 1000, 500, 500);
        }
    }

    @FXML
    private void updateDbBackupDate() {

        //fetch current date in Local Date object
        LocalDate backupDate = new Date().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

        //fetch day of week from drodown option
        DayOfWeek week = backupDayOptionList.getSelectionModel().getSelectedItem();

        //calculating difference between current day and selected day
        int diff = backupDate.getDayOfWeek().compareTo(week);
        if (diff < 0) {
            backupDate = backupDate.plusDays(Math.abs(diff));
        } else if (diff > 0) {
            backupDate = backupDate.plusDays(7 - diff);
        }
        Date dbBackupDateObject = Date.from(backupDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
        log.info("next backup date to be set= " + dbBackupDateObject);
        //update properties file
        inventoryConfig.getAppProperties().setProperty("nextDbBackupDate", new SimpleDateFormat(InventoryConstants.databaseDateTimeFormat).format(dbBackupDateObject));
        InventoryTimers.getInstance().reinitializeDbBackupTimers();
    }

    private void initializeDefaultLayout() {
        settingsAnchorPane.setPrefWidth(Main.WIDTH - Main.SIDE_BAR_WIDTH);
        settingsAnchorPane.setPrefHeight(Main.HEIGHT - 30);
        Double paneWidth = (Main.WIDTH - Main.SIDE_BAR_WIDTH) / 5 - 20;
        backupTab.setTabMinWidth(paneWidth);
        backupTab.setTabMaxWidth(paneWidth);
    }

}
