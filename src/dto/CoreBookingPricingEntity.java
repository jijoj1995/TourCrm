package dto;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table( name = "core_booking_pricing" )
public class CoreBookingPricingEntity implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "core_booking_pricing_id")
    private Integer coreBookingPricingId;
    private String sellingPrice;
    private String totalGpm;
    private String totalCost;
    private String discount;
    private String serviceCharge;
    private String totalReceivable;
    private String commission;
    private String couponAmount;
    private String lossAmount;
    private String totalCommitted;
    private String amountDue;
    private String totalReceived;
    private String surcharges;
}
