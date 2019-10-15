package dto;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class PassengerTableList {

    Integer index;
    private IntegerProperty coreBookingPassengerId=new SimpleIntegerProperty();
    private StringProperty segmentNumber=new SimpleStringProperty();
    private StringProperty gdsPnrNumber=new SimpleStringProperty();
    private StringProperty firstName=new SimpleStringProperty();
    private StringProperty middleName=new SimpleStringProperty();
    private StringProperty lastName=new SimpleStringProperty();
    private StringProperty passengerType=new SimpleStringProperty();
    private StringProperty gender=new SimpleStringProperty();
    private StringProperty dateOfBirth=new SimpleStringProperty();
    private StringProperty passportNumber=new SimpleStringProperty();
    private StringProperty nationality=new SimpleStringProperty();
    private StringProperty typeOfVisa=new SimpleStringProperty();

    public PassengerTableList(IntegerProperty coreBookingPassengerId, StringProperty segmentNumber, StringProperty gdsPnrNumber, StringProperty firstName, StringProperty middleName, StringProperty lastName, StringProperty passengerType, StringProperty gender, StringProperty dateOfBirth, StringProperty passportNumber, StringProperty nationality, StringProperty typeOfVisa) {
        this.coreBookingPassengerId = coreBookingPassengerId;
        this.segmentNumber = segmentNumber;
        this.gdsPnrNumber = gdsPnrNumber;
        this.firstName = firstName;
        this.middleName = middleName;
        this.lastName = lastName;
        this.passengerType = passengerType;
        this.gender = gender;
        this.dateOfBirth = dateOfBirth;
        this.passportNumber = passportNumber;
        this.nationality = nationality;
        this.typeOfVisa = typeOfVisa;
    }

    public PassengerTableList(Integer coreBookingPassengerId, String segmentNumber, String gdsPnrNumber, String firstName, String middleName, String lastName, String passengerType, String gender, String dateOfBirth, String passportNumber, String nationality, String typeOfVisa) {
        this.coreBookingPassengerId.setValue( coreBookingPassengerId);
        this.segmentNumber.setValue( segmentNumber);
        this.gdsPnrNumber.setValue( gdsPnrNumber);
        this.firstName.setValue( firstName);
        this.middleName.setValue( middleName);
        this.lastName.setValue( lastName);
        this.passengerType.setValue( passengerType);
        this.gender.setValue( gender);
        this.dateOfBirth.setValue( dateOfBirth);
        this.passportNumber.setValue( passportNumber);
        this.nationality.setValue( nationality);
        this.typeOfVisa.setValue( typeOfVisa);
    }

    public Integer getIndex() {
        return index;
    }

    public void setIndex(Integer index) {
        this.index = index;
    }

    public int getCoreBookingPassengerId() {
        return coreBookingPassengerId.get();
    }

    public IntegerProperty coreBookingPassengerIdProperty() {
        return coreBookingPassengerId;
    }

    public void setCoreBookingPassengerId(int coreBookingPassengerId) {
        this.coreBookingPassengerId.set(coreBookingPassengerId);
    }

    public String getSegmentNumber() {
        return segmentNumber.get();
    }

    public StringProperty segmentNumberProperty() {
        return segmentNumber;
    }

    public void setSegmentNumber(String segmentNumber) {
        this.segmentNumber.set(segmentNumber);
    }

    public String getGdsPnrNumber() {
        return gdsPnrNumber.get();
    }

    public StringProperty gdsPnrNumberProperty() {
        return gdsPnrNumber;
    }

    public void setGdsPnrNumber(String gdsPnrNumber) {
        this.gdsPnrNumber.set(gdsPnrNumber);
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

    public String getMiddleName() {
        return middleName.get();
    }

    public StringProperty middleNameProperty() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName.set(middleName);
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

    public String getPassengerType() {
        return passengerType.get();
    }

    public StringProperty passengerTypeProperty() {
        return passengerType;
    }

    public void setPassengerType(String passengerType) {
        this.passengerType.set(passengerType);
    }

    public String getGender() {
        return gender.get();
    }

    public StringProperty genderProperty() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender.set(gender);
    }

    public String getDateOfBirth() {
        return dateOfBirth.get();
    }

    public StringProperty dateOfBirthProperty() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth.set(dateOfBirth);
    }

    public String getPassportNumber() {
        return passportNumber.get();
    }

    public StringProperty passportNumberProperty() {
        return passportNumber;
    }

    public void setPassportNumber(String passportNumber) {
        this.passportNumber.set(passportNumber);
    }

    public String getNationality() {
        return nationality.get();
    }

    public StringProperty nationalityProperty() {
        return nationality;
    }

    public void setNationality(String nationality) {
        this.nationality.set(nationality);
    }

    public String getTypeOfVisa() {
        return typeOfVisa.get();
    }

    public StringProperty typeOfVisaProperty() {
        return typeOfVisa;
    }

    public void setTypeOfVisa(String typeOfVisa) {
        this.typeOfVisa.set(typeOfVisa);
    }
}
