package dto;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table( name = "core_booking_status" )
public class coreBookingStatusEntity implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "core_booking_status_id")
    private Integer coreBookingStatusId;
    private String queryDate;
    private String bookingDate;
    private String userId;
    private String paymentCommitted;
    private String qcStatus;
    private String qcDoneBy;
    private String qcDate;
    private String moveToDispatch;

    public Integer getCoreBookingStatusId() {
        return coreBookingStatusId;
    }

    public void setCoreBookingStatusId(Integer coreBookingStatusId) {
        this.coreBookingStatusId = coreBookingStatusId;
    }

    public String getQueryDate() {
        return queryDate;
    }

    public void setQueryDate(String queryDate) {
        this.queryDate = queryDate;
    }

    public String getBookingDate() {
        return bookingDate;
    }

    public void setBookingDate(String bookingDate) {
        this.bookingDate = bookingDate;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getPaymentCommitted() {
        return paymentCommitted;
    }

    public void setPaymentCommitted(String paymentCommitted) {
        this.paymentCommitted = paymentCommitted;
    }

    public String getQcStatus() {
        return qcStatus;
    }

    public void setQcStatus(String qcStatus) {
        this.qcStatus = qcStatus;
    }

    public String getQcDoneBy() {
        return qcDoneBy;
    }

    public void setQcDoneBy(String qcDoneBy) {
        this.qcDoneBy = qcDoneBy;
    }

    public String getQcDate() {
        return qcDate;
    }

    public void setQcDate(String qcDate) {
        this.qcDate = qcDate;
    }

    public String getMoveToDispatch() {
        return moveToDispatch;
    }

    public void setMoveToDispatch(String moveToDispatch) {
        this.moveToDispatch = moveToDispatch;
    }
}
