package dto;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class SupplierPaymentHistoryList implements Comparable<SupplierPaymentHistoryList> {
    IntegerProperty paymentId = new SimpleIntegerProperty();
    IntegerProperty totalPrice = new SimpleIntegerProperty();
    IntegerProperty pendingAmount = new SimpleIntegerProperty();
    IntegerProperty payedAmount = new SimpleIntegerProperty();
    IntegerProperty supplierId = new SimpleIntegerProperty();
    StringProperty supplierName = new SimpleStringProperty();
    StringProperty createdOn = new SimpleStringProperty();
    StringProperty billingDate = new SimpleStringProperty();

    public SupplierPaymentHistoryList() {

    }

    public SupplierPaymentHistoryList(int paymentId, int totalPrice, int pendingAmount, int payedAmount, int supplierId, String supplierName, String createdOn,
                              String billingDate) {
        this.paymentId.set(paymentId);
        this.totalPrice.set( totalPrice);
        this.pendingAmount.set( pendingAmount);
        this.payedAmount.set( payedAmount);
        this.supplierId.set(supplierId);
        this.supplierName.setValue(supplierName);
        this.createdOn.set( createdOn);
        this.billingDate.set( billingDate);
    }

    public int getPaymentId() {
        return paymentId.get();
    }

    public IntegerProperty paymentIdProperty() {
        return paymentId;
    }

    public void setPaymentId(int paymentId) {
        this.paymentId.set(paymentId);
    }

    public int getTotalPrice() {
        return totalPrice.get();
    }

    public IntegerProperty totalPriceProperty() {
        return totalPrice;
    }

    public void setTotalPrice(int totalPrice) {
        this.totalPrice.set(totalPrice);
    }

    public int getPendingAmount() {
        return pendingAmount.get();
    }

    public IntegerProperty pendingAmountProperty() {
        return pendingAmount;
    }

    public void setPendingAmount(int pendingAmount) {
        this.pendingAmount.set(pendingAmount);
    }

    public int getPayedAmount() {
        return payedAmount.get();
    }

    public IntegerProperty payedAmountProperty() {
        return payedAmount;
    }

    public void setPayedAmount(int payedAmount) {
        this.payedAmount.set(payedAmount);
    }

    public int getSupplierId() {
        return supplierId.get();
    }

    public IntegerProperty supplierIdProperty() {
        return supplierId;
    }

    public void setSupplierId(int supplierId) {
        this.supplierId.set(supplierId);
    }

    public String getSupplierName() {
        return supplierName.get();
    }

    public StringProperty supplierNameProperty() {
        return supplierName;
    }

    public void setSupplierName(String supplierName) {
        this.supplierName.set(supplierName);
    }

    public String getCreatedOn() {
        return createdOn.get();
    }

    public StringProperty createdOnProperty() {
        return createdOn;
    }

    public void setCreatedOn(String createdOn) {
        this.createdOn.set(createdOn);
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


    @Override
    public int compareTo( SupplierPaymentHistoryList o2) {
        //To change body of generated methods, choose Tools | Templates.
        if(getPaymentId()<o2.getPaymentId()){
            return -1;
        }else if((getPaymentId()>o2.getPaymentId())){
            return 1;
        }else if((getPendingAmount()>o2.getPendingAmount())){
            return 1;
        }else{  if((getPendingAmount()<o2.getPendingAmount())){
            return -1;
        }
        }



        return 0;
    }
}
