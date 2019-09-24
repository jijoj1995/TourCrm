package dto;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import java.io.Serializable;

public class CoreLead implements Serializable {
    private IntegerProperty coreLeadId=new SimpleIntegerProperty();
    private StringProperty firstName=new SimpleStringProperty();
    private StringProperty middleName=new SimpleStringProperty();
    private StringProperty lastName=new SimpleStringProperty();
    private StringProperty branchCode=new SimpleStringProperty();
    private StringProperty channelCode=new SimpleStringProperty();
    private StringProperty country=new SimpleStringProperty();
    private StringProperty querySource=new SimpleStringProperty();
    private StringProperty currencyCode=new SimpleStringProperty();
    private StringProperty shift=new SimpleStringProperty();
    private StringProperty callReason=new SimpleStringProperty();
    private StringProperty lobCode=new SimpleStringProperty();

    private CoreLeadCommunication coreLeadCommunication;
    private CoreLeadAir coreLeadAir;
    private CoreLeadHolidays coreLeadHolidays;
    private CoreLeadHotel coreLeadHotel;
    private CoreLeadRail coreLeadRail;
    private CoreBookingEntity coreBookingEntity;

    public int getCoreLeadId() {
        return coreLeadId.get();
    }

    public IntegerProperty coreLeadIdProperty() {
        return coreLeadId;
    }

    public void setCoreLeadId(int coreLeadId) {
        this.coreLeadId.set(coreLeadId);
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

    public String getBranchCode() {
        return branchCode.get();
    }

    public StringProperty branchCodeProperty() {
        return branchCode;
    }

    public void setBranchCode(String branchCode) {
        this.branchCode.set(branchCode);
    }

    public String getChannelCode() {
        return channelCode.get();
    }

    public StringProperty channelCodeProperty() {
        return channelCode;
    }

    public void setChannelCode(String channelCode) {
        this.channelCode.set(channelCode);
    }

    public String getCountry() {
        return country.get();
    }

    public StringProperty countryProperty() {
        return country;
    }

    public void setCountry(String country) {
        this.country.set(country);
    }

    public String getQuerySource() {
        return querySource.get();
    }

    public StringProperty querySourceProperty() {
        return querySource;
    }

    public void setQuerySource(String querySource) {
        this.querySource.set(querySource);
    }

    public String getCurrencyCode() {
        return currencyCode.get();
    }

    public StringProperty currencyCodeProperty() {
        return currencyCode;
    }

    public void setCurrencyCode(String currencyCode) {
        this.currencyCode.set(currencyCode);
    }

    public String getShift() {
        return shift.get();
    }

    public StringProperty shiftProperty() {
        return shift;
    }

    public void setShift(String shift) {
        this.shift.set(shift);
    }

    public String getCallReason() {
        return callReason.get();
    }

    public StringProperty callReasonProperty() {
        return callReason;
    }

    public void setCallReason(String callReason) {
        this.callReason.set(callReason);
    }

    public String getLobCode() {
        return lobCode.get();
    }

    public StringProperty lobCodeProperty() {
        return lobCode;
    }

    public void setLobCode(String lobCode) {
        this.lobCode.set(lobCode);
    }

    public CoreLeadCommunication getCoreLeadCommunication() {
        return coreLeadCommunication;
    }

    public void setCoreLeadCommunication(CoreLeadCommunication coreLeadCommunication) {
        this.coreLeadCommunication = coreLeadCommunication;
    }

    public CoreLeadAir getCoreLeadAir() {
        return coreLeadAir;
    }

    public void setCoreLeadAir(CoreLeadAir coreLeadAir) {
        this.coreLeadAir = coreLeadAir;
    }

    public CoreLeadHolidays getCoreLeadHolidays() {
        return coreLeadHolidays;
    }

    public void setCoreLeadHolidays(CoreLeadHolidays coreLeadHolidays) {
        this.coreLeadHolidays = coreLeadHolidays;
    }

    public CoreLeadHotel getCoreLeadHotel() {
        return coreLeadHotel;
    }

    public void setCoreLeadHotel(CoreLeadHotel coreLeadHotel) {
        this.coreLeadHotel = coreLeadHotel;
    }

    public CoreLeadRail getCoreLeadRail() {
        return coreLeadRail;
    }

    public void setCoreLeadRail(CoreLeadRail coreLeadRail) {
        this.coreLeadRail = coreLeadRail;
    }

    public CoreBookingEntity getCoreBookingEntity() {
        return coreBookingEntity;
    }

    public void setCoreBookingEntity(CoreBookingEntity coreBookingEntity) {
        this.coreBookingEntity = coreBookingEntity;
    }
}
