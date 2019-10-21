package dto;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class QueriesListDto {
    private IntegerProperty queryId = new SimpleIntegerProperty();
    private StringProperty firstName = new SimpleStringProperty();
    private StringProperty lastName = new SimpleStringProperty();
    private StringProperty email = new SimpleStringProperty();
    private StringProperty branchCode = new SimpleStringProperty();
    private StringProperty callReason = new SimpleStringProperty();
    private StringProperty employeeName = new SimpleStringProperty();
    private StringProperty totalBookingAmount = new SimpleStringProperty();
    private CoreLead coreLeadDto;

    public QueriesListDto() {
    }

    public QueriesListDto(IntegerProperty queryId, StringProperty firstName, StringProperty lastName, StringProperty email, StringProperty branchCode, StringProperty callReason) {
        this.queryId = queryId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.branchCode = branchCode;
        this.callReason = callReason;
    }

    public int getQueryId() {
        return queryId.get();
    }

    public IntegerProperty queryIdProperty() {
        return queryId;
    }

    public void setQueryId(int queryId) {
        this.queryId.set(queryId);
    }

    public String getFirstName() {
        return firstName.get();
    }

    public StringProperty firstNameProperty() {
        return firstName;
    }

    public String getTotalBookingAmount() {
        return totalBookingAmount.get();
    }

    public StringProperty totalBookingAmountProperty() {
        return totalBookingAmount;
    }

    public void setTotalBookingAmount(String totalBookingAmount) {
        this.totalBookingAmount.set(totalBookingAmount);
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

    public String getEmail() {
        return email.get();
    }

    public StringProperty emailProperty() {
        return email;
    }

    public void setEmail(String email) {
        this.email.set(email);
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

    public String getCallReason() {
        return callReason.get();
    }

    public StringProperty callReasonProperty() {
        return callReason;
    }

    public void setCallReason(String callReason) {
        this.callReason.set(callReason);
    }

    public CoreLead getCoreLeadDto() {
        return coreLeadDto;
    }

    public void setCoreLeadDto(CoreLead coreLeadDto) {
        this.coreLeadDto = coreLeadDto;
    }

    public String getEmployeeName() {
        return employeeName.get();
    }

    public StringProperty employeeNameProperty() {
        return employeeName;
    }

    public void setEmployeeName(String employeeName) {
        this.employeeName.set(employeeName);
    }
}
