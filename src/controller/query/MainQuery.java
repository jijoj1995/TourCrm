package controller.query;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
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

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class MainQuery implements Initializable {
    @FXML
    private AnchorPane mainPane;
    @FXML
    private TabPane queryTabs;
    @FXML
    private JFXTextField firstName,middleName,lastName,userId,branchCode,country,paxEmail,usaMobile,landLine,usaWork;
    @FXML
    private JFXComboBox<String> channelCode,querySource,reasonOfCall,currencyCode,lobCode,shift;

    private CoreLead coreLeadDto;
    private Logger logger=Logger.getLogger(MainQuery.class);
    @Override
    public void initialize(URL location, ResourceBundle resources) {
                                        //set window based on screen size
        initializeDefaultLayout();
        initialiseAllCheckBoxDefalutValues();
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
        if(coreLeadDto.getCoreLeadCommunication()==null){
            logger.warn("coreLeadCommunication object is null of coreLead. returning");
            return;
        }
            paxEmail.setText(coreLeadDto.getCoreLeadCommunication().getPaxEmail());
            usaMobile.setText(coreLeadDto.getCoreLeadCommunication().getUsaMobile());
            landLine.setText(coreLeadDto.getCoreLeadCommunication().getLandline());
            usaWork.setText(coreLeadDto.getCoreLeadCommunication().getUsaWorkNumber());
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
           // coreLeadDto.setCoreLeadAir(new CoreLeadAir());
          //  coreLeadDto.setCoreLeadHolidays(new CoreLeadHolidays());
           // coreLeadDto.setCoreLeadHotel(new CoreLeadHotel());
           // coreLeadDto.setCoreLeadRail(new CoreLeadRail());
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
        coreLeadDto.getCoreLeadCommunication().setUsaWorkNumber(usaWork.getText());
        coreLeadDto.getCoreLeadCommunication().setPaxEmail(paxEmail.getText());
        coreLeadDto.getCoreLeadCommunication().setUsaMobile(usaMobile.getText());
        coreLeadDto.getCoreLeadCommunication().setLandline(landLine.getText());
    }

    @FXML
    private void showNotesTab() throws IOException {
       //show notes tab upfront
        Stage stage=new Stage();
        Parent root= FXMLLoader.load(getClass().getResource("/view/query/notesPopup.fxml"));
        stage.setScene(new Scene(root, 450, 450));
        stage.show();

    }

    @FXML
    private void saveCompleteLeadInformation() throws IOException{
        //before saving set data from all textFields
        setTextFieldDataToDto();

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
        double paneWidth = (Main.WIDTH - Main.SIDE_BAR_WIDTH) / 2 - 20;
        queryTabs.setTabMinWidth(paneWidth);
        queryTabs.setTabMaxWidth(paneWidth);

    }
}
