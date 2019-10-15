package controller.suppliers;

import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import com.sun.javafx.application.LauncherImpl;
import constants.InventoryConstants;
import db.CustomerService;
import db.ReportService;
import db.SupplierService;
import dto.*;
import javafx.application.Platform;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import main.Main;
import main.MyPreloader;
import main.ProgressBarFrame;
import org.apache.log4j.Logger;
import service.Toast;

import java.io.InputStream;
import java.net.URISyntaxException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;
import java.util.logging.Level;

public class SupplierPaymentRecords implements Initializable {


    private Logger logger = Logger.getLogger(SupplierPaymentRecords.class);

    @FXML
    private DatePicker fromPaymentDate;
    @FXML
    TableView table;
    CustomerService cService;
    ReportService reportService;

    SupplierService supplierService;
    @FXML
    ProgressBar progressBarField;
    @FXML
    private JFXTextField customerName, customerPhone;
    @FXML
    private JFXTextArea customerAddress;
    @FXML
    private TableColumn<SupplierPaymentHistoryList, String> createdOnDate;
    @FXML
    private TableColumn<SupplierPaymentHistoryList, Number> totalAmountColumn;
    @FXML
    private TableColumn<SupplierPaymentHistoryList, Number> payedAmountColumn;
    @FXML
    private TableColumn<SupplierPaymentHistoryList, SupplierPaymentHistoryList> paymentIdColumn;
    @FXML
    private TableColumn<SupplierPaymentHistoryList, Number> pendingAmountColumn;
    @FXML
    private TableColumn<SupplierPaymentHistoryList, String> billingDateColumn;
    @FXML
    private TextField filterField, rowNumberField;

    @FXML
    private AnchorPane customerPaymentHistoryMainPane;
    @FXML
    Stage stage;
    private ObservableList<SupplierPaymentHistoryList> masterData = FXCollections.observableArrayList();

    private Supplier mainSupplier;

