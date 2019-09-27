package controller.customer;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXDialogLayout;
import controller.billing.CustomerBilling;
import controller.billing.PaymentUpdate;
import controller.main.ScreenController;
import db.CustomerService;
import dto.Customer;
import dto.CustomerList;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.Mnemonic;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import main.Main;
import service.Toast;

import java.io.IOException;

public class ListCustomer {

    @FXML
    private AnchorPane customerListOuterPane;
    @FXML
    private AnchorPane listCustomerMainAnchor;
    @FXML
    private TextField filterField;
    @FXML
    private TableView<CustomerList> personTable;
    @FXML
    private TableColumn<CustomerList, String> idColumn;
    @FXML
    private TableColumn<CustomerList, CustomerList> nameColumn;
    @FXML
    private TableColumn<CustomerList, String> addressColumn;
    @FXML
    private TableColumn<CustomerList, String> mobileColumn;
    @FXML
    private TableColumn<CustomerList, CustomerList> pendingAmountColumn;

    @FXML
    private TableColumn<CustomerList, CustomerList> deleteColumn;
    @FXML
    VBox dialogVbox;
    @FXML
    JFXDialog jfxDialog;

    private ObservableList<CustomerList> masterData = FXCollections.observableArrayList();
    CustomerService cs=new CustomerService();
    ScreenController myController;
    public ListCustomer() {
        masterData.addAll(cs.getCustomerList());
    }

