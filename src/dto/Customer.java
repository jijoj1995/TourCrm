package dto;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

@SuppressWarnings("unused")
public class Customer {
    private StringProperty customerId = new SimpleStringProperty();
    private  StringProperty name=new SimpleStringProperty();
    private StringProperty address=new SimpleStringProperty();
    private StringProperty email = new SimpleStringProperty();
    private StringProperty mobileNumber=new SimpleStringProperty();
    public Customer(){
    }

    public Customer(String name, String address, String mobileNumber, String email) {
        this.name = new SimpleStringProperty(name);
        this.address = new SimpleStringProperty(address);
        this.mobileNumber = new SimpleStringProperty(mobileNumber);
        this.email = new SimpleStringProperty(email);
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

    public StringProperty nameProperty() {
        return name;
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public String getAddress() {
        return address.get();
    }

    public StringProperty addressProperty() {
        return address;
    }

    public void setAddress(String address) {
        this.address.set(address);
    }

    public String getMobileNumber() {
        return mobileNumber.get();
    }

    public StringProperty mobileNumberProperty() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber.set(mobileNumber);
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

}
