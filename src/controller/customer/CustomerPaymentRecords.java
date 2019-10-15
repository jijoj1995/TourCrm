package controller.customer;

import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import constants.InventoryConstants;
import controller.History.PaymentItemHistory;
import controller.billing.CustomerBilling;
import db.BillingService;
import db.CustomerService;
import db.ReportService;
import dto.Customer;
import dto.Item;
import dto.PaymentHistoryList;
import dto.ReportData;
import java.io.InputStream;
import java.net.URISyntaxException;
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
import org.apache.log4j.Logger;
import service.Toast;

import java.net.URL;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.logging.Level;

public class CustomerPaymentRecords implements Initializable {

    private Logger logger = Logger.getLogger(CustomerPaymentRecords.class);

    @FXML
    private DatePicker fromPaymentDate;
    @FXML
    TableView table;
    CustomerService cService;
    ReportService reportService;

    BillingService BService;
    @FXML
    private JFXTextField customerName, customerPhone, customerEmail;
    @FXML
    JFXTextArea customerAddress;
    @FXML
    private TableColumn<PaymentHistoryList, String> createdOnDate;

    @FXML
    private TableColumn<PaymentHistoryList, Number> totalAmountColumn;
    @FXML
    private TableColumn<PaymentHistoryList, Number> payedAmountColumn;
    @FXML
    private TableColumn<PaymentHistoryList, PaymentHistoryList> paymentIdColumn;
    @FXML
    private TableColumn<PaymentHistoryList, Number> pendingAmountColumn;
    @FXML
    private TableColumn<PaymentHistoryList, String> billingDateColumn;
    @FXML
    private JFXTextField filterField;

    @FXML
    private AnchorPane outerPane;
    @FXML
    Stage stage;
    private ObservableList<PaymentHistoryList> masterData = FXCollections.observableArrayList();

