package dto;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table( name = "core_booking_communication" )
public class CoreBookingCommunicationEntity implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "core_booking_communication_id")
    private Integer coreBookingCommunicationId;
    private String usaMobile;
    private String usaWorkNumber;
    private String landline;
    private String paxEmail;

    public Integer getCoreBookingCommunicationId() {
        return coreBookingCommunicationId;
    }

    public void setCoreBookingCommunicationId(Integer coreBookingCommunicationId) {
        this.coreBookingCommunicationId = coreBookingCommunicationId;
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

    public String getLandline() {
        return landline;
    }

    public void setLandline(String landline) {
        this.landline = landline;
    }

    public String getPaxEmail() {
        return paxEmail;
    }

    public void setPaxEmail(String paxEmail) {
        this.paxEmail = paxEmail;
    }
}
