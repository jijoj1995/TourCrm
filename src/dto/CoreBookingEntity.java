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
}
