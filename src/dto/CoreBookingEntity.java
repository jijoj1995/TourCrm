package dto;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table( name = "core_booking" )
public class CoreBookingEntity implements Serializable {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @PrimaryKeyJoinColumn
    @Column(name = "core_booking_id")
    private Integer coreBookingId;
    private String bookingTime;
    private Integer queryId;
    private String userId;
    private String firstName;
    private String middleName;
    private String lastName;
    private String departmentCode;
    private String channelCode;
    private String querySource;
    private String gdsType;
    private String pnrNumber;
    private String currencyCode;
    private String dueDate;
    private String holdBooking;
    private String supplierName;
    private String lobCode;


    @OneToOne(targetEntity=CoreBookingBillingAddressEntity.class,cascade=CascadeType.ALL)
    @JoinColumn(name="core_booking_billing_address_id")
    private CoreBookingBillingAddressEntity coreBookingBillingAddressEntity;

    @OneToOne(targetEntity=CoreBookingShippingAddressEntity.class,cascade=CascadeType.ALL)
    @JoinColumn(name="core_booking_shipping_address_id")
    private CoreBookingShippingAddressEntity coreBookingShippingAddressEntity;

    @OneToOne(targetEntity=CoreBookingCommunicationEntity.class,cascade=CascadeType.ALL)
    @JoinColumn(name="core_booking_communication_id")
    private CoreBookingCommunicationEntity coreBookingCommunicationEntity;

    @OneToOne(targetEntity=CoreBookingStatusEntity.class,cascade=CascadeType.ALL)
    @JoinColumn(name="core_booking_status_id")
    private CoreBookingStatusEntity coreBookingStatusEntity;

    @OneToOne(targetEntity=CoreBookingPassengerEntity.class,cascade=CascadeType.ALL)
    @JoinColumn(name="core_booking_passenger_id")
    private CoreBookingPassengerEntity coreBookingPassengerEntity;

    @OneToOne(targetEntity=CoreBookingPricingEntity.class,cascade=CascadeType.ALL)
    @JoinColumn(name="core_booking_pricing_id")
    private CoreBookingPricingEntity coreBookingPricingEntity;

    @OneToOne(targetEntity=CoreBookingItineraryEntity.class,cascade=CascadeType.ALL)
    @JoinColumn(name="core_booking_itinerary_id")
    private CoreBookingItineraryEntity coreBookingItineraryEntity;

    @OneToOne(targetEntity=CoreBookingTicketingEntity.class,cascade=CascadeType.ALL)
    @JoinColumn(name="core_booking_ticketing_id")
    private CoreBookingTicketingEntity coreBookingTicketingEntity;

    @OneToOne(targetEntity=CoreBookingFareEntity.class,cascade=CascadeType.ALL)
    @JoinColumn(name="core_booking_fare_id")
    private CoreBookingFareEntity coreBookingFareEntity;

    @OneToOne(targetEntity=CoreBookingPromotionEntity.class,cascade=CascadeType.ALL)
    @JoinColumn(name="core_booking_promotion_id")
    private CoreBookingPromotionEntity coreBookingPromotionEntity;



    public Integer getCoreBookingId() {
        return coreBookingId;
    }

    public void setCoreBookingId(Integer coreBookingId) {
        this.coreBookingId = coreBookingId;
    }

    public String getBookingTime() {
        return bookingTime;
    }

    public void setBookingTime(String bookingTime) {
        this.bookingTime = bookingTime;
    }

    public Integer getQueryId() {
        return queryId;
    }

