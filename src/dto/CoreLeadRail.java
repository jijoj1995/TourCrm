package dto;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.StringProperty;

import java.io.Serializable;

public class CoreLeadRail implements Serializable {

    private IntegerProperty coreLeadRailId;
    private StringProperty departureCity;
    private StringProperty arrivalCity;
    private StringProperty departureDate;
    private StringProperty trainNumber;
    private StringProperty numberofAdult;
    private StringProperty numberofChild;
    private StringProperty numberOfInfant;
    private StringProperty totalPax;
    private StringProperty adultFare;
    private StringProperty childFare;
    private StringProperty totalFare;
    private StringProperty classOfTravel;
    private StringProperty status;

    public int getCoreLeadRailId() {
        return coreLeadRailId.get();
    }

    public IntegerProperty coreLeadRailIdProperty() {
        return coreLeadRailId;
    }

    public void setCoreLeadRailId(int coreLeadRailId) {
        this.coreLeadRailId.set(coreLeadRailId);
    }

    public String getDepartureCity() {
        return departureCity.get();
    }

    public StringProperty departureCityProperty() {
        return departureCity;
    }

    public void setDepartureCity(String departureCity) {
        this.departureCity.set(departureCity);
    }

    public String getArrivalCity() {
        return arrivalCity.get();
    }

    public StringProperty arrivalCityProperty() {
        return arrivalCity;
    }

    public void setArrivalCity(String arrivalCity) {
        this.arrivalCity.set(arrivalCity);
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

    public String getTrainNumber() {
        return trainNumber.get();
    }

    public StringProperty trainNumberProperty() {
        return trainNumber;
    }

    public void setTrainNumber(String trainNumber) {
        this.trainNumber.set(trainNumber);
    }

    public String getNumberofAdult() {
        return numberofAdult.get();
    }

    public StringProperty numberofAdultProperty() {
        return numberofAdult;
    }

    public void setNumberofAdult(String numberofAdult) {
        this.numberofAdult.set(numberofAdult);
    }

    public String getNumberofChild() {
        return numberofChild.get();
    }

    public StringProperty numberofChildProperty() {
        return numberofChild;
    }

    public void setNumberofChild(String numberofChild) {
        this.numberofChild.set(numberofChild);
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

    public String getTotalFare() {
        return totalFare.get();
    }

    public StringProperty totalFareProperty() {
        return totalFare;
    }

    public void setTotalFare(String totalFare) {
        this.totalFare.set(totalFare);
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
