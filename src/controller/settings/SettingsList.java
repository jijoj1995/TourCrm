package controller.settings;

import com.gn.global.util.Alerts;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXToggleButton;
import constants.InventoryConstants;
import db.DbBackupService;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import main.InventoryConfig;
import main.Main;
import org.apache.log4j.Logger;
import service.Toast;
import service.Validator;
import timers.InventoryTimers;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.ResourceBundle;

public class SettingsList implements Initializable {
    private static Logger log = Logger.getLogger(SettingsList.class.getName());
    private DateFormat fileNameDateFormat = new SimpleDateFormat(InventoryConstants.FileNamedateTimeFormat);
    @FXML
    private StackPane mainPane;
    @FXML
    private TabPane settingsTabPane;

    @FXML
    private JFXToggleButton automaticBackupCheckBox,usePortCheck,usePasswordCheck,emableEmailCheck;
    @FXML
    private HBox backupDetailsBox,portNumberHbox,passwordHbox;
    @FXML
    private VBox emailVbox;
    @FXML
    private JFXComboBox<DayOfWeek> backupDayOptionList;
    @FXML
    private PasswordField oldPasswordField, newPasswordField, confirmPasswordField,emailPasswordField;
    @FXML
    private JFXTextField dbName,systemIpAddress,dbUserName,dbPassword,dbIpAddress,dbPortNumber,emailField,emailSubjectField;
    @FXML
    private JFXTextArea emailMessageField;
    @FXML
    private Tab overallSettingsTab,databaseTab,emailTab;
    private Logger logger=Logger.getLogger(SettingsList.class);

    private InventoryConfig inventoryConfig = InventoryConfig.getInstance();


