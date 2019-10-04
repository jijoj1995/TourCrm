package controller.users;

import controller.booking.MainBooking;
import controller.query.ListQueries;
import controller.query.MainQuery;
import db.QueryService;
import db.UserService;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import dto.CoreLead;
import dto.CoreUserDto;
import dto.CoreUserEntity;
import dto.QueriesListDto;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import main.Main;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ListUsers implements Initializable {

    @FXML
    private AnchorPane mainPane;
    @FXML
    private TextField filterField;
    @FXML
    private TableView<CoreUserDto> usersTable;
    @FXML
    private TableColumn<CoreUserDto, CoreUserDto> idColumn;
    @FXML
    private TableColumn<CoreUserDto, String> firstNameColumn;
    @FXML
    private TableColumn<CoreUserDto, String> lastNameColumn;
    @FXML
    private TableColumn<CoreUserDto, String> emailColumn;

    @FXML
    private TableColumn<CoreUserDto, String> usernameColumn;

    private ObservableList<CoreUserDto> masterData = FXCollections.observableArrayList();
    private UserService userService=new UserService();
    private Logger logger=Logger.getLogger(ListQueries.class);

    public ListUsers() {
        masterData.addAll(userService.getAllUsersList());
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initializeDefaultLayout();

        idColumn.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue()));
        idColumn.setCellFactory(param -> new TableCell<CoreUserDto, CoreUserDto>() {
            private final Button payButton = new Button();

            @Override
            protected void updateItem(CoreUserDto usersList, boolean empty) {
                super.updateItem(usersList, empty);
                if (usersList == null) {
                    setGraphic(null);
                    return;
                }
                payButton.getStyleClass().add("buttonLink");
                /*payButton.setStyle("-fx-text-fill: #006464;\n" +
                        "    -fx-background-color: #b8ffb0;\n" +
                        "    -fx-border-radius: 20;\n" +
                        "    -fx-background-radius: 20;\n" +
                        "    -fx-padding: 5;-fx-min-width:100");*/
                payButton.setText(String.valueOf(usersList.getCoreUserId()));
                setGraphic(payButton);
                payButton.setOnAction(event -> {

                    FXMLLoader Loader = new FXMLLoader();
                    Loader.setLocation(getClass().getResource("/view/users/editUser.fxml"));
                    try {
                        Loader.load();

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    EditUser controller = Loader.getController();
                    CoreUserEntity coreUserEntity = usersList.getCoreUserEntity();

                    controller.initializeCoreUserDto(coreUserEntity);
                    Parent p = Loader.getRoot();
                    mainPane.getChildren().setAll(p);


                });
            }
        });
        firstNameColumn.setCellValueFactory(cellData -> cellData.getValue().firstNameProperty());
        lastNameColumn.setCellValueFactory(cellData -> cellData.getValue().lastNameProperty());
        emailColumn.setCellValueFactory(cellData -> cellData.getValue().emailAddressProperty());
        usernameColumn.setCellValueFactory(cellData -> cellData.getValue().userNameProperty());



        FilteredList<CoreUserDto> filteredData = new FilteredList<>(masterData, p -> true);

        // 2. Set the filter Predicate whenever the filter changes.
        filterField.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(person -> {
                // If filter text is empty, display all persons.
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }

                // Compare first name and last name of every person with filter text.
                String lowerCaseFilter = newValue.toLowerCase();
                if (person.getEmailAddress().toLowerCase().indexOf(lowerCaseFilter) != -1) {
                    return true; // Filter matches first name.
                }  else
                if (person.getFirstName().toLowerCase().indexOf(lowerCaseFilter) != -1) {
                    return true; // Filter matches first name.
                } else // Filter lastname mobile.
                   return person.getLastName().toLowerCase().indexOf(lowerCaseFilter) != -1 ;
            });
        });

        // 3. Wrap the FilteredList in a SortedList.
        SortedList<CoreUserDto> sortedData = new SortedList<>(filteredData);

        // 4. Bind the SortedList comparator to the TableView comparator.
        // 	  Otherwise, sorting the TableView would have no effect.
        sortedData.comparatorProperty().bind(usersTable.comparatorProperty());

        // 5. Add sorted (and filtered) data to the table.
        usersTable.setItems(sortedData);

    }

    @FXML
    private void showEditUserPage() throws IOException {
        Parent root= FXMLLoader.load(getClass().getResource("/view/users/editUser.fxml"));
        mainPane.getChildren().setAll(root);
    }


    private void initializeDefaultLayout(){
        mainPane.setPrefWidth(Main.WIDTH-Main.SIDE_BAR_WIDTH);
        mainPane.setPrefHeight(Main.HEIGHT-30);
    }
}
