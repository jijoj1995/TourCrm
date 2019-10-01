package controller.query;

import com.jfoenix.controls.*;
import constants.LeadsConstants;
import db.QueryService;
import dto.*;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.TabPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import main.InventoryConfig;
import main.Main;
import org.apache.log4j.Logger;
import service.Toast;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.Date;
import java.util.HashSet;
import java.util.ResourceBundle;
import java.util.Set;

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
    private JFXScrollPane jfxDialogScrollPane;
    @FXML
    private VBox notesdialogVbox;
    private QueryService queryService=new QueryService();
    private CoreLead coreLeadDto;
    private Logger logger=Logger.getLogger(MainQuery.class);
    private int numberOfNotes =2;
    Set<CoreLeadsNotesEntity>coreLeadsNotesEntitySet=new HashSet<>();
    InventoryConfig inventoryConfig=InventoryConfig.getInstance();
    @Override
    public void initialize(URL location, ResourceBundle resources) {
                                  // notesDialogBox.setPrefWidth(0);
                                        //set window based on screen size
        initializeDefaultLayout();
        initialiseAllCheckBoxDefalutValues();
        setNumberOnlyInputCheck();
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
    }

   /* @FXML
    private void toggleNotesTab() {
                //toggle notes tab on button click
       if (notesDialogBox.isVisible()){
           notesDialogBox.setVisible(false);
           notesDialogBox.setMaxWidth(0);
       }
       else {
           notesDialogBox.setVisible(true);
           notesDialogBox.setMaxWidth(500);
           notesDialogBox.setPrefWidth(500);

       }
    }*/

    @FXML
    private void saveCompleteLeadInformation() throws IOException{
        Stage stage = (Stage) mainPane.getScene().getWindow();

        //before saving set data from all textFields
        setTextFieldDataToDto();
        //set querytime as current time
        coreLeadDto.setQuerytime(new Date().toString());

            //save to db
        if (queryService.saveQueryData(coreLeadDto)){
            //saving successful
            boolean senEmailNotification=Boolean.parseBoolean(inventoryConfig.getAppProperties().getProperty("sendEmailOnQuery"));
            if (senEmailNotification){
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

   /* private void initialiseNotesTab(){
       notesDialogBox.setStyle("-fx-text-fill: #006464;\n" +
                "    -fx-background-color: white;\n" +
                "-fx-border-color:black;\n" +
                "    -fx-border-radius: 20;\n" +
                "    -fx-background-radius: 20;");

        notesDialogBox.setVisible(false);
        notesDialogBox.setMaxWidth(0);
        FontAwesomeIconView addNotesButton = new FontAwesomeIconView(FontAwesomeIcon.PLUS_CIRCLE);
        addNotesButton.setCursor(Cursor.HAND);
        addNotesButton.setGlyphSize(35);
        notesdialogVbox.getChildren().add(addNotesButton);

        if (coreLeadDto!=null&&coreLeadDto.getCoreLeadsNotesEntitySet()!=null){
            coreLeadsNotesEntitySet=coreLeadDto.getCoreLeadsNotesEntitySet();

        }
        numberOfNotes =coreLeadsNotesEntitySet.size();
        for (int i = 0; i< numberOfNotes; i++){
            HBox hBox=new HBox();
            JFXTextArea jfxTextArea=new JFXTextArea();

            jfxTextArea.setText("position==="+i);
          //  jfxTextArea.setMinWidth(400);
            jfxTextArea.setMinHeight(100);

            FontAwesomeIconView listIcon = new FontAwesomeIconView(FontAwesomeIcon.SAVE);
            listIcon.setCursor(Cursor.HAND);
            listIcon.setGlyphSize(35);
            final int value=i;
            listIcon.setOnMouseClicked(event -> {
                deleteSpecificNoteTab(value);
            });
            hBox.getChildren().add(jfxTextArea);
            hBox.getChildren().add(listIcon);
            hBox.setAlignment(Pos.CENTER);
            hBox.setPadding(new Insets(0,30,0,30));
            notesdialogVbox.getChildren().add(hBox);
        }

        addNotesButton.setOnMouseClicked(event -> {

            reInitialiseNotesTab();
        });
    }
    private void reInitialiseNotesTab(){
            final int hboxPosition= numberOfNotes;
            HBox hBox=new HBox();
            JFXTextArea jfxTextArea=new JFXTextArea();
            jfxTextArea.setText("postion===="+ numberOfNotes);
            jfxTextArea.setMinHeight(100);

            FontAwesomeIconView listIcon = new FontAwesomeIconView(FontAwesomeIcon.SAVE);
            listIcon.setCursor(Cursor.HAND);
            listIcon.setGlyphSize(35);
            listIcon.setOnMouseClicked(event -> {
                //deleteSpecificNoteTab(hboxPosition);
                if (hboxPosition>=coreLeadsNotesEntitySet.size()) {
                    CoreLeadsNotesEntity notesEntity = new CoreLeadsNotesEntity();
                    notesEntity.setNotesData(jfxTextArea.getText());
                    if (coreLeadDto==null){
                        coreLeadDto=new CoreLead();
                        coreLeadDto.setCoreLeadsNotesEntitySet(coreLeadsNotesEntitySet);
                    }
                    coreLeadDto.getCoreLeadsNotesEntitySet().add(notesEntity);
                }
            });
            hBox.getChildren().add(jfxTextArea);
            hBox.getChildren().add(listIcon);
            hBox.setAlignment(Pos.CENTER);
            hBox.setPadding(new Insets(0,30,0,30));
            notesdialogVbox.getChildren().add(hBox);
            numberOfNotes++;

    }
    private void deleteSpecificNoteTab(int positionNumber){
        logger.info("position of button clicked == "+positionNumber);
        notesdialogVbox.getChildren().remove(positionNumber+2);
        numberOfNotes--;
    }*/
}
