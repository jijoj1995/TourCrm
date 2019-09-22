package dto;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.StringProperty;

import java.io.Serializable;

public class CoreLeadHolidays implements Serializable {

    private IntegerProperty coreLeadHolidaysId;
    private StringProperty fromDestination;
    private StringProperty toDestination;
    private StringProperty departureDate;
    private StringProperty returnDate;
    private StringProperty currencyCode;
    private StringProperty hotelCategory;
    private StringProperty numberOfNights;
    private StringProperty numberOfAdult;
    private StringProperty numberOfChild;
    private StringProperty numberOfInfant;
    private StringProperty adultFare;
    private StringProperty childFare;
    private StringProperty infantFare;
    private StringProperty totalPrice;
    private StringProperty travelType;
    private StringProperty status;

    public int getCoreLeadHolidaysId() {
        return coreLeadHolidaysId.get();
    }

    public IntegerProperty coreLeadHolidaysIdProperty() {
        return coreLeadHolidaysId;
    }

    public void setCoreLeadHolidaysId(int coreLeadHolidaysId) {
        this.coreLeadHolidaysId.set(coreLeadHolidaysId);
    }

    public String getFromDestination() {
        return fromDestination.get();
    }

    public StringProperty fromDestinationProperty() {
        return fromDestination;
    }

    public void setFromDestination(String fromDestination) {
        this.fromDestination.set(fromDestination);
    }

    public String getToDestination() {
        return toDestination.get();
    }

    public StringProperty toDestinationProperty() {
        return toDestination;
    }

    public void setToDestination(String toDestination) {
        this.toDestination.set(toDestination);
    }

    public String getDepartureDate() {
        return departureDate.get();
    }

    public StringProperty departureDateProperty() {
        return departureDate;
    }

    public void setDepartureDate(String departureDate) {
        this.departureDate.set(departureDate);
    }

    public String getReturnDate() {
        return returnDate.get();
    }

    public StringProperty returnDateProperty() {
        return returnDate;
    }

    public void setReturnDate(String returnDate) {
        this.returnDate.set(returnDate);
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

    public String getNumberOfInfant() {
        return numberOfInfant.get();
    }

    public StringProperty numberOfInfantProperty() {
        return numberOfInfant;
    }

    public void setNumberOfInfant(String numberOfInfant) {
        this.numberOfInfant.set(numberOfInfant);
    }

    public String getAdultFare() {
        return adultFare.get();
    }

    public StringProperty adultFareProperty() {
        return adultFare;
    }

    public void setAdultFare(String adultFare) {
        this.adultFare.set(adultFare);
    }

    public String getChildFare() {
        return childFare.get();
    }

    public StringProperty childFareProperty() {
        return childFare;
    }

    public void setChildFare(String childFare) {
        this.childFare.set(childFare);
    }

    public String getInfantFare() {
        return infantFare.get();
    }

    public StringProperty infantFareProperty() {
        return infantFare;
    }

    public void setInfantFare(String infantFare) {
        this.infantFare.set(infantFare);
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

    public String getTravelType() {
        return travelType.get();
    }

    public StringProperty travelTypeProperty() {
        return travelType;
    }

    public void setTravelType(String travelType) {
        this.travelType.set(travelType);
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