    private Customer customer;

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
        BService = new BillingService();
        reportService = new ReportService();
        cService = new CustomerService();
        media1 = this.getClass().getResourceAsStream("/resource/images/logo.jpg");
        file = this.getClass().getResourceAsStream("/resource/reports/customerReport.jasper");
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MONTH, -6);
        Date date = calendar.getTime();
        logger.info("date value::::::;" + calendar.getTime());
        fromPaymentDate.setValue(date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
        createdOnDate.setCellValueFactory(cellData -> cellData.getValue().createdOnProperty());
        totalAmountColumn.setCellValueFactory(cellData -> cellData.getValue().totalPriceProperty());
        payedAmountColumn.setCellValueFactory(cellData -> cellData.getValue().payedAmountProperty());
        pendingAmountColumn.setCellValueFactory(cellData -> cellData.getValue().pendingAmountProperty());
        billingDateColumn.setCellValueFactory(cellData -> cellData.getValue().billingDateProperty());

        paymentIdColumn.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue()));
        paymentIdColumn.setCellFactory(param -> new TableCell<PaymentHistoryList, PaymentHistoryList>() {
            private final Button payButton = new Button();

            @Override
            protected void updateItem(PaymentHistoryList paymentHistoryList, boolean empty) {
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
                    Loader.setLocation(getClass().getResource("/view/History/PaymentItemHistory.fxml"));
                    try {
                        Loader.load();

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    PaymentItemHistory paymentItemHistory = Loader.getController();
                    paymentItemHistory.initialize(paymentHistoryList);

                    Parent p = Loader.getRoot();
                    outerPane.getChildren().setAll(p);

                });
            }
        });

        FilteredList<PaymentHistoryList> filteredData = new FilteredList<>(masterData, p -> true);

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
                } else if (payment.getCustomerName().toLowerCase().indexOf(lowerCaseFilter) != -1) {
                    return true; // Filter matches first customerName.
                } else // Filter matches mobile.
                // Filter matches customerAddress.
                {
                    return false;
                }
            });
        });

        // 3. Wrap the FilteredList in a SortedList.
        SortedList<PaymentHistoryList> sortedData = new SortedList<>(filteredData);

        // 4. Bind the SortedList comparator to the TableView comparator.
        // 	  Otherwise, sorting the TableView would have no effect.
        sortedData.comparatorProperty().bind(table.comparatorProperty());

        // 5. Add sorted (and filtered) data to the table.
        table.setItems(sortedData);
    }

    @FXML
    private void updatePaymentRows() {
        stage = (Stage) outerPane.getScene().getWindow();

        //going to update table row number
        //check if user input is empty
        //first remove all rows to show new rows, else it gets added on
        masterData.removeAll(masterData.sorted());
        LocalDate ld = fromPaymentDate.getValue();
        SimpleDateFormat sdf = new SimpleDateFormat(InventoryConstants.databaseDateTimeFormat);
        Date paymentBillingDate = Date.from(ld.atStartOfDay(ZoneId.systemDefault()).toInstant());
        String billingDateValue = sdf.format(paymentBillingDate);
        //add rows from payment History table
        masterData.addAll(BService.getCustomerPaymentHistoryList(Integer.parseInt(customer.getCustomerId()), billingDateValue));
        Toast.makeText(stage, "Rows Updated", 500, 500, 500);
    }

    @FXML
    public void reportGenrate() {
        Set<String> paymentIdList = new HashSet<String>();
        ArrayList<PaymentHistoryList> datam = new ArrayList<PaymentHistoryList>();
        for (PaymentHistoryList obj : masterData) {
            paymentIdList.add(String.valueOf(obj.getPaymentId()));
            datam.add(obj);
        }
        HashMap<String, ArrayList<Item>> dataMap = cService.getPaymentDetailsCustomer(paymentIdList);

        ArrayList<ReportData> dataList = new ArrayList<ReportData>();

        Set<String> check = new HashSet<String>();
        Collections.sort(datam);
        for (PaymentHistoryList paymentItem : datam) {

            if (!check.contains(String.valueOf(paymentItem.getPaymentId()))) {
                ReportData row = new ReportData("Product ID", "Product Name", "Unit Price", "Quantity", "Net Cost");
                dataList.add(row);
                ArrayList<Item> rowData = dataMap.get(String.valueOf(paymentItem.getPaymentId()));
                for (Item item : rowData) {

                    row = new ReportData(item.getStockId().toString(), item.getName(), item.getRate().toString(),
                            item.getQty(), item.getPrice().toString());
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
        for (PaymentHistoryList paymentItem : datam) {
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
        prameter.put("customerid", customer.getCustomerId());
        prameter.put("customername", customer.getName());
        prameter.put("mobileno", customer.getMobileNumber());
        prameter.put("email", customer.getEmail());
        prameter.put("address", customer.getAddress().replaceAll("\n"," "));
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(InventoryConstants.dateTimeFormat);
        prameter.put("date", simpleDateFormat.format(new Date()));

        try {
            reportService.printReport(dataList, prameter, file);
            initialize(urlmain, bundle);
        } catch (URISyntaxException ex) {
            java.util.logging.Logger.getLogger(CustomerPaymentRecords.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void initializeCustomer(Customer customer) {
        this.customer = customer;
        customerName.setText(customer.getName());
        customerEmail.setText(customer.getEmail());
        customerAddress.setText(customer.getAddress());
        customerPhone.setText(customer.getMobileNumber());
        LocalDate ld = fromPaymentDate.getValue();
        SimpleDateFormat sdf = new SimpleDateFormat(InventoryConstants.databaseDateTimeFormat);
        Date paymentBillingDate = Date.from(ld.atStartOfDay(ZoneId.systemDefault()).toInstant());
        String billingDateValue = sdf.format(paymentBillingDate);
        masterData.addAll(BService.getCustomerPaymentHistoryList(Integer.parseInt(customer.getCustomerId()), billingDateValue));
    }

    private void initializeDefaultLayout(){
        outerPane.setPrefWidth(Main.WIDTH-Main.SIDE_BAR_WIDTH);
        outerPane.setPrefHeight(Main.HEIGHT-40);
    }
    @FXML
    private void backToBillingPage(){
        FXMLLoader Loader = new FXMLLoader();
        Loader.setLocation(getClass().getResource("/view/billing/customerBilling.fxml"));
        try {
            Loader.load();

        } catch (Exception e) {
            e.printStackTrace();
        }
        CustomerBilling CBS = Loader.getController();
        CBS.setCst(customer);
        Parent p = Loader.getRoot();
        outerPane.getChildren().setAll(p);
    }
}

