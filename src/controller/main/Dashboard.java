package controller.main;

import db.DashboardService;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.*;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import main.Main;
import org.apache.log4j.Logger;

import java.net.URL;
import java.util.ResourceBundle;

public class Dashboard implements Initializable {
    @FXML
    private AnchorPane mainPane;
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

        /*
        HashMap<String,Integer>paymentTableDetails=dashboardService.getPaymentTableDetails();
        customerNumber.setText(String.valueOf(dashboardService.getTotalCustomers()));
        supplierNumber.setText(String.valueOf(dashboardService.getTotalSuppliers()));
        totalStocksNumber.setText(String.valueOf(dashboardService.getTotalStocks()));
        categoryNumber.setText(String.valueOf(dashboardService.getTotalCategory()));
        pendingNumber.setText("$"+(paymentTableDetails.get("totalPendingAmount")));
        revenueNumber.setText("$"+(paymentTableDetails.get("totalRevenueAmount")));
        receiveAmountNumber.setText("$"+(paymentTableDetails.get("totalPayedAmount")));*/


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
        xAxis.setLabel("Transport");

        NumberAxis yAxis = new NumberAxis();
        yAxis.setLabel("Visits");



        XYChart.Series dataSeries1 = new XYChart.Series();
        dataSeries1.setName("2014");
        dataSeries1.getData().add(new XYChart.Data("Train", 567));
        dataSeries1.getData().add(new XYChart.Data("Flight"  , 65));
        dataSeries1.getData().add(new XYChart.Data("Bus"  , 23));

        barChart.getData().add(dataSeries1);

    }
    private void initializeDefaultLayout(){
        mainPane.setPrefWidth(Main.WIDTH-Main.SIDE_BAR_WIDTH);
        mainPane.setPrefHeight(Main.HEIGHT-30);
    }
}
