package dto;

import javax.persistence.*;
import java.io.Serializable;


@Entity
@Table( name = "core_lead_notes" )
public class CoreLeadsNotesEntity implements Serializable {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @PrimaryKeyJoinColumn
    @Column(name = "core_lead_notes_id")
    private Integer coreLeadNotesId;
    private String notesData;

    @ManyToOne
    @JoinColumn(name="core_lead_id")
    private CoreLeadEntity coreLeadEntity;

    public Integer getCoreLeadNotesId() {
        return coreLeadNotesId;
    }

    public void setCoreLeadNotesId(Integer coreLeadNotesId) {
        this.coreLeadNotesId = coreLeadNotesId;
    }

    public String getNotesData() {
        return notesData;
    }

    public void setNotesData(String notesData) {
        this.notesData = notesData;
    }

    public CoreLeadEntity getCoreLeadEntity() {
        return coreLeadEntity;
    }

    public void setCoreLeadEntity(CoreLeadEntity coreLeadEntity) {
        this.coreLeadEntity = coreLeadEntity;
    }
}
