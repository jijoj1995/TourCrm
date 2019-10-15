package dto;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Payment {
    private IntegerProperty payment_id = new SimpleIntegerProperty();
    private IntegerProperty customer_id = new SimpleIntegerProperty();
    private StringProperty price = new SimpleStringProperty();
    private StringProperty payed = new SimpleStringProperty();
    private StringProperty pending = new SimpleStringProperty();
    private StringProperty created_on = new SimpleStringProperty();
    private StringProperty billingDate = new SimpleStringProperty();

    public Payment(Integer payment_id, Integer customer_id, String price, String payed, String pending, String created_on) {
        this.payment_id = new SimpleIntegerProperty(payment_id);
        this.customer_id = new SimpleIntegerProperty(customer_id);
        this.price = new SimpleStringProperty(price);
        this.payed = new SimpleStringProperty(payed);
        this.pending = new SimpleStringProperty(pending);
        this.created_on = new SimpleStringProperty(created_on);
    }
    public Payment(Integer payment_id, Integer customer_id, String price, String payed, String pending, String created_on,String billingDate) {
        this.payment_id = new SimpleIntegerProperty(payment_id);
        this.customer_id = new SimpleIntegerProperty(customer_id);
        this.price = new SimpleStringProperty(price);
        this.payed = new SimpleStringProperty(payed);
        this.pending = new SimpleStringProperty(pending);
        this.created_on = new SimpleStringProperty(created_on);
        this.billingDate = new SimpleStringProperty(billingDate);
    }
    public Payment(){

    }

    public int getPayment_id() {
        return payment_id.get();
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

    public void setPayment_id(int payment_id) {
        this.payment_id.set(payment_id);
    }

    public IntegerProperty payment_idProperty() {
        return payment_id;
    }

    public int getCustomer_id() {
        return customer_id.get();
    }

    public void setCustomer_id(int customer_id) {
        this.customer_id.set(customer_id);
    }

    public IntegerProperty customer_idProperty() {
        return customer_id;
    }

    public String getPrice() {
        return price.get();
    }

    public void setPrice(String price) {
        this.price.set(price);
    }

    public StringProperty priceProperty() {
        return price;
    }

    public String getPayed() {
        return payed.get();
    }

    public void setPayed(String payed) {
        this.payed.set(payed);
    }

    public StringProperty payedProperty() {
        return payed;
    }

    public String getPending() {
        return pending.get();
    }

    public void setPending(String pending) {
        this.pending.set(pending);
    }

    public StringProperty pendingProperty() {
        return pending;
    }

    public String getCreated_on() {
        return created_on.get();
    }

    public void setCreated_on(String created_on) {
        this.created_on.set(created_on);
    }

    public StringProperty created_onProperty() {
        return created_on;
    }
}
