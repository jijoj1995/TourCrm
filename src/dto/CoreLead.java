package dto;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table( name = "core_lead" )
@SecondaryTables({
        @SecondaryTable(name="core_lead_communication", pkJoinColumns={
                @PrimaryKeyJoinColumn(name="core_lead_communication_id", referencedColumnName="core_lead_id") })

})
public class CoreLead implements Serializable {
    @Id
    @GeneratedValue(generator = "increment")
    @GenericGenerator(name = "increment", strategy = "increment")
    @Column(name = "core_lead_id")
    private Integer coreLeadId;
    private String firstName;
    private String middleName;
    private String lastName;
    private String branchCode;
    private String channelCode;
    private String country;
    private String querySource;
    private String currencyCode;
    private String shift;
    private String callReason;
    private String lobCode;

    @Column(table="core_lead_communication")
    private CoreLeadCommunication coreLeadCommunication;

    public Integer getCoreLeadId() {
        return coreLeadId;
    }

    public void setCoreLeadId(Integer coreLeadId) {
        this.coreLeadId = coreLeadId;
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

    public CoreLeadCommunication getCoreLeadCommunication() {
        return coreLeadCommunication;
    }

    public void setCoreLeadCommunication(CoreLeadCommunication coreLeadCommunication) {
        this.coreLeadCommunication = coreLeadCommunication;
    }
}
