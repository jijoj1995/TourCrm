package controller.booking;

import com.jfoenix.controls.JFXTextField;
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

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class MainBooking implements Initializable {
    @FXML
    private AnchorPane mainPane;
    @FXML
    private TabPane bookingTabs;
    @FXML
    private JFXTextField bookingId,bookingTime,queryId,userId,firstName,middleName,lastName,departmentCode,channelCode,querySource,gdsType,currencyCode,
    pnrNumber,dueDate,holdBooking,supplierName,lobCode,billingAddressName,billingAddress1,billingAddress2,billingAddressState,billingAddressCountry,
    billingAddressCity,billingAddressZipCode,shippingAddressName,shippingAddress1,shippingAddress2,shippingAddressState,shippingAddressCountry,
    shippingAddressCity,shippingAddressZipCode,communicationPaxEmail,communicationUsaMobile,communicationUsaWork,communicationLandline,
    statusQueryDate,statusBookingDate,statusUserId,statusPaymentCommitted,statusQcStatus,statusQcDoneBy,statusQcDate,statusMoveToDispatch;

    private CoreLead coreLeadDto;
    private CoreBookingEntity coreBookingEntity;
    private Logger logger =Logger.getLogger(MainBooking.class);
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initializeDefaultLayout();
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
        channelCode.setText(coreBookingEntity.getChannelCode());
        querySource.setText(coreBookingEntity.getQuerySource());
        gdsType.setText(coreBookingEntity.getGdsType());
        currencyCode.setText(coreBookingEntity.getCurrencyCode());
        dueDate.setText(coreBookingEntity.getDueDate());
        pnrNumber.setText(coreBookingEntity.getPnrNumber());
        supplierName.setText(coreBookingEntity.getSupplierName());
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
        statusQueryDate.setText(coreBookingEntity.getCoreBookingStatusEntity().getQueryDate());
        statusBookingDate.setText(coreBookingEntity.getCoreBookingStatusEntity().getBookingDate());
        statusUserId.setText(coreBookingEntity.getCoreBookingStatusEntity().getUserId());
        statusPaymentCommitted.setText(coreBookingEntity.getCoreBookingStatusEntity().getPaymentCommitted());
        statusQcStatus.setText(coreBookingEntity.getCoreBookingStatusEntity().getQcStatus());
        statusQcDoneBy.setText(coreBookingEntity.getCoreBookingStatusEntity().getQcDoneBy());
        statusQcDate.setText(coreBookingEntity.getCoreBookingStatusEntity().getQcDate());
        statusMoveToDispatch.setText(coreBookingEntity.getCoreBookingStatusEntity().getMoveToDispatch());
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
        coreBookingEntity.setChannelCode(channelCode.getText());
        coreBookingEntity.setQuerySource(querySource.getText());
        coreBookingEntity.setGdsType(gdsType.getText());
        coreBookingEntity.setCurrencyCode(currencyCode.getText());
        coreBookingEntity.setPnrNumber(pnrNumber.getText());
        coreBookingEntity.setDueDate(dueDate.getText());
        coreBookingEntity.setHoldBooking(holdBooking.getText());
        coreBookingEntity.setLobCode(lobCode.getText());
        coreBookingEntity.setSupplierName(supplierName.getText());
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
        coreBookingEntity.getCoreBookingStatusEntity().setQueryDate(statusQueryDate.getText());
        coreBookingEntity.getCoreBookingStatusEntity().setBookingDate(statusBookingDate.getText());
        coreBookingEntity.getCoreBookingStatusEntity().setUserId(statusUserId.getText());
        coreBookingEntity.getCoreBookingStatusEntity().setPaymentCommitted(statusPaymentCommitted.getText());
        coreBookingEntity.getCoreBookingStatusEntity().setQcStatus(statusQcStatus.getText());
        coreBookingEntity.getCoreBookingStatusEntity().setQcDoneBy(statusQcDoneBy.getText());
        coreBookingEntity.getCoreBookingStatusEntity().setQcDate(statusQcDate.getText());
        coreBookingEntity.getCoreBookingStatusEntity().setQueryDate(statusQcDate.getText());
        coreBookingEntity.getCoreBookingStatusEntity().setMoveToDispatch(statusMoveToDispatch.getText());
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
}
