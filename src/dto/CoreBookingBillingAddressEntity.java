package dto;

import javax.persistence.*;

@Entity
@Table( name = "core_booking_billing_address" )
public class CoreBookingBillingAddressEntity {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @PrimaryKeyJoinColumn
    @Column(name = "core_booking_billing_address_id")
    private Integer coreBookingBillingAddressId;
    private String name;
    private String address1;
    private String address2;
    private String state;
    private String country;
    private String city;
    private String zipCode;

    public Integer getCoreBookingBillingAddressId() {
        return coreBookingBillingAddressId;
    }

    public void setCoreBookingBillingAddressId(Integer coreBookingBillingAddressId) {
        this.coreBookingBillingAddressId = coreBookingBillingAddressId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress1() {
        return address1;
    }

    public void setAddress1(String address1) {
        this.address1 = address1;
    }

    public String getAddress2() {
        return address2;
    }

    public void setAddress2(String address2) {
        this.address2 = address2;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }
}
