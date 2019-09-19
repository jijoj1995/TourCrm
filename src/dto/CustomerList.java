package dto;


import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class CustomerList {
    private StringProperty customerId = new SimpleStringProperty();
    private StringProperty name = new SimpleStringProperty();
    private StringProperty address = new SimpleStringProperty();
    private StringProperty email = new SimpleStringProperty();
    private StringProperty mobileNumber = new SimpleStringProperty();
    private StringProperty pending = new SimpleStringProperty();

    public CustomerList(String customerId, String name, String address, String email, String mobileNumber, String pending) {
        this.customerId = new SimpleStringProperty(customerId);
        this.name = new SimpleStringProperty(name);
        this.address = new SimpleStringProperty(address);
        this.email = new SimpleStringProperty(email);
        this.mobileNumber = new SimpleStringProperty(mobileNumber);
        this.pending = new SimpleStringProperty(pending);
    }

    public CustomerList() {
    }

    @Override
    public String toString() {
        return this.getPending();
    }

    public String getCustomerId() {
        return customerId.get();
    }

    public void setCustomerId(String customerId) {
        this.customerId.set(customerId);
    }

    public StringProperty customerIdProperty() {
        return customerId;
    }

    public String getName() {
        return name.get();
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public StringProperty nameProperty() {
        return name;
    }

    public String getAddress() {
        return address.get();
    }

    public void setAddress(String address) {
        this.address.set(address);
    }

    public StringProperty addressProperty() {
        return address;
    }

    public String getEmail() {
        return email.get();
    }

    public void setEmail(String email) {
        this.email.set(email);
    }

    public StringProperty emailProperty() {
        return email;
    }

    public String getMobileNumber() {
        return mobileNumber.get();
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber.set(mobileNumber);
    }

    public StringProperty mobileNumberProperty() {
        return mobileNumber;
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
}