    @Override
    public void initialize(URL location, ResourceBundle resources) {
                    //check whether to give full access or not
        enableTabBasedOnUser();
        initialiseDatabaseProperties();
        initialiseEmailDetails();
                                          //fetch backup property enabled from properties file
        boolean isAutomaticBackupEnabled = false;
        try {
            isAutomaticBackupEnabled = Boolean.parseBoolean(inventoryConfig.getAppProperties().getProperty("automaticBackup"));
        } catch (Exception e) {
            log.warn("exception while fetching automatic backup properties from property file" + e.getMessage());
        }
                                        //set vbox visibility based on backup property
        if (!isAutomaticBackupEnabled) {
            backupDetailsBox.setVisible(false);
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

        //toggle port hbox
        usePortCheck.setOnAction((event) -> {
            ToggleButton but = (ToggleButton) event.getTarget();
            if (but.isSelected()) {
                portNumberHbox.setVisible(true);
                portNumberHbox.setMaxHeight(50);
                portNumberHbox.setPrefHeight(50);
            } else {
                portNumberHbox.setVisible(false);
                portNumberHbox.setPrefHeight(0);
                portNumberHbox.setMaxHeight(0);
            }
        });
            //toggle for use database password
        usePasswordCheck.setOnAction((event) -> {
            ToggleButton but = (ToggleButton) event.getTarget();
            if (but.isSelected()) {
                passwordHbox.setVisible(true);
                passwordHbox.setMaxHeight(50);
                passwordHbox.setPrefHeight(50);
            } else {
                passwordHbox.setVisible(false);
                passwordHbox.setPrefHeight(0);
                passwordHbox.setMaxHeight(0);
            }
        });
        //check email hbox toggle
        emableEmailCheck.setOnAction((event) -> {
            ToggleButton but = (ToggleButton) event.getTarget();
            if (but.isSelected()) {
                emailVbox.setVisible(true);
                inventoryConfig.getAppProperties().setProperty("sendEmailOnQuery","true");
            } else {
                emailVbox.setVisible(false);
                inventoryConfig.getAppProperties().setProperty("sendEmailOnQuery","false");
            }
        });
    }

    private void initialiseDatabaseProperties(){
        systemIpAddress.setText(inventoryConfig.getAppProperties().getProperty("currentIpAddress"));
        if (!Validator.useSpecificDatabasePort()){
            portNumberHbox.setVisible(false);
            portNumberHbox.setMaxHeight(0);
            portNumberHbox.setPrefHeight(0);
            usePortCheck.setSelected(false);
        }
        else {
            portNumberHbox.setVisible(true);
            usePortCheck.setSelected(true);
        }
        if (!Validator.useSpecificDatabasePassword()){
            passwordHbox.setVisible(false);
            passwordHbox.setMaxHeight(0);
            passwordHbox.setPrefHeight(0);
            usePasswordCheck.setSelected(false);
        }
        else {
            passwordHbox.setVisible(true);
            usePasswordCheck.setSelected(true);
        }
        dbName.setText(inventoryConfig.getAppProperties().getProperty("databaseName"));
        dbIpAddress.setText(inventoryConfig.getAppProperties().getProperty("databaseIpAddress"));
        dbPassword.setText(inventoryConfig.getAppProperties().getProperty("databasePassword"));
        dbPortNumber.setText(inventoryConfig.getAppProperties().getProperty("databasePortNumber"));
        dbUserName.setText(inventoryConfig.getAppProperties().getProperty("databaseUserName"));
    }


    private void initialiseEmailDetails(){
        boolean sendEmailNotification= Boolean.parseBoolean((inventoryConfig.getAppProperties().getProperty("sendEmailOnQuery")==null)?"false":inventoryConfig.getAppProperties().getProperty("sendEmailOnQuery"));
        if (sendEmailNotification){
            emableEmailCheck.setSelected(true);
            emailVbox.setVisible(true);
        }
        else {
            emableEmailCheck.setSelected(false);
            emailVbox.setVisible(false);
        }
        emailField.setText(inventoryConfig.getAppProperties().getProperty("adminEmailId"));
        emailPasswordField.setText(inventoryConfig.getAppProperties().getProperty("adminEmailPassword"));
        emailSubjectField.setText(inventoryConfig.getAppProperties().getProperty("emailSubject"));
        emailMessageField.setText(inventoryConfig.getAppProperties().getProperty("emailMessage"));
    }

    private void enableTabBasedOnUser(){
        if (!Validator.isCurrentUserAdmin()){
            logger.info("current user is not admin. Restricting Tabs in settings page");
            //donot show these tabs to other user
            overallSettingsTab.setDisable(true);
            emailTab.setDisable(true);
        }
    }

    @FXML
    private void saveEmailSettings(){
        inventoryConfig.getAppProperties().setProperty("adminEmailId",emailField.getText());
        inventoryConfig.getAppProperties().setProperty("adminEmailPassword",emailPasswordField.getText());
        inventoryConfig.getAppProperties().setProperty("emailSubject",emailSubjectField.getText());
        inventoryConfig.getAppProperties().setProperty("emailMessage",emailMessageField.getText());
        inventoryConfig.getAppProperties().setProperty("sendEmailOnQuery",String.valueOf(emableEmailCheck.isSelected()));
        Stage stage = (Stage) mainPane.getScene().getWindow();
        Alerts.success("Updated","Updated Successfully");
    }


    @FXML
    private void saveDatabaseConnection(){
        inventoryConfig.getAppProperties().setProperty("databaseName",dbName.getText());
        inventoryConfig.getAppProperties().setProperty("databaseIpAddress",dbIpAddress.getText());
        inventoryConfig.getAppProperties().setProperty("databasePassword",dbPassword.getText());
        inventoryConfig.getAppProperties().setProperty("databasePortNumber",dbPortNumber.getText());
        inventoryConfig.getAppProperties().setProperty("databaseUserName",dbUserName.getText());
        inventoryConfig.getAppProperties().setProperty("usePortCheck",String.valueOf(usePortCheck.isSelected()));
        inventoryConfig.getAppProperties().setProperty("useDatabasePassword",String.valueOf(usePasswordCheck.isSelected()));

        Stage stage = (Stage) mainPane.getScene().getWindow();
        Alerts.success("Updated","Updated Successfully");
    }

    @FXML
    private void backupEntireDatabase() {
            //stage for showing messages
        Stage stage = (Stage) mainPane.getScene().getWindow();

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
        Stage stage = (Stage) mainPane.getScene().getWindow();
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



    /*  @FXML
    private void exportStocksData() {

        log.info("going to backup stock data");
        Stage stage = (Stage) mainPane.getScene().getWindow();
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

   */
}
