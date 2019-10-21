package dto;

import java.io.Serializable;

public class CoreCustomerDetailsDto implements Serializable {

    private Integer coreCustomerDetailsId;
    private String firstName;
    private String middleName;
    private String lastName;
    private String userId;
    private String branchCode;
    private String channelCode;
    private String country;
    private String querySource;
    private String currencyCode;
    private String shift;
    private String callReason;
    private String lobCode;
    private String usaMobile;
    private String usaWorkNumber;
    private String indiaLandline;
    private String paxEmailFirst;
    private String paxEmailSecond;
    private String indiaMobile;
    private String usaHome;

    public CoreCustomerDetailsDto() {
    }

    public CoreCustomerDetailsDto(Integer coreCustomerDetailsId, String firstName, String middleName, String lastName, String userId, String branchCode, String channelCode, String country, String querySource, String currencyCode, String shift, String callReason, String lobCode, String usaMobile, String usaWorkNumber, String indiaLandline, String paxEmailFirst, String paxEmailSecond, String indiaMobile, String usaHome) {
        this.coreCustomerDetailsId = coreCustomerDetailsId;
        this.firstName = firstName;
        this.middleName = middleName;
        this.lastName = lastName;
        this.userId = userId;
        this.branchCode = branchCode;
        this.channelCode = channelCode;
        this.country = country;
        this.querySource = querySource;
        this.currencyCode = currencyCode;
        this.shift = shift;
        this.callReason = callReason;
        this.lobCode = lobCode;
        this.usaMobile = usaMobile;
        this.usaWorkNumber = usaWorkNumber;
        this.indiaLandline = indiaLandline;
        this.paxEmailFirst = paxEmailFirst;
        this.paxEmailSecond = paxEmailSecond;
        this.indiaMobile = indiaMobile;
        this.usaHome = usaHome;
    }

    public Integer getCoreCustomerDetailsId() {
        return coreCustomerDetailsId;
    }

    public void setCoreCustomerDetailsId(Integer coreCustomerDetailsId) {
        this.coreCustomerDetailsId = coreCustomerDetailsId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getBranchCode() {
        return branchCode;
    }

    public void setBranchCode(String branchCode) {
        this.branchCode = branchCode;
    }

    public String getChannelCode() {
        return channelCode;
    }

    public void setChannelCode(String channelCode) {
        this.channelCode = channelCode;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getQuerySource() {
        return querySource;
    }

    public void setQuerySource(String querySource) {
        this.querySource = querySource;
    }

    public String getCurrencyCode() {
        return currencyCode;
    }

    public void setCurrencyCode(String currencyCode) {
        this.currencyCode = currencyCode;
    }

    public String getShift() {
        return shift;
    }

    public void setShift(String shift) {
        this.shift = shift;
    }

    public String getCallReason() {
        return callReason;
    }

    public void setCallReason(String callReason) {
        this.callReason = callReason;
    }

    public String getLobCode() {
        return lobCode;
    }

    public void setLobCode(String lobCode) {
        this.lobCode = lobCode;
    }

    public String getUsaMobile() {
        return usaMobile;
    }

    public void setUsaMobile(String usaMobile) {
        this.usaMobile = usaMobile;
    }

    public String getUsaWorkNumber() {
        return usaWorkNumber;
    }

    public void setUsaWorkNumber(String usaWorkNumber) {
        this.usaWorkNumber = usaWorkNumber;
    }

    public String getIndiaLandline() {
        return indiaLandline;
    }

    public void setIndiaLandline(String indiaLandline) {
        this.indiaLandline = indiaLandline;
    }

    public String getPaxEmailFirst() {
        return paxEmailFirst;
    }

    public void setPaxEmailFirst(String paxEmailFirst) {
        this.paxEmailFirst = paxEmailFirst;
    }

    public String getPaxEmailSecond() {
        return paxEmailSecond;
    }

    public void setPaxEmailSecond(String paxEmailSecond) {
        this.paxEmailSecond = paxEmailSecond;
    }

    public String getIndiaMobile() {
        return indiaMobile;
    }

    public void setIndiaMobile(String indiaMobile) {
        this.indiaMobile = indiaMobile;
    }

    public String getUsaHome() {
        return usaHome;
    }

    public void setUsaHome(String usaHome) {
        this.usaHome = usaHome;
    }

    @Override
    public String toString() {
        return firstName.toUpperCase()+" "+lastName.toUpperCase();
    }
}
