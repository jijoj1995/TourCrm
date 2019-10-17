package controller.query;

import com.gn.global.plugin.ViewManager;
import com.gn.global.util.Alerts;
import com.gn.global.util.NotesDialog;
import com.gn.lab.GNButton;
import com.gn.module.main.Main;
import com.jfoenix.controls.*;
import constants.InventoryConstants;
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
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import main.InventoryConfig;
import main.WorkIndicatorDialog;
import org.apache.log4j.Logger;
import org.controlsfx.control.PopOver;
import service.Toast;

import java.io.IOException;
import java.net.URL;
import java.util.*;
import java.util.concurrent.atomic.AtomicReference;
import java.util.regex.Pattern;

public class MainQuery implements Initializable {
    @FXML
    private StackPane mainPane;
    @FXML
    private TabPane queryTabs;
    @FXML
    private JFXTextField firstName,middleName,lastName,userId,queryIdColumn,queryTime,branchCode,country, paxEmailFirst,paxEmailSecond,usaHome,usaMobile,indiaMobile,indiaLandLine,usaWork;
    @FXML
    private JFXComboBox<String> channelCode,querySource,reasonOfCall,currencyCode,lobCode,shift;
    @FXML
    private GNButton notesButton;
    @FXML
    private HBox queryIdHbox;
    @FXML
    private Label queryTimeLabel,queryIdLabel;
    @FXML
    private QueryService queryService=new QueryService();
    private CoreLead coreLeadDto;
    private Logger logger=Logger.getLogger(MainQuery.class);
    private InventoryConfig inventoryConfig=InventoryConfig.getInstance();
    private ObservableList<CoreLeadNotesDto> notesData = FXCollections.observableArrayList();
    private PopOver notesDialog=new PopOver();
    private WorkIndicatorDialog wd=null;
    @Override
    public void initialize(URL location, ResourceBundle resources) {
                                        //set window based on screen size
        initialiseAllCheckBoxDefalutValues();
        setNumberOnlyInputCheck();
                   //hide query id checkbox if first time
        queryIdHbox.setVisible(false);
        queryIdHbox.setMaxHeight(0);
        queryIdHbox.setMinHeight(0);
        queryIdHbox.setPrefHeight(0);
    }
     void initializeCoreLeadDto(CoreLead coreLead){
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
        if (coreLeadDto.getCoreLeadId()!=0) {
            queryIdHbox.setVisible(true);
            queryIdHbox.setPrefHeight(25);
            queryIdHbox.setMaxHeight(25);
        }
        userId.setText(coreLeadDto.getUserId());
        firstName.setText(coreLeadDto.getFirstName());
        middleName.setText(coreLeadDto.getMiddleName());
        lastName.setText(coreLeadDto.getLastName());
        branchCode.setText(coreLeadDto.getBranchCode());
        channelCode.setValue(coreLeadDto.getChannelCode());
        userId.setText(coreLeadDto.getUserId());
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

            //set notes notesData to table
        if (coreLeadDto.getCoreLeadNotesEntitySet()!=null) {
            for (CoreLeadNotesEntity entity : coreLeadDto.getCoreLeadNotesEntitySet())
                notesData.add(new CoreLeadNotesDto(entity.getCoreLeadNotesId(), entity.getNotesData()));
        }
    }

    private void setNumberOnlyInputCheck(){

    }

    @FXML
    private void showSubQueryPage(){
        logger.info("saving entered notesData to Core Lead dto before going to subQueryPage");
            setTextFieldDataToDto();
            //loading sub queryPage
        FXMLLoader Loader = new FXMLLoader();
        Loader.setLocation(getClass().getResource(ViewManager.getInstance().get(InventoryConstants.subQueryPage)));
        try {
            Loader.load();
        } catch (Exception e) {
            e.printStackTrace();
        }
        SubQuery subQueryPage = Loader.getController();
        subQueryPage.initializeCoreLeadObject(coreLeadDto);
        Main.ctrl.body.setContent(Loader.getRoot());
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
        coreLeadDto.setUserId(userId.getText());
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

        //set notes notesData
        setNotesDataToDto();
    }

