package controller.query;

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

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class MainQuery implements Initializable {
    @FXML
    private AnchorPane mainPane;
    @FXML
    private TabPane queryTabs;
    @FXML
    private JFXTextField firstName,middleName,lastName,userId,branchCode,channelCode,country,querySource,currencyCode,shift,reasonOfCall,lobCode,paxEmail,usaMobile,landLine,usaWork;

    private CoreLead coreLeadDto;
    private Logger logger=Logger.getLogger(MainQuery.class);
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initializeDefaultLayout();
    }

    private void initializeDefaultLayout() {
        mainPane.setPrefWidth(Main.WIDTH - Main.SIDE_BAR_WIDTH);
        mainPane.setPrefHeight(Main.HEIGHT - 30);
        double paneWidth = (Main.WIDTH - Main.SIDE_BAR_WIDTH) / 3 - 20;
        queryTabs.setTabMinWidth(paneWidth);
        queryTabs.setTabMaxWidth(paneWidth);
        if(coreLeadDto !=null){
            initializeAllInputTexts(coreLeadDto);
        }
    }


    @FXML
    private void showSubQueryPage() throws IOException {
        FXMLLoader Loader = new FXMLLoader();
        Loader.setLocation(getClass().getResource("/view/query/subQuery.fxml"));
        try {
            Loader.load();
        } catch (Exception e) {
            e.printStackTrace();
        }
        logger.info("payment complete. now showing billing summary page");
        SubQuery subQueryPage = Loader.getController();
        subQueryPage.initializeCoreLeadObject(coreLeadDto);
        Parent p = Loader.getRoot();
        mainPane.getChildren().setAll(p);
        // Parent root= FXMLLoader.load(getClass().getResource("/view/query/subQuery.fxml"));
        //mainPane.getChildren().setAll(root);
    }
    @FXML
    private void showNotesTab() throws IOException {
       // Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
        Stage stage=new Stage();
        Parent root= FXMLLoader.load(getClass().getResource("/view/query/notesPopup.fxml"));
        stage.setScene(new Scene(root, 450, 450));
        stage.show();

    }

    public void initializeAllInputTexts(CoreLead coreLeadDto){

        if (coreLeadDto ==null){
            logger.warn("coreLeadDto is null. returning");
            return;
        }
        //userId.setText(coreLeadDto.getCoreLeadId());
        firstName.setText(coreLeadDto.getFirstName());
        middleName.setText(coreLeadDto.getMiddleName());
        lastName.setText(coreLeadDto.getLastName());
        branchCode.setText(coreLeadDto.getBranchCode());
        channelCode.setText(coreLeadDto.getChannelCode());
        country.setText(coreLeadDto.getCountry());
        querySource.setText(coreLeadDto.getQuerySource());
        currencyCode.setText(coreLeadDto.getCurrencyCode());
        shift.setText(coreLeadDto.getShift());
        reasonOfCall.setText(coreLeadDto.getCallReason());
        lobCode.setText(coreLeadDto.getLobCode());
        if(coreLeadDto.getCoreLeadCommunication()==null){
            logger.warn("coreLeadCommunication object is null of coreLead. returning");
            return;
        }
        else {
            paxEmail.setText(coreLeadDto.getCoreLeadCommunication().getPaxEmail());
            usaMobile.setText(coreLeadDto.getCoreLeadCommunication().getUsaMobile());
            landLine.setText(coreLeadDto.getCoreLeadCommunication().getLandline());
            usaWork.setText(coreLeadDto.getCoreLeadCommunication().getUsaWorkNumber());
        }
    }

    @FXML
    private void saveCompleteLeadInformation(){

        if (coreLeadDto ==null){
            coreLeadDto =new CoreLead();
            coreLeadDto.setCoreLeadCommunication(new CoreLeadCommunication());
            coreLeadDto.setCoreLeadAir(new CoreLeadAir());
            coreLeadDto.setCoreLeadHolidays(new CoreLeadHolidays());
            coreLeadDto.setCoreLeadHotel(new CoreLeadHotel());
            coreLeadDto.setCoreLeadRail(new CoreLeadRail());
        }
        coreLeadDto.setFirstName(firstName.getText());
        coreLeadDto.setMiddleName(middleName.getText());
        coreLeadDto.setLastName(lastName.getText());
        coreLeadDto.setChannelCode(channelCode.getText());
        coreLeadDto.setCountry(country.getText());
        coreLeadDto.setQuerySource(querySource.getText());
        coreLeadDto.setCurrencyCode(currencyCode.getText());
        coreLeadDto.setShift(shift.getText());
        coreLeadDto.setCallReason(reasonOfCall.getText());
        coreLeadDto.setLobCode(lobCode.getText());
        coreLeadDto.getCoreLeadCommunication().setUsaWorkNumber(usaWork.getText());
        coreLeadDto.getCoreLeadCommunication().setPaxEmail(paxEmail.getText());
        coreLeadDto.getCoreLeadCommunication().setUsaMobile(usaMobile.getText());
        coreLeadDto.getCoreLeadCommunication().setLandline(landLine.getText());


        //save to db
        new QueryService().saveQueryData(coreLeadDto);
    }
}
