package controller.users;

import com.gn.global.plugin.ViewManager;
import com.gn.module.main.Main;
import constants.InventoryConstants;
import controller.query.ListQueries;
import db.UserService;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import dto.CoreUserDto;
import dto.CoreUserEntity;
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
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import main.WorkIndicatorDialog;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ListUsers implements Initializable {

    @FXML
    private StackPane mainPane;
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
    @FXML
    private TableColumn<CoreUserDto, CoreUserDto> actionColumn;

    private ObservableList<CoreUserDto> masterData = FXCollections.observableArrayList();
    private UserService userService=new UserService();
    private Logger logger=Logger.getLogger(ListQueries.class);
    private WorkIndicatorDialog wd=null;

    public ListUsers() {

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Stage stage=new Stage();
        wd = new WorkIndicatorDialog(stage, "Loading...");
        wd.exec("123", inputParam -> {
            masterData.addAll(userService.getAllUsersList());
            return 1;
        });
        wd.addTaskEndNotification(result -> {
            logger.info(result);

        });
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
                payButton.getStyleClass().add("round");

                payButton.setText(String.valueOf(usersList.getCoreUserId()));
                setGraphic(payButton);
                payButton.setOnAction(event -> {

                    FXMLLoader Loader = new FXMLLoader();
                    Loader.setLocation(getClass().getResource(ViewManager.getInstance().get(InventoryConstants.editUserPage)));
                    try {
                        Loader.load();

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    EditUser controller = Loader.getController();
                    CoreUserEntity coreUserEntity = usersList.getCoreUserEntity();

                    controller.initializeCoreUserDto(coreUserEntity);
                    Main.ctrl.body.setContent(Loader.getRoot());
                    Main.ctrl.title.setText(" Edit User");

                });
            }
        });
        firstNameColumn.setCellValueFactory(cellData -> cellData.getValue().firstNameProperty());
        lastNameColumn.setCellValueFactory(cellData -> cellData.getValue().lastNameProperty());
        emailColumn.setCellValueFactory(cellData -> cellData.getValue().emailAddressProperty());
        usernameColumn.setCellValueFactory(cellData -> cellData.getValue().userNameProperty());
        actionColumn.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue()));
        actionColumn.setCellFactory(param -> new TableCell<CoreUserDto, CoreUserDto>() {
            FontAwesomeIconView deleteIcon = new FontAwesomeIconView(FontAwesomeIcon.REMOVE);

            @Override
            protected void updateItem(CoreUserDto usersListDto, boolean empty) {
                super.updateItem(usersListDto, empty);
                if (usersListDto == null) {
                    setGraphic(null);
                    return;
                }
                HBox hbox =new HBox();
                deleteIcon.setCursor(Cursor.HAND);
                deleteIcon.setGlyphSize(30);
                hbox.getChildren().add(deleteIcon);
                hbox.setAlignment(Pos.CENTER);
                hbox.setSpacing(20);
                // setGraphic(deleteIcon);
                setGraphic(hbox);
                deleteIcon.setOnMouseClicked(event -> {
                   //delete user
                    logger.info("going to delete user== "+usersListDto.getUserName());
                    if (userService.deleteUser(usersListDto.getCoreUserEntity())) masterData.remove(usersListDto);

                });
            } });


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
    private void showEditUserPage() {
        Main.ctrl.title.setText(" Add User");
        Main.ctrl.body.setContent(ViewManager.getInstance().loadPage(InventoryConstants.editUserPage).getRoot());
    }





}