    @FXML
    private Button rowsUpdateButton;
    InputStream media1;
    InputStream file;
    URL urlmain;
    ResourceBundle bundle;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        initializeDefaultLayout();
        urlmain = url;
        bundle = rb;
        supplierService = new SupplierService();
        reportService = new ReportService();
        cService = new CustomerService();
        media1 = this.getClass().getResourceAsStream("/resource/images/logo.jpg");
        file = this.getClass().getResourceAsStream("/resource/reports/customerReport.jasper");
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MONTH, -6);
        Date date = calendar.getTime();
        fromPaymentDate.setValue(date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
        createdOnDate.setCellValueFactory(cellData -> cellData.getValue().createdOnProperty());
        totalAmountColumn.setCellValueFactory(cellData -> cellData.getValue().totalPriceProperty());
        payedAmountColumn.setCellValueFactory(cellData -> cellData.getValue().payedAmountProperty());
        pendingAmountColumn.setCellValueFactory(cellData -> cellData.getValue().pendingAmountProperty());
        billingDateColumn.setCellValueFactory(cellData -> cellData.getValue().billingDateProperty());
        paymentIdColumn.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue()));
        paymentIdColumn.setCellFactory(param -> new TableCell<SupplierPaymentHistoryList, SupplierPaymentHistoryList>() {
            private final Button payButton = new Button();

            @Override
            protected void updateItem(SupplierPaymentHistoryList paymentHistoryList, boolean empty) {
                super.updateItem(paymentHistoryList, empty);
                if (paymentHistoryList == null) {
                    setGraphic(null);
                    return;
                }
                payButton.setText(String.valueOf(paymentHistoryList.getPaymentId()));

                payButton.setStyle("-fx-text-fill: #006464;\n"
                        + "    -fx-background-color: #DFB951;\n"
                        + "    -fx-border-radius: 20;\n"
                        + "    -fx-background-radius: 20;\n"
                        + "    -fx-padding: 5;-fx-min-width:100");
                setGraphic(payButton);
                payButton.setOnAction(event -> {
                    FXMLLoader Loader = new FXMLLoader();
                    Loader.setLocation(getClass().getResource("/view/suppliers/supplierPaymentRecordsItem.fxml"));
                    try {
                        Loader.load();

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    SupplierPaymentRecordsItem paymentItemHistory = Loader.getController();
                    paymentItemHistory.initialize(paymentHistoryList);

                    Parent p = Loader.getRoot();
                    customerPaymentHistoryMainPane.getChildren().setAll(p);

                });
            }
        });

        FilteredList<SupplierPaymentHistoryList> filteredData = new FilteredList<>(masterData, p -> true);

        // 2. Set the filter Predicate whenever the filter changes.
        filterField.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(payment -> {
                // If filter text is empty, display all persons.
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }

                // Compare first customerName and last customerName of every person with filter text.
                String lowerCaseFilter = newValue.toLowerCase();

                if (String.valueOf(payment.getPaymentId()).toLowerCase().indexOf(lowerCaseFilter) != -1) {
                    return true; // Filter matches first customerName.
                } else if (payment.getSupplierName().toLowerCase().indexOf(lowerCaseFilter) != -1) {
                    return true; // Filter matches first customerName.
                } else // Filter matches mobile.
                // Filter matches customerAddress.
                {
                    return false;
                }
            });
        });

        // 3. Wrap the FilteredList in a SortedList.
        SortedList<SupplierPaymentHistoryList> sortedData = new SortedList<>(filteredData);

        // 4. Bind the SortedList comparator to the TableView comparator.
        // 	  Otherwise, sorting the TableView would have no effect.
        sortedData.comparatorProperty().bind(table.comparatorProperty());

        // 5. Add sorted (and filtered) data to the table.
        table.setItems(sortedData);
    }

    @FXML
    private void updatePaymentRows() {
        progressBarField.setVisible(true);
        stage = (Stage) customerPaymentHistoryMainPane.getScene().getWindow();

        //going to update table row number
        //check if user input is empty
        //first remove all rows to show new rows, else it gets added on
        masterData.removeAll(masterData.sorted());
        LocalDate ld = fromPaymentDate.getValue();
        SimpleDateFormat sdf = new SimpleDateFormat(InventoryConstants.databaseDateTimeFormat);
        Date paymentBillingDate = Date.from(ld.atStartOfDay(ZoneId.systemDefault()).toInstant());
        String billingDateValue = sdf.format(paymentBillingDate);
        //add rows from payment History table
        masterData.addAll(supplierService.getSupplierPaymentHistoryList(mainSupplier.getSupplierId(), billingDateValue));
        Toast.makeText(stage, "Rows Updated", 500, 500, 500);
    }

    @FXML
    public void generateReport() {
       ProgressBarFrame progressBarFrame=new ProgressBarFrame();
       progressBarFrame.showProgressBar();
        if(!reportProcess()){
            stage = (Stage) customerPaymentHistoryMainPane.getScene().getWindow();
            Toast.makeText(stage, "Unable to print report", 500, 500, 500);
        }

      //  progressBarFrame.hideProgressBar();

    }
    private boolean reportProcess(){
        boolean isSuccessful=false;
        Set<String> paymentIdList = new HashSet<String>();
        ArrayList<SupplierPaymentHistoryList> datam = new ArrayList<SupplierPaymentHistoryList>();
        for (SupplierPaymentHistoryList obj : masterData) {
            paymentIdList.add(String.valueOf(obj.getPaymentId()));
            datam.add(obj);
        }
        HashMap<String, ArrayList<SupplierPaymentItem>> dataMap = supplierService.getPaymentDetailsSupplier(paymentIdList);

        ArrayList<ReportData> dataList = new ArrayList<>();

        Set<String> check = new HashSet<>();
        Collections.sort(datam);
        for (SupplierPaymentHistoryList paymentItem : datam) {

            if (!check.contains(String.valueOf(paymentItem.getPaymentId()))) {
                ReportData row = new ReportData("Product ID", "Product Name", "Unit Price", "Quantity", "Net Cost");
                dataList.add(row);
                ArrayList<SupplierPaymentItem> rowData = dataMap.get(String.valueOf(paymentItem.getPaymentId()));
//find errro here
                for (SupplierPaymentItem item : rowData) {

                    row = new ReportData(String.valueOf(item.getStockId()), item.getItemName(), String.valueOf(item.getItemPrice()),
                            String.valueOf(item.getItemQuantity()),String.valueOf( item.getTotalAmount()));
                    dataList.add(row);
                }
                check.add(String.valueOf(paymentItem.getPaymentId()));
                row = new ReportData(null, null, null,
                        "Payment ID", String.valueOf(paymentItem.getPaymentId()));
                dataList.add(row);
                row = new ReportData(null, null, null,
                        "Billing Date", paymentItem.getBillingDate());
                dataList.add(row);
                row = new ReportData(null, null, null,
                        "Total Amount", String.valueOf(paymentItem.getTotalPrice()));
                dataList.add(row);

//            row = new ReportData(null, null, null,
//                    "Payed Amount", String.valueOf(paymentItem.getPayedAmount()));
//            dataList.add(row);
                row = new ReportData(null, null, null,
                        "Pending Amount", String.valueOf(paymentItem.getPendingAmount()));
                dataList.add(row);

                row = new ReportData(null, null, null,
                        null, "");
                dataList.add(row);
            }
        }
        ReportData row = new ReportData("Payment ID", "Billing Date", "Total Amount", "Payed Amount", "Pending");
        dataList.add(row);
        for (SupplierPaymentHistoryList paymentItem : datam) {
            row = new ReportData(String.valueOf(paymentItem.getPaymentId()), paymentItem.getBillingDate(),
                    String.valueOf(paymentItem.getTotalPrice()), String.valueOf(paymentItem.getPayedAmount()), String.valueOf(paymentItem.getPendingAmount()));
            dataList.add(row);

        }
        InputStream targetStream = null;
        HashMap<String, Object> prameter = new HashMap<>();
        try {
            targetStream = media1;
        } catch (Exception e) {
            e.printStackTrace();
        }
        prameter.put("image_Logo", targetStream);
        prameter.put("titile", InventoryConstants.companyName);
        prameter.put("address1", InventoryConstants.companyAddress1);
        prameter.put("address2", InventoryConstants.companyAddress2);
        prameter.put("address3", InventoryConstants.companyAddress3);
        prameter.put("customerid", mainSupplier.getSupplierId());
        prameter.put("customername", mainSupplier.getName());
        prameter.put("mobileno", mainSupplier.getMobileNumber());
        prameter.put("email", "N/A");
        prameter.put("address", mainSupplier.getAddress());
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(InventoryConstants.dateTimeFormat);
        prameter.put("date", simpleDateFormat.format(new Date()));

        try {
            reportService.printReport(dataList, prameter, file);
            initialize(urlmain, bundle);
            isSuccessful=true;
        } catch (Exception ex) {
            logger.warn("error while printing report ",ex);
        }
        return isSuccessful;
    }

    public void initializeSupplier(Supplier supplier) {
        this.mainSupplier = supplier;
        customerName.setText(supplier.getName());
        customerAddress.setText(supplier.getAddress());
        customerPhone.setText(supplier.getMobileNumber());
        LocalDate ld = fromPaymentDate.getValue();
        SimpleDateFormat sdf = new SimpleDateFormat(InventoryConstants.databaseDateTimeFormat);
        Date paymentBillingDate = Date.from(ld.atStartOfDay(ZoneId.systemDefault()).toInstant());
        String billingDateValue = sdf.format(paymentBillingDate);
        masterData.addAll(supplierService.getSupplierPaymentHistoryList(supplier.getSupplierId(), billingDateValue));
    }


    @FXML
    private void backToBillingPage(){
        FXMLLoader Loader = new FXMLLoader();
        Loader.setLocation(getClass().getResource("/view/suppliers/SupplierBilling.fxml"));
        try {
            Loader.load();

        } catch (Exception e) {
            e.printStackTrace();
        }
        SupplierBilling supplierBillingController = Loader.getController();


        supplierBillingController.setMainSupplier(mainSupplier);
        Parent p = Loader.getRoot();
        customerPaymentHistoryMainPane.getChildren().setAll(p);
    }

    private void initializeDefaultLayout(){
        customerPaymentHistoryMainPane.setPrefWidth(Main.WIDTH-Main.SIDE_BAR_WIDTH);
        customerPaymentHistoryMainPane.setPrefHeight(Main.HEIGHT-30);
    }
}
