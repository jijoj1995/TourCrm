package controller.booking;

import controller.query.MainQuery;
import dto.CoreBookingEntity;
import dto.CoreLead;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.TabPane;
import javafx.scene.layout.AnchorPane;
import main.Main;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class SubBooking implements Initializable {
    @FXML
    private AnchorPane mainPane;
    @FXML
    private TabPane bookingTabs;
    private CoreBookingEntity coreBookingEntity;
    private Logger logger=Logger.getLogger(SubBooking.class);
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initializeDefaultLayout();
    }
    private void initializeDefaultLayout() {
        mainPane.setPrefWidth(Main.WIDTH - Main.SIDE_BAR_WIDTH);
        mainPane.setPrefHeight(Main.HEIGHT - 30);
        double paneWidth = (Main.WIDTH - Main.SIDE_BAR_WIDTH) / 6 - 20;
        bookingTabs.setTabMinWidth(paneWidth);
        bookingTabs.setTabMaxWidth(paneWidth);
    }

    public void initializeCoreBookingObject(CoreBookingEntity coreBookingEntity){
        this.coreBookingEntity = coreBookingEntity;
        setAllTextFieldsFromDto();
    }
    private void setAllTextFieldsFromDto(){
/*
        //set data from coreBooking Dto to all textfields
        logger.info(" in setAllTextFieldsFromDto method in sub query");
        if(coreBookingEntity==null){
            logger.warn("Invalid Core Booking Data. Returning");
            return;
        }
        if(coreBookingEntity.getCoreLeadHotel()!=null){
            setHotelTabTextFieldsFromDto();
        }
        if(coreBookingEntity.getCoreLeadHolidays()!=null){
            setHolidaysTabTextFieldsFromDto();
        }
        if(coreBookingEntity.getCoreLeadRail()!=null){
            setRailTabTextFieldsFromDto();
        }
        if(coreBookingEntity.getCoreLeadAir()!=null){
            setAirTabTextFieldsFromDto();
        }*/
    }
    @FXML
    private void saveSubBookingDataToDto(){

    }
    @FXML
    private void showMainBookingPage() {
        FXMLLoader Loader = new FXMLLoader();
        Loader.setLocation(getClass().getResource("/view/booking/mainBooking.fxml"));
        try {
            Loader.load();
        } catch (Exception e) {
            e.printStackTrace();
        }
        MainBooking mainBooking = Loader.getController();
        //mainBooking.initializeCoreBookingDto(coreBookingEntity);
        Parent p = Loader.getRoot();
        mainPane.getChildren().setAll(p);
    }
}
