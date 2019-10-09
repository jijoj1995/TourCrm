package controller.booking;

import com.jfoenix.controls.*;
import constants.LeadsConstants;
import db.QueryService;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import dto.*;
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
import main.Main;
import org.apache.log4j.Logger;
import service.BookingService;
import service.Toast;
import service.Validator;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class MainBooking implements Initializable {
    @FXML
    private AnchorPane mainPane;
    @FXML
    private TabPane bookingTabs;
    @FXML
    private JFXTextField bookingId,queryId,userId,firstName,middleName,lastName,departmentCode,gdsType,
    pnrNumber,holdBooking,authorisePartialMissing, shift,billingAddressName,billingAddress1,billingAddress2,billingAddressState,billingAddressCountry,
    billingAddressCity,billingAddressZipCode,shippingAddressName,shippingAddress1,shippingAddress2,shippingAddressState,shippingAddressCountry,
    shippingAddressCity,shippingAddressZipCode, communicationPaxEmailFirst,communicationPaxEmailSecond,communicationUsaMobile,communicationUsaWork, communicationIndiaLandLine,communicationUsaHome,communicationIndiaMobile,
    statusUserId,statusQcDoneBy;
    @FXML
    JFXComboBox<String>channelCode,currencyCode,querySource,supplierName,statusPaymentCommitted,statusQcStatus,statusMoveToDispatch;
    @FXML
    JFXTimePicker statusQcTimePicker,statusQueryTimePicker;
    @FXML
    JFXDatePicker statusQueryDate,statusQcDate,statusBookingDate,bookingTime,dueDate;
    @FXML
    private Button notesButton;
    @FXML
    private HBox shippingHbox;
    @FXML
    private JFXToggleButton showShippingBox;
    Stage notesDialog=null;
    private CoreLead coreLeadDto;
    private CoreBookingEntity coreBookingEntity;
    ObservableList<CoreLeadNotesDto> data = FXCollections.observableArrayList();
    private BookingService bookingService=new BookingService();
    private QueryService queryService=new QueryService();
    private Logger logger =Logger.getLogger(MainBooking.class);
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initializeDefaultLayout();
        initialiseAllCheckBoxDefalutValues();

        //hide booking id and time details
        hideBookingIdAndTimeDetails();

        showShippingBox.setOnAction((event) -> {
            ToggleButton but = (ToggleButton) event.getTarget();
            if (but.isSelected()) {
                shippingHbox.setVisible(false);
            } else {
                shippingHbox.setVisible(true);
            }
        });
    }
    private void hideBookingIdAndTimeDetails(){
        bookingId.setVisible(false);
        bookingTime.setVisible(false);
    }

    private void showBookingIdAndTimeDetails(){
        bookingId.setVisible(true);
        bookingTime.setVisible(true);
    }

    public void initializeCoreLeadDto(CoreLead coreLead){
        this.coreLeadDto=coreLead;
        this.coreBookingEntity=coreLead.getCoreBookingEntity();
                                            //booking communication detials should be initially fetched fro coreLeadCommunication
        initialiseNotesTabData();
        initialiseDefaultCoreBookingCommunicationFromDto();
        initializeAllInputTextsFromDto(coreBookingEntity);
        checkForSameBillingShippingAddress();
    }
    private void initialiseNotesTabData(){
        //set notes data to table
        if (coreLeadDto.getCoreLeadNotesEntitySet()!=null)
        for (CoreLeadNotesEntity entity:coreLeadDto.getCoreLeadNotesEntitySet()) data.add(new CoreLeadNotesDto(entity.getCoreLeadNotesId(),entity.getNotesData()));
    }

    private void initialiseDefaultCoreBookingCommunicationFromDto(){
        if (coreLeadDto!=null && coreLeadDto.getCoreLeadCommunication()!=null && (coreBookingEntity==null ||coreBookingEntity.getCoreBookingCommunicationEntity()==null)){
            CoreLeadCommunication coreLeadCommunication=coreLeadDto.getCoreLeadCommunication();
            communicationPaxEmailFirst.setText(coreLeadCommunication.getPaxEmailFirst());
            communicationUsaMobile.setText(coreLeadCommunication.getUsaMobile());
            communicationUsaWork.setText(coreLeadCommunication.getUsaWorkNumber());
            communicationIndiaLandLine.setText(coreLeadCommunication.getIndiaLandline());
            communicationPaxEmailSecond.setText(coreLeadCommunication.getPaxEmailSecond());
            communicationUsaHome.setText(coreLeadCommunication.getUsaHome());
            communicationIndiaMobile.setText(coreLeadCommunication.getIndiaMobile());

        }
    }

    private void initializeAllInputTextsFromDto(CoreBookingEntity coreBookingEntity) {
        logger.info("initialising all text fields from dto object");
        if (coreBookingEntity == null) {
            queryId.setText(String.valueOf(coreLeadDto.getCoreLeadId()));
            logger.warn("coreLeadDto is null. returning");
            return;
        }

        initializeGeneralInputTextsFromDto();
        if (coreBookingEntity.getCoreBookingBillingAddressEntity()!=null) initializeBillingAddressInputTextsFromDto();
        if (coreBookingEntity.getCoreBookingShippingAddressEntity()!=null) initializeShippingAddressInputTextsFromDto();
        if (coreBookingEntity.getCoreBookingCommunicationEntity()!=null) initializeCommunicationInputTextsFromDto();
        if (coreBookingEntity.getCoreBookingStatusEntity()!=null) initializeStatusInputTextsFromDto();

    }

    private void initializeGeneralInputTextsFromDto(){
        if (coreBookingEntity.getCoreBookingId()!=null) {
            bookingId.setText(String.valueOf(coreBookingEntity.getCoreBookingId()));

            showBookingIdAndTimeDetails();
        }
        bookingTime.setValue(Validator.getNotNullLocalDateFromString(coreBookingEntity.getBookingTime()));
        if (coreBookingEntity.getQueryId()!=null) {
            queryId.setText(String.valueOf(coreBookingEntity.getQueryId()));
        }
        userId.setText(coreBookingEntity.getUserId());
        firstName.setText(coreBookingEntity.getFirstName());
        middleName.setText(coreBookingEntity.getMiddleName());

        lastName.setText(coreBookingEntity.getLastName());
        departmentCode.setText(coreBookingEntity.getDepartmentCode());
        channelCode.setValue(coreBookingEntity.getChannelCode());
        querySource.setValue(coreBookingEntity.getQuerySource());
        gdsType.setText(coreBookingEntity.getGdsType());
        currencyCode.setValue(coreBookingEntity.getCurrencyCode());
        dueDate.setValue(Validator.getNotNullLocalDateFromString(coreBookingEntity.getDueDate()));
        pnrNumber.setText(coreBookingEntity.getPnrNumber());
        supplierName.setValue(coreBookingEntity.getSupplierName());
        shift.setText(coreBookingEntity.getShift());
        holdBooking.setText(coreBookingEntity.getHoldBooking());
        authorisePartialMissing.setText(coreBookingEntity.getAuthorisePartialMissing());
    }
    private void initializeBillingAddressInputTextsFromDto(){

        billingAddressName.setText(coreBookingEntity.getCoreBookingBillingAddressEntity().getName());
        billingAddress1.setText(coreBookingEntity.getCoreBookingBillingAddressEntity().getAddress1());
        billingAddress2.setText(coreBookingEntity.getCoreBookingBillingAddressEntity().getAddress2());
        billingAddressState.setText(coreBookingEntity.getCoreBookingBillingAddressEntity().getState());
        billingAddressCountry.setText(coreBookingEntity.getCoreBookingBillingAddressEntity().getCountry());
        billingAddressCity.setText(coreBookingEntity.getCoreBookingBillingAddressEntity().getCity());
        billingAddressZipCode.setText(coreBookingEntity.getCoreBookingBillingAddressEntity().getZipCode());
    }
    private void initializeShippingAddressInputTextsFromDto(){
        shippingAddressName.setText(coreBookingEntity.getCoreBookingShippingAddressEntity().getName());
        shippingAddress1.setText(coreBookingEntity.getCoreBookingShippingAddressEntity().getAddress1());
        shippingAddress2.setText(coreBookingEntity.getCoreBookingShippingAddressEntity().getAddress2());
        shippingAddressState.setText(coreBookingEntity.getCoreBookingShippingAddressEntity().getState());
        shippingAddressCountry.setText(coreBookingEntity.getCoreBookingShippingAddressEntity().getCountry());
        shippingAddressCity.setText(coreBookingEntity.getCoreBookingShippingAddressEntity().getCity());
        shippingAddressZipCode.setText(coreBookingEntity.getCoreBookingShippingAddressEntity().getZipCode());
    }
    private void initializeCommunicationInputTextsFromDto(){
        communicationPaxEmailFirst.setText(coreBookingEntity.getCoreBookingCommunicationEntity().getPaxEmailFirst());
        communicationUsaMobile.setText(coreBookingEntity.getCoreBookingCommunicationEntity().getUsaMobile());
        communicationUsaWork.setText(coreBookingEntity.getCoreBookingCommunicationEntity().getUsaWorkNumber());
        communicationIndiaLandLine.setText(coreBookingEntity.getCoreBookingCommunicationEntity().getIndiaLandline());
        communicationPaxEmailSecond.setText(coreBookingEntity.getCoreBookingCommunicationEntity().getPaxEmailSecond());
        communicationUsaHome.setText(coreBookingEntity.getCoreBookingCommunicationEntity().getUsaHome());
        communicationIndiaMobile.setText(coreBookingEntity.getCoreBookingCommunicationEntity().getIndiaMobile());
    }
    private void initializeStatusInputTextsFromDto(){
        statusQueryDate.setValue(Validator.getLocalDateFromDateTimeString(coreBookingEntity.getCoreBookingStatusEntity().getQueryDate()));
        statusBookingDate.setValue(Validator.getNotNullLocalDateFromString(coreBookingEntity.getCoreBookingStatusEntity().getBookingDate()));
        statusUserId.setText(coreBookingEntity.getCoreBookingStatusEntity().getUserId());
        statusPaymentCommitted.setValue(coreBookingEntity.getCoreBookingStatusEntity().getPaymentCommitted());
        statusQcStatus.setValue(coreBookingEntity.getCoreBookingStatusEntity().getQcStatus());
        statusQcDoneBy.setText(coreBookingEntity.getCoreBookingStatusEntity().getQcDoneBy());
        statusQcDate.setValue(Validator.getLocalDateFromDateTimeString(coreBookingEntity.getCoreBookingStatusEntity().getQcDate()));
        statusMoveToDispatch.setValue(coreBookingEntity.getCoreBookingStatusEntity().getMoveToDispatch());

        //extra time picker initialise
        statusQcTimePicker.setValue(Validator.getLocalTimeFromDateTimeString(coreBookingEntity.getCoreBookingStatusEntity().getQcDate()));
        statusQueryTimePicker.setValue(Validator.getLocalTimeFromDateTimeString(coreBookingEntity.getCoreBookingStatusEntity().getQueryDate()));
    }

    private void checkForSameBillingShippingAddress(){

                        //this method is used to automatically decide to show/hide shipping details Hbox
        if(coreBookingEntity!=null &&coreBookingEntity.getCoreBookingBillingAddressEntity()!=null&&coreBookingEntity.getCoreBookingShippingAddressEntity()!=null){
                //now check whether billing and shipping address are same
            if (bookingService.isShippingBillingAddressSame(coreBookingEntity.getCoreBookingBillingAddressEntity(),coreBookingEntity.getCoreBookingShippingAddressEntity())){
                        //address are same
                showShippingBox.setSelected(true);
                shippingHbox.setVisible(false);
            }
            else {
                showShippingBox.setSelected(false);
                shippingHbox.setVisible(true);
            }
        }
        else{
            //coreBooking dto is null. first start. set shipping  same as billing addres
            showShippingBox.setSelected(true);
            shippingHbox.setVisible(false);
        }
    }

    private void setNotesDataToDto(){
        if (data.isEmpty()){
            logger.warn("no notes details to be saved. returning");
            return;
        }
        ArrayList<CoreLeadNotesEntity> coreBookingPassengerEntities= queryService.getNotesListFromTable(data);
        coreLeadDto.setCoreLeadNotesEntitySet(coreBookingPassengerEntities);

    }

    @FXML
    private void showSubBookingPage() {

        logger.info("saving entered data to Core Booking dto before going to subBookingPage");
        setAllTextFieldDataToDto();
        //loading sub booking page
        FXMLLoader Loader = new FXMLLoader();
        Loader.setLocation(getClass().getResource("/view/booking/subBooking.fxml"));
        try {
            Loader.load();
        } catch (Exception e) {
            e.printStackTrace();
        }
        SubBooking subBooking = Loader.getController();
        subBooking.initializeCoreLeadObject(coreLeadDto);
        Parent p = Loader.getRoot();
        mainPane.getChildren().setAll(p);

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
            JFXTextArea jfxTextArea = new JFXTextArea();
            Button addButton = new Button("Add");
            addButton.getStyleClass().add("buttonPrimary");
            addButton.setCursor(Cursor.HAND);
            Button closeButton = new Button("Close");
            closeButton.getStyleClass().add("buttonPrimary");
            addButton.setCursor(Cursor.HAND);
            closeButton.setOnAction(event -> {
                notesDialog.close();
            });

            addButton.setOnAction(event -> {
                if (!jfxTextArea.getText().equals(""))
                    data.add(new CoreLeadNotesDto(null, jfxTextArea.getText()));
                jfxTextArea.setText("");
                jfxTextArea.requestFocus();

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
            HBox buttonHbox=new HBox();
            buttonHbox.getChildren().add(addButton);
            buttonHbox.getChildren().add(closeButton);
            tableView.setItems(data);
            vBox.getChildren().add(label);
            vBox.getChildren().add(jfxTextArea);
            vBox.getChildren().add(buttonHbox);
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


    @FXML
    private void setAllTextFieldDataToDto(){

        if (coreBookingEntity ==null){
            coreBookingEntity =new CoreBookingEntity();
            coreBookingEntity.setCoreBookingCommunicationEntity(new CoreBookingCommunicationEntity());
            coreBookingEntity.setCoreBookingBillingAddressEntity(new CoreBookingBillingAddressEntity());
            coreBookingEntity.setCoreBookingShippingAddressEntity(new CoreBookingShippingAddressEntity());
            coreBookingEntity.setCoreBookingStatusEntity(new CoreBookingStatusEntity());
        }
        setGeneralTextFieldDataToDto();
        setBillingAddressTextFieldDataToDto();
        setShippingAddressTextFieldDataToDto();
        setCommunicationTextFieldDataToDto();
        setStatusTextFieldDataToDto();
        //set notes data to dto
        setNotesDataToDto();
            //set all booking Details to coreLeadDto
        coreLeadDto.setCoreBookingEntity(coreBookingEntity);
    }

    private void setGeneralTextFieldDataToDto(){
        coreBookingEntity.setBookingTime(Validator.getStringDateValue(bookingTime.getValue()));
        coreBookingEntity.setFirstName(firstName.getText());
        coreBookingEntity.setMiddleName(middleName.getText());
        coreBookingEntity.setLastName(lastName.getText());
        coreBookingEntity.setDepartmentCode(departmentCode.getText());
        coreBookingEntity.setChannelCode(channelCode.getValue());
        coreBookingEntity.setQuerySource(querySource.getValue());
        coreBookingEntity.setGdsType(gdsType.getText());
        coreBookingEntity.setCurrencyCode(currencyCode.getValue());
        coreBookingEntity.setPnrNumber(pnrNumber.getText());
        coreBookingEntity.setDueDate(Validator.getStringDateValue(dueDate.getValue()));
        coreBookingEntity.setHoldBooking(holdBooking.getText());
        coreBookingEntity.setShift(shift.getText());
        coreBookingEntity.setSupplierName(supplierName.getValue());
        coreBookingEntity.setAuthorisePartialMissing(authorisePartialMissing.getText());
    }

    private void setBillingAddressTextFieldDataToDto(){
        coreBookingEntity.getCoreBookingBillingAddressEntity().setName(billingAddressName.getText());
        coreBookingEntity.getCoreBookingBillingAddressEntity().setAddress1( billingAddress1.getText());
        coreBookingEntity.getCoreBookingBillingAddressEntity().setAddress2( billingAddress2.getText());
        coreBookingEntity.getCoreBookingBillingAddressEntity().setState( billingAddressState.getText());
        coreBookingEntity.getCoreBookingBillingAddressEntity().setCountry( billingAddressCountry.getText());
        coreBookingEntity.getCoreBookingBillingAddressEntity().setZipCode( billingAddressZipCode.getText());
        coreBookingEntity.getCoreBookingBillingAddressEntity().setCity(billingAddressCity.getText());
    }

    private void setShippingAddressTextFieldDataToDto() {
        if (showShippingBox.isSelected()) {
                             //address same as billing address
           coreBookingEntity.setCoreBookingShippingAddressEntity(bookingService.getShippingAddressFromBillingAddress(coreBookingEntity.getCoreBookingBillingAddressEntity()));
        } else {
            coreBookingEntity.getCoreBookingShippingAddressEntity().setName(shippingAddressName.getText());
            coreBookingEntity.getCoreBookingShippingAddressEntity().setAddress1(shippingAddress1.getText());
            coreBookingEntity.getCoreBookingShippingAddressEntity().setAddress2(shippingAddress2.getText());
            coreBookingEntity.getCoreBookingShippingAddressEntity().setState(shippingAddressState.getText());
            coreBookingEntity.getCoreBookingShippingAddressEntity().setCountry(shippingAddressCountry.getText());
            coreBookingEntity.getCoreBookingShippingAddressEntity().setZipCode(shippingAddressZipCode.getText());
            coreBookingEntity.getCoreBookingShippingAddressEntity().setCity(shippingAddressCity.getText());
        }
    }

    private void setCommunicationTextFieldDataToDto(){
        coreBookingEntity.getCoreBookingCommunicationEntity().setPaxEmailFirst(communicationPaxEmailFirst.getText());
        coreBookingEntity.getCoreBookingCommunicationEntity().setPaxEmailSecond(communicationPaxEmailSecond.getText());
        coreBookingEntity.getCoreBookingCommunicationEntity().setUsaMobile(communicationUsaMobile.getText());
        coreBookingEntity.getCoreBookingCommunicationEntity().setUsaWorkNumber(communicationUsaWork.getText());
        coreBookingEntity.getCoreBookingCommunicationEntity().setUsaHome(communicationUsaHome.getText());
        coreBookingEntity.getCoreBookingCommunicationEntity().setIndiaLandline(communicationIndiaLandLine.getText());
        coreBookingEntity.getCoreBookingCommunicationEntity().setIndiaMobile(communicationIndiaMobile.getText());
    }

    private void    setStatusTextFieldDataToDto(){
        coreBookingEntity.getCoreBookingStatusEntity().setQueryDate(Validator.getStringValueFromDateTime(statusQueryDate.getValue(),statusQueryTimePicker.getValue()));
        coreBookingEntity.getCoreBookingStatusEntity().setBookingDate(Validator.getStringDateValue(statusBookingDate.getValue()));
        coreBookingEntity.getCoreBookingStatusEntity().setUserId(statusUserId.getText());
        coreBookingEntity.getCoreBookingStatusEntity().setPaymentCommitted(statusPaymentCommitted.getValue());
        coreBookingEntity.getCoreBookingStatusEntity().setQcStatus(statusQcStatus.getValue());
        coreBookingEntity.getCoreBookingStatusEntity().setQcDoneBy(statusQcDoneBy.getText());
        coreBookingEntity.getCoreBookingStatusEntity().setQcDate(Validator.getStringValueFromDateTime(statusQcDate.getValue(),statusQcTimePicker.getValue()));
        coreBookingEntity.getCoreBookingStatusEntity().setMoveToDispatch(statusMoveToDispatch.getValue());
    }

    @FXML
    private void saveCompleteBookingInformationToDb() throws IOException{
                                //before saving set data from all textFields
        setAllTextFieldDataToDto();
                               //set booking details dto to main Lead dto
        coreLeadDto.setCoreBookingEntity(coreBookingEntity);

                            //save to db
        if (new QueryService().saveQueryData(coreLeadDto)){
            //saving successfull
            showQuickTransactionPage();
        }
        else{
            Stage stage = (Stage) mainPane.getScene().getWindow();
            Toast.makeText(stage,"Unable to save query data. Please check input values or restart application",1000,500,500 );
        }
    }


    @FXML
    private void showQuickTransactionPage() throws IOException {
        Parent root= FXMLLoader.load(getClass().getResource("/view/query/listQueries.fxml"));
        mainPane.getChildren().setAll(root);
    }

    private void initializeDefaultLayout() {
        mainPane.setPrefWidth(Main.WIDTH - Main.SIDE_BAR_WIDTH);
        mainPane.setPrefHeight(Main.HEIGHT - 30);
        double paneWidth = (Main.WIDTH - Main.SIDE_BAR_WIDTH) / 5 - 20;
        bookingTabs.setTabMinWidth(paneWidth);
        bookingTabs.setTabMaxWidth(paneWidth);
    }
    private void initialiseAllCheckBoxDefalutValues(){
        channelCode.getItems().addAll(LeadsConstants.channelCode);
        channelCode.setValue(LeadsConstants.channelCode[0]);

        querySource.getItems().addAll(LeadsConstants.querySource);
        querySource.setValue(LeadsConstants.querySource[0]);

        supplierName.getItems().addAll(LeadsConstants.supplierNames);
        supplierName.setValue(LeadsConstants.supplierNames[0]);

        currencyCode.getItems().addAll(LeadsConstants.currencyCodes);
        currencyCode.setValue(LeadsConstants.currencyCodes[0]);

        statusPaymentCommitted.getItems().addAll(LeadsConstants.committed);
        statusPaymentCommitted.setValue(LeadsConstants.committed[0]);

        statusQcStatus.getItems().addAll(LeadsConstants.qcStatus);
        statusQcStatus.setValue(LeadsConstants.qcStatus[0]);

        statusMoveToDispatch.getItems().addAll(LeadsConstants.committed);
        statusMoveToDispatch.setValue(LeadsConstants.committed[0]);
    }
}
