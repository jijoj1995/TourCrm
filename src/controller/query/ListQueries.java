package controller.query;

import controller.booking.MainBooking;
import db.QueryService;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import dto.CoreLead;
import dto.QueriesListDto;
import javafx.application.Platform;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import main.Main;
import main.WorkIndicatorDialog;
import org.apache.log4j.Logger;
import service.Toast;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ListQueries implements Initializable {
    @FXML
    private AnchorPane mainPane;
    @FXML
    private TextField filterField;
    @FXML
    private TableView<QueriesListDto> queriesTable;
    @FXML
    private TableColumn<QueriesListDto, QueriesListDto> idColumn;
    @FXML
    private TableColumn<QueriesListDto, String> firstNameColumn;
    @FXML
    private TableColumn<QueriesListDto, String> lastNameColumn;
    @FXML
    private TableColumn<QueriesListDto, String> emailColumn;
    @FXML
    private TableColumn<QueriesListDto, String> branchCodeColumn;
    @FXML
    private TableColumn<QueriesListDto, String> callReasonColumn;
    @FXML
    private TableColumn<QueriesListDto, String> employeeColumn;

    @FXML
    private TableColumn<QueriesListDto, QueriesListDto> actionColumn;

    private ObservableList<QueriesListDto> masterData = FXCollections.observableArrayList();
    private QueryService queryService=new QueryService();
    private Logger logger=Logger.getLogger(ListQueries.class);
    private WorkIndicatorDialog wd=null;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Stage stage=new Stage();
        wd = new WorkIndicatorDialog(stage, "Loading...");
        wd.exec("123", inputParam -> {
            masterData.addAll(queryService.getAllQueriesList());
            return 1;
        });

        wd.addTaskEndNotification(result -> {
           logger.info(result);
        });
        initializeDefaultLayout();


        idColumn.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue()));
        idColumn.setCellFactory(param -> new TableCell<QueriesListDto, QueriesListDto>() {
            private final Button editUserButton = new Button();

            @Override
            protected void updateItem(QueriesListDto queriesList, boolean empty) {
                super.updateItem(queriesList, empty);
                if (queriesList == null) {
                    setGraphic(null);
                    return;
                }
                editUserButton.getStyleClass().add("buttonLink");
                editUserButton.setCursor(Cursor.HAND);
                editUserButton.setText(String.valueOf(queriesList.getQueryId()));
                setGraphic(editUserButton);
                editUserButton.setOnAction(event -> {

                    FXMLLoader Loader = new FXMLLoader();
                    Loader.setLocation(getClass().getResource("/view/query/mainQuery.fxml"));
                    try {
                        Loader.load();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    MainQuery controller = Loader.getController();
                    CoreLead coreLeadDto = queriesList.getCoreLeadDto();

                    controller.initializeCoreLeadDto(coreLeadDto);
                    Parent p = Loader.getRoot();
                    mainPane.getChildren().setAll(p);

                });
            }
        });
        firstNameColumn.setCellValueFactory(cellData -> cellData.getValue().firstNameProperty());
        lastNameColumn.setCellValueFactory(cellData -> cellData.getValue().lastNameProperty());
        emailColumn.setCellValueFactory(cellData -> cellData.getValue().emailProperty());
        branchCodeColumn.setCellValueFactory(cellData -> cellData.getValue().branchCodeProperty());
        employeeColumn.setCellValueFactory(cellData -> cellData.getValue().employeeNameProperty());
        callReasonColumn.setCellValueFactory(cellData -> cellData.getValue().callReasonProperty());
        actionColumn.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue()));
        actionColumn.setCellFactory(param -> new TableCell<QueriesListDto, QueriesListDto>() {
            FontAwesomeIconView listIcon = new FontAwesomeIconView(FontAwesomeIcon.LIST);
            FontAwesomeIconView emailIcon = new FontAwesomeIconView(FontAwesomeIcon.INBOX);


            @Override
            protected void updateItem(QueriesListDto queriesListDto, boolean empty) {
                super.updateItem(queriesListDto, empty);
                if (queriesListDto == null) {
                    setGraphic(null);
                    return;
                }
                HBox hbox =new HBox();
                listIcon.setCursor(Cursor.HAND);
                listIcon.setGlyphSize(30);
                hbox.getChildren().add(listIcon);
                hbox.getChildren().add(emailIcon);
                hbox.setSpacing(20);
                hbox.setAlignment(Pos.CENTER);
               // setGraphic(listIcon);
                emailIcon.setCursor(Cursor.HAND);
                emailIcon.setGlyphSize(30);
                setGraphic(hbox);

                emailIcon.setOnMouseClicked(event -> {
                    //send email for specific query user
                    Platform.runLater(new Runnable() {
                        @Override public void run() {
                            Stage stage = (Stage) emailIcon.getScene().getWindow();
                            boolean emailSendSuccessful= queryService.sendEmailNotification(queriesListDto.getCoreLeadDto().getCoreLeadCommunication().getPaxEmailFirst());
                            if (emailSendSuccessful){
                                Toast.makeText(stage,"Email sent Successfully",1000,1000,1000 );
                            }
                            else Toast.makeText(stage,"Unable to send Email. Please check your internet or firewall Settings",1000,500,500 );
                        }
                    });
                });
                listIcon.setOnMouseClicked(event -> {
                    //show booking page
                    showMainBookingPage(queriesListDto.getCoreLeadDto());
                });
            } });
        FilteredList<QueriesListDto> filteredData = new FilteredList<>(masterData, p -> true);

        // 2. Set the filter Predicate whenever the filter changes.
        filterField.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(person -> {
                // If filter text is empty, display all persons.
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }

                // Compare first name and last name of every person with filter text.
                String lowerCaseFilter = newValue.toLowerCase();
                if (person.getEmail().toLowerCase().contains(lowerCaseFilter)) {
                    return true; // Filter matches first name.
                }  else
                if (person.getFirstName().toLowerCase().contains(lowerCaseFilter) ) {
                    return true; // Filter matches first name.
                } else // Filter matches mobile.
                    if (person.getLastName().toLowerCase().contains(lowerCaseFilter)) {
                        return true; // Filter matches address.
                    } else // Filter matches mobile.
                        if (person.getBranchCode().toLowerCase().contains(lowerCaseFilter) ) {
                            return true; // Filter matches address.
                        } else // Filter matches mobile.
                            return person.getCallReason().toLowerCase().contains(lowerCaseFilter);
            });
        });

        // 3. Wrap the FilteredList in a SortedList.
        SortedList<QueriesListDto> sortedData = new SortedList<>(filteredData);

        // 4. Bind the SortedList comparator to the TableView comparator.
        // 	  Otherwise, sorting the TableView would have no effect.
        sortedData.comparatorProperty().bind(queriesTable.comparatorProperty());

        // 5. Add sorted (and filtered) data to the table.
        queriesTable.setItems(sortedData);

    }

    @FXML
    private void showMainQueryPage() throws IOException {
        Parent root= FXMLLoader.load(getClass().getResource("/view/query/mainQuery.fxml"));
        mainPane.getChildren().setAll(root);
    }

    @FXML
    private void showMainBookingPage(CoreLead coreLeadDto)  {
        logger.info("showing Main booking page for query id= "+coreLeadDto.getCoreLeadId());
        //loading mainBookingPage
        FXMLLoader Loader = new FXMLLoader();
        Loader.setLocation(getClass().getResource("/view/booking/mainBooking.fxml"));
        try {
            Loader.load();
        } catch (Exception e) {
            e.printStackTrace();
        }
        MainBooking mainBookingPage = Loader.getController();
        mainBookingPage.initializeCoreLeadDto(coreLeadDto);
        Parent p = Loader.getRoot();
        mainPane.getChildren().setAll(p);
    }


    @FXML
    private void showDashboardPage() throws IOException {
        Parent root= FXMLLoader.load(getClass().getResource("/view/main/dashboard.fxml"));
        mainPane.getChildren().setAll(root);
    }


    private void initializeDefaultLayout(){
        mainPane.setPrefWidth(Main.WIDTH-Main.SIDE_BAR_WIDTH);
        mainPane.setPrefHeight(Main.HEIGHT-30);
    }
}
