package controller.query;

import controller.booking.MainBooking;
import db.QueryService;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import dto.CoreLead;
import dto.CoreLeadEntity;
import dto.CustomerList;
import dto.QueriesListDto;
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
import main.Main;
import org.apache.log4j.Logger;

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
    private TableColumn<QueriesListDto, QueriesListDto> actionColumn;

    private ObservableList<QueriesListDto> masterData = FXCollections.observableArrayList();
    private QueryService queryService=new QueryService();
    private Logger logger=Logger.getLogger(ListQueries.class);

    public ListQueries() {
        masterData.addAll(queryService.getAllQueriesList());
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initializeDefaultLayout();



        idColumn.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue()));
        idColumn.setCellFactory(param -> new TableCell<QueriesListDto, QueriesListDto>() {
            private final Button payButton = new Button();

            @Override
            protected void updateItem(QueriesListDto queriesList, boolean empty) {
                super.updateItem(queriesList, empty);
                if (queriesList == null) {
                    setGraphic(null);
                    return;
                }
                payButton.setStyle("-fx-text-fill: #006464;\n" +
                        "    -fx-background-color: #b8ffb0;\n" +
                        "    -fx-border-radius: 20;\n" +
                        "    -fx-background-radius: 20;\n" +
                        "    -fx-padding: 5;-fx-min-width:100");
                payButton.setText(String.valueOf(queriesList.getQueryId()));
                setGraphic(payButton);
                payButton.setOnAction(event -> {

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
        firstNameColumn.setCellValueFactory(cellData -> cellData.getValue().firstnameProperty());
        lastNameColumn.setCellValueFactory(cellData -> cellData.getValue().lastNameProperty());
        emailColumn.setCellValueFactory(cellData -> cellData.getValue().emailProperty());
        branchCodeColumn.setCellValueFactory(cellData -> cellData.getValue().branchCodeProperty());
        callReasonColumn.setCellValueFactory(cellData -> cellData.getValue().callReasonProperty());
        actionColumn.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue()));

        actionColumn.setCellFactory(param -> new TableCell<QueriesListDto, QueriesListDto>() {
            private final Button deleteButton = new Button();
            FontAwesomeIconView listIcon = new FontAwesomeIconView(FontAwesomeIcon.LIST);

            @Override
            protected void updateItem(QueriesListDto queriesListDto, boolean empty) {
                super.updateItem(queriesListDto, empty);
                if (queriesListDto == null) {
                    setGraphic(null);
                    return;
                }

                deleteButton.setText("Booking Section");
                deleteButton.setStyle("-fx-text-fill: #006464;\n" +
                        "    -fx-background-color: #b8ffb0;\n" +
                        "    -fx-border-radius: 20;\n" +
                        "    -fx-background-radius: 20;\n" +
                        "    -fx-padding: 5;-fx-min-width:100");
                listIcon.setGlyphSize(30);
                setGraphic(listIcon);
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
                if (person.getEmail().toLowerCase().indexOf(lowerCaseFilter) != -1) {
                    return true; // Filter matches first name.
                }  else
                if (person.getFirstname().toLowerCase().indexOf(lowerCaseFilter) != -1) {
                    return true; // Filter matches first name.
                } else // Filter matches mobile.
                    if (person.getLastName().toLowerCase().indexOf(lowerCaseFilter) != -1) {
                        return true; // Filter matches address.
                    } else // Filter matches mobile.
                        if (person.getBranchCode().toLowerCase().indexOf(lowerCaseFilter) != -1) {
                            return true; // Filter matches address.
                        } else // Filter matches mobile.
                            return person.getCallReason().toLowerCase().indexOf(lowerCaseFilter) != -1;
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


    private void initializeDefaultLayout(){
        mainPane.setPrefWidth(Main.WIDTH-Main.SIDE_BAR_WIDTH);
        mainPane.setPrefHeight(Main.HEIGHT-30);
    }
}
