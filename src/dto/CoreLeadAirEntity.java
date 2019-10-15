package dto;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table( name = "core_lead_air" )
public class CoreLeadAirEntity implements Serializable {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @PrimaryKeyJoinColumn
    @Column(name = "core_lead_air_id")
    private Integer coreLeadAirId;
    private String fromDestination;
    private String toDestination;
    private String departureDate;
    private String returnDate;
    private String airlinesOffered;
    private String currencyCode;
    private String numberOfAdult;
    private String numberOfChild;
    private String numberOfInfant;
    private String totalPax;
    private String adultFare;
    private String childFare;
    private String infantFare;
    private String totalPrice;
    private String typeOfTravel;
    private String classOfTravel;
    private String status;
    @ManyToOne
    @JoinColumn(name="core_lead_id")
    private CoreLeadEntity coreLeadEntity;

    public CoreLeadAirEntity(){

    }
    public CoreLeadAirEntity(Integer coreLeadAirId, String fromDestination, String toDestination, String departureDate, String returnDate, String airlinesOffered, String currencyCode, String numberOfAdult, String numberOfChild, String numberOfInfant, String totalPax, String adultFare, String childFare, String infantFare, String totalPrice, String typeOfTravel, String classOfTravel, String status) {
        this.coreLeadAirId = coreLeadAirId;
        this.fromDestination = fromDestination;
        this.toDestination = toDestination;
        this.departureDate = departureDate;
        this.returnDate = returnDate;
        this.airlinesOffered = airlinesOffered;
        this.currencyCode = currencyCode;
        this.numberOfAdult = numberOfAdult;
        this.numberOfChild = numberOfChild;
        this.numberOfInfant = numberOfInfant;
        this.totalPax = totalPax;
        this.adultFare = adultFare;
        this.childFare = childFare;
        this.infantFare = infantFare;
        this.totalPrice = totalPrice;
        this.typeOfTravel = typeOfTravel;
        this.classOfTravel = classOfTravel;
        this.status = status;
    }

    public Integer getCoreLeadAirId() {
        return coreLeadAirId;
    }

    public void setCoreLeadAirId(Integer coreLeadAirId) {
        this.coreLeadAirId = coreLeadAirId;
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

    public String getDepartureDate() {
        return departureDate;
    }

    public void setDepartureDate(String departureDate) {
        this.departureDate = departureDate;
    }

    public CoreLeadEntity getCoreLeadEntity() {
        return coreLeadEntity;
    }

    public void setCoreLeadEntity(CoreLeadEntity coreLeadEntity) {
        this.coreLeadEntity = coreLeadEntity;
    }

    public String getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(String returnDate) {
        this.returnDate = returnDate;
    }

    public String getAirlinesOffered() {
        return airlinesOffered;
    }

    public void setAirlinesOffered(String airlinesOffered) {
        this.airlinesOffered = airlinesOffered;
    }

    public String getCurrencyCode() {
        return currencyCode;
    }

    public void setCurrencyCode(String currencyCode) {
        this.currencyCode = currencyCode;
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

    public String getInfantFare() {
        return infantFare;
    }

    public void setInfantFare(String infantFare) {
        this.infantFare = infantFare;
    }

    public String getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(String totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getTypeOfTravel() {
        return typeOfTravel;
    }

    public void setTypeOfTravel(String typeOfTravel) {
        this.typeOfTravel = typeOfTravel;
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
