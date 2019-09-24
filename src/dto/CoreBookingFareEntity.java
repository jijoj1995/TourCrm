package dto;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table( name = "core_booking_fare" )
public class CoreBookingFareEntity implements Serializable {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @PrimaryKeyJoinColumn
    @Column(name = "core_booking_fare_id")
    private Integer coreBookingFareId;
    private String departureCity;
    private Integer arrivalCity;
    private String dateOfDeparture;
    private String trainNumber;
    private String numberOfAdult;
    private String numberOfChild;
    private String numberOfInfants;
    private String totalPax;
    private String adultFare;
    private String childFare;
    private String totalFare;
    private String classOfTravel;
    private String status;

    public Integer getCoreBookingFareId() {
        return coreBookingFareId;
    }

    public void setCoreBookingFareId(Integer coreBookingFareId) {
        this.coreBookingFareId = coreBookingFareId;
    }

    public String getDepartureCity() {
        return departureCity;
    }

    public void setDepartureCity(String departureCity) {
        this.departureCity = departureCity;
    }

    public Integer getArrivalCity() {
        return arrivalCity;
    }

    public void setArrivalCity(Integer arrivalCity) {
        this.arrivalCity = arrivalCity;
    }

    public String getDateOfDeparture() {
        return dateOfDeparture;
    }

    public void setDateOfDeparture(String dateOfDeparture) {
        this.dateOfDeparture = dateOfDeparture;
    }

    public String getTrainNumber() {
        return trainNumber;
    }

    public void setTrainNumber(String trainNumber) {
        this.trainNumber = trainNumber;
    }

    public String getNumberOfAdult() {
        return numberOfAdult;
    }

    public void setNumberOfAdult(String numberOfAdult) {
        this.numberOfAdult = numberOfAdult;
    }

    public String getNumberOfChild() {
        return numberOfChild;
    }

    public void setNumberOfChild(String numberOfChild) {
        this.numberOfChild = numberOfChild;
    }

    public String getNumberOfInfants() {
        return numberOfInfants;
    }

    public void setNumberOfInfants(String numberOfInfants) {
        this.numberOfInfants = numberOfInfants;
    }

    public String getTotalPax() {
        return totalPax;
    }

    public void setTotalPax(String totalPax) {
        this.totalPax = totalPax;
    }

    public String getAdultFare() {
        return adultFare;
    }

    public void setAdultFare(String adultFare) {
        this.adultFare = adultFare;
    }

    public String getChildFare() {
        return childFare;
    }

    public void setChildFare(String childFare) {
        this.childFare = childFare;
    }

    public String getTotalFare() {
        return totalFare;
    }

    public void setTotalFare(String totalFare) {
        this.totalFare = totalFare;
    }

    public String getClassOfTravel() {
        return classOfTravel;
    }

    public void setClassOfTravel(String classOfTravel) {
        this.classOfTravel = classOfTravel;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
