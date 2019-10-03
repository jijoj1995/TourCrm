package dto;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.io.Serializable;

public class CoreLeadNotesDto implements Serializable {
    private IntegerProperty coreLeadNotesId=new SimpleIntegerProperty();
    private StringProperty notesData=new SimpleStringProperty();

    public CoreLeadNotesDto(Integer coreLeadNotesId, String notesData) {
        this.coreLeadNotesId.setValue(coreLeadNotesId);
        this.notesData.set(notesData);
    }

    public int getCoreLeadNotesId() {
        return coreLeadNotesId.get();
    }

    public IntegerProperty coreLeadNotesIdProperty() {
        return coreLeadNotesId;
    }

    public void setCoreLeadNotesId(int coreLeadNotesId) {
        this.coreLeadNotesId.set(coreLeadNotesId);
    }

    public String getNotesData() {
        return notesData.get();
    }

    public StringProperty notesDataProperty() {
        return notesData;
    }

    public void setNotesData(String notesData) {
        this.notesData.set(notesData);
    }
}
