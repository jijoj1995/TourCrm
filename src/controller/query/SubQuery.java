package controller.query;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextField;
import constants.LeadsConstants;
import dto.*;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.TabPane;
import javafx.scene.layout.AnchorPane;
import main.Main;
import org.apache.log4j.Logger;
import service.Validator;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class SubQuery implements Initializable {
    @FXML
    private AnchorPane mainPane;
    @FXML
    private TabPane queryTabs;
    @FXML
    private JFXTextField airFromDestination,airToDestination,airAirlinesOffered,airNumberOfAdult,
            airNumberOfChild,airNumberOfInfant,airTotalPax,airAdultFare,airChildFare,airInfantFare,airTotalPrice,
            hotelDestination,hotelNumberOfNights,hotelNumberOfAdult,
            hotelNumberOfChild,hotelNumberOfInfant,hotelTotalPax,hotelRoomTariff,hotelExtraBed,hotelTotalPrice,
            holidaysFrom,holidaysTo,holidaysNumberOfNights,holidaysNumberOfAdult,
            holidaysNumberOfChild,holidaysTotalInfant,holidaysAdultFare,holidaysChildFare,holidaysInfantFare,holidaysTotalPax,holidaysTotalPrice,
            railDepartureCity,railArrivalCity,railTrainNumber,railNumberOfAdult,railNumberOfChild,railNumberOfInfant,railTotalPax,railAdultFare,
            railChildFare,railTotalFare;
    @FXML
    private JFXComboBox<String> airTypeOfTravel,airClassOfTravel,airStatus,airCurrencyCode,hotelCurrencyCode,hotelCategory,hotelPlan,hotelStatus,holidaysCurrencyCode,holidaysHotelCategory,holidaysTravelType,
            holidaysStatus,railClassOfTravel,railStatus;

    @FXML
    private JFXDatePicker airDepartureDate,airReturnDate,hotelCheckInDate,hotelCheckOutDate,holidaysDepartureDate,holidaysReturnDate,railDateOfDeparture;

    private CoreLead coreLeadDto;
    private static Logger logger=Logger.getLogger(SubQuery.class);
    @Override
    public void initialize(URL location, ResourceBundle resources) {
                    //set window size based on screens size
        initializeDefaultLayout();
                    //initialise all checkbox with default values
        initialiseAllCheckBoxDefalutValues();
                    //initialise all empty datePickers with today date
        initialiseAllEmptyDatePickers();
                    //set specific number only with validations
        setNumberOnlyInputCheck();

        //initialiseTotalFareCalculationEvent
        setListenersToHolidaysFields();
       setListenersToAirFields();
       setListenersToRailFields();
    }

    private void setListenersToHolidaysFields(){
        holidaysNumberOfAdult.setOnKeyTyped((keyEvent)->{Validator.checkNumberEntered(keyEvent);});
        holidaysNumberOfNights.setOnKeyTyped((keyEvent)->{Validator.checkNumberEntered(keyEvent);});
        holidaysNumberOfChild.setOnKeyTyped((keyEvent)->{Validator.checkNumberEntered(keyEvent);});
        holidaysTotalInfant.setOnKeyTyped((keyEvent)->{Validator.checkNumberEntered(keyEvent);});
        holidaysTotalPax.setOnKeyTyped((keyEvent)->{Validator.checkNumberEntered(keyEvent);});
        holidaysAdultFare.setOnKeyTyped((keyEvent)->{Validator.checkNumberEntered(keyEvent);});
        holidaysChildFare.setOnKeyTyped((keyEvent)->{Validator.checkNumberEntered(keyEvent);});
        holidaysInfantFare.setOnKeyTyped((keyEvent)->{Validator.checkNumberEntered(keyEvent);});

        holidaysNumberOfChild.textProperty().addListener((observable, oldValue, newValue) -> {setTotalHolidaysPrice();});
        holidaysNumberOfAdult.textProperty().addListener((observable, oldValue, newValue) -> {setTotalHolidaysPrice();});
        holidaysTotalInfant.textProperty().addListener((observable, oldValue, newValue) -> {setTotalHolidaysPrice();});
        holidaysInfantFare.textProperty().addListener((observable, oldValue, newValue) -> {setTotalHolidaysPrice();});
        holidaysChildFare.textProperty().addListener((observable, oldValue, newValue) -> {setTotalHolidaysPrice();});
        holidaysAdultFare.textProperty().addListener((observable, oldValue, newValue) -> {setTotalHolidaysPrice();});
    }

    private void setListenersToAirFields(){
        airNumberOfAdult.setOnKeyTyped((keyEvent)->{Validator.checkNumberEntered(keyEvent);});
        airNumberOfChild.setOnKeyTyped((keyEvent)->{Validator.checkNumberEntered(keyEvent);});
        airNumberOfInfant.setOnKeyTyped((keyEvent)->{Validator.checkNumberEntered(keyEvent);});
        airNumberOfInfant.setOnKeyTyped((keyEvent)->{Validator.checkNumberEntered(keyEvent);});
        airAdultFare.setOnKeyTyped((keyEvent)->{Validator.checkNumberEntered(keyEvent);});
        airChildFare.setOnKeyTyped((keyEvent)->{Validator.checkNumberEntered(keyEvent);});
        airInfantFare.setOnKeyTyped((keyEvent)->{Validator.checkNumberEntered(keyEvent);});

        airNumberOfAdult.textProperty().addListener((observable, oldValue, newValue) -> {setTotalAirPrice();});
        airNumberOfChild.textProperty().addListener((observable, oldValue, newValue) -> { setTotalAirPrice();});
        airNumberOfInfant.textProperty().addListener((observable, oldValue, newValue) -> {setTotalAirPrice();});
        airAdultFare.textProperty().addListener((observable, oldValue, newValue) -> {setTotalAirPrice();});
        airChildFare.textProperty().addListener((observable, oldValue, newValue) -> {setTotalAirPrice();});
        airInfantFare.textProperty().addListener((observable, oldValue, newValue) -> {setTotalAirPrice();});
    }

    private void setListenersToRailFields(){
        railNumberOfAdult.setOnKeyTyped((keyEvent)->{Validator.checkNumberEntered(keyEvent);});
        railNumberOfChild.setOnKeyTyped((keyEvent)->{Validator.checkNumberEntered(keyEvent);});
        railNumberOfInfant.setOnKeyTyped((keyEvent)->{Validator.checkNumberEntered(keyEvent);});
        railTotalPax.setOnKeyTyped((keyEvent)->{Validator.checkNumberEntered(keyEvent);});
        railAdultFare.setOnKeyTyped((keyEvent)->{Validator.checkNumberEntered(keyEvent);});
        railChildFare.setOnKeyTyped((keyEvent)->{Validator.checkNumberEntered(keyEvent);});

        railNumberOfAdult.textProperty().addListener((observable, oldValue, newValue) -> {setTotalRailPrice();});
        railNumberOfChild.textProperty().addListener((observable, oldValue, newValue) -> { setTotalRailPrice();});
        railNumberOfInfant.textProperty().addListener((observable, oldValue, newValue) -> {setTotalRailPrice();});
        railAdultFare.textProperty().addListener((observable, oldValue, newValue) -> {setTotalRailPrice();});
        railChildFare.textProperty().addListener((observable, oldValue, newValue) -> {setTotalRailPrice();});
    }


    void initializeCoreLeadObject(CoreLead coreLead){
                               //initialise main core lead dto for setting all textFields
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
        if(coreLeadDto.getCoreLeadAir()!=null){
            setAirTabTextFieldsFromDto();
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
                            //method to set text Fields from dto
        hotelDestination.setText(coreLeadDto.getCoreLeadHotel().getDestination());
        hotelCheckInDate.setValue(Validator.getNotNullLocalDateFromString(coreLeadDto.getCoreLeadHotel().getCheckInDate()));
        hotelCheckOutDate.setValue(Validator.getNotNullLocalDateFromString(coreLeadDto.getCoreLeadHotel().getCheckoutDate()));
        hotelCurrencyCode.setValue(coreLeadDto.getCoreLeadHotel().getCurrencyCode());
        hotelCategory.setValue(coreLeadDto.getCoreLeadHotel().getHotelCategory());
        hotelNumberOfNights.setText(coreLeadDto.getCoreLeadHotel().getNumberOfNights());
        hotelNumberOfAdult.setText(coreLeadDto.getCoreLeadHotel().getNumberOfAdult());
        hotelNumberOfChild.setText(coreLeadDto.getCoreLeadHotel().getNumberOfChild());
        hotelNumberOfInfant.setText(coreLeadDto.getCoreLeadHotel().getNumberOfInfants());
        hotelTotalPax.setText(coreLeadDto.getCoreLeadHotel().getTotalPax());
        hotelRoomTariff.setText(coreLeadDto.getCoreLeadHotel().getRoomTariff());
        hotelExtraBed.setText(coreLeadDto.getCoreLeadHotel().getExtraBed());
        hotelTotalPrice.setText(coreLeadDto.getCoreLeadHotel().getTotalPrice());
        hotelPlan.setValue(coreLeadDto.getCoreLeadHotel().getHotelPlan());
        hotelStatus.setValue(coreLeadDto.getCoreLeadHotel().getStatus());
    }
    private void setHolidaysTabTextFieldsFromDto(){
        holidaysFrom.setText(coreLeadDto.getCoreLeadHolidays().getFromDestination());
        holidaysTo.setText(coreLeadDto.getCoreLeadHolidays().getToDestination());
        holidaysDepartureDate.setValue(Validator.getNotNullLocalDateFromString(coreLeadDto.getCoreLeadHolidays().getDepartureDate()));
        holidaysReturnDate.setValue(Validator.getNotNullLocalDateFromString(coreLeadDto.getCoreLeadHolidays().getReturnDate()));
        holidaysCurrencyCode.setValue(coreLeadDto.getCoreLeadHolidays().getCurrencyCode());
        holidaysHotelCategory.setValue(coreLeadDto.getCoreLeadHolidays().getHotelCategory());
        holidaysNumberOfNights.setText(coreLeadDto.getCoreLeadHolidays().getNumberOfNights());
        holidaysNumberOfAdult.setText(coreLeadDto.getCoreLeadHolidays().getNumberOfAdult());
        holidaysTotalPrice.setText(coreLeadDto.getCoreLeadHolidays().getTotalPrice());
        holidaysNumberOfChild.setText(coreLeadDto.getCoreLeadHolidays().getNumberOfChild());
        holidaysTotalInfant.setText(coreLeadDto.getCoreLeadHolidays().getNumberOfInfant());
        holidaysAdultFare.setText(coreLeadDto.getCoreLeadHolidays().getAdultFare());
        holidaysChildFare.setText(coreLeadDto.getCoreLeadHolidays().getChildFare());
        holidaysTotalPax.setText(coreLeadDto.getCoreLeadHolidays().getTotalPax());
        holidaysInfantFare.setText(coreLeadDto.getCoreLeadHolidays().getInfantFare());
        holidaysStatus.setValue(coreLeadDto.getCoreLeadHolidays().getStatus());
        holidaysTravelType.setValue(coreLeadDto.getCoreLeadHolidays().getTravelType());
    }
    private void setRailTabTextFieldsFromDto(){
        railDepartureCity.setText(coreLeadDto.getCoreLeadRail().getDepartureCity());
        railArrivalCity.setText(coreLeadDto.getCoreLeadRail().getArrivalCity());
        railDateOfDeparture.setValue(Validator.getNotNullLocalDateFromString(coreLeadDto.getCoreLeadRail().getDepartureDate()));
        railTrainNumber.setText(coreLeadDto.getCoreLeadRail().getTrainNumber());
        railNumberOfAdult.setText(coreLeadDto.getCoreLeadRail().getNumberOfAdult());
        railNumberOfChild.setText(coreLeadDto.getCoreLeadRail().getNumberOfChild());
        railNumberOfInfant.setText(coreLeadDto.getCoreLeadRail().getNumberOfInfant());
        railTotalPax.setText(coreLeadDto.getCoreLeadRail().getTotalPax());
        railAdultFare.setText(coreLeadDto.getCoreLeadRail().getAdultFare());
        railChildFare.setText(coreLeadDto.getCoreLeadRail().getChildFare());
        railTotalFare.setText(coreLeadDto.getCoreLeadRail().getTotalFare());
        railClassOfTravel.setValue(coreLeadDto.getCoreLeadRail().getClassOfTravel());
        railStatus.setValue(coreLeadDto.getCoreLeadRail().getStatus());
    }
    private void setAirTabTextFieldsFromDto(){
        airFromDestination.setText(coreLeadDto.getCoreLeadAir().getFromDestination());
        airToDestination.setText(coreLeadDto.getCoreLeadAir().getToDestination());
        airDepartureDate.setValue(Validator.getNotNullLocalDateFromString(coreLeadDto.getCoreLeadAir().getDepartureDate()));
        airReturnDate.setValue(Validator.getNotNullLocalDateFromString(coreLeadDto.getCoreLeadAir().getReturnDate()));
        airAirlinesOffered.setText(coreLeadDto.getCoreLeadAir().getAirlinesOffered());
        airCurrencyCode.setValue(coreLeadDto.getCoreLeadAir().getCurrencyCode());
        airNumberOfAdult.setText(coreLeadDto.getCoreLeadAir().getNumberOfAdult());
        airNumberOfChild.setText(coreLeadDto.getCoreLeadAir().getNumberOfChild());
        airNumberOfInfant.setText(coreLeadDto.getCoreLeadAir().getNumberOfInfant());
        airTotalPax.setText(coreLeadDto.getCoreLeadAir().getTotalPax());
        airAdultFare.setText(coreLeadDto.getCoreLeadAir().getAdultFare());
        airChildFare.setText(coreLeadDto.getCoreLeadAir().getChildFare());
        airInfantFare.setText(coreLeadDto.getCoreLeadAir().getInfantFare());
        airTotalPrice.setText(coreLeadDto.getCoreLeadAir().getTotalPrice());
        airTypeOfTravel.setValue(coreLeadDto.getCoreLeadAir().getTypeOfTravel());
        airClassOfTravel.setValue(coreLeadDto.getCoreLeadAir().getClassOfTravel());
        airStatus.setValue(coreLeadDto.getCoreLeadAir().getStatus());

    }

    private void setNumberOnlyInputCheck(){
        airNumberOfAdult.setOnKeyTyped(event ->{Validator.checkNumberEntered(event);});
        airNumberOfChild.setOnKeyTyped(event ->{Validator.checkNumberEntered(event);});
        airNumberOfInfant.setOnKeyTyped(event ->{Validator.checkNumberEntered(event);});
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
        setAirDetailsToCoreLead();
        setHotelDetailsToCoreLead();
        setHolidaysDetailsToCoreLead();
        setRailDetailsToCoreLead();
                 //details all saved to dto. Going back to main QueryPage
        showMainQueryPage();
    }


    private void setAirDetailsToCoreLead(){
        if(coreLeadDto.getCoreLeadAir()==null){
            coreLeadDto.setCoreLeadAir(new CoreLeadAir());
        }
        coreLeadDto.getCoreLeadAir().setFromDestination(airFromDestination.getText());
        coreLeadDto.getCoreLeadAir().setToDestination(airToDestination.getText());
        coreLeadDto.getCoreLeadAir().setDepartureDate(Validator.getStringDateValue(airDepartureDate.getValue()));
        coreLeadDto.getCoreLeadAir().setReturnDate(Validator.getStringDateValue(airReturnDate.getValue()));
        coreLeadDto.getCoreLeadAir().setAirlinesOffered(airAirlinesOffered.getText());
        coreLeadDto.getCoreLeadAir().setCurrencyCode(airCurrencyCode.getValue());
        coreLeadDto.getCoreLeadAir().setNumberOfAdult(airNumberOfAdult.getText());
        coreLeadDto.getCoreLeadAir().setNumberOfChild(airNumberOfChild.getText());
        coreLeadDto.getCoreLeadAir().setNumberOfInfant(airNumberOfInfant.getText());
        coreLeadDto.getCoreLeadAir().setTotalPax(airTotalPax.getText());
        coreLeadDto.getCoreLeadAir().setAdultFare(airAdultFare.getText());
        coreLeadDto.getCoreLeadAir().setChildFare(airChildFare.getText());
        coreLeadDto.getCoreLeadAir().setInfantFare(airInfantFare.getText());
        coreLeadDto.getCoreLeadAir().setTotalPrice(airTotalPrice.getText());
        coreLeadDto.getCoreLeadAir().setTypeOfTravel(airTypeOfTravel.getValue());
        coreLeadDto.getCoreLeadAir().setClassOfTravel(airClassOfTravel.getValue());
        coreLeadDto.getCoreLeadAir().setStatus(airStatus.getValue());

    }

    private void setHotelDetailsToCoreLead(){

        if(coreLeadDto.getCoreLeadHotel()==null){
            coreLeadDto.setCoreLeadHotel(new CoreLeadHotel());
        }
        coreLeadDto.getCoreLeadHotel().setCheckInDate(Validator.getStringDateValue(hotelCheckInDate.getValue()));
        coreLeadDto.getCoreLeadHotel().setCheckoutDate(Validator.getStringDateValue(hotelCheckOutDate.getValue()));
        coreLeadDto.getCoreLeadHotel().setCurrencyCode(hotelCurrencyCode.getValue());
        coreLeadDto.getCoreLeadHotel().setDestination(hotelDestination.getText());
        coreLeadDto.getCoreLeadHotel().setExtraBed(hotelExtraBed.getText());
        coreLeadDto.getCoreLeadHotel().setHotelCategory(hotelCategory.getValue());
        coreLeadDto.getCoreLeadHotel().setHotelPlan(hotelPlan.getValue());
        coreLeadDto.getCoreLeadHotel().setNumberOfAdult(hotelNumberOfAdult.getText());
        coreLeadDto.getCoreLeadHotel().setNumberOfChild(hotelNumberOfChild.getText());
        coreLeadDto.getCoreLeadHotel().setNumberOfInfants(hotelNumberOfInfant.getText());
        coreLeadDto.getCoreLeadHotel().setNumberOfNights(hotelNumberOfNights.getText());
        coreLeadDto.getCoreLeadHotel().setRoomTariff(hotelRoomTariff.getText());
        coreLeadDto.getCoreLeadHotel().setStatus(hotelStatus.getValue());
        coreLeadDto.getCoreLeadHotel().setTotalPax(hotelTotalPax.getText());
        coreLeadDto.getCoreLeadHotel().setTotalPrice(hotelTotalPrice.getText());

    }

    private void setHolidaysDetailsToCoreLead(){
        if(coreLeadDto.getCoreLeadHolidays()==null){
            coreLeadDto.setCoreLeadHolidays(new CoreLeadHolidays());
        }
        coreLeadDto.getCoreLeadHolidays().setAdultFare(holidaysAdultFare.getText());
        coreLeadDto.getCoreLeadHolidays().setChildFare(holidaysChildFare.getText());
        coreLeadDto.getCoreLeadHolidays().setCurrencyCode(holidaysCurrencyCode.getValue());
        coreLeadDto.getCoreLeadHolidays().setDepartureDate(Validator.getStringDateValue(holidaysDepartureDate.getValue()));
        coreLeadDto.getCoreLeadHolidays().setFromDestination(holidaysFrom.getText());
        coreLeadDto.getCoreLeadHolidays().setHotelCategory(holidaysHotelCategory.getValue());
        coreLeadDto.getCoreLeadHolidays().setInfantFare(holidaysInfantFare.getText());
        coreLeadDto.getCoreLeadHolidays().setNumberOfAdult(holidaysNumberOfAdult.getText());
        coreLeadDto.getCoreLeadHolidays().setNumberOfChild(holidaysNumberOfChild.getText());
        coreLeadDto.getCoreLeadHolidays().setNumberOfInfant(holidaysTotalInfant.getText());
        coreLeadDto.getCoreLeadHolidays().setNumberOfNights(holidaysNumberOfNights.getText());
        coreLeadDto.getCoreLeadHolidays().setReturnDate(Validator.getStringDateValue(holidaysReturnDate.getValue()));
        coreLeadDto.getCoreLeadHolidays().setStatus(holidaysStatus.getValue());
        coreLeadDto.getCoreLeadHolidays().setToDestination(holidaysTo.getText());
        coreLeadDto.getCoreLeadHolidays().setTotalPrice(holidaysTotalPrice.getText());
        coreLeadDto.getCoreLeadHolidays().setTravelType(holidaysTravelType.getValue());
        coreLeadDto.getCoreLeadHolidays().setTotalPax(holidaysTotalPax.getText());
    }

    private void setRailDetailsToCoreLead(){
        if(coreLeadDto.getCoreLeadRail()==null){
            coreLeadDto.setCoreLeadRail(new CoreLeadRail());
        }
        coreLeadDto.getCoreLeadRail().setAdultFare(railAdultFare.getText());
        coreLeadDto.getCoreLeadRail().setArrivalCity(railArrivalCity.getText());
        coreLeadDto.getCoreLeadRail().setChildFare(railChildFare.getText());
        coreLeadDto.getCoreLeadRail().setClassOfTravel(railClassOfTravel.getValue());
        coreLeadDto.getCoreLeadRail().setDepartureCity(railDepartureCity.getText());
        coreLeadDto.getCoreLeadRail().setDepartureDate(Validator.getStringDateValue(railDateOfDeparture.getValue()));
        coreLeadDto.getCoreLeadRail().setNumberOfAdult(railNumberOfAdult.getText());
        coreLeadDto.getCoreLeadRail().setNumberOfChild(railNumberOfChild.getText());
        coreLeadDto.getCoreLeadRail().setNumberOfInfant(railNumberOfInfant.getText());
        coreLeadDto.getCoreLeadRail().setStatus(railStatus.getValue());
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
    private void initialiseAllCheckBoxDefalutValues(){

        //setting all checkbox default values
        airCurrencyCode.getItems().addAll(LeadsConstants.currencyCodes);
        airCurrencyCode.setValue(LeadsConstants.currencyCodes[0]);

        airTypeOfTravel.getItems().addAll(LeadsConstants.airTypeOfTravel);
        airTypeOfTravel.setValue(LeadsConstants.airTypeOfTravel[0]);

        airClassOfTravel.getItems().addAll(LeadsConstants.airClassOfTravel);
        airClassOfTravel.setValue(LeadsConstants.airClassOfTravel[0]);

        airStatus.getItems().addAll(LeadsConstants.basicStatus);
        airStatus.setValue(LeadsConstants.basicStatus[0]);

        hotelCurrencyCode.getItems().addAll(LeadsConstants.currencyCodes);
        hotelCurrencyCode.setValue(LeadsConstants.currencyCodes[0]);
        hotelCategory.getItems().addAll(LeadsConstants.hotelCategory);
        hotelCategory.setValue(LeadsConstants.hotelCategory[0]);

        hotelPlan.getItems().addAll(LeadsConstants.hotelPlan);
        hotelPlan.setValue(LeadsConstants.hotelPlan[0]);
        hotelStatus.getItems().addAll(LeadsConstants.basicStatus);
        hotelStatus.setValue(LeadsConstants.basicStatus[0]);

        holidaysCurrencyCode.getItems().addAll(LeadsConstants.currencyCodes);
        holidaysCurrencyCode.setValue(LeadsConstants.currencyCodes[0]);

        holidaysHotelCategory.getItems().addAll(LeadsConstants.hotelCategory);
        holidaysHotelCategory.setValue(LeadsConstants.hotelCategory[0]);

        holidaysTravelType.getItems().addAll(LeadsConstants.travelType);
        holidaysTravelType.setValue(LeadsConstants.travelType[0]);

        holidaysStatus.getItems().addAll(LeadsConstants.basicStatus);
        holidaysStatus.setValue(LeadsConstants.basicStatus[0]);

        railClassOfTravel.getItems().addAll(LeadsConstants.classOfTravel);
        railClassOfTravel.setValue(LeadsConstants.classOfTravel[0]);

        railStatus.getItems().addAll(LeadsConstants.basicStatus);
        railStatus.setValue(LeadsConstants.basicStatus[0]);
    }
    private void initialiseAllEmptyDatePickers(){
        //setting default date picker values
        if (airDepartureDate.getValue()==null)airDepartureDate.setValue(LocalDate.now());
        if (airReturnDate.getValue()==null)airReturnDate.setValue(LocalDate.now());
        if (hotelCheckInDate.getValue()==null)hotelCheckInDate.setValue(LocalDate.now());
        if (hotelCheckOutDate.getValue()==null)hotelCheckOutDate.setValue(LocalDate.now());
        if (railDateOfDeparture.getValue()==null)railDateOfDeparture.setValue(LocalDate.now());
        if (holidaysDepartureDate.getValue()==null)holidaysDepartureDate.setValue(LocalDate.now());
        if (holidaysReturnDate.getValue()==null)holidaysReturnDate.setValue(LocalDate.now());
    }

    private void initializeDefaultLayout() {
        mainPane.setPrefWidth(Main.WIDTH - Main.SIDE_BAR_WIDTH);
        mainPane.setPrefHeight(Main.HEIGHT - 30);
        double paneWidth = (Main.WIDTH - Main.SIDE_BAR_WIDTH) / 4 - 23;
        queryTabs.setTabMinWidth(paneWidth);
        queryTabs.setTabMaxWidth(paneWidth);
    }

    private void setTotalAirPrice(){
       airTotalPrice.setText( String.valueOf(Validator.getIntValue(airAdultFare.getText())*Validator.getIntValue(airNumberOfAdult.getText())+
                Validator.getIntValue(airChildFare.getText())*Validator.getIntValue(airNumberOfChild.getText())+
                Validator.getIntValue(airInfantFare.getText())*Validator.getIntValue(airNumberOfInfant.getText())));
    }

    private void setTotalRailPrice(){
        railTotalFare.setText( String.valueOf(Validator.getIntValue(railAdultFare.getText())*Validator.getIntValue(railNumberOfAdult.getText())+
                Validator.getIntValue(railChildFare.getText())*Validator.getIntValue(railNumberOfChild.getText())+
                Validator.getIntValue("0"/*railInfantFare.getText()*/)*Validator.getIntValue(railNumberOfInfant.getText())));
    }
    /*private void setTotalHotelPrice(){
        hotelTotalPrice.setText( String.valueOf(Validator.getIntValue(ho.getText())*Validator.getIntValue(airNumberOfAdult.getText())+
                Validator.getIntValue(airChildFare.getText())*Validator.getIntValue(airNumberOfChild.getText())+
                Validator.getIntValue(airInfantFare.getText())*Validator.getIntValue(airNumberOfInfant.getText())));
    }*/
    private void setTotalHolidaysPrice(){
        holidaysTotalPrice.setText( String.valueOf(Validator.getIntValue(holidaysAdultFare.getText())*Validator.getIntValue(holidaysNumberOfAdult.getText())+
                Validator.getIntValue(holidaysChildFare.getText())*Validator.getIntValue(holidaysNumberOfChild.getText())+
                Validator.getIntValue(holidaysInfantFare.getText())*Validator.getIntValue(holidaysTotalInfant.getText())));
    }
}
