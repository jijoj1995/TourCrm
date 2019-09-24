package dto;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table( name = "core_booking_pricing" )
public class CoreBookingPricingEntity implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "core_booking_pricing_id")
    private Integer coreBookingPricingId;
    private String sellingPrice;
    private String totalGpm;
    private String totalCost;
    private String discount;
    private String serviceCharge;
    private String totalReceivable;
    private String commission;
    private String couponAmount;
    private String lossAmount;
    private String totalCommitted;
    private String amountDue;
    private String totalReceived;
    private String surcharges;
    private String currencyCode;

    public Integer getCoreBookingPricingId() {
        return coreBookingPricingId;
    }

    public void setCoreBookingPricingId(Integer coreBookingPricingId) {
        this.coreBookingPricingId = coreBookingPricingId;
    }

    public String getSellingPrice() {
        return sellingPrice;
    }

    public void setSellingPrice(String sellingPrice) {
        this.sellingPrice = sellingPrice;
    }

    public String getTotalGpm() {
        return totalGpm;
    }

    public void setTotalGpm(String totalGpm) {
        this.totalGpm = totalGpm;
    }

    public String getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(String totalCost) {
        this.totalCost = totalCost;
    }

    public String getDiscount() {
        return discount;
    }

    public void setDiscount(String discount) {
        this.discount = discount;
    }

    public String getServiceCharge() {
        return serviceCharge;
    }

    public void setServiceCharge(String serviceCharge) {
        this.serviceCharge = serviceCharge;
    }

    public String getTotalReceivable() {
        return totalReceivable;
    }

    public void setTotalReceivable(String totalReceivable) {
        this.totalReceivable = totalReceivable;
    }

    public String getCommission() {
        return commission;
    }

    public void setCommission(String commission) {
        this.commission = commission;
    }

    public String getCouponAmount() {
        return couponAmount;
    }

    public void setCouponAmount(String couponAmount) {
        this.couponAmount = couponAmount;
    }

    public String getLossAmount() {
        return lossAmount;
    }

    public void setLossAmount(String lossAmount) {
        this.lossAmount = lossAmount;
    }

    public String getTotalCommitted() {
        return totalCommitted;
    }

    public void setTotalCommitted(String totalCommitted) {
        this.totalCommitted = totalCommitted;
    }

    public String getAmountDue() {
        return amountDue;
    }

    public void setAmountDue(String amountDue) {
        this.amountDue = amountDue;
    }

    public String getTotalReceived() {
        return totalReceived;
    }

    public void setTotalReceived(String totalReceived) {
        this.totalReceived = totalReceived;
    }

    public String getSurcharges() {
        return surcharges;
    }

    public void setSurcharges(String surcharges) {
        this.surcharges = surcharges;
    }

    public String getCurrencyCode() {
        return currencyCode;
    }

    public void setCurrencyCode(String currencyCode) {
        this.currencyCode = currencyCode;
    }
}