    public void setQueryId(Integer queryId) {
        this.queryId = queryId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
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

    public String getDepartmentCode() {
        return departmentCode;
    }

    public void setDepartmentCode(String departmentCode) {
        this.departmentCode = departmentCode;
    }

    public String getChannelCode() {
        return channelCode;
    }

    public void setChannelCode(String channelCode) {
        this.channelCode = channelCode;
    }

    public String getQuerySource() {
        return querySource;
    }

    public void setQuerySource(String querySource) {
        this.querySource = querySource;
    }

    public String getGdsType() {
        return gdsType;
    }

    public void setGdsType(String gdsType) {
        this.gdsType = gdsType;
    }

    public String getPnrNumber() {
        return pnrNumber;
    }

    public void setPnrNumber(String pnrNumber) {
        this.pnrNumber = pnrNumber;
    }

    public String getCurrencyCode() {
        return currencyCode;
    }

    public void setCurrencyCode(String currencyCode) {
        this.currencyCode = currencyCode;
    }

    public String getDueDate() {
        return dueDate;
    }

    public void setDueDate(String dueDate) {
        this.dueDate = dueDate;
    }

    public String getHoldBooking() {
        return holdBooking;
    }

    public void setHoldBooking(String holdBooking) {
        this.holdBooking = holdBooking;
    }

    public String getSupplierName() {
        return supplierName;
    }

    public void setSupplierName(String supplierName) {
        this.supplierName = supplierName;
    }

    public String getLobCode() {
        return lobCode;
    }

    public void setLobCode(String lobCode) {
        this.lobCode = lobCode;
    }

    public CoreBookingBillingAddressEntity getCoreBookingBillingAddressEntity() {
        return coreBookingBillingAddressEntity;
    }

    public void setCoreBookingBillingAddressEntity(CoreBookingBillingAddressEntity coreBookingBillingAddressEntity) {
        this.coreBookingBillingAddressEntity = coreBookingBillingAddressEntity;
    }

    public CoreBookingShippingAddressEntity getCoreBookingShippingAddressEntity() {
        return coreBookingShippingAddressEntity;
    }

    public void setCoreBookingShippingAddressEntity(CoreBookingShippingAddressEntity coreBookingShippingAddressEntity) {
        this.coreBookingShippingAddressEntity = coreBookingShippingAddressEntity;
    }

    public CoreBookingCommunicationEntity getCoreBookingCommunicationEntity() {
        return coreBookingCommunicationEntity;
    }

    public void setCoreBookingCommunicationEntity(CoreBookingCommunicationEntity coreBookingCommunicationEntity) {
        this.coreBookingCommunicationEntity = coreBookingCommunicationEntity;
    }

    public CoreBookingStatusEntity getCoreBookingStatusEntity() {
        return coreBookingStatusEntity;
    }

    public void setCoreBookingStatusEntity(CoreBookingStatusEntity coreBookingStatusEntity) {
        this.coreBookingStatusEntity = coreBookingStatusEntity;
    }

    public CoreBookingPassengerEntity getCoreBookingPassengerEntity() {
        return coreBookingPassengerEntity;
    }

    public void setCoreBookingPassengerEntity(CoreBookingPassengerEntity coreBookingPassengerEntity) {
        this.coreBookingPassengerEntity = coreBookingPassengerEntity;
    }

    public CoreBookingPricingEntity getCoreBookingPricingEntity() {
        return coreBookingPricingEntity;
    }

    public void setCoreBookingPricingEntity(CoreBookingPricingEntity coreBookingPricingEntity) {
        this.coreBookingPricingEntity = coreBookingPricingEntity;
    }

    public CoreBookingItineraryEntity getCoreBookingItineraryEntity() {
        return coreBookingItineraryEntity;
    }

    public void setCoreBookingItineraryEntity(CoreBookingItineraryEntity coreBookingItineraryEntity) {
        this.coreBookingItineraryEntity = coreBookingItineraryEntity;
    }

    public CoreBookingTicketingEntity getCoreBookingTicketingEntity() {
        return coreBookingTicketingEntity;
    }

    public void setCoreBookingTicketingEntity(CoreBookingTicketingEntity coreBookingTicketingEntity) {
        this.coreBookingTicketingEntity = coreBookingTicketingEntity;
    }

    public CoreBookingFareEntity getCoreBookingFareEntity() {
        return coreBookingFareEntity;
    }

    public void setCoreBookingFareEntity(CoreBookingFareEntity coreBookingFareEntity) {
        this.coreBookingFareEntity = coreBookingFareEntity;
    }

    public CoreBookingPromotionEntity getCoreBookingPromotionEntity() {
        return coreBookingPromotionEntity;
    }

    public void setCoreBookingPromotionEntity(CoreBookingPromotionEntity coreBookingPromotionEntity) {
        this.coreBookingPromotionEntity = coreBookingPromotionEntity;
    }
}
