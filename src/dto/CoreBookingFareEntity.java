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
    private String segmentNumber;
    private String gdsPnrNumber;
    private String passengerType;
    private String numberOfPassenger;
    private String fromDestination;
    private String toDestination;
    private String airlines;
    private String currencyCode;
    private String roe;
    private String supplier;
    private String baseFare;
    private String taxes;
    private String commissionPercent;
    private String commissionAmount;
    private String serviceCharge;
    private String sellingPrice;
    private String discountAmount;
    private String lossAmount;
    private String freezeFareLine;
    private String dateChangePenalitybeforeDep;
    private String dateChangePenalityAfterDep;
    private String cancellationPenaltyBeforeDep;
    private String cancellationPenalityAfterDep;

    public Integer getCoreBookingFareId() {
        return coreBookingFareId;
    }

    public void setCoreBookingFareId(Integer coreBookingFareId) {
        this.coreBookingFareId = coreBookingFareId;
    }

    public String getSegmentNumber() {
        return segmentNumber;
    }

    public void setSegmentNumber(String segmentNumber) {
        this.segmentNumber = segmentNumber;
    }

    public String getGdsPnrNumber() {
        return gdsPnrNumber;
    }

    public void setGdsPnrNumber(String gdsPnrNumber) {
        this.gdsPnrNumber = gdsPnrNumber;
    }

    public String getPassengerType() {
        return passengerType;
    }

    public void setPassengerType(String passengerType) {
        this.passengerType = passengerType;
    }

    public String getNumberOfPassenger() {
        return numberOfPassenger;
    }

    public void setNumberOfPassenger(String numberOfPassenger) {
        this.numberOfPassenger = numberOfPassenger;
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

    public String getAirlines() {
        return airlines;
    }

    public void setAirlines(String airlines) {
        this.airlines = airlines;
    }

    public String getCurrencyCode() {
        return currencyCode;
    }

    public void setCurrencyCode(String currencyCode) {
        this.currencyCode = currencyCode;
    }

    public String getRoe() {
        return roe;
    }

    public void setRoe(String roe) {
        this.roe = roe;
    }

    public String getSupplier() {
        return supplier;
    }

    public void setSupplier(String supplier) {
        this.supplier = supplier;
    }

    public String getBaseFare() {
        return baseFare;
    }

    public void setBaseFare(String baseFare) {
        this.baseFare = baseFare;
    }

    public String getTaxes() {
        return taxes;
    }

    public void setTaxes(String taxes) {
        this.taxes = taxes;
    }

    public String getCommissionPercent() {
        return commissionPercent;
    }

    public void setCommissionPercent(String commissionPercent) {
        this.commissionPercent = commissionPercent;
    }

    public String getCommissionAmount() {
        return commissionAmount;
    }

    public void setCommissionAmount(String commissionAmount) {
        this.commissionAmount = commissionAmount;
    }

    public String getServiceCharge() {
        return serviceCharge;
    }

    public void setServiceCharge(String serviceCharge) {
        this.serviceCharge = serviceCharge;
    }

    public String getSellingPrice() {
        return sellingPrice;
    }

    public void setSellingPrice(String sellingPrice) {
        this.sellingPrice = sellingPrice;
    }

    public String getDiscountAmount() {
        return discountAmount;
    }

    public void setDiscountAmount(String discountAmount) {
        this.discountAmount = discountAmount;
    }

    public String getLossAmount() {
        return lossAmount;
    }

    public void setLossAmount(String lossAmount) {
        this.lossAmount = lossAmount;
    }

    public String getFreezeFareLine() {
        return freezeFareLine;
    }

    public void setFreezeFareLine(String freezeFareLine) {
        this.freezeFareLine = freezeFareLine;
    }

    public String getDateChangePenalitybeforeDep() {
        return dateChangePenalitybeforeDep;
    }

    public void setDateChangePenalitybeforeDep(String dateChangePenalitybeforeDep) {
        this.dateChangePenalitybeforeDep = dateChangePenalitybeforeDep;
    }

    public String getDateChangePenalityAfterDep() {
        return dateChangePenalityAfterDep;
    }

    public void setDateChangePenalityAfterDep(String dateChangePenalityAfterDep) {
        this.dateChangePenalityAfterDep = dateChangePenalityAfterDep;
    }

    public String getCancellationPenaltyBeforeDep() {
        return cancellationPenaltyBeforeDep;
    }

    public void setCancellationPenaltyBeforeDep(String cancellationPenaltyBeforeDep) {
        this.cancellationPenaltyBeforeDep = cancellationPenaltyBeforeDep;
    }

    public String getCancellationPenalityAfterDep() {
        return cancellationPenalityAfterDep;
    }

    public void setCancellationPenalityAfterDep(String cancellationPenalityAfterDep) {
        this.cancellationPenalityAfterDep = cancellationPenalityAfterDep;
    }
}
