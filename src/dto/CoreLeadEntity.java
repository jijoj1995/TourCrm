package dto;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Table( name = "core_lead" )
@NamedQueries({
        @NamedQuery(name = "CoreLeadEntity.findAll", query = "SELECT f FROM CoreLeadEntity f"),
        @NamedQuery(name = "CoreLeadEntity.findBasedOnUser", query = "SELECT f FROM CoreLeadEntity f where employeeName=:employeeName")
})
@NamedEntityGraph(
        name = "post-entity-graph",
        attributeNodes = {
                @NamedAttributeNode("coreLeadCommunicationEntity"),
                /*@NamedAttributeNode("coreLeadAirEntities"),*/
                @NamedAttributeNode("coreLeadHolidaysEntity"),
                @NamedAttributeNode("coreLeadHotelEntities"),
                /*@NamedAttributeNode("coreLeadRailEntity"),*/
                @NamedAttributeNode(value = "corebookingEntity",subgraph = "coreBookingEntitySubgraph"),
               /* @NamedAttributeNode("coreLeadsNotesEntities"),*/
        },
        subgraphs = {
                @NamedSubgraph(
                        name = "coreBookingEntitySubgraph",
                        attributeNodes = {
                                @NamedAttributeNode("coreBookingBillingAddressEntity"),
                                @NamedAttributeNode("coreBookingShippingAddressEntity"),
                                @NamedAttributeNode("coreBookingCommunicationEntity"),
                                @NamedAttributeNode("coreBookingStatusEntity"),
                                @NamedAttributeNode("coreBookingPricingEntity"),
                                @NamedAttributeNode("coreBookingItineraryEntity"),
                                @NamedAttributeNode("coreBookingTicketingEntity"),
                                @NamedAttributeNode("coreBookingFareEntity"),
                                @NamedAttributeNode("coreBookingPromotionEntity"),
                        }
                )
        }
)

public class CoreLeadEntity implements Serializable {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    @PrimaryKeyJoinColumn
    @Column(name = "core_lead_id")
    private Integer coreLeadId;
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
    private String employeeName;
    private String queryTime;

    @OneToOne(targetEntity=CoreLeadCommunicationEntity.class,cascade=CascadeType.ALL)
    @JoinColumn(name="core_lead_communication_id")
    private CoreLeadCommunicationEntity coreLeadCommunicationEntity;

    @OneToMany(fetch = FetchType.EAGER,targetEntity= CoreLeadAirEntity.class,cascade=CascadeType.ALL)
    @Fetch(value = FetchMode.SUBSELECT)
    @JoinColumn(name="core_lead_id")
    private List<CoreLeadAirEntity> coreLeadAirEntities;

    @OneToMany(fetch = FetchType.EAGER,targetEntity= CoreLeadHotelEntity.class,cascade=CascadeType.ALL)
    @Fetch(value = FetchMode.SUBSELECT)
    @JoinColumn(name="core_lead_id")
    private List<CoreLeadHotelEntity> coreLeadHotelEntities;

    @OneToOne(targetEntity=CoreLeadHolidaysEntity.class,cascade=CascadeType.ALL)
    @JoinColumn(name="core_lead_holidays_id")
    private CoreLeadHolidaysEntity coreLeadHolidaysEntity;

    @OneToMany(fetch = FetchType.EAGER,targetEntity= CoreLeadRailEntity.class,cascade=CascadeType.ALL)
    @Fetch(value = FetchMode.SUBSELECT)
    @JoinColumn(name="core_lead_id")
    private List<CoreLeadRailEntity> coreLeadRailEntities;

    @OneToOne(targetEntity=CoreBookingEntity.class,cascade=CascadeType.ALL)
    @JoinColumn(name="core_booking_id")
    private CoreBookingEntity corebookingEntity;

    @OneToMany(fetch = FetchType.EAGER,targetEntity= CoreLeadNotesEntity.class,cascade=CascadeType.ALL)
    @Fetch(value = FetchMode.SUBSELECT)
    @JoinColumn(name="core_lead_id")
    private List<CoreLeadNotesEntity> coreLeadsNotesEntities;


    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getQueryTime() {
        return queryTime;
    }

    public void setQueryTime(String queryTime) {
        this.queryTime = queryTime;
    }

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

    public String getEmployeeName() {
        return employeeName;
    }

    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
    }

    public CoreLeadCommunicationEntity getCoreLeadCommunicationEntity() {
        return coreLeadCommunicationEntity;
    }

    public void setCoreLeadCommunicationEntity(CoreLeadCommunicationEntity coreLeadCommunicationEntity) {
        this.coreLeadCommunicationEntity = coreLeadCommunicationEntity;
    }

    public List<CoreLeadAirEntity> getCoreLeadAirEntities() {
        return coreLeadAirEntities;
    }

    public void setCoreLeadAirEntities(List<CoreLeadAirEntity> coreLeadAirEntities) {
        this.coreLeadAirEntities = coreLeadAirEntities;
    }

    public CoreLeadHolidaysEntity getCoreLeadHolidaysEntity() {
        return coreLeadHolidaysEntity;
    }

    public void setCoreLeadHolidaysEntity(CoreLeadHolidaysEntity coreLeadHolidaysEntity) {
        this.coreLeadHolidaysEntity = coreLeadHolidaysEntity;
    }

    public List<CoreLeadHotelEntity> getCoreLeadHotelEntities() {
        return coreLeadHotelEntities;
    }

    public void setCoreLeadHotelEntities(List<CoreLeadHotelEntity> coreLeadHotelEntities) {
        this.coreLeadHotelEntities = coreLeadHotelEntities;
    }

    public List<CoreLeadRailEntity> getCoreLeadRailEntities() {
        return coreLeadRailEntities;
    }

    public void setCoreLeadRailEntities(List<CoreLeadRailEntity> coreLeadRailEntities) {
        this.coreLeadRailEntities = coreLeadRailEntities;
    }

    public CoreBookingEntity getCorebookingEntity() {
        return corebookingEntity;
    }

    public void setCorebookingEntity(CoreBookingEntity corebookingEntity) {
        this.corebookingEntity = corebookingEntity;
    }

    public List<CoreLeadNotesEntity> getCoreLeadsNotesEntities() {
        return coreLeadsNotesEntities;
    }

    public void setCoreLeadsNotesEntities(List<CoreLeadNotesEntity> coreLeadsNotesEntities) {
        this.coreLeadsNotesEntities = coreLeadsNotesEntities;
    }
}
