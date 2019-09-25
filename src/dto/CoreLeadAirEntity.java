package dto;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table( name = "core_lead_air" )
public class CoreLeadAirEntity implements Serializable {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name = "core_lead_air_id")
    private Integer coreLeadAirId;
    private String fromDestination;
    private String toDestination;
    private String departureDate;
    private String returnDate;
    private String airlinesOffered;
    private String currencyCode;
    private String numberOfAdult;
    private String numberOfChild;
    private String numberOfInfant;
    private String totalPax;
    private String adultFare;
    private String childFare;
    private String infantFare;
    private String totalPrice;
    private String typeOfTravel;
    private String classOfTravel;
    private String status;

    public Integer getCoreLeadAirId() {
        return coreLeadAirId;
    }

    public void setCoreLeadAirId(Integer coreLeadAirId) {
        this.coreLeadAirId = coreLeadAirId;
    }

    public String getFromDestination() {
        return fromDestination;
    }

    public void setFromDestination(String fromDestination) {
        this.fromDestination = fromDestination;
    }

    public String getToDestination() {
        return toDestination;
    }

    public void setToDestination(String toDestination) {
        this.toDestination = toDestination;
    }

    public String getDepartureDate() {
        return departureDate;
    }

    public void setDepartureDate(String departureDate) {
        this.departureDate = departureDate;
    }

    public String getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(String returnDate) {
        this.returnDate = returnDate;
    }

    public String getAirlinesOffered() {
        return airlinesOffered;
    }

    public void setAirlinesOffered(String airlinesOffered) {
        this.airlinesOffered = airlinesOffered;
    }

    public String getCurrencyCode() {
        return currencyCode;
    }

    public void setCurrencyCode(String currencyCode) {
        this.currencyCode = currencyCode;
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

    public String getNumberOfInfant() {
        return numberOfInfant;
    }

    public void setNumberOfInfant(String numberOfInfant) {
        this.numberOfInfant = numberOfInfant;
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

    public String getInfantFare() {
        return infantFare;
    }

    public void setInfantFare(String infantFare) {
        this.infantFare = infantFare;
    }

    public String getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(String totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getTypeOfTravel() {
        return typeOfTravel;
    }

    public void setTypeOfTravel(String typeOfTravel) {
        this.typeOfTravel = typeOfTravel;
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
