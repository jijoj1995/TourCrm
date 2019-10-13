package controller.query;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextField;
import constants.LeadsConstants;
import db.QueryService;
import dto.*;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import main.Main;
import org.apache.log4j.Logger;
import service.BookingService;
import service.Validator;

import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
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
    @FXML
    private TableView hotelTable,airTable,railTable;

    private CoreLead coreLeadDto;
    private ObservableList<CoreLeadHotel> hotelTableData = FXCollections.observableArrayList();
    private ObservableList<CoreLeadAir> airTableData = FXCollections.observableArrayList();
    private ObservableList<CoreLeadRail> railTableData = FXCollections.observableArrayList();

    private static Logger logger=Logger.getLogger(SubQuery.class);
    @Override
    public void initialize(URL location, ResourceBundle resources) {

                    //set window size based on screens size
        initializeDefaultLayout();
                    //initialise all checkbox with default values
        initialiseAllCheckBoxDefaultValues();
                    //initialise all empty datePickers with today date
        initialiseAllEmptyDatePickers();
                    //set specific number only with validations
        setNumberOnlyInputCheck();
        initialiseHotelTable();
        initialiseAirTable();
        initialiseRailTable();
        //initialiseTotalFareCalculationEvent
        setListenersToHolidaysFields();
        setListenersToAirFields();
        setListenersToRailFields();
        setListenersToHotelFields();
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
    private void setListenersToHotelFields(){
        hotelNumberOfAdult.setOnKeyTyped((keyEvent)->{Validator.checkNumberEntered(keyEvent);});
        hotelNumberOfChild.setOnKeyTyped((keyEvent)->{Validator.checkNumberEntered(keyEvent);});
        hotelNumberOfInfant.setOnKeyTyped((keyEvent)->{Validator.checkNumberEntered(keyEvent);});
        hotelNumberOfNights.setOnKeyTyped((keyEvent)->{Validator.checkNumberEntered(keyEvent);});
        hotelTotalPax.setOnKeyTyped((keyEvent)->{Validator.checkNumberEntered(keyEvent);});
        hotelRoomTariff.setOnKeyTyped((keyEvent)->{Validator.checkNumberEntered(keyEvent);});
        hotelExtraBed.setOnKeyTyped((keyEvent)->{Validator.checkNumberEntered(keyEvent);});

        hotelRoomTariff.textProperty().addListener((observable, oldValue, newValue) -> {setTotalHotelPrice();});
        hotelNumberOfNights.textProperty().addListener((observable, oldValue, newValue) -> {setTotalHotelPrice();});
        hotelExtraBed.textProperty().addListener((observable, oldValue, newValue) -> {setTotalHotelPrice();});
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
        if(coreLeadDto.getCoreLeadAirList()!=null){
            setAirTabTextFieldsFromDto();
        }
        if(coreLeadDto.getCoreLeadHotelList()!=null){
            setHotelTabTextFieldsFromDto();
        }
        if(coreLeadDto.getCoreLeadHolidays()!=null){
            setHolidaysTabTextFieldsFromDto();
        }
        if(coreLeadDto.getCoreLeadRailList()!=null){
            setRailTabTextFieldsFromDto();
        }

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

    private void setHotelTabTextFieldsFromDto(){
        //method to set text Fields from dto
        for (CoreLeadHotelEntity entity:coreLeadDto.getCoreLeadHotelList())
            hotelTableData.add(new CoreLeadHotel(entity.getCoreLeadHotelId(),entity.getDestination(),entity.getCheckInDate(),entity.getCheckoutDate(),entity.getCurrencyCode(),entity.getHotelCategory(),entity.getNumberOfNights(),entity.getNumberOfAdult(),entity.getNumberOfChild(),entity.getNumberOfInfants(),entity.getTotalPax(),entity.getRoomTariff(),entity.getExtraBed(),entity.getTotalPrice(),entity.getHotelPlan(),entity.getStatus()));
        hotelTable.setItems(hotelTableData);
    }

    private void setRailTabTextFieldsFromDto(){
        for (CoreLeadRailEntity entity:coreLeadDto.getCoreLeadRailList())
            railTableData.add(new CoreLeadRail( entity.getCoreLeadRailId(),  entity.getDepartureCity(),  entity.getArrivalCity(),  entity.getDepartureDate(),  entity.getTrainNumber(),  entity.getNumberOfAdult(),  entity.getNumberOfChild(),  entity.getNumberOfInfant(),  entity.getTotalPax(),  entity.getAdultFare(),  entity.getChildFare(),  entity.getTotalFare(),  entity.getClassOfTravel(),  entity.getStatus()));
        railTable.setItems(railTableData);
    }

    private void setAirTabTextFieldsFromDto(){
        for (CoreLeadAirEntity entity:coreLeadDto.getCoreLeadAirList())
            airTableData.add(new CoreLeadAir(entity.getCoreLeadAirId(),  entity.getFromDestination(),  entity.getToDestination(),  entity.getDepartureDate(),  entity.getReturnDate(),  entity.getAirlinesOffered(),  entity.getCurrencyCode(),  entity.getNumberOfAdult(),  entity.getNumberOfChild(),  entity.getNumberOfInfant(),  entity.getTotalPax(),  entity.getAdultFare(),  entity.getChildFare(),  entity.getInfantFare(),  entity.getTotalPrice(),  entity.getTypeOfTravel(),  entity.getClassOfTravel(),  entity.getStatus()));
        airTable.setItems(airTableData);

    }
    private void initialiseHotelTable(){

        TableColumn<CoreLeadHotel, String> destination = new TableColumn<>("Destination");
        TableColumn<CoreLeadHotel, String> checkInDate = new TableColumn<>("Check In Date");
        TableColumn<CoreLeadHotel, String> checkoutDate = new TableColumn<>("Checkout Date");
        TableColumn<CoreLeadHotel, String> currencyCode = new TableColumn<>("C. Code");
        TableColumn<CoreLeadHotel, String> hotelCategory = new TableColumn<>("H. Category");
        TableColumn<CoreLeadHotel, String> numberOfNights = new TableColumn<>("No. Of Nights");
        TableColumn<CoreLeadHotel, String> numberOfAdult = new TableColumn<>("No. Of Adult");
        TableColumn<CoreLeadHotel, String> numberOfChild = new TableColumn<>("No. Of Child");
        TableColumn<CoreLeadHotel, String> numberOfInfants = new TableColumn<>("No. Of Infants");
        TableColumn<CoreLeadHotel, String> totalPax = new TableColumn<>("Total Pax");
        TableColumn<CoreLeadHotel, String> roomTariff = new TableColumn<>("Room Tariff");
        TableColumn<CoreLeadHotel, String> extraBed = new TableColumn<>("Extra Bed");
        TableColumn<CoreLeadHotel, String> totalPrice = new TableColumn<>("Total Price");
        TableColumn<CoreLeadHotel, String> hotelPlan = new TableColumn<>("Hotel Plan");
        TableColumn<CoreLeadHotel, String> status = new TableColumn<>("Status");

        TableColumn<CoreLeadHotel, CoreLeadHotel> delete = new TableColumn<>("Action");
        hotelTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        hotelTable.getColumns().addAll(destination, checkInDate,checkoutDate,currencyCode,hotelCategory, numberOfNights,numberOfAdult,numberOfChild,numberOfInfants,totalPax, roomTariff,extraBed,totalPrice,hotelPlan,status);
        hotelTable.setEditable(true);

        destination.setCellValueFactory(new PropertyValueFactory<>("destination"));
        checkInDate.setCellValueFactory(new PropertyValueFactory<>("checkInDate"));
        checkoutDate.setCellValueFactory(new PropertyValueFactory<>("checkoutDate"));
        currencyCode.setCellValueFactory(new PropertyValueFactory<>("currencyCode"));
        hotelCategory.setCellValueFactory(new PropertyValueFactory<>("hotelCategory"));
        numberOfNights.setCellValueFactory(new PropertyValueFactory<>("numberOfNights"));
        numberOfAdult.setCellValueFactory(new PropertyValueFactory<>("numberOfAdult"));
        numberOfChild.setCellValueFactory(new PropertyValueFactory<>("numberOfChild"));
        numberOfInfants.setCellValueFactory(new PropertyValueFactory<>("numberOfInfants"));
        totalPax.setCellValueFactory(new PropertyValueFactory<>("totalPax"));
        roomTariff.setCellValueFactory(new PropertyValueFactory<>("roomTariff"));
        extraBed.setCellValueFactory(new PropertyValueFactory<>("extraBed"));
        totalPrice.setCellValueFactory(new PropertyValueFactory<>("totalPrice"));
        hotelPlan.setCellValueFactory(new PropertyValueFactory<>("hotelPlan"));
        status.setCellValueFactory(new PropertyValueFactory<>("status"));
        delete.setCellValueFactory(new PropertyValueFactory<>("Action"));

        delete.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue()));
        delete.setCellFactory(param -> new TableCell<CoreLeadHotel, CoreLeadHotel>() {
            private final Button deleteButton = new Button("Delete");

            @Override
            protected void updateItem(CoreLeadHotel item, boolean empty) {
                super.updateItem(item, empty);
                if (item == null) {
                    setGraphic(null);
                    return;
                }

                setGraphic(deleteButton);
                deleteButton.setOnAction(event -> {
                    hotelTableData.remove(item);
                });
            }
        });
        hotelTable.setItems(hotelTableData);
    }


    private void initialiseAirTable(){


        TableColumn<CoreLeadAir, String> fromDestination=new TableColumn<>("From Dest.");
        TableColumn<CoreLeadAir, String> toDestination=new TableColumn<>("To Dest.");
        TableColumn<CoreLeadAir, String> departureDate=new TableColumn<>("Dept. Date");
        TableColumn<CoreLeadAir, String> returnDate=new TableColumn<>("Return Date");
        TableColumn<CoreLeadAir, String> airlinesOffered=new TableColumn<>("Airlines Offered");
        TableColumn<CoreLeadAir, String> currencyCode=new TableColumn<>("Currency Code");
        TableColumn<CoreLeadAir, String> numberOfAdult=new TableColumn<>("No. of Adult");
        TableColumn<CoreLeadAir, String> numberOfChild=new TableColumn<>("No. of Child");
        TableColumn<CoreLeadAir, String> numberOfInfant=new TableColumn<>("No. of Infant");
        TableColumn<CoreLeadAir, String> totalPax=new TableColumn<>("Total Pax");
        TableColumn<CoreLeadAir, String> adultFare=new TableColumn<>("Adult Fare");
        TableColumn<CoreLeadAir, String> childFare=new TableColumn<>("Child Fare");
        TableColumn<CoreLeadAir, String> infantFare=new TableColumn<>("Infant Fare");
        TableColumn<CoreLeadAir, String> totalPrice=new TableColumn<>("Total Price");
        TableColumn<CoreLeadAir, String> typeOfTravel=new TableColumn<>("Type Of Travel");
        TableColumn<CoreLeadAir, String> classOfTravel=new TableColumn<>("Class Of Travel");
        TableColumn<CoreLeadAir, String> status=new TableColumn<>("Status");

        TableColumn<CoreLeadAir, CoreLeadAir> delete = new TableColumn<>("Action");
        hotelTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        airTable.getColumns().addAll(fromDestination,toDestination,departureDate,returnDate,airlinesOffered,currencyCode,numberOfAdult,numberOfChild,numberOfInfant,totalPax,adultFare,childFare,infantFare,totalPrice,typeOfTravel,classOfTravel,status);

        fromDestination.setCellValueFactory(new PropertyValueFactory<>("fromDestination"));
        toDestination.setCellValueFactory(new PropertyValueFactory<>("toDestination"));
        departureDate.setCellValueFactory(new PropertyValueFactory<>("departureDate"));
        returnDate.setCellValueFactory(new PropertyValueFactory<>("returnDate"));
        airlinesOffered.setCellValueFactory(new PropertyValueFactory<>("airlinesOffered"));
        currencyCode.setCellValueFactory(new PropertyValueFactory<>("currencyCode"));
        numberOfAdult.setCellValueFactory(new PropertyValueFactory<>("numberOfAdult"));
        numberOfChild.setCellValueFactory(new PropertyValueFactory<>("numberOfChild"));
        numberOfInfant.setCellValueFactory(new PropertyValueFactory<>("numberOfInfant"));
        totalPax.setCellValueFactory(new PropertyValueFactory<>("totalPax"));
        adultFare.setCellValueFactory(new PropertyValueFactory<>("adultFare"));
        childFare.setCellValueFactory(new PropertyValueFactory<>("childFare"));
        infantFare.setCellValueFactory(new PropertyValueFactory<>("infantFare"));
        totalPrice.setCellValueFactory(new PropertyValueFactory<>("totalPrice"));
        typeOfTravel.setCellValueFactory(new PropertyValueFactory<>("typeOfTravel"));
        classOfTravel.setCellValueFactory(new PropertyValueFactory<>("classOfTravel"));
        status.setCellValueFactory(new PropertyValueFactory<>("status"));
        delete.setCellValueFactory(new PropertyValueFactory<CoreLeadAir, CoreLeadAir>("Action"));

        delete.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue()));
        delete.setCellFactory(param -> new TableCell<CoreLeadAir, CoreLeadAir>() {
            private final Button deleteButton = new Button("Delete");

            @Override
            protected void updateItem(CoreLeadAir item, boolean empty) {
                super.updateItem(item, empty);
                if (item == null) {
                    setGraphic(null);
                    return;
                }

                setGraphic(deleteButton);
                deleteButton.setOnAction(event -> {
                    hotelTableData.remove(item);
                });
            }
        });
        airTable.setItems(airTableData);
    }
    private void initialiseRailTable(){

        TableColumn<CoreLeadRail, String> departureCity=new TableColumn<>("Dept City");
        TableColumn<CoreLeadRail, String> arrivalCity=new TableColumn<>("Arrival City");
        TableColumn<CoreLeadRail, String> departureDate=new TableColumn<>("Dept. Date");
        TableColumn<CoreLeadRail, String> trainNumber=new TableColumn<>("Train NO.");
        TableColumn<CoreLeadRail, String> numberOfAdult=new TableColumn<>("No. of Infant");
        TableColumn<CoreLeadRail, String> numberOfChild=new TableColumn<>("No. of Child");
        TableColumn<CoreLeadRail, String> numberOfInfant=new TableColumn<>("No. of Infant");
        TableColumn<CoreLeadRail, String> totalPax=new TableColumn<>("Total Pax");
        TableColumn<CoreLeadRail, String> adultFare=new TableColumn<>("Adult Fare");
        TableColumn<CoreLeadRail, String> childFare=new TableColumn<>("Child Fare");
        TableColumn<CoreLeadRail, String> totalFare=new TableColumn<>("Total Fare");
        TableColumn<CoreLeadRail, String> classOfTravel=new TableColumn<>("Class Of Travel");
        TableColumn<CoreLeadRail, String> status=new TableColumn<>("Status");

        TableColumn<CoreLeadRail, CoreLeadRail> delete = new TableColumn<>("Action");
        railTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        railTable.getColumns().addAll( departureCity,  arrivalCity,  departureDate,  trainNumber,  numberOfAdult,  numberOfChild,  numberOfInfant,  totalPax,  adultFare,  childFare,  totalFare,  classOfTravel,  status);

        departureCity.setCellValueFactory(new PropertyValueFactory<>("departureCity"));
        arrivalCity.setCellValueFactory(new PropertyValueFactory<>("arrivalCity"));
        departureDate.setCellValueFactory(new PropertyValueFactory<>("departureDate"));
        trainNumber.setCellValueFactory(new PropertyValueFactory<>("trainNumber"));
        numberOfAdult.setCellValueFactory(new PropertyValueFactory<>("numberOfAdult"));
        numberOfChild.setCellValueFactory(new PropertyValueFactory<>("numberOfChild"));
        numberOfInfant.setCellValueFactory(new PropertyValueFactory<>("numberOfInfant"));
        totalPax.setCellValueFactory(new PropertyValueFactory<>("totalPax"));
        adultFare.setCellValueFactory(new PropertyValueFactory<>("adultFare"));
        childFare.setCellValueFactory(new PropertyValueFactory<>("childFare"));
        totalFare.setCellValueFactory(new PropertyValueFactory<>("totalFare"));
        classOfTravel.setCellValueFactory(new PropertyValueFactory<>("classOfTravel"));
        status.setCellValueFactory(new PropertyValueFactory<>("status"));

        delete.setCellValueFactory(new PropertyValueFactory<>("Action"));

        delete.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue()));
        delete.setCellFactory(param -> new TableCell<CoreLeadRail, CoreLeadRail>() {
            private final Button deleteButton = new Button("Delete");

            @Override
            protected void updateItem(CoreLeadRail item, boolean empty) {
                super.updateItem(item, empty);
                if (item == null) {
                    setGraphic(null);
                    return;
                }
                setGraphic(deleteButton);
                deleteButton.setOnAction(event -> {
                    hotelTableData.remove(item);
                });
            }
        });
        railTable.setItems(railTableData);
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
            coreLeadDto.setCoreLeadAirList(new ArrayList<>());
            coreLeadDto.setCoreLeadHotelList(new ArrayList<>());
            coreLeadDto.setCoreLeadHolidays(new CoreLeadHolidays());
            coreLeadDto.setCoreLeadRailList(new ArrayList<>());
        }
        setAirDetailsToCoreLead();
        setHotelDetailsToCoreLead();
        setHolidaysDetailsToCoreLead();
        setRailDetailsToCoreLead();
                 //details all saved to dto. Going back to main QueryPage
        showMainQueryPage();
    }

    private void setAirDetailsToCoreLead(){
        if(coreLeadDto.getCoreLeadAirList()==null) coreLeadDto.setCoreLeadAirList(new ArrayList<>());
        if (airTableData.isEmpty()){
            logger.warn("no air details to be saved. returning");
            return;
        }
        ArrayList<CoreLeadAirEntity>coreLeadAirEntities= new QueryService().getAirListFromTableData(airTableData);
        coreLeadDto.setCoreLeadAirList(coreLeadAirEntities);
    }

    private void setHotelDetailsToCoreLead(){
        if(coreLeadDto.getCoreLeadHotelList()==null)coreLeadDto.setCoreLeadHotelList(new ArrayList<>());
        if (hotelTableData.isEmpty()){
            logger.warn("no hotel details to be saved. returning");
            return;
        }
        ArrayList<CoreLeadHotelEntity>coreLeadHotelEntityArrayList= new QueryService().getHotelListFromTableData(hotelTableData);
        coreLeadDto.setCoreLeadHotelList(coreLeadHotelEntityArrayList);
    }

    private void setRailDetailsToCoreLead(){
        if(coreLeadDto.getCoreLeadRailList()==null)coreLeadDto.setCoreLeadRailList(new ArrayList<>());
        if (railTableData.isEmpty()){
            logger.warn("no rail details to be saved. returning");
            return;
        }
        ArrayList<CoreLeadRailEntity>coreLeadRailEntityArrayList= new QueryService().getRailListFromTableData(railTableData);
        coreLeadDto.setCoreLeadRailList(coreLeadRailEntityArrayList);
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

    @FXML
    private void addHotelToTable(){
        hotelTableData.add(new CoreLeadHotel(null, hotelDestination.getText(), hotelCheckInDate.getValue().toString(), hotelCheckOutDate.getValue().toString(), hotelCurrencyCode.getValue(),  hotelCategory.getValue(), hotelNumberOfNights.getText(),  hotelNumberOfAdult.getText(), hotelNumberOfChild.getText(),  hotelNumberOfInfant.getText(), hotelTotalPax.getText(), hotelRoomTariff.getText(), hotelExtraBed.getText(), hotelTotalPrice.getText(), hotelPlan.getValue(), hotelStatus.getValue()));
       // resetPassengerDataFields();
    }
    @FXML
    private void addAirToTable(){
        airTableData.add(new CoreLeadAir(null,  airFromDestination.getText(),  airToDestination.getText(),  airDepartureDate.getValue().toString(),  airReturnDate.getValue().toString(),  airAirlinesOffered.getText(),  airCurrencyCode.getValue(),  airNumberOfAdult.getText(),  airNumberOfChild.getText(),  airNumberOfInfant.getText(),  airTotalPax.getText(),  airAdultFare.getText(),  airChildFare.getText(),  airInfantFare.getText(),  airTotalPrice.getText(),  airTypeOfTravel.getValue(),  airClassOfTravel.getValue(),  airStatus.getValue()));
        // resetPassengerDataFields();
    }
    @FXML
    private void addRailToTable(){
        railTableData.add(new CoreLeadRail(null,railDepartureCity.getText(),railArrivalCity.getText(),railDateOfDeparture.getValue().toString(),railTrainNumber.getText(),railNumberOfAdult.getText(),railNumberOfChild.getText(),railNumberOfInfant.getText(),railTotalPax.getText(),railAdultFare.getText(),railChildFare.getText(),railTotalFare.getText(),railClassOfTravel.getValue(),railStatus.getValue()));
        // resetPassengerDataFields();
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
    private void initialiseAllCheckBoxDefaultValues(){

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
    private void setTotalHotelPrice(){
        hotelTotalPrice.setText( String.valueOf((Validator.getIntValue(hotelRoomTariff.getText())+
                Validator.getIntValue(hotelExtraBed.getText()))*Validator.getIntValue(hotelNumberOfNights.getText())));
    }
    private void setTotalHolidaysPrice(){
        holidaysTotalPrice.setText( String.valueOf(Validator.getIntValue(holidaysAdultFare.getText())*Validator.getIntValue(holidaysNumberOfAdult.getText())+
                Validator.getIntValue(holidaysChildFare.getText())*Validator.getIntValue(holidaysNumberOfChild.getText())+
                Validator.getIntValue(holidaysInfantFare.getText())*Validator.getIntValue(holidaysTotalInfant.getText())));
    }
}
