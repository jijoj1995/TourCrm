package controller.query;

import com.jfoenix.controls.*;
import com.jfoenix.validation.RequiredFieldValidator;
import constants.LeadsConstants;
import db.QueryService;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import dto.*;
import javafx.application.Platform;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.Window;
import main.InventoryConfig;
import main.Main;
import org.apache.log4j.Logger;
import service.Toast;

import java.io.IOException;
import java.net.URL;
import java.util.*;

public class MainQuery implements Initializable {
    @FXML
    private AnchorPane mainPane;
    @FXML
    private TabPane queryTabs;
    @FXML
    private JFXTextField firstName,middleName,lastName,userId,queryIdColumn,queryTime,branchCode,country, paxEmailFirst,paxEmailSecond,usaHome,usaMobile,indiaMobile,indiaLandLine,usaWork;
    @FXML
    private JFXComboBox<String> channelCode,querySource,reasonOfCall,currencyCode,lobCode,shift;
    @FXML
    private Button notesButton;
    @FXML
    private JFXScrollPane jfxDialogScrollPane;
    @FXML
    private VBox notesdialogVbox;
    @FXML
    private HBox queryIdHbox;
    @FXML
    private RequiredFieldValidator requiredFirstName;
    private QueryService queryService=new QueryService();
    private CoreLead coreLeadDto;
    private Logger logger=Logger.getLogger(MainQuery.class);
    private int numberOfNotes =2;
    Set<CoreLeadNotesEntity> coreLeadNotesEntitySet =new HashSet<>();
    InventoryConfig inventoryConfig=InventoryConfig.getInstance();
    ObservableList<CoreLeadNotesDto> data = FXCollections.observableArrayList();
    Stage notesDialog=null;
    @Override
    public void initialize(URL location, ResourceBundle resources) {
                                  // notesDialogBox.setPrefWidth(0);
                                        //set window based on screen size
        initializeDefaultLayout();
        initialiseAllCheckBoxDefalutValues();
        setNumberOnlyInputCheck();
        //hide query id checkbox if first time
        queryIdHbox.setVisible(false);
        queryIdHbox.setMaxHeight(0);
        queryIdHbox.setPrefHeight(0);
     //   initialiseNotesTab();
    }
    public void initializeCoreLeadDto(CoreLead coreLead){
                                 //this method called when already present queryData is clicked
        this.coreLeadDto=coreLead;
        initializeAllInputTextsFromDto(coreLeadDto);
    }

    private void initializeAllInputTextsFromDto(CoreLead coreLeadDto){
        logger.info("initialising all text fields from dto object");
        if (coreLeadDto ==null){
            logger.warn("coreLeadDto is null. returning");
            return;
        }
        queryIdHbox.setVisible(true);
        queryIdHbox.setPrefHeight(25);
        queryIdHbox.setMaxHeight(25);
        //userId.setText(coreLeadDto.getCoreLeadId());
        firstName.setText(coreLeadDto.getFirstName());
        middleName.setText(coreLeadDto.getMiddleName());
        lastName.setText(coreLeadDto.getLastName());
        branchCode.setText(coreLeadDto.getBranchCode());
        channelCode.setValue(coreLeadDto.getChannelCode());
        country.setText(coreLeadDto.getCountry());
        querySource.setValue(coreLeadDto.getQuerySource());
        currencyCode.setValue(coreLeadDto.getCurrencyCode());
        shift.setValue(coreLeadDto.getShift());
        reasonOfCall.setValue(coreLeadDto.getCallReason());
        lobCode.setValue(coreLeadDto.getLobCode());
        queryTime.setText(coreLeadDto.getQuerytime());
        queryIdColumn.setText(String.valueOf(coreLeadDto.getCoreLeadId()));
        if(coreLeadDto.getCoreLeadCommunication()==null){
            logger.warn("coreLeadCommunication object is null of coreLead. returning");
            return;
        }
            paxEmailFirst.setText(coreLeadDto.getCoreLeadCommunication().getPaxEmailFirst());
            paxEmailSecond.setText(coreLeadDto.getCoreLeadCommunication().getPaxEmailSecond());
            usaMobile.setText(coreLeadDto.getCoreLeadCommunication().getUsaMobile());
            indiaLandLine.setText(coreLeadDto.getCoreLeadCommunication().getIndiaLandline());
            usaWork.setText(coreLeadDto.getCoreLeadCommunication().getUsaWorkNumber());
            usaHome.setText(coreLeadDto.getCoreLeadCommunication().getUsaHome());
            indiaMobile.setText(coreLeadDto.getCoreLeadCommunication().getIndiaMobile());

            //set notes data to table
        if (coreLeadDto.getCoreLeadNotesEntitySet()!=null) {
            for (CoreLeadNotesEntity entity : coreLeadDto.getCoreLeadNotesEntitySet())
                data.add(new CoreLeadNotesDto(entity.getCoreLeadNotesId(), entity.getNotesData()));
        }

    }

