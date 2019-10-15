package dto;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table( name = "core_lead_rail" )
public class CoreLeadRailEntity implements Serializable {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @PrimaryKeyJoinColumn
    @Column(name = "core_lead_rail_id")
    private Integer coreLeadRailId;
    private String departureCity;
    private String arrivalCity;
    private String departureDate;
    private String trainNumber;
    private String numberOfAdult;
    private String numberOfChild;
    private String numberOfInfant;
    private String totalPax;
    private String adultFare;
    private String childFare;
    private String totalFare;
    private String classOfTravel;
    private String status;
    @ManyToOne
    @JoinColumn(name="core_lead_id")
    private CoreLeadEntity coreLeadEntity;

    public CoreLeadRailEntity() {
    }

    public CoreLeadRailEntity(Integer coreLeadRailId, String departureCity, String arrivalCity, String departureDate, String trainNumber, String numberOfAdult, String numberOfChild, String numberOfInfant, String totalPax, String adultFare, String childFare, String totalFare, String classOfTravel, String status) {
        this.coreLeadRailId = coreLeadRailId;
        this.departureCity = departureCity;
        this.arrivalCity = arrivalCity;
        this.departureDate = departureDate;
        this.trainNumber = trainNumber;
        this.numberOfAdult = numberOfAdult;
        this.numberOfChild = numberOfChild;
        this.numberOfInfant = numberOfInfant;
        this.totalPax = totalPax;
        this.adultFare = adultFare;
        this.childFare = childFare;
        this.totalFare = totalFare;
        this.classOfTravel = classOfTravel;
        this.status = status;
    }

    public CoreLeadEntity getCoreLeadEntity() {
        return coreLeadEntity;
    }

    public void setCoreLeadEntity(CoreLeadEntity coreLeadEntity) {
        this.coreLeadEntity = coreLeadEntity;
    }

    public Integer getCoreLeadRailId() {
        return coreLeadRailId;
    }

    public void setCoreLeadRailId(Integer coreLeadRailId) {
        this.coreLeadRailId = coreLeadRailId;
    }

    public String getDepartureCity() {
        return departureCity;
    }

    public void setDepartureCity(String departureCity) {
        this.departureCity = departureCity;
    }

    public String getArrivalCity() {
        return arrivalCity;
    }

    public void setArrivalCity(String arrivalCity) {
        this.arrivalCity = arrivalCity;
    }

    public String getDepartureDate() {
        return departureDate;
    }

    public void setDepartureDate(String departureDate) {
        this.departureDate = departureDate;
    }

    public String getTrainNumber() {
        return trainNumber;
    }

    public void setTrainNumber(String trainNumber) {
        this.trainNumber = trainNumber;
    }

    public String getNumberOfAdult() {
        return numberOfAdult;
    }

    public void setNumberOfAdult(String numberOfAdult) {
        this.numberOfAdult = numberOfAdult;
    }

    public String getNumberOfChild() {
        return numberOfChild;
    }

    public void setNumberOfChild(String numberOfChild) {
        this.numberOfChild = numberOfChild;
    }

    public String getNumberOfInfant() {
        return numberOfInfant;
    }

    public void setNumberOfInfant(String numberOfInfant) {
        this.numberOfInfant = numberOfInfant;
    }

    public String getTotalPax() {
        return totalPax;
    }

    public void setTotalPax(String totalPax) {
        this.totalPax = totalPax;
    }

    public String getAdultFare() {
        return adultFare;
    }

    public void setAdultFare(String adultFare) {
        this.adultFare = adultFare;
    }

    public String getChildFare() {
        return childFare;
    }

    public void setChildFare(String childFare) {
        this.childFare = childFare;
    }

    public String getTotalFare() {
        return totalFare;
    }

    public void setTotalFare(String totalFare) {
        this.totalFare = totalFare;
    }

    public String getClassOfTravel() {
        return classOfTravel;
    }

    public void setClassOfTravel(String classOfTravel) {
        this.classOfTravel = classOfTravel;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
