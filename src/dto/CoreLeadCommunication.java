package dto;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import java.io.Serializable;


public class
CoreLeadCommunication implements Serializable {

    private IntegerProperty coreLeadCommunicationId=new SimpleIntegerProperty();
    private StringProperty usaMobile=new SimpleStringProperty();
    private StringProperty usaWorkNumber=new SimpleStringProperty();
    private StringProperty indiaLandline =new SimpleStringProperty();
    private StringProperty paxEmailFirst =new SimpleStringProperty();
    private StringProperty paxEmailSecond =new SimpleStringProperty();
    private StringProperty indiaMobile =new SimpleStringProperty();
    private StringProperty usaHome =new SimpleStringProperty();

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

    public String getIndiaLandline() {
        return indiaLandline.get();
    }

    public StringProperty indiaLandlineProperty() {
        return indiaLandline;
    }

    public void setIndiaLandline(String indiaLandline) {
        this.indiaLandline.set(indiaLandline);
    }

    public String getPaxEmailFirst() {
        return paxEmailFirst.get();
    }

    public StringProperty paxEmailFirstProperty() {
        return paxEmailFirst;
    }

    public void setPaxEmailFirst(String paxEmailFirst) {
        this.paxEmailFirst.set(paxEmailFirst);
    }

    public String getPaxEmailSecond() {
        return paxEmailSecond.get();
    }

    public StringProperty paxEmailSecondProperty() {
        return paxEmailSecond;
    }

    public void setPaxEmailSecond(String paxEmailSecond) {
        this.paxEmailSecond.set(paxEmailSecond);
    }

    public String getIndiaMobile() {
        return indiaMobile.get();
    }

    public StringProperty indiaMobileProperty() {
        return indiaMobile;
    }

    public void setIndiaMobile(String indiaMobile) {
        this.indiaMobile.set(indiaMobile);
    }

    public String getUsaHome() {
        return usaHome.get();
    }

    public StringProperty usaHomeProperty() {
        return usaHome;
    }

    public void setUsaHome(String usaHome) {
        this.usaHome.set(usaHome);
    }
}