    private void setNumberOnlyInputCheck(){

    }


    @FXML
    private void showSubQueryPage(){
        logger.info("saving entered data to Core Lead dto before going to subQueryPage");
            setTextFieldDataToDto();
            //loading sub queryPage
        FXMLLoader Loader = new FXMLLoader();
        Loader.setLocation(getClass().getResource("/view/query/subQuery.fxml"));
        try {
            Loader.load();
        } catch (Exception e) {
            e.printStackTrace();
        }
        SubQuery subQueryPage = Loader.getController();
        subQueryPage.initializeCoreLeadObject(coreLeadDto);
        Parent p = Loader.getRoot();
        mainPane.getChildren().setAll(p);
    }

    private void setTextFieldDataToDto(){
        if (coreLeadDto ==null){
            coreLeadDto =new CoreLead();
            coreLeadDto.setCoreLeadCommunication(new CoreLeadCommunication());

        }
        coreLeadDto.setFirstName(firstName.getText());
        coreLeadDto.setMiddleName(middleName.getText());
        coreLeadDto.setLastName(lastName.getText());
        coreLeadDto.setChannelCode(channelCode.getValue());
        coreLeadDto.setCountry(country.getText());
        coreLeadDto.setQuerySource(querySource.getValue());
        coreLeadDto.setCurrencyCode(currencyCode.getValue());
        coreLeadDto.setShift(shift.getValue());
        coreLeadDto.setCallReason(reasonOfCall.getValue());
        coreLeadDto.setLobCode(lobCode.getValue());
        coreLeadDto.setBranchCode(branchCode.getText());
        if (coreLeadDto.getCoreLeadCommunication()==null){
            coreLeadDto.setCoreLeadCommunication(new CoreLeadCommunication());
        }
        coreLeadDto.getCoreLeadCommunication().setUsaWorkNumber(usaWork.getText());
        coreLeadDto.getCoreLeadCommunication().setPaxEmailFirst(paxEmailFirst.getText());
        coreLeadDto.getCoreLeadCommunication().setUsaMobile(usaMobile.getText());
        coreLeadDto.getCoreLeadCommunication().setIndiaLandline(indiaLandLine.getText());
        coreLeadDto.getCoreLeadCommunication().setIndiaMobile(indiaMobile.getText());
        coreLeadDto.getCoreLeadCommunication().setPaxEmailSecond(paxEmailSecond.getText());
        coreLeadDto.getCoreLeadCommunication().setUsaHome(usaHome.getText());

        //set notes data
        setNotesDataToDto();
    }

    private void setNotesDataToDto(){
        if (data.isEmpty()){
            logger.warn("no notes details to be saved. returning");
            return;
        }
        ArrayList<CoreLeadNotesEntity>coreBookingPassengerEntities= queryService.getNotesListFromTable(data);
        coreLeadDto.setCoreLeadNotesEntitySet(coreBookingPassengerEntities);

    }


    @FXML
    private void saveCompleteLeadInformation() throws IOException{
       if (!isRequiredFieldEntered()){
           Toast.makeText((Stage)mainPane.getScene().getWindow(),"Please enter the Required Fields",1000,500,500);
        //showAlert(Alert.AlertType.ERROR, mainPane.getScene().getWindow(),"Form Error!", "Please enter your email id");
        return;
    }
        Stage stage = (Stage) mainPane.getScene().getWindow();
        //before saving set data from all textFields
        setTextFieldDataToDto();
        //set querytime as current time
        coreLeadDto.setQuerytime(new Date().toString());

            //save to db
        if (queryService.saveQueryData(coreLeadDto)){
            //saving successful
            boolean senEmailNotification=Boolean.parseBoolean(inventoryConfig.getAppProperties().getProperty("sendEmailOnQuery"));
            if (senEmailNotification&&coreLeadDto.getCoreLeadId()==0){//send email first time only
                Platform.runLater(new Runnable() {
                    @Override public void run() {
                        boolean emailSendSuccessful= queryService.sendEmailNotification(coreLeadDto.getCoreLeadCommunication().getPaxEmailFirst());
                        if (emailSendSuccessful){
                            Toast.makeText(stage,"Email sent Successfully",1000,1000,1000 );
                        }
                        else Toast.makeText(stage,"Unable to send Email. Please check your internet or firewall Settings",1000,500,500 );
                    }
                });


            }
            showQuickTransactionPage();
        }
        else{

            Toast.makeText(stage,"Unable to save query data. Please check input values or restart application",1000,500,500 );
        }
    }

    @FXML
    private void showQuickTransactionPage() throws IOException {
        Parent root= FXMLLoader.load(getClass().getResource("/view/query/listQueries.fxml"));
        mainPane.getChildren().setAll(root);
    }

