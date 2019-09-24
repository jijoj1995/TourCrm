package controller.booking;

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

import java.net.URL;
import java.util.ResourceBundle;

public class SubBooking implements Initializable {
    @FXML
    private AnchorPane mainPane;
    @FXML
    private TabPane bookingTabs;
    @FXML
    private JFXTextField passengerSegmentNumber,passengerGdsPnrNumber,passengerFirstName,passengerMiddleName,passengerLastName,passengerType,
            passengerGender,passengerDateOfBirth,passengerPassportNumber,passengerNationality,passengerTypeOfVisa,
            pricingSellingPrice,pricingTotalGpm,pricingTotalCost,pricingDiscount,pricingServiceCharge,pricingTotalReceivable,pricingCommision,pricingCouponAmount,
            pricingLossAmount,pricingCurrencyCode,pricingTotalCommitted,pricingAmountDue,pricingTotalReceived,pricingSurcharges,
            itinerarySegmentnumber,itineraryAirlinePnr,itineraryPnrNumber,itineraryOfferedAirline,itineraryFlightnumber,itineraryClass,itineraryTravelType,
            itineraryDepartureTime,itineraryArrivalDate,itineraryArrivalTime,itineraryBookingStatus,itineraryDepartureDate,itineraryFrom,itineraryTo,
            ticketingSegmentNumber,ticketingGdPnrNumber,ticketingFirstName,ticketingMiddleName,ticketingLastName,ticketingPassengerType,ticketingGender,
            ticketingStatus,ticketingAirlines,ticketingTicketNumber,ticketingSupplier,
            fareDepartureCity,fareArrivalCity,fareDateOfDeparture,fareTrainNumber,fareNumberOfAdult,fareNumberOfChild,fareNumberOfInfants,fareTotalPax,
            fareAdultFare,fareChildFare,fareTotalFare,fareClassOfTravel,fareStatus,
            promotionFirstName,promotionMiddleName,promotionLastName,promotionTypeOfPassenger,promotionTypeOfPromotion,promotionPromotionCode,
            promotionDiscountAmount,promotionCurrencyCode;
    private CoreBookingEntity coreBookingEntity;
    private CoreLead coreLeadDto;
    private Logger logger=Logger.getLogger(SubBooking.class);
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initializeDefaultLayout();
    }


    public void initializeCoreLeadObject(CoreLead coreLeadDto){
        this.coreLeadDto=coreLeadDto;
        this.coreBookingEntity = coreLeadDto.getCoreBookingEntity();
        initialiseAllTextFieldsFromDto();
    }
    private void initialiseAllTextFieldsFromDto(){
        //set data from coreBooking Dto to all textfields
        logger.info(" in initialiseAllTextFieldsFromDto method in sub query");
        if(coreBookingEntity==null){
            logger.warn("coreBookingEntity Dto is null. Returning");
            return;
        }
        if(coreBookingEntity.getCoreBookingPassengerEntity()!=null) initialisePassengerDetailsFromDto();
        if(coreBookingEntity.getCoreBookingPricingEntity()!=null) initialisePricingDetailsFromDto();
        if(coreBookingEntity.getCoreBookingItineraryEntity()!=null) initialiseItineraryDetailsFromDto();
        if(coreBookingEntity.getCoreBookingTicketingEntity()!=null) initialiseTicketingDetailsFromDto();
        if(coreBookingEntity.getCoreBookingFareEntity()!=null) initialiseFareDetailsFromDto();
        if(coreBookingEntity.getCoreBookingPromotionEntity()!=null) initialisePromotionDetailsFromDto();

    }
    private void initialisePassengerDetailsFromDto(){
        passengerSegmentNumber.setText(coreBookingEntity.getCoreBookingPassengerEntity().getSegmentNumber());
        passengerGdsPnrNumber.setText(coreBookingEntity.getCoreBookingPassengerEntity().getGdsPnrNumber());
        passengerFirstName.setText(coreBookingEntity.getCoreBookingPassengerEntity().getFirstName());
        passengerMiddleName.setText(coreBookingEntity.getCoreBookingPassengerEntity().getMiddleName());
        passengerLastName.setText(coreBookingEntity.getCoreBookingPassengerEntity().getLastName());
        passengerType.setText(coreBookingEntity.getCoreBookingPassengerEntity().getPassengerType());
        passengerTypeOfVisa.setText(coreBookingEntity.getCoreBookingPassengerEntity().getTypeOfVisa());
        passengerGender.setText(coreBookingEntity.getCoreBookingPassengerEntity().getGender());
        passengerDateOfBirth.setText(coreBookingEntity.getCoreBookingPassengerEntity().getDateOfBirth());
        passengerPassportNumber.setText(coreBookingEntity.getCoreBookingPassengerEntity().getPassportNumber());
        passengerNationality.setText(coreBookingEntity.getCoreBookingPassengerEntity().getNationality());

    }
    private void initialisePricingDetailsFromDto(){
        pricingSellingPrice.setText(coreBookingEntity.getCoreBookingPricingEntity().getSellingPrice());
        pricingTotalGpm.setText(coreBookingEntity.getCoreBookingPricingEntity().getTotalGpm());
        pricingTotalCost.setText(coreBookingEntity.getCoreBookingPricingEntity().getTotalCost());
        pricingDiscount.setText(coreBookingEntity.getCoreBookingPricingEntity().getDiscount());
        pricingServiceCharge.setText(coreBookingEntity.getCoreBookingPricingEntity().getServiceCharge());
        pricingTotalReceivable.setText(coreBookingEntity.getCoreBookingPricingEntity().getTotalReceivable());
        pricingCommision.setText(coreBookingEntity.getCoreBookingPricingEntity().getCommission());
        pricingCouponAmount.setText(coreBookingEntity.getCoreBookingPricingEntity().getCouponAmount());
        pricingSurcharges.setText(coreBookingEntity.getCoreBookingPricingEntity().getSurcharges());
        pricingLossAmount.setText(coreBookingEntity.getCoreBookingPricingEntity().getLossAmount());
        pricingCurrencyCode.setText(coreBookingEntity.getCoreBookingPricingEntity().getCurrencyCode());
        pricingTotalCommitted.setText(coreBookingEntity.getCoreBookingPricingEntity().getTotalCommitted());
        pricingAmountDue.setText(coreBookingEntity.getCoreBookingPricingEntity().getAmountDue());
        pricingTotalReceived.setText(coreBookingEntity.getCoreBookingPricingEntity().getTotalReceived());
    }
    private void initialiseItineraryDetailsFromDto(){
        itinerarySegmentnumber.setText(coreBookingEntity.getCoreBookingItineraryEntity().getSegmentNumber());
        itineraryAirlinePnr.setText(coreBookingEntity.getCoreBookingItineraryEntity().getAirlinePnr());
        itineraryPnrNumber.setText(coreBookingEntity.getCoreBookingItineraryEntity().getSegmentNumber());
        itineraryOfferedAirline.setText(coreBookingEntity.getCoreBookingItineraryEntity().getSegmentNumber());
        itineraryFlightnumber.setText(coreBookingEntity.getCoreBookingItineraryEntity().getSegmentNumber());
        itineraryClass.setText(coreBookingEntity.getCoreBookingItineraryEntity().getSegmentNumber());
        itineraryTravelType.setText(coreBookingEntity.getCoreBookingItineraryEntity().getSegmentNumber());
        itineraryTo.setText(coreBookingEntity.getCoreBookingItineraryEntity().getSegmentNumber());
        itineraryDepartureTime.setText(coreBookingEntity.getCoreBookingItineraryEntity().getSegmentNumber());
        itineraryArrivalDate.setText(coreBookingEntity.getCoreBookingItineraryEntity().getSegmentNumber());
        itineraryArrivalTime.setText(coreBookingEntity.getCoreBookingItineraryEntity().getSegmentNumber());
        itineraryBookingStatus.setText(coreBookingEntity.getCoreBookingItineraryEntity().getSegmentNumber());
        itineraryDepartureDate.setText(coreBookingEntity.getCoreBookingItineraryEntity().getSegmentNumber());
        itineraryFrom.setText(coreBookingEntity.getCoreBookingItineraryEntity().getSegmentNumber());
    }
    private void initialiseTicketingDetailsFromDto(){
        ticketingSegmentNumber.setText(coreBookingEntity.getCoreBookingTicketingEntity().getSegmentNumber());
        ticketingGdPnrNumber.setText(coreBookingEntity.getCoreBookingTicketingEntity().getGdPnrNumber());
        ticketingFirstName.setText(coreBookingEntity.getCoreBookingTicketingEntity().getFirstName());
        ticketingMiddleName.setText(coreBookingEntity.getCoreBookingTicketingEntity().getMiddleName());
        ticketingLastName.setText(coreBookingEntity.getCoreBookingTicketingEntity().getLastName());
        ticketingPassengerType.setText(coreBookingEntity.getCoreBookingTicketingEntity().getPassengerType());
        ticketingGender.setText(coreBookingEntity.getCoreBookingTicketingEntity().getGender());
        ticketingStatus.setText(coreBookingEntity.getCoreBookingTicketingEntity().getStatus());
        ticketingAirlines.setText(coreBookingEntity.getCoreBookingTicketingEntity().getAirlines());
        ticketingSupplier.setText(coreBookingEntity.getCoreBookingTicketingEntity().getSupplier());
        ticketingTicketNumber.setText(coreBookingEntity.getCoreBookingTicketingEntity().getTicketNumber());
    }
    private void initialiseFareDetailsFromDto(){
        fareDepartureCity.setText(coreBookingEntity.getCoreBookingFareEntity().getDepartureCity());
        fareArrivalCity.setText(coreBookingEntity.getCoreBookingFareEntity().getArrivalCity());
        fareDateOfDeparture.setText(coreBookingEntity.getCoreBookingFareEntity().getDateOfDeparture());
        fareTrainNumber.setText(coreBookingEntity.getCoreBookingFareEntity().getTrainNumber());
        fareNumberOfAdult.setText(coreBookingEntity.getCoreBookingFareEntity().getNumberOfAdult());
        fareNumberOfChild.setText(coreBookingEntity.getCoreBookingFareEntity().getNumberOfChild());
        fareNumberOfInfants.setText(coreBookingEntity.getCoreBookingFareEntity().getNumberOfInfants());
        fareTotalPax.setText(coreBookingEntity.getCoreBookingFareEntity().getTotalPax());
        fareStatus.setText(coreBookingEntity.getCoreBookingFareEntity().getStatus());
        fareAdultFare.setText(coreBookingEntity.getCoreBookingFareEntity().getAdultFare());
        fareChildFare.setText(coreBookingEntity.getCoreBookingFareEntity().getChildFare());
        fareTotalFare.setText(coreBookingEntity.getCoreBookingFareEntity().getTotalFare());
        fareClassOfTravel.setText(coreBookingEntity.getCoreBookingFareEntity().getClassOfTravel());
    }
    private void initialisePromotionDetailsFromDto(){
        promotionFirstName.setText(coreBookingEntity.getCoreBookingPromotionEntity().getFirstName());
        promotionMiddleName.setText(coreBookingEntity.getCoreBookingPromotionEntity().getMiddleName());
        promotionLastName.setText(coreBookingEntity.getCoreBookingPromotionEntity().getLastName());
        promotionTypeOfPassenger.setText(coreBookingEntity.getCoreBookingPromotionEntity().getTypeOfPassenger());
        promotionDiscountAmount.setText(coreBookingEntity.getCoreBookingPromotionEntity().getDiscountAmount());
        promotionCurrencyCode.setText(coreBookingEntity.getCoreBookingPromotionEntity().getCurrencyCode());
        promotionTypeOfPromotion.setText(coreBookingEntity.getCoreBookingPromotionEntity().getTypeOfPromotion());
        promotionPromotionCode.setText(coreBookingEntity.getCoreBookingPromotionEntity().getPromotionCode());
    }

    @FXML
    private void saveAllSubBookingDataToDto(){
        // save all tabs data to coreLeadDto

        logger.info("saving all sub-booking tabs data to dto");
        if(coreBookingEntity ==null){
            coreBookingEntity =new CoreBookingEntity();
            coreBookingEntity.setCoreBookingPassengerEntity(new CoreBookingPassengerEntity());
            coreBookingEntity.setCoreBookingPricingEntity(new CoreBookingPricingEntity());
            coreBookingEntity.setCoreBookingItineraryEntity(new CoreBookingItineraryEntity());
            coreBookingEntity.setCoreBookingTicketingEntity(new CoreBookingTicketingEntity());
            coreBookingEntity.setCoreBookingFareEntity(new CoreBookingFareEntity());
            coreBookingEntity.setCoreBookingPromotionEntity(new CoreBookingPromotionEntity());
        }
        setPassengerDataToDto();
        setPricingDataToDto();
        setItineraryDataToDto();
        setTicketingDataToDto();
        setFareDataToDto();
        setPromotionDataToDto();
                            //set booking details to main Lead dto
        coreLeadDto.setCoreBookingEntity(coreBookingEntity);
        showMainBookingPage();

    }

    private void setPassengerDataToDto(){
        if(coreBookingEntity.getCoreBookingPassengerEntity()==null)coreBookingEntity.setCoreBookingPassengerEntity(new CoreBookingPassengerEntity());

        coreBookingEntity.getCoreBookingPassengerEntity().setSegmentNumber(passengerSegmentNumber.getText());
        coreBookingEntity.getCoreBookingPassengerEntity().setGdsPnrNumber(passengerGdsPnrNumber.getText());
        coreBookingEntity.getCoreBookingPassengerEntity().setFirstName(passengerFirstName.getText());
        coreBookingEntity.getCoreBookingPassengerEntity().setMiddleName(passengerMiddleName.getText());
        coreBookingEntity.getCoreBookingPassengerEntity().setLastName(passengerLastName.getText());
        coreBookingEntity.getCoreBookingPassengerEntity().setPassengerType(passengerType.getText());
        coreBookingEntity.getCoreBookingPassengerEntity().setTypeOfVisa(passengerTypeOfVisa.getText());
        coreBookingEntity.getCoreBookingPassengerEntity().setGender(passengerGender.getText());
        coreBookingEntity.getCoreBookingPassengerEntity().setDateOfBirth(passengerDateOfBirth.getText());
        coreBookingEntity.getCoreBookingPassengerEntity().setPassportNumber(passengerPassportNumber.getText());
        coreBookingEntity.getCoreBookingPassengerEntity().setNationality(passengerNationality.getText());

    }
    private void setPricingDataToDto(){
        if(coreBookingEntity.getCoreBookingPricingEntity()==null)coreBookingEntity.setCoreBookingPricingEntity(new CoreBookingPricingEntity());
        coreBookingEntity.getCoreBookingPricingEntity().setSellingPrice(pricingSellingPrice.getText());
        coreBookingEntity.getCoreBookingPricingEntity().setSurcharges(pricingSurcharges.getText());
        coreBookingEntity.getCoreBookingPricingEntity().setTotalGpm(pricingTotalGpm.getText());
        coreBookingEntity.getCoreBookingPricingEntity().setTotalCost(pricingTotalCost.getText());
        coreBookingEntity.getCoreBookingPricingEntity().setDiscount(pricingDiscount.getText());
        coreBookingEntity.getCoreBookingPricingEntity().setServiceCharge(pricingServiceCharge.getText());
        coreBookingEntity.getCoreBookingPricingEntity().setTotalReceivable(pricingTotalReceivable.getText());
        coreBookingEntity.getCoreBookingPricingEntity().setCommission(pricingCommision.getText());
        coreBookingEntity.getCoreBookingPricingEntity().setCouponAmount(pricingCouponAmount.getText());
        coreBookingEntity.getCoreBookingPricingEntity().setLossAmount(pricingLossAmount.getText());
        coreBookingEntity.getCoreBookingPricingEntity().setCurrencyCode(pricingCurrencyCode.getText());
        coreBookingEntity.getCoreBookingPricingEntity().setTotalCommitted(pricingTotalCommitted.getText());
        coreBookingEntity.getCoreBookingPricingEntity().setAmountDue(pricingAmountDue.getText());
        coreBookingEntity.getCoreBookingPricingEntity().setTotalReceived(pricingTotalReceived.getText());
    }
    private void setItineraryDataToDto(){
        if(coreBookingEntity.getCoreBookingItineraryEntity()==null)coreBookingEntity.setCoreBookingItineraryEntity(new CoreBookingItineraryEntity());

        coreBookingEntity.getCoreBookingItineraryEntity().setToDestination(itineraryTo.getText());
        coreBookingEntity.getCoreBookingItineraryEntity().setSegmentNumber(itinerarySegmentnumber.getText());
        coreBookingEntity.getCoreBookingItineraryEntity().setAirlinePnr(itineraryAirlinePnr.getText());
        coreBookingEntity.getCoreBookingItineraryEntity().setPnrNumber(itineraryPnrNumber.getText());
        coreBookingEntity.getCoreBookingItineraryEntity().setOfferedAirline(itineraryOfferedAirline.getText());
        coreBookingEntity.getCoreBookingItineraryEntity().setFlightNumber(itineraryFlightnumber.getText());
        coreBookingEntity.getCoreBookingItineraryEntity().setClassType(itineraryClass.getText());
        coreBookingEntity.getCoreBookingItineraryEntity().setTravelType(itineraryTravelType.getText());
        coreBookingEntity.getCoreBookingItineraryEntity().setDepartureTime(itineraryDepartureTime.getText());
        coreBookingEntity.getCoreBookingItineraryEntity().setArrivalDate(itineraryArrivalDate.getText());
        coreBookingEntity.getCoreBookingItineraryEntity().setArrivalTime(itineraryArrivalTime.getText());
        coreBookingEntity.getCoreBookingItineraryEntity().setBookingStatus(itineraryBookingStatus.getText());
        coreBookingEntity.getCoreBookingItineraryEntity().setDepartureDate(itineraryDepartureDate.getText());
        coreBookingEntity.getCoreBookingItineraryEntity().setFromDestination(itineraryFrom.getText());

    }
    private void setTicketingDataToDto(){
        if(coreBookingEntity.getCoreBookingTicketingEntity()==null)coreBookingEntity.setCoreBookingTicketingEntity(new CoreBookingTicketingEntity());

        coreBookingEntity.getCoreBookingTicketingEntity().setSupplier(ticketingSupplier.getText());
        coreBookingEntity.getCoreBookingTicketingEntity().setSegmentNumber(ticketingSegmentNumber.getText());
        coreBookingEntity.getCoreBookingTicketingEntity().setGdPnrNumber(ticketingGdPnrNumber.getText());
        coreBookingEntity.getCoreBookingTicketingEntity().setFirstName(ticketingFirstName.getText());
        coreBookingEntity.getCoreBookingTicketingEntity().setMiddleName(ticketingMiddleName.getText());
        coreBookingEntity.getCoreBookingTicketingEntity().setLastName(ticketingLastName.getText());
        coreBookingEntity.getCoreBookingTicketingEntity().setPassengerType(ticketingPassengerType.getText());
        coreBookingEntity.getCoreBookingTicketingEntity().setGender(ticketingGender.getText());
        coreBookingEntity.getCoreBookingTicketingEntity().setStatus(ticketingStatus.getText());
        coreBookingEntity.getCoreBookingTicketingEntity().setAirlines(ticketingAirlines.getText());
        coreBookingEntity.getCoreBookingTicketingEntity().setTicketNumber(ticketingTicketNumber.getText());
    }
    private void setFareDataToDto(){
        if(coreBookingEntity.getCoreBookingFareEntity()==null)coreBookingEntity.setCoreBookingFareEntity(new CoreBookingFareEntity());

        coreBookingEntity.getCoreBookingFareEntity().setDepartureCity(fareDepartureCity.getText());
        coreBookingEntity.getCoreBookingFareEntity().setStatus(fareStatus.getText());
        coreBookingEntity.getCoreBookingFareEntity().setArrivalCity(fareArrivalCity.getText());
        coreBookingEntity.getCoreBookingFareEntity().setDateOfDeparture(fareDateOfDeparture.getText());
        coreBookingEntity.getCoreBookingFareEntity().setTrainNumber(fareTrainNumber.getText());
        coreBookingEntity.getCoreBookingFareEntity().setNumberOfAdult(fareNumberOfAdult.getText());
        coreBookingEntity.getCoreBookingFareEntity().setNumberOfChild(fareNumberOfChild.getText());
        coreBookingEntity.getCoreBookingFareEntity().setNumberOfInfants(fareNumberOfInfants.getText());
        coreBookingEntity.getCoreBookingFareEntity().setTotalPax(fareTotalPax.getText());
        coreBookingEntity.getCoreBookingFareEntity().setAdultFare(fareAdultFare.getText());
        coreBookingEntity.getCoreBookingFareEntity().setChildFare(fareChildFare.getText());
        coreBookingEntity.getCoreBookingFareEntity().setTotalFare(fareTotalFare.getText());
        coreBookingEntity.getCoreBookingFareEntity().setClassOfTravel(fareClassOfTravel.getText());

    }
    private void setPromotionDataToDto(){
        if(coreBookingEntity.getCoreBookingPromotionEntity()==null)coreBookingEntity.setCoreBookingPromotionEntity(new CoreBookingPromotionEntity());

        coreBookingEntity.getCoreBookingPromotionEntity().setCurrencyCode(promotionCurrencyCode.getText());
        coreBookingEntity.getCoreBookingPromotionEntity().setFirstName(promotionFirstName.getText());
        coreBookingEntity.getCoreBookingPromotionEntity().setMiddleName(promotionMiddleName.getText());
        coreBookingEntity.getCoreBookingPromotionEntity().setLastName(promotionLastName.getText());
        coreBookingEntity.getCoreBookingPromotionEntity().setTypeOfPassenger(promotionTypeOfPassenger.getText());
        coreBookingEntity.getCoreBookingPromotionEntity().setTypeOfPromotion(promotionTypeOfPromotion.getText());
        coreBookingEntity.getCoreBookingPromotionEntity().setPromotionCode(promotionPromotionCode.getText());
        coreBookingEntity.getCoreBookingPromotionEntity().setDiscountAmount(promotionDiscountAmount.getText());
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
        mainBooking.initializeCoreLeadDto(coreLeadDto);
        Parent p = Loader.getRoot();
        mainPane.getChildren().setAll(p);
    }

    private void initializeDefaultLayout() {
        mainPane.setPrefWidth(Main.WIDTH - Main.SIDE_BAR_WIDTH);
        mainPane.setPrefHeight(Main.HEIGHT - 30);
        double paneWidth = (Main.WIDTH - Main.SIDE_BAR_WIDTH) / 6 - 20;
        bookingTabs.setTabMinWidth(paneWidth);
        bookingTabs.setTabMaxWidth(paneWidth);
    }
}
