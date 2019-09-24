package dto;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table( name = "core_booking_ticketing" )
public class CoreBookingTicketingEntity implements Serializable {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @PrimaryKeyJoinColumn
    @Column(name = "core_booking_ticketing_id")
    private Integer coreBookingTicketingId;
    private String SegmentNumber;
    private String gdPnrNumber;
    private String firstName;
    private String middleName;
    private String lastName;
    private String passengerType;
    private String gender;
    private String status;
    private String airlines;
    private String ticketNumber;
    private String supplier;

    public Integer getCoreBookingTicketingId() {
        return coreBookingTicketingId;
    }

    public void setCoreBookingTicketingId(Integer coreBookingTicketingId) {
        this.coreBookingTicketingId = coreBookingTicketingId;
    }

    public String getSegmentNumber() {
        return SegmentNumber;
    }

    public void setSegmentNumber(String segmentNumber) {
        SegmentNumber = segmentNumber;
    }

    public String getGdPnrNumber() {
        return gdPnrNumber;
    }

    public void setGdPnrNumber(String gdPnrNumber) {
        this.gdPnrNumber = gdPnrNumber;
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

    public String getPassengerType() {
        return passengerType;
    }

    public void setPassengerType(String passengerType) {
        this.passengerType = passengerType;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getAirlines() {
        return airlines;
    }

    public void setAirlines(String airlines) {
        this.airlines = airlines;
    }

    public String getTicketNumber() {
        return ticketNumber;
    }

    public void setTicketNumber(String ticketNumber) {
        this.ticketNumber = ticketNumber;
    }

    public String getSupplier() {
        return supplier;
    }

    public void setSupplier(String supplier) {
        this.supplier = supplier;
    }
}
