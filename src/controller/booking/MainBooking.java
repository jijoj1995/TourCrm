package controller.booking;

import dto.CoreBookingEntity;
import dto.CoreLead;
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

public class MainBooking implements Initializable {
    @FXML
    private AnchorPane mainPane;
    @FXML
    private TabPane bookingTabs;
    private CoreBookingEntity coreBookingEntity;
    private Logger logger =Logger.getLogger(MainBooking.class);
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initializeDefaultLayout();
    }
    private void initializeDefaultLayout() {
        mainPane.setPrefWidth(Main.WIDTH - Main.SIDE_BAR_WIDTH);
        mainPane.setPrefHeight(Main.HEIGHT - 30);
        double paneWidth = (Main.WIDTH - Main.SIDE_BAR_WIDTH) / 5 - 20;
        bookingTabs.setTabMinWidth(paneWidth);
        bookingTabs.setTabMaxWidth(paneWidth);
    }

    @FXML
    private void showSubBookingPage() throws IOException {

        logger.info("saving entered data to Core Booking dto before going to subBookingPage");
        setTextFieldDataToDto();
        //loading sub booking page
        FXMLLoader Loader = new FXMLLoader();
        Loader.setLocation(getClass().getResource("/view/booking/subBooking.fxml"));
        try {
            Loader.load();
        } catch (Exception e) {
            e.printStackTrace();
        }
        SubBooking subBooking = Loader.getController();
        subBooking.initializeCoreBookingObject(coreBookingEntity);
        Parent p = Loader.getRoot();
        mainPane.getChildren().setAll(p);

    }

    @FXML
    private void setTextFieldDataToDto(){
        /*
        if (coreLeadDto ==null){
            coreLeadDto =new CoreLead();
            coreLeadDto.setCoreLeadCommunication(new CoreLeadCommunication());
            coreLeadDto.setCoreLeadAir(new CoreLeadAir());

        }

        coreLeadDto.getCoreLeadCommunication().setUsaMobile(usaMobile.getText());
        coreLeadDto.getCoreLeadCommunication().setLandline(landLine.getText());*/
    }

    public void initializeAllInputTexts(CoreBookingEntity coreBookingEntity) {
        logger.info("initialising all text fields from dto object");
        if (coreBookingEntity == null) {
            logger.warn("coreLeadDto is null. returning");
            return;
        }
        //userId.setText(coreLeadDto.getCoreLeadId());
       // firstName.setText(coreLeadDto.getFirstName());
        //middleName.setText(coreLeadDto.getMiddleName());
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
    public void initializeCoreBookingDto(CoreBookingEntity coreBookingEntity){
        this.coreBookingEntity=coreBookingEntity;
        initializeAllInputTexts(this.coreBookingEntity);
    }
}