    private void initialiseAllCheckBoxDefalutValues(){
        currencyCode.getItems().addAll(LeadsConstants.currencyCodes);
        currencyCode.setValue(LeadsConstants.currencyCodes[0]);

        channelCode.getItems().addAll(LeadsConstants.channelCode);
        channelCode.setValue(LeadsConstants.channelCode[0]);

        querySource.getItems().addAll(LeadsConstants.querySource);
        querySource.setValue(LeadsConstants.querySource[0]);

        reasonOfCall.getItems().addAll(LeadsConstants.reasonOfCall);
        reasonOfCall.setValue(LeadsConstants.reasonOfCall[0]);

        lobCode.getItems().addAll(LeadsConstants.lobCode);
        lobCode.setValue(LeadsConstants.lobCode[0]);

        shift.getItems().addAll(LeadsConstants.shift);
        shift.setValue(LeadsConstants.shift[0]);
    }
    private void initializeDefaultLayout() {
        //setting window size based on screen size
        mainPane.setPrefWidth(Main.WIDTH - Main.SIDE_BAR_WIDTH);
        mainPane.setPrefHeight(Main.HEIGHT - 30);
        double paneWidth = (Main.WIDTH - Main.SIDE_BAR_WIDTH) / 2 - 30;
        queryTabs.setTabMinWidth(paneWidth);
        queryTabs.setTabMaxWidth(paneWidth);

    }
    @FXML
    private void changeTabPane(){
        queryTabs.getSelectionModel().selectNext();
    }

    @FXML
    private void showNotesTab(){
        if (notesDialog==null) {
            notesDialog = new Stage();
            notesDialog.addEventHandler(KeyEvent.KEY_RELEASED, (KeyEvent event) -> {
                if (KeyCode.ESCAPE == event.getCode()) {
                    notesDialog.close();
                }
            });
            notesDialog.setX(notesButton.getLayoutX() + 200);
            notesDialog.setY(notesButton.getLayoutY() + 350);
            notesDialog.setWidth(400);
            notesDialog.setHeight(350);
            notesDialog.initOwner(mainPane.getScene().getWindow());
            notesDialog.initModality(Modality.APPLICATION_MODAL);
            VBox vBox = new VBox();
            vBox.setSpacing(15);
            Label label = new Label("Notes Section");
            label.setFont(Font.font("", FontWeight.BOLD, 16));
            Button button = new Button("add");
            button.getStyleClass().add("buttonPrimary");
            JFXTextArea jfxTextArea = new JFXTextArea();

            button.setOnAction(event -> {
                if (!jfxTextArea.getText().equals(""))
                    data.add(new CoreLeadNotesDto(null, jfxTextArea.getText()));
                jfxTextArea.setText("");
            });
            TableView tableView = new TableView();
            TableColumn notesColumn = new TableColumn("Notes");
            TableColumn<CoreLeadNotesDto, CoreLeadNotesDto> deleteColumn = new TableColumn<>("Action");
            tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
            tableView.getColumns().addAll(notesColumn,deleteColumn);
            tableView.setEditable(true);

            notesColumn.setCellValueFactory(new PropertyValueFactory<CoreLeadNotesDto, String>("notesData"));

            deleteColumn.setCellValueFactory(
                    new PropertyValueFactory<CoreLeadNotesDto, CoreLeadNotesDto>("Action")
            );
            deleteColumn.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue()));
            deleteColumn.setCellFactory(param -> new TableCell<CoreLeadNotesDto, CoreLeadNotesDto>() {
                FontAwesomeIconView deleteButton = new FontAwesomeIconView(FontAwesomeIcon.REMOVE);

                @Override
                protected void updateItem(CoreLeadNotesDto item, boolean empty) {
                    super.updateItem(item, empty);
                    if (item == null) {
                        setGraphic(null);
                        return;
                    }
                    deleteButton.setCursor(Cursor.HAND);
                    deleteButton.setGlyphSize(30);
                    setGraphic(deleteButton);
                    deleteButton.setOnMouseClicked(event -> {
                        data.remove(item);
                    });
                }
            });
            tableView.setItems(data);
            vBox.getChildren().add(label);
            vBox.getChildren().add(jfxTextArea);
            vBox.getChildren().add(button);
            vBox.getChildren().add(tableView);
            Scene scene = new Scene(vBox);
            scene.getStylesheets().add("/resource/css/notesPopup.css");
            notesDialog.setScene(scene);
            notesDialog.initStyle(StageStyle.UNDECORATED);
            notesDialog.show();
        }
        else if (notesDialog.isShowing()){
            notesDialog.close();
        }
        else
            notesDialog.show();
    }

    private boolean isRequiredFieldEntered(){
        if (firstName.getText().isEmpty()||lastName.getText().isEmpty()||paxEmailFirst.getText().isEmpty()) return false;
        return true;
    }


    private void showAlert(Alert.AlertType alertType, Window owner, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.initOwner(owner);
        alert.show();
    }
}
