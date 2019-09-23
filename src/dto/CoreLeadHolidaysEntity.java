package dto;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table( name = "core_lead_holidays" )
public class CoreLeadHolidaysEntity implements Serializable {

    @Id
    @GeneratedValue(generator = "increment")
    @GenericGenerator(name = "increment", strategy = "increment")
    @Column(name = "core_lead_holidays_id")
    private Integer coreLeadHolidaysId;
    private String fromDestination;
    private String toDestination;
    private String departureDate;
    private String returnDate;
    private String currencyCode;
    private String hotelCategory;
    private String numberOfNights;
    private String numberOfAdult;
    private String numberOfChild;
    private String numberOfInfant;
    private String adultFare;
    private String childFare;
    private String infantFare;
    private String totalPrice;
    private String travelType;
    private String status;

    public Integer getCoreLeadHolidaysId() {
        return coreLeadHolidaysId;
    }

    public void setCoreLeadHolidaysId(Integer coreLeadHolidaysId) {
        this.coreLeadHolidaysId = coreLeadHolidaysId;
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

    public String getCurrencyCode() {
        return currencyCode;
    }

    public void setCurrencyCode(String currencyCode) {
        this.currencyCode = currencyCode;
    }

    public String getHotelCategory() {
        return hotelCategory;
    }

    public void setHotelCategory(String hotelCategory) {
        this.hotelCategory = hotelCategory;
    }

    public String getNumberOfNights() {
        return numberOfNights;
    }

    public void setNumberOfNights(String numberOfNights) {
        this.numberOfNights = numberOfNights;
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

    public String getTravelType() {
        return travelType;
    }

    public void setTravelType(String travelType) {
        this.travelType = travelType;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
