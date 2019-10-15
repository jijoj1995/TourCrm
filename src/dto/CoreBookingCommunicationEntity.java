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
    private String indiaLandline;
    private String paxEmailFirst;
    private String paxEmailSecond;
    private String indiaMobile;
    private String usaHome;

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
}
