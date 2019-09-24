package dto;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table( name = "core_lead" )

public class CoreLeadEntity implements Serializable {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    @PrimaryKeyJoinColumn
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

    @OneToOne(targetEntity=CoreLeadCommunicationEntity.class,cascade=CascadeType.ALL)
    @JoinColumn(name="core_lead_communication_id")
    private CoreLeadCommunicationEntity coreLeadCommunicationEntity;

    @OneToOne(targetEntity=CoreLeadAirEntity.class,cascade=CascadeType.ALL)
    @JoinColumn(name="core_lead_Air_id")
    private CoreLeadAirEntity coreLeadAirEntity;

    @OneToOne(targetEntity=CoreLeadHolidaysEntity.class,cascade=CascadeType.ALL)
    @JoinColumn(name="core_lead_holidays_id")
    private CoreLeadHolidaysEntity coreLeadHolidaysEntity;

    @OneToOne(targetEntity=CoreLeadHotelEntity.class,cascade=CascadeType.ALL)
    @JoinColumn(name="core_lead_hotel_id")
    private CoreLeadHotelEntity coreLeadHotelEntity;

    @OneToOne(targetEntity=CoreLeadRailEntity.class,cascade=CascadeType.ALL)
    @JoinColumn(name="core_lead_rail_id")
    private CoreLeadRailEntity coreLeadRailEntity;

    @OneToOne(targetEntity=CoreBookingEntity.class,cascade=CascadeType.ALL)
    @JoinColumn(name="core_booking_id")
    private CoreBookingEntity corebookingEntity;


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

    public CoreLeadCommunicationEntity getCoreLeadCommunicationEntity() {
        return coreLeadCommunicationEntity;
    }

    public void setCoreLeadCommunicationEntity(CoreLeadCommunicationEntity coreLeadCommunicationEntity) {
        this.coreLeadCommunicationEntity = coreLeadCommunicationEntity;
    }

    public CoreLeadAirEntity getCoreLeadAirEntity() {
        return coreLeadAirEntity;
    }

    public void setCoreLeadAirEntity(CoreLeadAirEntity coreLeadAirEntity) {
        this.coreLeadAirEntity = coreLeadAirEntity;
    }

    public CoreLeadHolidaysEntity getCoreLeadHolidaysEntity() {
        return coreLeadHolidaysEntity;
    }

    public void setCoreLeadHolidaysEntity(CoreLeadHolidaysEntity coreLeadHolidaysEntity) {
        this.coreLeadHolidaysEntity = coreLeadHolidaysEntity;
    }

    public CoreLeadHotelEntity getCoreLeadHotelEntity() {
        return coreLeadHotelEntity;
    }

    public void setCoreLeadHotelEntity(CoreLeadHotelEntity coreLeadHotelEntity) {
        this.coreLeadHotelEntity = coreLeadHotelEntity;
    }

    public CoreLeadRailEntity getCoreLeadRailEntity() {
        return coreLeadRailEntity;
    }

    public void setCoreLeadRailEntity(CoreLeadRailEntity coreLeadRailEntity) {
        this.coreLeadRailEntity = coreLeadRailEntity;
    }

    public CoreBookingEntity getCorebookingEntity() {
        return corebookingEntity;
    }

    public void setCorebookingEntity(CoreBookingEntity corebookingEntity) {
        this.corebookingEntity = corebookingEntity;
    }
}
