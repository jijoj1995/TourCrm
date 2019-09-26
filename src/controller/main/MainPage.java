package controller.main;

import com.sun.javafx.application.LauncherImpl;
import db.DashboardService;
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
import main.Main;

import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.ResourceBundle;


public class MainPage implements Initializable {

    @FXML
    private VBox sideBarVbox,pieChartVbox;
    @FXML
    private PieChart salesChart;
    @FXML
    private BarChart barChart;
    @FXML
    private Label customerNumber,supplierNumber,totalStocksNumber,categoryNumber,pendingNumber,revenueNumber,receiveAmountNumber;
    private DashboardService dashboardService=new DashboardService();


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initializeDefaultLayout();

        HashMap<String,Integer>paymentTableDetails=dashboardService.getPaymentTableDetails();
        customerNumber.setText(String.valueOf(dashboardService.getTotalCustomers()));
        supplierNumber.setText(String.valueOf(dashboardService.getTotalSuppliers()));
        totalStocksNumber.setText(String.valueOf(dashboardService.getTotalStocks()));
        categoryNumber.setText(String.valueOf(dashboardService.getTotalCategory()));
        pendingNumber.setText("$"+(paymentTableDetails.get("totalPendingAmount")));
        revenueNumber.setText("$"+(paymentTableDetails.get("totalRevenueAmount")));
        receiveAmountNumber.setText("$"+(paymentTableDetails.get("totalPayedAmount")));


        ObservableList<PieChart.Data> pieChartData =
                FXCollections.observableArrayList(
                        new PieChart.Data("Receivable", 13),
                        new PieChart.Data("Received", 87));

        salesChart.setTitle("Sales Chart");
        salesChart.setData(pieChartData);
        salesChart.setLabelLineLength(10);
       /*//
        salesChart.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                salesChart.setLegendSide(Side.LEFT);
            }
        });*/


        CategoryAxis xAxis    = new CategoryAxis();
        xAxis.setLabel("Devices");

        NumberAxis yAxis = new NumberAxis();
        yAxis.setLabel("Visits");



        XYChart.Series dataSeries1 = new XYChart.Series();
        dataSeries1.setName("2014");
        dataSeries1.getData().add(new XYChart.Data("Desktop", 567));
        dataSeries1.getData().add(new XYChart.Data("Phone"  , 65));
        dataSeries1.getData().add(new XYChart.Data("Tablet"  , 23));

        barChart.getData().add(dataSeries1);

    }

    @FXML
    public AnchorPane innerAnchorPane,mainOuterPane;
    @FXML
    private void showCustomerPage() throws IOException {
        Parent root= FXMLLoader.load(getClass().getResource("/view/customer/listCustomer.fxml"));
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
