package dto;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.io.Serializable;

public class CoreUserDto implements Serializable {

    private IntegerProperty coreUserId=new SimpleIntegerProperty();
    private StringProperty userName=new SimpleStringProperty();
    private StringProperty userPassword=new SimpleStringProperty();
    private StringProperty firstName=new SimpleStringProperty();
    private StringProperty lastName=new SimpleStringProperty();
    private StringProperty emailAddress=new SimpleStringProperty();
    private CoreUserEntity coreUserEntity=new CoreUserEntity();

    public int getCoreUserId() {
        return coreUserId.get();
    }

    public IntegerProperty coreUserIdProperty() {
        return coreUserId;
    }

    public void setCoreUserId(int coreUserId) {
        this.coreUserId.set(coreUserId);
    }

    public String getUserName() {
        return userName.get();
    }

    public StringProperty userNameProperty() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName.set(userName);
    }

    public String getUserPassword() {
        return userPassword.get();
    }

    public StringProperty userPasswordProperty() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword.set(userPassword);
    }

    public String getFirstName() {
        return firstName.get();
    }

    public StringProperty firstNameProperty() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName.set(firstName);
    }

    public String getLastName() {
        return lastName.get();
    }

    public StringProperty lastNameProperty() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName.set(lastName);
    }

    public String getEmailAddress() {
        return emailAddress.get();
    }

    public StringProperty emailAddressProperty() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress.set(emailAddress);
    }

    public CoreUserEntity getCoreUserEntity() {
        return coreUserEntity;
    }

    public void setCoreUserEntity(CoreUserEntity coreUserEntity) {
        this.coreUserEntity = coreUserEntity;
    }
}
