package dto;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.StringProperty;

import java.io.Serializable;

public class CoreLeadHotel implements Serializable {

    private IntegerProperty coreLeadAirId;
    private StringProperty destination;
    private StringProperty checkInDate;
    private StringProperty checkoutDate;
    private StringProperty currencyCode;
    private StringProperty hotelCategory;
    private StringProperty numberOfNights;
    private StringProperty numberOfAdult;
    private StringProperty numberOfChild;
    private StringProperty numberOfInfants;
    private StringProperty totalPax;
    private StringProperty RoomTariff;
    private StringProperty ExtraBed;
    private StringProperty totalPrice;
    private StringProperty hotelPlan;
    private StringProperty status;


    public int getCoreLeadAirId() {
        return coreLeadAirId.get();
    }

    public IntegerProperty coreLeadAirIdProperty() {
        return coreLeadAirId;
    }

    public void setCoreLeadAirId(int coreLeadAirId) {
        this.coreLeadAirId.set(coreLeadAirId);
    }

    public String getDestination() {
        return destination.get();
    }

    public StringProperty destinationProperty() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination.set(destination);
    }

    public String getCheckInDate() {
        return checkInDate.get();
    }

    public StringProperty checkInDateProperty() {
        return checkInDate;
    }

    public void setCheckInDate(String checkInDate) {
        this.checkInDate.set(checkInDate);
    }

    public String getCheckoutDate() {
        return checkoutDate.get();
    }

    public StringProperty checkoutDateProperty() {
        return checkoutDate;
    }

    public void setCheckoutDate(String checkoutDate) {
        this.checkoutDate.set(checkoutDate);
    }

    public String getCurrencyCode() {
        return currencyCode.get();
    }

    public StringProperty currencyCodeProperty() {
        return currencyCode;
    }

    public void setCurrencyCode(String currencyCode) {
        this.currencyCode.set(currencyCode);
    }

    public String getHotelCategory() {
        return hotelCategory.get();
    }

    public StringProperty hotelCategoryProperty() {
        return hotelCategory;
    }

    public void setHotelCategory(String hotelCategory) {
        this.hotelCategory.set(hotelCategory);
    }

    public String getNumberOfNights() {
        return numberOfNights.get();
    }

    public StringProperty numberOfNightsProperty() {
        return numberOfNights;
    }

    public void setNumberOfNights(String numberOfNights) {
        this.numberOfNights.set(numberOfNights);
    }

    public String getNumberOfAdult() {
        return numberOfAdult.get();
    }

    public StringProperty numberOfAdultProperty() {
        return numberOfAdult;
    }

    public void setNumberOfAdult(String numberOfAdult) {
        this.numberOfAdult.set(numberOfAdult);
    }

    public String getNumberOfChild() {
        return numberOfChild.get();
    }

    public StringProperty numberOfChildProperty() {
        return numberOfChild;
    }

    public void setNumberOfChild(String numberOfChild) {
        this.numberOfChild.set(numberOfChild);
    }

    public String getNumberOfInfants() {
        return numberOfInfants.get();
    }

    public StringProperty numberOfInfantsProperty() {
        return numberOfInfants;
    }

    public void setNumberOfInfants(String numberOfInfants) {
        this.numberOfInfants.set(numberOfInfants);
    }

    public String getTotalPax() {
        return totalPax.get();
    }

    public StringProperty totalPaxProperty() {
        return totalPax;
    }

    public void setTotalPax(String totalPax) {
        this.totalPax.set(totalPax);
    }

    public String getRoomTariff() {
        return RoomTariff.get();
    }

    public StringProperty roomTariffProperty() {
        return RoomTariff;
    }

    public void setRoomTariff(String roomTariff) {
        this.RoomTariff.set(roomTariff);
    }

    public String getExtraBed() {
        return ExtraBed.get();
    }

    public StringProperty extraBedProperty() {
        return ExtraBed;
    }

    public void setExtraBed(String extraBed) {
        this.ExtraBed.set(extraBed);
    }

    public String getTotalPrice() {
        return totalPrice.get();
    }

    public StringProperty totalPriceProperty() {
        return totalPrice;
    }

    public void setTotalPrice(String totalPrice) {
        this.totalPrice.set(totalPrice);
    }

    public String getHotelPlan() {
        return hotelPlan.get();
    }

    public StringProperty hotelPlanProperty() {
        return hotelPlan;
    }

    public void setHotelPlan(String hotelPlan) {
        this.hotelPlan.set(hotelPlan);
    }

    public String getStatus() {
        return status.get();
    }

    public StringProperty statusProperty() {
        return status;
    }

    public void setStatus(String status) {
        this.status.set(status);
    }
}
