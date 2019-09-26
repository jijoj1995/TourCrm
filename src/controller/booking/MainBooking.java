package controller.booking;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXTimePicker;
import constants.LeadsConstants;
import db.QueryService;
import dto.*;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TabPane;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import main.Main;
import org.apache.log4j.Logger;
import service.Toast;
import service.Validator;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class MainBooking implements Initializable {
    @FXML
    private AnchorPane mainPane;
    @FXML
    private TabPane bookingTabs;
    @FXML
    private JFXTextField bookingId,bookingTime,queryId,userId,firstName,middleName,lastName,departmentCode,gdsType,
    pnrNumber,dueDate,holdBooking,lobCode,billingAddressName,billingAddress1,billingAddress2,billingAddressState,billingAddressCountry,
    billingAddressCity,billingAddressZipCode,shippingAddressName,shippingAddress1,shippingAddress2,shippingAddressState,shippingAddressCountry,
    shippingAddressCity,shippingAddressZipCode,communicationPaxEmail,communicationUsaMobile,communicationUsaWork,communicationLandline,
    statusUserId,statusQcDoneBy;

    @FXML
    JFXComboBox<String>channelCode,currencyCode,querySource,supplierName,statusPaymentCommitted,statusQcStatus,statusMoveToDispatch;
    @FXML
    JFXTimePicker statusQcTimePicker,statusQueryTimePicker;
    @FXML
    JFXDatePicker statusQueryDate,statusQcDate,statusBookingDate;

    private CoreLead coreLeadDto;
    private CoreBookingEntity coreBookingEntity;
    private Logger logger =Logger.getLogger(MainBooking.class);
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initializeDefaultLayout();
        initialiseAllCheckBoxDefalutValues();
    }

    public void initializeCoreLeadDto(CoreLead coreLead){
        this.coreLeadDto=coreLead;
        this.coreBookingEntity=coreLead.getCoreBookingEntity();
        initializeAllInputTextsFromDto(coreBookingEntity);
    }

    private void initializeAllInputTextsFromDto(CoreBookingEntity coreBookingEntity) {
        logger.info("initialising all text fields from dto object");
        if (coreBookingEntity == null) {
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
        }
        bookingTime.setText(coreBookingEntity.getBookingTime());
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
        dueDate.setText(coreBookingEntity.getDueDate());
        pnrNumber.setText(coreBookingEntity.getPnrNumber());
        supplierName.setValue(coreBookingEntity.getSupplierName());
        lobCode.setText(coreBookingEntity.getLobCode());
        holdBooking.setText(coreBookingEntity.getHoldBooking());
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
        communicationPaxEmail.setText(coreBookingEntity.getCoreBookingCommunicationEntity().getPaxEmail());
        communicationUsaMobile.setText(coreBookingEntity.getCoreBookingCommunicationEntity().getUsaMobile());
        communicationUsaWork.setText(coreBookingEntity.getCoreBookingCommunicationEntity().getUsaWorkNumber());
        communicationLandline.setText(coreBookingEntity.getCoreBookingCommunicationEntity().getLandline());
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
            //set all booking Details to coreLeadDto
        coreLeadDto.setCoreBookingEntity(coreBookingEntity);
    }

    private void setGeneralTextFieldDataToDto(){
        coreBookingEntity.setBookingTime(bookingTime.getText());
        coreBookingEntity.setFirstName(firstName.getText());
        coreBookingEntity.setMiddleName(middleName.getText());
        coreBookingEntity.setLastName(lastName.getText());
        coreBookingEntity.setDepartmentCode(departmentCode.getText());
        coreBookingEntity.setChannelCode(channelCode.getValue());
        coreBookingEntity.setQuerySource(querySource.getValue());
        coreBookingEntity.setGdsType(gdsType.getText());
        coreBookingEntity.setCurrencyCode(currencyCode.getValue());
        coreBookingEntity.setPnrNumber(pnrNumber.getText());
        coreBookingEntity.setDueDate(dueDate.getText());
        coreBookingEntity.setHoldBooking(holdBooking.getText());
        coreBookingEntity.setLobCode(lobCode.getText());
        coreBookingEntity.setSupplierName(supplierName.getValue());
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

    private void setShippingAddressTextFieldDataToDto(){
        coreBookingEntity.getCoreBookingShippingAddressEntity().setName(shippingAddressName.getText());
        coreBookingEntity.getCoreBookingShippingAddressEntity().setAddress1( shippingAddress1.getText());
        coreBookingEntity.getCoreBookingShippingAddressEntity().setAddress2( shippingAddress2.getText());
        coreBookingEntity.getCoreBookingShippingAddressEntity().setState( shippingAddressState.getText());
        coreBookingEntity.getCoreBookingShippingAddressEntity().setCountry( shippingAddressCountry.getText());
        coreBookingEntity.getCoreBookingShippingAddressEntity().setZipCode( shippingAddressZipCode.getText());
        coreBookingEntity.getCoreBookingShippingAddressEntity().setCity(shippingAddressCity.getText());
    }

    private void setCommunicationTextFieldDataToDto(){
        coreBookingEntity.getCoreBookingCommunicationEntity().setPaxEmail(communicationPaxEmail.getText());
        coreBookingEntity.getCoreBookingCommunicationEntity().setUsaMobile(communicationUsaMobile.getText());
        coreBookingEntity.getCoreBookingCommunicationEntity().setUsaWorkNumber(communicationUsaWork.getText());
        coreBookingEntity.getCoreBookingCommunicationEntity().setLandline(communicationLandline.getText());
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
    private void showNotesTab() throws IOException {
        Stage stage=new Stage();
        Parent root= FXMLLoader.load(getClass().getResource("/view/query/notesPopup.fxml"));
        stage.setScene(new Scene(root, 450, 450));
        stage.show();
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
