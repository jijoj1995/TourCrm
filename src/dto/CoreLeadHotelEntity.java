package dto;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table( name = "core_lead_hotel" )
public class CoreLeadHotelEntity implements Serializable {

    @Id
    @GeneratedValue(generator = "increment")
    @GenericGenerator(name = "increment", strategy = "increment")
    @Column(name = "core_lead_hotel_id")
    private Integer coreLeadHotelId;
    private String destination;
    private String checkInDate;
    private String checkoutDate;
    private String currencyCode;
    private String hotelCategory;
    private String numberOfNights;
    private String numberOfAdult;
    private String numberOfChild;
    private String numberOfInfants;
    private String totalPax;
    private String RoomTariff;
    private String ExtraBed;
    private String totalPrice;
    private String hotelPlan;
    private String status;

    public Integer getCoreLeadHotelId() {
        return coreLeadHotelId;
    }

    public void setCoreLeadHotelId(Integer coreLeadHotelId) {
        this.coreLeadHotelId = coreLeadHotelId;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public String getCheckInDate() {
        return checkInDate;
    }

    public void setCheckInDate(String checkInDate) {
        this.checkInDate = checkInDate;
    }

    public String getCheckoutDate() {
        return checkoutDate;
    }

    public void setCheckoutDate(String checkoutDate) {
        this.checkoutDate = checkoutDate;
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

    public String getRoomTariff() {
        return RoomTariff;
    }

    public void setRoomTariff(String roomTariff) {
        RoomTariff = roomTariff;
    }

    public String getExtraBed() {
        return ExtraBed;
    }

    public void setExtraBed(String extraBed) {
        ExtraBed = extraBed;
    }

    public String getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(String totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getHotelPlan() {
        return hotelPlan;
    }

    public void setHotelPlan(String hotelPlan) {
        this.hotelPlan = hotelPlan;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