    /**
     * Initializes the controller class. This method is automatically called
     * after the fxml file has been loaded.
     *
     * Initializes the table columns and sets up sorting and filtering.
     */
    @FXML
    private void initialize() {
        final int numTextFields = 4 ;
        TextField[] textFields = new TextField[numTextFields];
        VBox root = new VBox(5);
        for (int i = 1; i <= numTextFields; i++) {
            TextField tf = new TextField();
            Button button=new Button("sdfsdf");
            root.getChildren().add(button);
            String name = "Text field "+i ;
            tf.setOnAction(e -> {
                System.out.println("Action on "+name+": text is "+tf.getText());
            });
            root.getChildren().add(tf);
            textFields[i-1] = tf ;
        }

        jfxDialog.setContent(root);
        jfxDialog.setVisible(false);
        /*
        initializeDefaultLayout();
        // 0. Initialize the columns.
        nameColumn.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue()));
        nameColumn.setCellFactory(param -> new TableCell<CustomerList, CustomerList>() {
            private final Button payButton = new Button();

            @Override
            protected void updateItem(CustomerList customerList, boolean empty) {
                super.updateItem(customerList, empty);
                if (customerList == null) {
                    setGraphic(null);
                    return;
                }
                payButton.setStyle("-fx-text-fill: #006464;\n" +
                        "    -fx-background-color: #b8ffb0;\n" +
                        "    -fx-border-radius: 20;\n" +
                        "    -fx-background-radius: 20;\n" +
                        "    -fx-padding: 5;-fx-min-width:100");
                payButton.setText(customerList.getName());
                setGraphic(payButton);
                payButton.setOnAction(event -> {
                    FXMLLoader Loader = new FXMLLoader();
                    Loader.setLocation(getClass().getResource("/view/billing/customerBilling.fxml"));
                    try {
                        Loader.load();

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    CustomerBilling CBS = Loader.getController();
                    Customer Cst = new Customer(customerList.getName(), customerList.getAddress(), customerList.getMobileNumber(), customerList.getEmail());
                    Cst.setCustomerId(customerList.getCustomerId());
                    CBS.setCst(Cst);
                    Parent p = Loader.getRoot();
                    customerListOuterPane.getChildren().setAll(p);
                });
            }
        });
        idColumn.setCellValueFactory(cellData -> cellData.getValue().customerIdProperty());
        addressColumn.setCellValueFactory(cellData -> cellData.getValue().addressProperty());
        mobileColumn.setCellValueFactory(cellData -> cellData.getValue().mobileNumberProperty());
        pendingAmountColumn.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue()));
        deleteColumn.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue()));

        deleteColumn.setCellFactory(param -> new TableCell<CustomerList, CustomerList>() {
            private final Button deleteButton = new Button();

            @Override
            protected void updateItem(CustomerList customerList, boolean empty) {
                super.updateItem(customerList, empty);
                if (customerList == null) {
                    setGraphic(null);
                    return;
                }
                if (!(customerList.getPending().equalsIgnoreCase("0.0") || customerList.getPending().equalsIgnoreCase("0"))) {
                    deleteButton.setDisable(true);
                }else{
                    deleteButton.setDisable(false);
                }
                deleteButton.setText("Delete");
                deleteButton.setStyle("-fx-text-fill: #006464;\n" +
                        "    -fx-background-color: RED;\n" +
                        "    -fx-border-radius: 20;\n" +
                        "    -fx-background-radius: 20;\n" +
                        "    -fx-padding: 5;-fx-min-width:100");
                setGraphic(deleteButton);
                deleteButton.setOnAction(event -> {
                    cs.deleteCustomer(customerList.getCustomerId());
                    masterData.remove(customerList);
                });
            } });
        pendingAmountColumn.setCellFactory(param -> new TableCell<CustomerList, CustomerList>() {
            private final Button payButton = new Button();

            @Override
            protected void updateItem(CustomerList customerList, boolean empty) {
                super.updateItem(customerList, empty);
                if (customerList == null) {
                    setGraphic(null);
                    return;
                }
                payButton.setText(customerList.getPending());
                if (customerList.getPending().equalsIgnoreCase("0.0") || customerList.getPending().equalsIgnoreCase("0")) {
                    payButton.setDisable(true);
                }
                payButton.setStyle("-fx-text-fill: #006464;\n" +
                        "    -fx-background-color: #DFB951;\n" +
                        "    -fx-border-radius: 20;\n" +
                        "    -fx-background-radius: 20;\n" +
                        "    -fx-padding: 5;-fx-min-width:100");
                setGraphic(payButton);
                payButton.setOnAction(event -> {
                    FXMLLoader Loader = new FXMLLoader();
                    Loader.setLocation(getClass().getResource("/view/billing/PaymentUpdate.fxml"));
                    try {
                        Loader.load();

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    PaymentUpdate CBS = Loader.getController();
                    Customer Cst = new Customer(customerList.getName(), customerList.getAddress(), customerList.getAddress(), customerList.getEmail());
                    Cst.setCustomerId(customerList.getCustomerId());
                    CBS.setCst(Cst, customerList.getPending());
                    Parent p = Loader.getRoot();
                    customerListOuterPane.getChildren().setAll(p);

                });
            }
        });


        // 1. Wrap the ObservableList in a FilteredList (initially display all data).
        FilteredList<CustomerList> filteredData = new FilteredList<>(masterData, p -> true);

        // 2. Set the filter Predicate whenever the filter changes.
        filterField.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(person -> {
                // If filter text is empty, display all persons.
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }

                // Compare first name and last name of every person with filter text.
                String lowerCaseFilter = newValue.toLowerCase();
                if (person.getEmail().toLowerCase().indexOf(lowerCaseFilter) != -1) {
                    return true; // Filter matches first name.
                } else if (person.getCustomerId().toLowerCase().indexOf(lowerCaseFilter) != -1) {
                    return true; // Filter matches first name.
                } else
                if (person.getName().toLowerCase().indexOf(lowerCaseFilter) != -1) {
                    return true; // Filter matches first name.
                } else // Filter matches mobile.
                    if (person.getAddress().toLowerCase().indexOf(lowerCaseFilter) != -1) {
                        return true; // Filter matches address.
                    } else // Filter matches mobile.
                        if (person.getName().toLowerCase().indexOf(lowerCaseFilter) != -1) {
                            return true; // Filter matches address.
                        } else // Filter matches mobile.
                            if (person.getPending().toLowerCase().indexOf(lowerCaseFilter) != -1) {
                                return true; // Filter matches address.
                            } else return person.getMobileNumber().toLowerCase().indexOf(lowerCaseFilter) != -1;
            });
        });

        // 3. Wrap the FilteredList in a SortedList.
        SortedList<CustomerList> sortedData = new SortedList<>(filteredData);

        // 4. Bind the SortedList comparator to the TableView comparator.
        // 	  Otherwise, sorting the TableView would have no effect.
        sortedData.comparatorProperty().bind(personTable.comparatorProperty());

        // 5. Add sorted (and filtered) data to the table.
        personTable.setItems(sortedData);


        KeyCombination saveKeyCombination = new KeyCodeCombination(KeyCode.S, KeyCombination.CONTROL_ANY);
        Scene scene1=customerListOuterPane.getScene();
        Scene scene= new Scene(customerListOuterPane);
        Runnable rn = ()-> {
            System.out.println("Accelerator key worked");

        };
        Button btn = new Button("Mnemonic");
        KeyCombination kc = new KeyCodeCombination(KeyCode.P, KeyCombination.ALT_DOWN);
        /*Mnemonic mn = new Mnemonic(add, kc);
        scene.addMnemonic(mn);
        scene.getAccelerators().put(saveKeyCombination,rn);*/



    }
    @FXML
    private void showBillingPage(){
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/view/billing/customerBilling.fxml"));
            customerListOuterPane.getChildren().setAll(root);

        } catch (IOException ex) {
            Stage stage = (Stage) customerListOuterPane.getScene().getWindow();
            Toast.makeText(stage, "Some Error while opening customer page.Please restart the application", 1000, 500, 500);
        }
    }
    private void initializeDefaultLayout(){
        customerListOuterPane.setPrefWidth(Main.WIDTH-Main.SIDE_BAR_WIDTH);
        customerListOuterPane.setPrefHeight(Main.HEIGHT-30);
    }

    @FXML
    public void showInfoDialog(){
        jfxDialog.setVisible(!jfxDialog.isVisible());
        JFXDialogLayout content = new JFXDialogLayout();
        content.setHeading(new Text("Information"));
        content.setBody(new Text("We are going to open your default browser window to let \n" +
                "you choose the gmail account , allow the specified permissions\n" +
                "and then close the window."));

        JFXDialog dialog = new JFXDialog(myController, content, JFXDialog.DialogTransition.CENTER);
        JFXButton button = new JFXButton("Okay");
        button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                dialog.close();
               // SplashWaitController.startBackgroundTasks();
                //myController.setScreen(AmailMain.splashWaitId);

            }
        });
        content.setActions(button);
        dialog.show();
    }

}
