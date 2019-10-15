package dto;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Supplier {

    private IntegerProperty supplierId = new SimpleIntegerProperty();
    private StringProperty name = new SimpleStringProperty();
    private StringProperty address = new SimpleStringProperty();
    private StringProperty mobileNumber = new SimpleStringProperty();

    public Supplier() {

    }

    public Supplier(Integer supplierId, String name, String address, String mobileNumber) {
        this.supplierId = new SimpleIntegerProperty( supplierId);
        this.name = new SimpleStringProperty( name);
        this.address = new SimpleStringProperty( address);
        this.mobileNumber =new SimpleStringProperty( mobileNumber);
    }

    public Supplier(String name, String address, String mobileNumber) {
        this.name = new SimpleStringProperty( name);
        this.address = new SimpleStringProperty( address);
        this.mobileNumber =new SimpleStringProperty( mobileNumber);
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
}
