package controller.main;

import com.sun.javafx.application.LauncherImpl;
import db.DashboardService;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.application.Application;
import javafx.application.Preloader;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Side;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.*;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import main.InventoryConfig;
import main.Main;
import org.apache.log4j.Logger;
import service.Validator;

import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.ResourceBundle;


public class MainPage implements Initializable {

    @FXML
    private VBox sideBarVbox,pieChartVbox;
    @FXML
    private FontAwesomeIconView usersIcon,settingsIcon,queryButton;
    private Logger logger=Logger.getLogger(MainPage.class);


    @Override
    public void initialize(URL location, ResourceBundle resources){
        initializeDefaultLayout();
        if (!Validator.isCurrentUserAdmin()){
            logger.info("current user is not admin. Restricting");
            usersIcon.setDisable(true);
            //settingsIcon.setDisable(true);
        }
        if (Validator.isCurrentUserTest()){
            logger.info("current user is Test user. Restricting");
            usersIcon.setDisable(true);
            queryButton.setDisable(true);
            //settingsIcon.setDisable(true);
        }

        //show dashboard page
        try {
            Parent root= FXMLLoader.load(getClass().getResource("/view/main/dashboard.fxml"));
            innerAnchorPane.getChildren().setAll(root);
        }
        catch (Exception e){
            e.printStackTrace();
        }

    }

    @FXML
    public AnchorPane innerAnchorPane,mainOuterPane;
    @FXML
    private void showCustomerPage() throws IOException {
        Parent root= FXMLLoader.load(getClass().getResource("/view/users/listUsers.fxml"));
        innerAnchorPane.getChildren().setAll(root);
    }

    @FXML
    private void showQuickTransactionPage() throws IOException {
        Parent root= FXMLLoader.load(getClass().getResource("/view/query/listQueries.fxml"));
        innerAnchorPane.getChildren().setAll(root);
    }
    @FXML
    private void showStockPage() throws IOException {
        Parent root= FXMLLoader.load(getClass().getResource("/view/settings/settingsList.fxml"));
        innerAnchorPane.getChildren().setAll(root);
    }
    @FXML
    private void showPaymentHistoryPage() throws IOException {
        Parent root= FXMLLoader.load(getClass().getResource("/view/History/PaymentHistory.fxml"));
        innerAnchorPane.getChildren().setAll(root);
    }

    @FXML
    private void showSettingsPage() throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/view/settings/settingsList.fxml"));
        innerAnchorPane.getChildren().setAll(root);
    }
    @FXML
    private void showSuppliersPage() throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/view/suppliers/listSuppliers.fxml"));
        innerAnchorPane.getChildren().setAll(root);
    }
    @FXML
    private void showSuppliersPaymentHistoryPage() throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/view/History/SupplierPaymentHistory.fxml"));
        innerAnchorPane.getChildren().setAll(root);
    }
    @FXML
    private void showLoginPage(){
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/view/login/loginPage.fxml"));
            mainOuterPane.getChildren().setAll(root);
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    private void initializeDefaultLayout(){
        mainOuterPane.setPrefWidth(Main.WIDTH);
        mainOuterPane.setPrefHeight(Main.HEIGHT-20);
        sideBarVbox.setPrefHeight(Main.HEIGHT-20);
    }

}
