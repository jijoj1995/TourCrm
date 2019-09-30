package dto;

import javax.persistence.*;
import java.io.Serializable;

@Entity(name = "ForeignKeyAssoPassengerEntity")
@Table(name = "core_booking_passenger", uniqueConstraints = {
        @UniqueConstraint(columnNames = "core_booking_passenger_id")})
public class CoreBookingPassengerEntity implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @PrimaryKeyJoinColumn
    @Column(name = "core_booking_passenger_id")
    private Integer coreBookingPassengerId;
    private String segmentNumber;
    private String gdsPnrNumber;
    private String firstName;
    private String middleName;
    private String lastName;
    private String passengerType;
    private String gender;
    private String dateOfBirth;
    private String passportNumber;
    private String nationality;
    private String typeOfVisa;
    @ManyToOne
    @JoinColumn(name="core_booking_id")
    private CoreBookingEntity coreBookingEntity;

    public CoreBookingEntity getCoreBookingEntity() {
        return coreBookingEntity;
    }

    public void setCoreBookingEntity(CoreBookingEntity coreBookingEntity) {
        this.coreBookingEntity = coreBookingEntity;
    }

    public Integer getCoreBookingPassengerId() {
        return coreBookingPassengerId;
    }

    public void setCoreBookingPassengerId(Integer coreBookingPassengerId) {
        this.coreBookingPassengerId = coreBookingPassengerId;
    }

    public String getSegmentNumber() {
        return segmentNumber;
    }

    public void setSegmentNumber(String segmentNumber) {
        this.segmentNumber = segmentNumber;
    }

    public String getGdsPnrNumber() {
        return gdsPnrNumber;
    }

    public void setGdsPnrNumber(String gdsPnrNumber) {
        this.gdsPnrNumber = gdsPnrNumber;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPassengerType() {
        return passengerType;
    }

    public void setPassengerType(String passengerType) {
        this.passengerType = passengerType;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getPassportNumber() {
        return passportNumber;
    }

    public void setPassportNumber(String passportNumber) {
        this.passportNumber = passportNumber;
    }

    public String getNationality() {
        return nationality;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    public String getTypeOfVisa() {
        return typeOfVisa;
    }

    public void setTypeOfVisa(String typeOfVisa) {
        this.typeOfVisa = typeOfVisa;
    }
}
