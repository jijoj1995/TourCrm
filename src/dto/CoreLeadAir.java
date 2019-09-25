package dto;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class CoreLeadAir {

    private IntegerProperty coreLeadAirId=new SimpleIntegerProperty();
    private StringProperty fromDestination=new SimpleStringProperty();
    private StringProperty toDestination=new SimpleStringProperty();
    private StringProperty departureDate=new SimpleStringProperty();
    private StringProperty returnDate=new SimpleStringProperty();
    private StringProperty airlinesOffered=new SimpleStringProperty();
    private StringProperty currencyCode=new SimpleStringProperty();
    private StringProperty numberOfAdult=new SimpleStringProperty();
    private StringProperty numberOfChild=new SimpleStringProperty();
    private StringProperty numberOfInfant=new SimpleStringProperty();
    private StringProperty totalPax=new SimpleStringProperty();
    private StringProperty adultFare=new SimpleStringProperty();
    private StringProperty childFare=new SimpleStringProperty();
    private StringProperty infantFare=new SimpleStringProperty();
    private StringProperty totalPrice=new SimpleStringProperty();
    private StringProperty typeOfTravel=new SimpleStringProperty();
    private StringProperty classOfTravel=new SimpleStringProperty();
    private StringProperty status=new SimpleStringProperty();

    public int getCoreLeadAirId() {
        return coreLeadAirId.get();
    }

    public IntegerProperty coreLeadAirIdProperty() {
        return coreLeadAirId;
    }

    public void setCoreLeadAirId(int coreLeadAirId) {
        this.coreLeadAirId.set(coreLeadAirId);
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

    public String getAirlinesOffered() {
        return airlinesOffered.get();
    }

    public StringProperty airlinesOfferedProperty() {
        return airlinesOffered;
    }

    public void setAirlinesOffered(String airlinesOffered) {
        this.airlinesOffered.set(airlinesOffered);
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

    public String getTotalPax() {
        return totalPax.get();
    }

    public StringProperty totalPaxProperty() {
        return totalPax;
    }

    public void setTotalPax(String totalPax) {
        this.totalPax.set(totalPax);
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

    public String getTypeOfTravel() {
        return typeOfTravel.get();
    }

    public StringProperty typeOfTravelProperty() {
        return typeOfTravel;
    }

    public void setTypeOfTravel(String typeOfTravel) {
        this.typeOfTravel.set(typeOfTravel);
    }

    public String getClassOfTravel() {
        return classOfTravel.get();
    }

    public StringProperty classOfTravelProperty() {
        return classOfTravel;
    }

    public void setClassOfTravel(String classOfTravel) {
        this.classOfTravel.set(classOfTravel);
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
