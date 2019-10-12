package dto;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;


public class CoreLeadHotel implements Serializable {

    private IntegerProperty coreLeadHotelId=new SimpleIntegerProperty();
    private StringProperty destination=new SimpleStringProperty();
    private StringProperty checkInDate=new SimpleStringProperty();
    private StringProperty checkoutDate=new SimpleStringProperty();
    private StringProperty currencyCode=new SimpleStringProperty();
    private StringProperty hotelCategory=new SimpleStringProperty();
    private StringProperty numberOfNights=new SimpleStringProperty();
    private StringProperty numberOfAdult=new SimpleStringProperty();
    private StringProperty numberOfChild=new SimpleStringProperty();
    private StringProperty numberOfInfants=new SimpleStringProperty();
    private StringProperty totalPax=new SimpleStringProperty();
    private StringProperty RoomTariff=new SimpleStringProperty();
    private StringProperty ExtraBed=new SimpleStringProperty();
    private StringProperty totalPrice=new SimpleStringProperty();
    private StringProperty hotelPlan=new SimpleStringProperty();
    private StringProperty status=new SimpleStringProperty();

    public CoreLeadHotel(){

    }
    public CoreLeadHotel(Integer coreLeadHotelId, String destination, String checkInDate, String checkoutDate, String currencyCode, String hotelCategory, String numberOfNights, String numberOfAdult, String numberOfChild, String numberOfInfants, String totalPax, String roomTariff, String extraBed, String totalPrice, String hotelPlan, String status) {
        this.coreLeadHotelId.setValue(coreLeadHotelId);
        this.destination.setValue(destination);
        this.checkInDate.setValue(checkInDate);
        this.checkoutDate.setValue(checkoutDate);
        this.currencyCode.setValue(currencyCode);
        this.hotelCategory.setValue(hotelCategory);
        this.numberOfNights.setValue(numberOfNights);
        this.numberOfAdult.setValue(numberOfAdult);
        this.numberOfChild.setValue(numberOfChild);
        this.numberOfInfants.setValue(numberOfInfants);
        this.totalPax.setValue(totalPax);
        RoomTariff.setValue(roomTariff);
        ExtraBed.setValue(extraBed);
        this.totalPrice.setValue(totalPrice);
        this.hotelPlan.setValue(hotelPlan);
        this.status.setValue(status);
    }
    public CoreLeadHotel( String destination, String checkInDate, String checkoutDate, String currencyCode, String hotelCategory, String numberOfNights, String numberOfAdult, String numberOfChild, String numberOfInfants, String totalPax, String roomTariff, String extraBed, String totalPrice, String hotelPlan, String status) {
        this.destination.setValue(destination);
        this.checkInDate.setValue(checkInDate);
        this.checkoutDate.setValue(checkoutDate);
        this.currencyCode.setValue(currencyCode);
        this.hotelCategory.setValue(hotelCategory);
        this.numberOfNights.setValue(numberOfNights);
        this.numberOfAdult.setValue(numberOfAdult);
        this.numberOfChild.setValue(numberOfChild);
        this.numberOfInfants.setValue(numberOfInfants);
        this.totalPax.setValue(totalPax);
        RoomTariff.setValue(roomTariff);
        ExtraBed.setValue(extraBed);
        this.totalPrice.setValue(totalPrice);
        this.hotelPlan.setValue(hotelPlan);
        this.status.setValue(status);
    }

    public int getCoreLeadHotelId() {
        return coreLeadHotelId.get();
    }

    public IntegerProperty coreLeadHotelIdProperty() {
        return coreLeadHotelId;
    }

    public void setCoreLeadHotelId(int coreLeadHotelId) {
        this.coreLeadHotelId.set(coreLeadHotelId);
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
