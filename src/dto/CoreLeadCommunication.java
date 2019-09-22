package dto;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table( name = "core_lead_communication" )
public class CoreLeadCommunication implements Serializable {
    @Id
    @GeneratedValue(generator = "increment")
    @GenericGenerator(name = "increment", strategy = "increment")
    @Column(name = "core_lead_communication_id")
    private Integer coreLeadCommunicationId;
    private String usaMobile;
    private String usaWorkNumber;
    private String landline;
    private String paxEmail;

    public Integer getCoreLeadCommunicationId() {
        return coreLeadCommunicationId;
    }

    public void setCoreLeadCommunicationId(Integer coreLeadCommunicationId) {
        this.coreLeadCommunicationId = coreLeadCommunicationId;
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
