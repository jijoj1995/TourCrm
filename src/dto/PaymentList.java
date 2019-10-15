package dto;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class PaymentList {
    StringProperty paymentId = new SimpleStringProperty();
    StringProperty TotalPrice = new SimpleStringProperty();
    StringProperty PendingAmount = new SimpleStringProperty();
    StringProperty PayedAmount = new SimpleStringProperty();
    StringProperty CreatedOn = new SimpleStringProperty();
    StringProperty billingDate = new SimpleStringProperty();

    public PaymentList(String paymentId, String totalPrice, String pendingAmount, String payedAmount, String createdOn,String billingDate) {
        this.paymentId = new SimpleStringProperty(paymentId);
        TotalPrice = new SimpleStringProperty(totalPrice);
        PendingAmount = new SimpleStringProperty(pendingAmount);
        PayedAmount = new SimpleStringProperty(payedAmount);
        CreatedOn = new SimpleStringProperty(createdOn);
        this.billingDate=new SimpleStringProperty(billingDate);
    }
    public PaymentList(){

    }

    public String getBillingDate() {
        return billingDate.get();
    }

    public StringProperty billingDateProperty() {
        return billingDate;
    }

    public void setBillingDate(String billingDate) {
        this.billingDate.set(billingDate);
    }

    public String getPaymentId() {
        return paymentId.get();
    }

    public void setPaymentId(String paymentId) {
        this.paymentId.set(paymentId);
    }

    public StringProperty paymentIdProperty() {
        return paymentId;
    }

    public String getTotalPrice() {
        return TotalPrice.get();
    }

    public void setTotalPrice(String totalPrice) {
        this.TotalPrice.set(totalPrice);
    }

    public StringProperty totalPriceProperty() {
        return TotalPrice;
    }

    public String getPendingAmount() {
        return PendingAmount.get();
    }

    public void setPendingAmount(String pendingAmount) {
        this.PendingAmount.set(pendingAmount);
    }

    public StringProperty pendingAmountProperty() {
        return PendingAmount;
    }

    public String getPayedAmount() {
        return PayedAmount.get();
    }

    public void setPayedAmount(String payedAmount) {
        this.PayedAmount.set(payedAmount);
    }

    public StringProperty payedAmountProperty() {
        return PayedAmount;
    }

    public String getCreatedOn() {
        return CreatedOn.get();
    }

    public void setCreatedOn(String createdOn) {
        this.CreatedOn.set(createdOn);
    }

    public StringProperty createdOnProperty() {
        return CreatedOn;
    }
}