    private void setNotesDataToDto(){
        if (notesData.isEmpty()){
            logger.warn("no notes details to be saved. returning");
            return;
        }
        ArrayList<CoreLeadNotesEntity>coreBookingPassengerEntities= queryService.getNotesListFromTable(notesData);
        coreLeadDto.setCoreLeadNotesEntitySet(coreBookingPassengerEntities);

    }


    @FXML
    private void saveCompleteLeadInformation(){
        Stage stage = (Stage) mainPane.getScene().getWindow();
       if (!isRequiredFieldEntered()){
           Alerts.error("Required Fields","Please enter the Required Fields");
        return;
    }
    if (!isValidEmailEntered()){
        Alerts.error("Validation Error","Please enter valid email");
        return;
    }
                                                                    //check if notes added
        if (!isNotesAdded()){
            Alerts.warning("Validation Error","Please add some notes");
            return;
        }

        final AtomicReference<Integer> reference = new AtomicReference<>();
        wd = new WorkIndicatorDialog(mainPane.getScene().getWindow(), "Loading...");
        wd.exec("123", inputParam -> {
                 setTextFieldDataToDto();
                                        //set querytime as current time
            coreLeadDto.setQuerytime(new Date().toString());
            if(queryService.saveQueryData(coreLeadDto)){
                boolean senEmailNotification=Boolean.parseBoolean(inventoryConfig.getAppProperties().getProperty("sendEmailOnQuery"));
                if (senEmailNotification&&coreLeadDto.getCoreLeadId()==0) {
                                                                     //send email first time only
                    boolean emailSendSuccessful= queryService.sendEmailNotification(coreLeadDto.getCoreLeadCommunication().getPaxEmailFirst());
                    if (emailSendSuccessful) return InventoryConstants.queryInsertionSuccess;
                    else return InventoryConstants.queryInsertionEmailFailed;
                }
                return InventoryConstants.queryInsertionSuccess;
            }
            else return InventoryConstants.queryInsertionFailed;

        });

        wd.addTaskEndNotification(result -> {
            try {
                reference.set((Integer) result);
                Integer a=(Integer)result;
                switch (reference.get()) {
                    case InventoryConstants.queryInsertionSuccess:
                        showQuickTransactionPage();
                        return;
                    case InventoryConstants.queryInsertionEmailFailed:
                        Toast.makeText(stage,"Unable to send Email. Please check your internet or firewall Settings",1000,500,500 );
                        showQuickTransactionPage();
                        return;
                    case InventoryConstants.queryInsertionFailed:
                        Toast.makeText(stage,"Unable to save query notesData. Please check input values or restart application",1000,500,500 );
                }
            }
            catch (Exception e){
                logger.warn("exception found while saving query notesData== "+e.getMessage());
            }
        });
    }

    @FXML
    private void showQuickTransactionPage() throws IOException {
        Parent root= FXMLLoader.load(getClass().getResource(ViewManager.getInstance().get(InventoryConstants.listQueryPage)));
        Main.ctrl.body.setContent(root);
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

    @FXML
    private void selectCommunicationTabPane(){
        if (isGeneralRequiredFieldEntered()) queryTabs.getSelectionModel().selectNext();
        else Alerts.error("Required Fields","Please enter the required fields");
    }
    @FXML
    private void selectGeneralTabPane(){
        queryTabs.getSelectionModel().selectPrevious();
    }

    @FXML
    private void showNotesTab(){
            NotesDialog.createAlert(NotesDialog.Type.SUCCESS,"",notesData);
    }

    @FXML
    private void onTabSelection(){
        if(queryTabs.getSelectionModel().isSelected(1)&&!isGeneralRequiredFieldEntered()){
            Alerts.error("Required Fields","Please enter the required fields");
            queryTabs.getSelectionModel().select(0);
        }
    }

    private boolean isValidEmailEntered(){
        return Pattern.matches(InventoryConstants.emailRegex,paxEmailFirst.getText())&&Pattern.matches(InventoryConstants.emailRegex,paxEmailSecond.getText());
    }

    private boolean isRequiredFieldEntered(){
        return !(firstName.getText().isEmpty()||lastName.getText().isEmpty()||paxEmailFirst.getText().isEmpty()) ;
    }
    private boolean isGeneralRequiredFieldEntered(){
        return !(firstName.getText().isEmpty()||lastName.getText().isEmpty()) ;
    }
    private boolean isNotesAdded(){
        return  !(notesData.isEmpty());
    }

}
