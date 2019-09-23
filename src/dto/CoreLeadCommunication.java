package dto;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import java.io.Serializable;


public class CoreLeadCommunication implements Serializable {

    private IntegerProperty coreLeadCommunicationId=new SimpleIntegerProperty();
    private StringProperty usaMobile=new SimpleStringProperty();
    private StringProperty usaWorkNumber=new SimpleStringProperty();
    private StringProperty landline=new SimpleStringProperty();
    private StringProperty paxEmail=new SimpleStringProperty();

    public int getCoreLeadCommunicationId() {
        return coreLeadCommunicationId.get();
    }

    public IntegerProperty coreLeadCommunicationIdProperty() {
        return coreLeadCommunicationId;
    }

    public void setCoreLeadCommunicationId(int coreLeadCommunicationId) {
        this.coreLeadCommunicationId.set(coreLeadCommunicationId);
    }

    public String getUsaMobile() {
        return usaMobile.get();
    }

    public StringProperty usaMobileProperty() {
        return usaMobile;
    }

    public void setUsaMobile(String usaMobile) {
        this.usaMobile.set(usaMobile);
    }

    public String getUsaWorkNumber() {
        return usaWorkNumber.get();
    }

    public StringProperty usaWorkNumberProperty() {
        return usaWorkNumber;
    }

    public void setUsaWorkNumber(String usaWorkNumber) {
        this.usaWorkNumber.set(usaWorkNumber);
    }

    public String getLandline() {
        return landline.get();
    }

    public StringProperty landlineProperty() {
        return landline;
    }

    public void setLandline(String landline) {
        this.landline.set(landline);
    }

    public String getPaxEmail() {
        return paxEmail.get();
    }

    public StringProperty paxEmailProperty() {
        return paxEmail;
    }

    public void setPaxEmail(String paxEmail) {
        this.paxEmail.set(paxEmail);
    }
}
