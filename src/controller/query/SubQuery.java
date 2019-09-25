package controller.query;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import dto.*;
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

public class SubQuery implements Initializable {
    @FXML
    private AnchorPane mainPane;
    @FXML
    private TabPane queryTabs;
    @FXML
    private JFXTextField hotelDestination,hotelCheckInDate,hotelCheckOutDate,hotelNumberOfNights,hotelNumberOfAdult,
            hotelNumberOfChild,hotelNumberOfInfant,hotelTotalPax,hotelRoomTariff,hotelExtraBed,hotelTotalPrice,
            holidaysFrom,holidaysTo,holidaysDepartureDate,holidaysReturnDate,holidaysNumberOfNights,holidaysNumberOfAdult,
            holidaysNumberOfChild,holidaysTotalInfant,holidaysAdultFare,holidaysChildFare,holidaysInfantFare,holidaysTotalPrice,
            railDepartureCity,railArrivalCity,railDateOfDeparture,railTrainNumber,railNumberOfAdult,railNumberOfChild,railNumberOfInfant,railTotalPax,railAdultFare,
            railChildFare,railTotalFare;
    @FXML
    private JFXComboBox hotelCurrencyCode,hotelCategory,hotelPlan,hotelStatus,holidaysCurrencyCode,holidaysHotelCategory,holidaysTravelType,holidaysStatus,railClassOfTravel,railStatus;
    private CoreLead coreLeadDto;
    private static Logger logger=Logger.getLogger(SubQuery.class);
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initializeDefaultLayout();
        hotelCurrencyCode.getItems().addAll("USD","INR","CAD");
        hotelCurrencyCode.setValue("INR");
    }

    public void initializeCoreLeadObject(CoreLead coreLead){
        this.coreLeadDto = coreLead;
        setAllTextFieldsFromDto();

    }
    private void setAllTextFieldsFromDto(){

        //set data from coreLead Dto to all textfields
        logger.info(" in setAllTextFieldsFromDto method in sub query");
        if(coreLeadDto==null){
           logger.warn("coreLead Dto is null. Returning");
           return;
        }
        if(coreLeadDto.getCoreLeadHotel()!=null){
            setHotelTabTextFieldsFromDto();
        }
        if(coreLeadDto.getCoreLeadHolidays()!=null){
            setHolidaysTabTextFieldsFromDto();
        }
        if(coreLeadDto.getCoreLeadRail()!=null){
            setRailTabTextFieldsFromDto();
        }
        if(coreLeadDto.getCoreLeadAir()!=null){
            setAirTabTextFieldsFromDto();
        }
    }

    private void setHotelTabTextFieldsFromDto(){
        hotelDestination.setText(coreLeadDto.getCoreLeadHotel().getDestination());
        hotelCheckInDate.setText(coreLeadDto.getCoreLeadHotel().getCheckInDate());
        hotelCheckOutDate.setText(coreLeadDto.getCoreLeadHotel().getCheckoutDate());
       // hotelCurrencyCode.setText(coreLeadDto.getCoreLeadHotel().getCurrencyCode());
       // hotelCategory.setText(coreLeadDto.getCoreLeadHotel().getHotelCategory());
        hotelNumberOfNights.setText(coreLeadDto.getCoreLeadHotel().getNumberOfNights());
        hotelNumberOfAdult.setText(coreLeadDto.getCoreLeadHotel().getNumberOfAdult());
        hotelNumberOfChild.setText(coreLeadDto.getCoreLeadHotel().getNumberOfChild());
        hotelNumberOfInfant.setText(coreLeadDto.getCoreLeadHotel().getNumberOfInfants());
        hotelTotalPax.setText(coreLeadDto.getCoreLeadHotel().getTotalPax());
        hotelRoomTariff.setText(coreLeadDto.getCoreLeadHotel().getRoomTariff());
        hotelExtraBed.setText(coreLeadDto.getCoreLeadHotel().getExtraBed());
        hotelTotalPrice.setText(coreLeadDto.getCoreLeadHotel().getTotalPrice());
        //hotelPlan.setText(coreLeadDto.getCoreLeadHotel().getHotelPlan());
        //hotelStatus.setText(coreLeadDto.getCoreLeadHotel().getStatus());
    }
    private void setHolidaysTabTextFieldsFromDto(){
        holidaysFrom.setText(coreLeadDto.getCoreLeadHolidays().getFromDestination());
        holidaysTo.setText(coreLeadDto.getCoreLeadHolidays().getToDestination());
        holidaysDepartureDate.setText(coreLeadDto.getCoreLeadHolidays().getDepartureDate());
        holidaysReturnDate.setText(coreLeadDto.getCoreLeadHolidays().getReturnDate());
       // holidaysCurrencyCode.setText(coreLeadDto.getCoreLeadHolidays().getCurrencyCode());
        //holidaysHotelCategory.setText(coreLeadDto.getCoreLeadHolidays().getHotelCategory());
        holidaysNumberOfNights.setText(coreLeadDto.getCoreLeadHolidays().getNumberOfNights());
        holidaysNumberOfAdult.setText(coreLeadDto.getCoreLeadHolidays().getNumberOfAdult());
        holidaysTotalPrice.setText(coreLeadDto.getCoreLeadHolidays().getTotalPrice());
        holidaysNumberOfChild.setText(coreLeadDto.getCoreLeadHolidays().getNumberOfChild());
        holidaysTotalInfant.setText(coreLeadDto.getCoreLeadHolidays().getNumberOfInfant());
        holidaysAdultFare.setText(coreLeadDto.getCoreLeadHolidays().getAdultFare());
        holidaysChildFare.setText(coreLeadDto.getCoreLeadHolidays().getChildFare());
        holidaysInfantFare.setText(coreLeadDto.getCoreLeadHolidays().getInfantFare());
        //holidaysStatus.setText(coreLeadDto.getCoreLeadHolidays().getStatus());
        //holidaysTravelType.setText(coreLeadDto.getCoreLeadHolidays().getTravelType());
    }
    private void setRailTabTextFieldsFromDto(){
        railDepartureCity.setText(coreLeadDto.getCoreLeadRail().getDepartureCity());
        railArrivalCity.setText(coreLeadDto.getCoreLeadRail().getArrivalCity());
        railDateOfDeparture.setText(coreLeadDto.getCoreLeadRail().getDepartureDate());
        railTrainNumber.setText(coreLeadDto.getCoreLeadRail().getTrainNumber());
        railNumberOfAdult.setText(coreLeadDto.getCoreLeadRail().getNumberOfAdult());
        railNumberOfChild.setText(coreLeadDto.getCoreLeadRail().getNumberOfChild());
        railNumberOfInfant.setText(coreLeadDto.getCoreLeadRail().getNumberOfInfant());
        railTotalPax.setText(coreLeadDto.getCoreLeadRail().getTotalPax());
        railAdultFare.setText(coreLeadDto.getCoreLeadRail().getAdultFare());
        railChildFare.setText(coreLeadDto.getCoreLeadRail().getChildFare());
        railTotalFare.setText(coreLeadDto.getCoreLeadRail().getTotalFare());
        //railClassOfTravel.setText(coreLeadDto.getCoreLeadRail().getClassOfTravel());
        //railStatus.setText(coreLeadDto.getCoreLeadRail().getStatus());
    }
    private void setAirTabTextFieldsFromDto(){
    }

    @FXML
    private void saveSubQueryData(){
      // save all tabs data to coreLeadDto

        logger.info("saving sub-query all tabs data to dto");
        if(coreLeadDto ==null){
            coreLeadDto =new CoreLead();
            coreLeadDto.setCoreLeadCommunication(new CoreLeadCommunication());
            coreLeadDto.setCoreLeadAir(new CoreLeadAir());
            coreLeadDto.setCoreLeadHotel(new CoreLeadHotel());
            coreLeadDto.setCoreLeadHolidays(new CoreLeadHolidays());
            coreLeadDto.setCoreLeadRail(new CoreLeadRail());
        }
        setHotelDetailsToCoreLead();
        setHolidaysDetailsToCoreLead();
        setRailDetailsToCoreLead();
        //details all saved to dto going back to main QueryPage
        showMainQueryPage();
    }

    private void setHotelDetailsToCoreLead(){

        if(coreLeadDto.getCoreLeadHotel()==null){
            coreLeadDto.setCoreLeadHotel(new CoreLeadHotel());
        }
        coreLeadDto.getCoreLeadHotel().setCheckInDate(hotelCheckInDate.getText());
        coreLeadDto.getCoreLeadHotel().setCheckoutDate(hotelCheckOutDate.getText());
        //coreLeadDto.getCoreLeadHotel().setCurrencyCode(hotelCurrencyCode.getText());
        coreLeadDto.getCoreLeadHotel().setDestination(hotelDestination.getText());
        coreLeadDto.getCoreLeadHotel().setExtraBed(hotelExtraBed.getText());
        //coreLeadDto.getCoreLeadHotel().setHotelCategory(hotelCategory.getText());
        //coreLeadDto.getCoreLeadHotel().setHotelPlan(hotelPlan.getText());
        coreLeadDto.getCoreLeadHotel().setNumberOfAdult(hotelNumberOfAdult.getText());
        coreLeadDto.getCoreLeadHotel().setNumberOfChild(hotelNumberOfChild.getText());
        coreLeadDto.getCoreLeadHotel().setNumberOfInfants(hotelNumberOfInfant.getText());
        coreLeadDto.getCoreLeadHotel().setNumberOfNights(hotelNumberOfNights.getText());
        coreLeadDto.getCoreLeadHotel().setRoomTariff(hotelRoomTariff.getText());
        //coreLeadDto.getCoreLeadHotel().setStatus(hotelStatus.getText());
        coreLeadDto.getCoreLeadHotel().setTotalPax(hotelTotalPax.getText());
        coreLeadDto.getCoreLeadHotel().setTotalPrice(hotelTotalPrice.getText());

    }

    private void setHolidaysDetailsToCoreLead(){
        if(coreLeadDto.getCoreLeadHolidays()==null){
            coreLeadDto.setCoreLeadHolidays(new CoreLeadHolidays());
        }
        coreLeadDto.getCoreLeadHolidays().setAdultFare(holidaysAdultFare.getText());
        coreLeadDto.getCoreLeadHolidays().setChildFare(holidaysChildFare.getText());
        //coreLeadDto.getCoreLeadHolidays().setCurrencyCode(holidaysCurrencyCode.getText());
        coreLeadDto.getCoreLeadHolidays().setDepartureDate(holidaysDepartureDate.getText());
        coreLeadDto.getCoreLeadHolidays().setFromDestination(holidaysFrom.getText());
        //coreLeadDto.getCoreLeadHolidays().setHotelCategory(holidaysHotelCategory.getText());
        coreLeadDto.getCoreLeadHolidays().setInfantFare(holidaysInfantFare.getText());
        coreLeadDto.getCoreLeadHolidays().setNumberOfAdult(holidaysNumberOfAdult.getText());
        coreLeadDto.getCoreLeadHolidays().setNumberOfChild(holidaysNumberOfChild.getText());
        coreLeadDto.getCoreLeadHolidays().setNumberOfInfant(holidaysTotalInfant.getText());
        coreLeadDto.getCoreLeadHolidays().setNumberOfNights(holidaysNumberOfNights.getText());
        coreLeadDto.getCoreLeadHolidays().setReturnDate(holidaysReturnDate.getText());
        //coreLeadDto.getCoreLeadHolidays().setStatus(holidaysStatus.getText());
        coreLeadDto.getCoreLeadHolidays().setToDestination(holidaysTo.getText());
        coreLeadDto.getCoreLeadHolidays().setTotalPrice(holidaysTotalPrice.getText());
     //   coreLeadDto.getCoreLeadHolidays().setTravelType(holidaysTravelType.getText());
    }

    private void setRailDetailsToCoreLead(){
        if(coreLeadDto.getCoreLeadRail()==null){
            coreLeadDto.setCoreLeadRail(new CoreLeadRail());
        }
        coreLeadDto.getCoreLeadRail().setAdultFare(railAdultFare.getText());
        coreLeadDto.getCoreLeadRail().setArrivalCity(railArrivalCity.getText());
        coreLeadDto.getCoreLeadRail().setChildFare(railChildFare.getText());
       // coreLeadDto.getCoreLeadRail().setClassOfTravel(railClassOfTravel.getText());
        coreLeadDto.getCoreLeadRail().setDepartureCity(railDepartureCity.getText());
        coreLeadDto.getCoreLeadRail().setDepartureDate(railDateOfDeparture.getText());
        coreLeadDto.getCoreLeadRail().setNumberOfAdult(railNumberOfAdult.getText());
        coreLeadDto.getCoreLeadRail().setNumberOfChild(railNumberOfChild.getText());
        coreLeadDto.getCoreLeadRail().setNumberOfInfant(railNumberOfInfant.getText());
        //coreLeadDto.getCoreLeadRail().setStatus(railStatus.getText());
        coreLeadDto.getCoreLeadRail().setTotalFare(railTotalFare.getText());
        coreLeadDto.getCoreLeadRail().setTotalPax(railTotalPax.getText());
        coreLeadDto.getCoreLeadRail().setTrainNumber(railTrainNumber.getText());
    }


    @FXML
    private void showMainQueryPage() {
        FXMLLoader Loader = new FXMLLoader();
        Loader.setLocation(getClass().getResource("/view/query/mainQuery.fxml"));
        try {
            Loader.load();
        } catch (Exception e) {
            e.printStackTrace();
        }
        MainQuery mainQuery = Loader.getController();
        mainQuery.initializeCoreLeadDto(coreLeadDto);
        Parent p = Loader.getRoot();
        mainPane.getChildren().setAll(p);
    }

    private void initializeDefaultLayout() {
        mainPane.setPrefWidth(Main.WIDTH - Main.SIDE_BAR_WIDTH);
        mainPane.setPrefHeight(Main.HEIGHT - 30);
        double paneWidth = (Main.WIDTH - Main.SIDE_BAR_WIDTH) / 4 - 20;
        queryTabs.setTabMinWidth(paneWidth);
        queryTabs.setTabMaxWidth(paneWidth);
    }

}
