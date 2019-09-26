package controller.booking;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXTimePicker;
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

import java.net.URL;
import java.util.ResourceBundle;

public class SubBooking implements Initializable {
    @FXML
    private AnchorPane mainPane;
    @FXML
    private TabPane bookingTabs;
    @FXML
    private JFXTextField passengerSegmentNumber,passengerGdsPnrNumber,passengerFirstName,passengerMiddleName,passengerLastName,
            passengerPassportNumber,passengerNationality,passengerTypeOfVisa,
            pricingSellingPrice,pricingTotalGpm,pricingTotalCost,pricingDiscount,pricingServiceCharge,pricingTotalReceivable,pricingCommision,pricingCouponAmount,
            pricingLossAmount,pricingCurrencyCode,pricingTotalCommitted,pricingAmountDue,pricingTotalReceived,pricingSurcharges,
            itinerarySegmentnumber,itineraryAirlinePnr,itineraryPnrNumber,itineraryOfferedAirline,itineraryFlightnumber,itineraryClass,itineraryTravelType,
            itineraryBookingStatus,itineraryFrom,itineraryTo,
            ticketingSegmentNumber,ticketingGdPnrNumber,ticketingFirstName,ticketingMiddleName,ticketingLastName,
            ticketingAirlines,ticketingTicketNumber,ticketingSupplier,
            fareSegmentNumber,fareGdsPnrNumber,farePassengerType,fareNumberOfPassenger,fareFromDestination,fareToDestination,fareAirlines,fareCurrencyCode,fareRoe,fareSupplier,fareBaseFare,
            fareTaxes,fareCommissionPercent,fareCommissionAmount,fareServiceCharge,fareSellingPrice,fareDiscountAmount,fareLossAmount,
            fareFreezeFareLine,fareDateChangePenalityBeforeDep,fareDateChangePenalityAfterDep,fareCancellationPenaltyBeforeDep,fareCancellationPenalityAfterDep,
            promotionFirstName,promotionMiddleName,promotionLastName,promotionPromotionCode,promotionTypeOfPromotion,
            promotionDiscountAmount,promotionCurrencyCode;
    @FXML
    JFXComboBox<String> passengerType,passengerGender,ticketingPassengerType,ticketingGender,ticketingStatus,promotionTypeOfPassenger;
    @FXML
    private JFXDatePicker passengerDateOfBirth,itineraryArrivalDate,itineraryDepartureDate;
    @FXML
    private JFXTimePicker itineraryDepartureTime,itineraryArrivalTime;
    private CoreBookingEntity coreBookingEntity;
    private CoreLead coreLeadDto;
    private Logger logger=Logger.getLogger(SubBooking.class);
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initializeDefaultLayout();
        initialiseAllCheckBoxDefalutValues();
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
        passengerType.setValue(coreBookingEntity.getCoreBookingPassengerEntity().getPassengerType());
        passengerTypeOfVisa.setText(coreBookingEntity.getCoreBookingPassengerEntity().getTypeOfVisa());
        passengerGender.setValue(coreBookingEntity.getCoreBookingPassengerEntity().getGender());
        passengerDateOfBirth.setValue(Validator.getLocalDateFromString(coreBookingEntity.getCoreBookingPassengerEntity().getDateOfBirth()));
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
        itineraryPnrNumber.setText(coreBookingEntity.getCoreBookingItineraryEntity().getPnrNumber());
        itineraryOfferedAirline.setText(coreBookingEntity.getCoreBookingItineraryEntity().getOfferedAirline());
        itineraryFlightnumber.setText(coreBookingEntity.getCoreBookingItineraryEntity().getFlightNumber());
        itineraryClass.setText(coreBookingEntity.getCoreBookingItineraryEntity().getClassType());
        itineraryTravelType.setText(coreBookingEntity.getCoreBookingItineraryEntity().getTravelType());
        itineraryTo.setText(coreBookingEntity.getCoreBookingItineraryEntity().getToDestination());
        itineraryBookingStatus.setText(coreBookingEntity.getCoreBookingItineraryEntity().getBookingStatus());
        itineraryFrom.setText(coreBookingEntity.getCoreBookingItineraryEntity().getSegmentNumber());
        itineraryDepartureTime.setValue(Validator.getNotNullLocalTimeFromString(coreBookingEntity.getCoreBookingItineraryEntity().getDepartureTime()));
        itineraryArrivalTime.setValue(Validator.getNotNullLocalTimeFromString(coreBookingEntity.getCoreBookingItineraryEntity().getArrivalTime()));
        itineraryDepartureDate.setValue(Validator.getNotNullLocalDateFromString(coreBookingEntity.getCoreBookingItineraryEntity().getSegmentNumber()));
        itineraryArrivalDate.setValue(Validator.getNotNullLocalDateFromString(coreBookingEntity.getCoreBookingItineraryEntity().getArrivalDate()));
    }
    private void initialiseTicketingDetailsFromDto(){
        ticketingSegmentNumber.setText(coreBookingEntity.getCoreBookingTicketingEntity().getSegmentNumber());
        ticketingGdPnrNumber.setText(coreBookingEntity.getCoreBookingTicketingEntity().getGdPnrNumber());
        ticketingFirstName.setText(coreBookingEntity.getCoreBookingTicketingEntity().getFirstName());
        ticketingMiddleName.setText(coreBookingEntity.getCoreBookingTicketingEntity().getMiddleName());
        ticketingLastName.setText(coreBookingEntity.getCoreBookingTicketingEntity().getLastName());
        ticketingPassengerType.setValue(coreBookingEntity.getCoreBookingTicketingEntity().getPassengerType());
        ticketingGender.setValue(coreBookingEntity.getCoreBookingTicketingEntity().getGender());
        ticketingStatus.setValue(coreBookingEntity.getCoreBookingTicketingEntity().getStatus());
        ticketingAirlines.setText(coreBookingEntity.getCoreBookingTicketingEntity().getAirlines());
        ticketingSupplier.setText(coreBookingEntity.getCoreBookingTicketingEntity().getSupplier());
        ticketingTicketNumber.setText(coreBookingEntity.getCoreBookingTicketingEntity().getTicketNumber());
    }
    private void initialiseFareDetailsFromDto(){
        fareSegmentNumber.setText(coreBookingEntity.getCoreBookingFareEntity().getSegmentNumber());
        fareGdsPnrNumber.setText(coreBookingEntity.getCoreBookingFareEntity().getGdsPnrNumber());
        farePassengerType.setText(coreBookingEntity.getCoreBookingFareEntity().getPassengerType());
        fareNumberOfPassenger.setText(coreBookingEntity.getCoreBookingFareEntity().getNumberOfPassenger());
        fareFromDestination.setText(coreBookingEntity.getCoreBookingFareEntity().getFromDestination());
        fareToDestination.setText(coreBookingEntity.getCoreBookingFareEntity().getToDestination());
        fareAirlines.setText(coreBookingEntity.getCoreBookingFareEntity().getAirlines());
        fareCurrencyCode.setText(coreBookingEntity.getCoreBookingFareEntity().getCurrencyCode());
        fareRoe.setText(coreBookingEntity.getCoreBookingFareEntity().getRoe());
        fareSupplier.setText(coreBookingEntity.getCoreBookingFareEntity().getSupplier());
        fareBaseFare.setText(coreBookingEntity.getCoreBookingFareEntity().getBaseFare());
        fareTaxes.setText(coreBookingEntity.getCoreBookingFareEntity().getTaxes());
        fareCommissionPercent.setText(coreBookingEntity.getCoreBookingFareEntity().getCommissionPercent());
        fareCommissionAmount.setText(coreBookingEntity.getCoreBookingFareEntity().getCommissionAmount());
        fareServiceCharge.setText(coreBookingEntity.getCoreBookingFareEntity().getServiceCharge());
        fareSellingPrice.setText(coreBookingEntity.getCoreBookingFareEntity().getSellingPrice());
        fareDiscountAmount.setText(coreBookingEntity.getCoreBookingFareEntity().getDiscountAmount());
        fareLossAmount.setText(coreBookingEntity.getCoreBookingFareEntity().getLossAmount());
        fareFreezeFareLine.setText(coreBookingEntity.getCoreBookingFareEntity().getFreezeFareLine());
        fareDateChangePenalityBeforeDep.setText(coreBookingEntity.getCoreBookingFareEntity().getDateChangePenalitybeforeDep());
        fareDateChangePenalityAfterDep.setText(coreBookingEntity.getCoreBookingFareEntity().getDateChangePenalityAfterDep());
        fareCancellationPenaltyBeforeDep.setText(coreBookingEntity.getCoreBookingFareEntity().getCancellationPenaltyBeforeDep());
        fareCancellationPenalityAfterDep.setText(coreBookingEntity.getCoreBookingFareEntity().getCancellationPenalityAfterDep());
    }
    private void initialisePromotionDetailsFromDto(){
        promotionFirstName.setText(coreBookingEntity.getCoreBookingPromotionEntity().getFirstName());
        promotionMiddleName.setText(coreBookingEntity.getCoreBookingPromotionEntity().getMiddleName());
        promotionLastName.setText(coreBookingEntity.getCoreBookingPromotionEntity().getLastName());
        promotionTypeOfPassenger.setValue(coreBookingEntity.getCoreBookingPromotionEntity().getTypeOfPassenger());
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
        coreBookingEntity.getCoreBookingPassengerEntity().setPassengerType(passengerType.getValue());
        coreBookingEntity.getCoreBookingPassengerEntity().setTypeOfVisa(passengerTypeOfVisa.getText());
        coreBookingEntity.getCoreBookingPassengerEntity().setGender(passengerGender.getValue());
        coreBookingEntity.getCoreBookingPassengerEntity().setDateOfBirth(Validator.getStringDateValue(passengerDateOfBirth.getValue()));
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
        coreBookingEntity.getCoreBookingItineraryEntity().setFromDestination(itineraryFrom.getText());
        coreBookingEntity.getCoreBookingItineraryEntity().setTravelType(itineraryTravelType.getText());
        coreBookingEntity.getCoreBookingItineraryEntity().setBookingStatus(itineraryBookingStatus.getText());
        coreBookingEntity.getCoreBookingItineraryEntity().setDepartureDate(Validator.getStringDateValue(itineraryDepartureDate.getValue()));
        coreBookingEntity.getCoreBookingItineraryEntity().setDepartureTime(Validator.getStringTimeValue(itineraryDepartureTime.getValue()));
        coreBookingEntity.getCoreBookingItineraryEntity().setArrivalDate(Validator.getStringDateValue(itineraryArrivalDate.getValue()));
        coreBookingEntity.getCoreBookingItineraryEntity().setArrivalTime(Validator.getStringTimeValue(itineraryArrivalTime.getValue()));

    }
    private void setTicketingDataToDto(){
        if(coreBookingEntity.getCoreBookingTicketingEntity()==null)coreBookingEntity.setCoreBookingTicketingEntity(new CoreBookingTicketingEntity());

        coreBookingEntity.getCoreBookingTicketingEntity().setSupplier(ticketingSupplier.getText());
        coreBookingEntity.getCoreBookingTicketingEntity().setSegmentNumber(ticketingSegmentNumber.getText());
        coreBookingEntity.getCoreBookingTicketingEntity().setGdPnrNumber(ticketingGdPnrNumber.getText());
        coreBookingEntity.getCoreBookingTicketingEntity().setFirstName(ticketingFirstName.getText());
        coreBookingEntity.getCoreBookingTicketingEntity().setMiddleName(ticketingMiddleName.getText());
        coreBookingEntity.getCoreBookingTicketingEntity().setLastName(ticketingLastName.getText());
        coreBookingEntity.getCoreBookingTicketingEntity().setPassengerType(ticketingPassengerType.getValue());
        coreBookingEntity.getCoreBookingTicketingEntity().setGender(ticketingGender.getValue());
        coreBookingEntity.getCoreBookingTicketingEntity().setStatus(ticketingStatus.getValue());
        coreBookingEntity.getCoreBookingTicketingEntity().setAirlines(ticketingAirlines.getText());
        coreBookingEntity.getCoreBookingTicketingEntity().setTicketNumber(ticketingTicketNumber.getText());
    }
    private void setFareDataToDto(){
        if(coreBookingEntity.getCoreBookingFareEntity()==null)coreBookingEntity.setCoreBookingFareEntity(new CoreBookingFareEntity());

        coreBookingEntity.getCoreBookingFareEntity().setSegmentNumber(fareSegmentNumber.getText());
        coreBookingEntity.getCoreBookingFareEntity().setGdsPnrNumber(fareGdsPnrNumber.getText());
        coreBookingEntity.getCoreBookingFareEntity().setPassengerType(farePassengerType.getText());
        coreBookingEntity.getCoreBookingFareEntity().setNumberOfPassenger(fareNumberOfPassenger.getText());
        coreBookingEntity.getCoreBookingFareEntity().setFromDestination(fareFromDestination.getText());
        coreBookingEntity.getCoreBookingFareEntity().setToDestination(fareToDestination.getText());
        coreBookingEntity.getCoreBookingFareEntity().setAirlines(fareAirlines.getText());
        coreBookingEntity.getCoreBookingFareEntity().setCurrencyCode(fareCurrencyCode.getText());
        coreBookingEntity.getCoreBookingFareEntity().setRoe(fareRoe.getText());
        coreBookingEntity.getCoreBookingFareEntity().setSupplier(fareSupplier.getText());
        coreBookingEntity.getCoreBookingFareEntity().setBaseFare(fareBaseFare.getText());
        coreBookingEntity.getCoreBookingFareEntity().setTaxes(fareTaxes.getText());
        coreBookingEntity.getCoreBookingFareEntity().setCommissionPercent(fareCommissionPercent.getText());
        coreBookingEntity.getCoreBookingFareEntity().setCommissionAmount(fareCommissionAmount.getText());
        coreBookingEntity.getCoreBookingFareEntity().setServiceCharge(fareServiceCharge.getText());
        coreBookingEntity.getCoreBookingFareEntity().setSellingPrice(fareSellingPrice.getText());
        coreBookingEntity.getCoreBookingFareEntity().setDiscountAmount(fareDiscountAmount.getText());
        coreBookingEntity.getCoreBookingFareEntity().setLossAmount(fareLossAmount.getText());
        coreBookingEntity.getCoreBookingFareEntity().setFreezeFareLine(fareFreezeFareLine.getText());
        coreBookingEntity.getCoreBookingFareEntity().setDateChangePenalitybeforeDep(fareDateChangePenalityBeforeDep.getText());
        coreBookingEntity.getCoreBookingFareEntity().setDateChangePenalityAfterDep(fareDateChangePenalityAfterDep.getText());
        coreBookingEntity.getCoreBookingFareEntity().setCancellationPenaltyBeforeDep(fareCancellationPenaltyBeforeDep.getText());
        coreBookingEntity.getCoreBookingFareEntity().setCancellationPenalityAfterDep(fareCancellationPenalityAfterDep.getText());

    }
    private void setPromotionDataToDto(){
        if(coreBookingEntity.getCoreBookingPromotionEntity()==null)coreBookingEntity.setCoreBookingPromotionEntity(new CoreBookingPromotionEntity());

        coreBookingEntity.getCoreBookingPromotionEntity().setCurrencyCode(promotionCurrencyCode.getText());
        coreBookingEntity.getCoreBookingPromotionEntity().setFirstName(promotionFirstName.getText());
        coreBookingEntity.getCoreBookingPromotionEntity().setMiddleName(promotionMiddleName.getText());
        coreBookingEntity.getCoreBookingPromotionEntity().setLastName(promotionLastName.getText());
        coreBookingEntity.getCoreBookingPromotionEntity().setTypeOfPassenger(promotionTypeOfPassenger.getValue());
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

    private void initialiseAllCheckBoxDefalutValues(){

        passengerType.getItems().addAll(LeadsConstants.passengerType);
        passengerType.setValue(LeadsConstants.passengerType[0]);

        ticketingPassengerType.getItems().addAll(LeadsConstants.passengerType);
        ticketingPassengerType.setValue(LeadsConstants.passengerType[0]);

        passengerGender.getItems().addAll(LeadsConstants.gender);
        passengerGender.setValue(LeadsConstants.gender[0]);

        ticketingGender.getItems().addAll(LeadsConstants.gender);
        ticketingGender.setValue(LeadsConstants.gender[0]);

        ticketingStatus.getItems().addAll(LeadsConstants.ticketStatus);
        ticketingStatus.setValue(LeadsConstants.ticketStatus[0]);

        promotionTypeOfPassenger.getItems().addAll(LeadsConstants.passengerType);
        promotionTypeOfPassenger.setValue(LeadsConstants.passengerType[0]);

    }
}
