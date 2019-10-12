package dto;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;

public class CoreLeadRail implements Serializable {

    @Id
    @GeneratedValue(generator = "increment")
    @GenericGenerator(name = "increment", strategy = "increment")
    @Column(name = "core_lead_rail_id")
    private IntegerProperty coreLeadRailId=new SimpleIntegerProperty();
    private StringProperty departureCity=new SimpleStringProperty();
    private StringProperty arrivalCity=new SimpleStringProperty();
    private StringProperty departureDate=new SimpleStringProperty();
    private StringProperty trainNumber=new SimpleStringProperty();
    private StringProperty numberOfAdult=new SimpleStringProperty();
    private StringProperty numberOfChild=new SimpleStringProperty();
    private StringProperty numberOfInfant=new SimpleStringProperty();
    private StringProperty totalPax=new SimpleStringProperty();
    private StringProperty adultFare=new SimpleStringProperty();
    private StringProperty childFare=new SimpleStringProperty();
    private StringProperty totalFare=new SimpleStringProperty();
    private StringProperty classOfTravel=new SimpleStringProperty();
    private StringProperty status=new SimpleStringProperty();

    public CoreLeadRail(){

    }
    public CoreLeadRail(Integer coreLeadRailId, String departureCity, String arrivalCity, String departureDate, String trainNumber, String numberOfAdult, String numberOfChild, String numberOfInfant, String totalPax, String adultFare, String childFare, String totalFare, String classOfTravel, String status) {
        this.coreLeadRailId.setValue(coreLeadRailId);
        this.departureCity.setValue(departureCity);
        this.arrivalCity.setValue(arrivalCity);
        this.departureDate.setValue(departureDate);
        this.trainNumber.setValue(trainNumber);
        this.numberOfAdult.setValue(numberOfAdult);
        this.numberOfChild.setValue(numberOfChild);
        this.numberOfInfant.setValue(numberOfInfant);
        this.totalPax.setValue(totalPax);
        this.adultFare.setValue(adultFare);
        this.childFare.setValue(childFare);
        this.totalFare.setValue(totalFare);
        this.classOfTravel.setValue(classOfTravel);
        this.status.setValue(status);
    }

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
