package dto;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table( name = "core_booking_promotion" )
public class CoreBookingPromotionEntity implements Serializable {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @PrimaryKeyJoinColumn
    @Column(name = "core_booking_promotion_id")
    private Integer coreBookingPromotionId;
    private String firstName;
    private String middleName;
    private String lastName;
    private String typeOfPassenger;
    private String typeOfPromotion;
    private String promotionCode;
    private String discountAmount;
    private String currencyCode;

    public Integer getCoreBookingPromotionId() {
        return coreBookingPromotionId;
    }

    public void setCoreBookingPromotionId(Integer coreBookingPromotionId) {
        this.coreBookingPromotionId = coreBookingPromotionId;
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

    public String getTypeOfPassenger() {
        return typeOfPassenger;
    }

    public void setTypeOfPassenger(String typeOfPassenger) {
        this.typeOfPassenger = typeOfPassenger;
    }

    public String getTypeOfPromotion() {
        return typeOfPromotion;
    }

    public void setTypeOfPromotion(String typeOfPromotion) {
        this.typeOfPromotion = typeOfPromotion;
    }

    public String getPromotionCode() {
        return promotionCode;
    }

    public void setPromotionCode(String promotionCode) {
        this.promotionCode = promotionCode;
    }

    public String getDiscountAmount() {
        return discountAmount;
    }

    public void setDiscountAmount(String discountAmount) {
        this.discountAmount = discountAmount;
    }

    public String getCurrencyCode() {
        return currencyCode;
    }

    public void setCurrencyCode(String currencyCode) {
        this.currencyCode = currencyCode;
    }
}
