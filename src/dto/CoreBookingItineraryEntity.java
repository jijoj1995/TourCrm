package dto;

import javax.persistence.*;
import java.io.Serializable;


@Entity
@Table( name = "core_booking_itinerary" )
public class CoreBookingItineraryEntity implements Serializable {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @PrimaryKeyJoinColumn
    @Column(name = "core_booking_itinerary_id")
    private Integer coreBookingItineraryId;
    private String SegmentNumber;
    private String airlinePnr;
    private String pnrNumber;
    private String offeredAirline;
    private String flightNumber;
    private String classType;
    private String travelType;
    private String departureDate;
    private String fromDestination;
    private String toDestination;
    private String departureTime;
    private String arrivalDate;
    private String arrivalTime;
    private String bookingStatus;

    public Integer getCoreBookingItineraryId() {
        return coreBookingItineraryId;
    }

    public void setCoreBookingItineraryId(Integer coreBookingItineraryId) {
        this.coreBookingItineraryId = coreBookingItineraryId;
    }

    public String getSegmentNumber() {
        return SegmentNumber;
    }

    public void setSegmentNumber(String segmentNumber) {
        SegmentNumber = segmentNumber;
    }

    public String getAirlinePnr() {
        return airlinePnr;
    }

    public void setAirlinePnr(String airlinePnr) {
        this.airlinePnr = airlinePnr;
    }

    public String getPnrNumber() {
        return pnrNumber;
    }

    public void setPnrNumber(String pnrNumber) {
        this.pnrNumber = pnrNumber;
    }

    public String getOfferedAirline() {
        return offeredAirline;
    }

    public void setOfferedAirline(String offeredAirline) {
        this.offeredAirline = offeredAirline;
    }

    public String getFlightNumber() {
        return flightNumber;
    }

    public void setFlightNumber(String flightNumber) {
        this.flightNumber = flightNumber;
    }

    public String getClassType() {
        return classType;
    }

    public void setClassType(String classType) {
        this.classType = classType;
    }

    public String getTravelType() {
        return travelType;
    }

    public void setTravelType(String travelType) {
        this.travelType = travelType;
    }

    public String getDepartureDate() {
        return departureDate;
    }

    public void setDepartureDate(String departureDate) {
        this.departureDate = departureDate;
    }

    public String getFromDestination() {
        return fromDestination;
    }

    public void setFromDestination(String fromDestination) {
        this.fromDestination = fromDestination;
    }

    public String getToDestination() {
        return toDestination;
    }

    public void setToDestination(String toDestination) {
        this.toDestination = toDestination;
    }

    public String getDepartureTime() {
        return departureTime;
    }

    public void setDepartureTime(String departureTime) {
        this.departureTime = departureTime;
    }

    public String getArrivalDate() {
        return arrivalDate;
    }

    public void setArrivalDate(String arrivalDate) {
        this.arrivalDate = arrivalDate;
    }

    public String getArrivalTime() {
        return arrivalTime;
    }

    public void setArrivalTime(String arrivalTime) {
        this.arrivalTime = arrivalTime;
    }

    public String getBookingStatus() {
        return bookingStatus;
    }

    public void setBookingStatus(String bookingStatus) {
        this.bookingStatus = bookingStatus;
    }
}
