package service;

import dto.CoreBookingBillingAddressEntity;
import dto.CoreBookingShippingAddressEntity;
import org.apache.log4j.Logger;

public class BookingService {
    private Logger logger=Logger.getLogger(BookingService.class);

    public CoreBookingShippingAddressEntity getShippingAddressFromBillingAddress(CoreBookingBillingAddressEntity billingAddressEntity) {
        logger.info("in getShippingAddressFromBillingAddress");
        if (billingAddressEntity == null) {
            logger.warn("billingAddressEntity is null returning");
            return null;
        }
        CoreBookingShippingAddressEntity shippingAddressEntity = new CoreBookingShippingAddressEntity();
        shippingAddressEntity.setAddress1(billingAddressEntity.getAddress1());
        shippingAddressEntity.setAddress2(billingAddressEntity.getAddress2());
        shippingAddressEntity.setCity(billingAddressEntity.getCity());
        shippingAddressEntity.setCountry(billingAddressEntity.getCountry());
        shippingAddressEntity.setName(billingAddressEntity.getName());
        shippingAddressEntity.setState(billingAddressEntity.getState());
        shippingAddressEntity.setZipCode(billingAddressEntity.getZipCode());

        return shippingAddressEntity;
    }

    public boolean isShippingBillingAddressSame(CoreBookingBillingAddressEntity billingAddressEntity,CoreBookingShippingAddressEntity shippingAddressEntity){
        if (shippingAddressEntity==null||billingAddressEntity==null)return false;
        if ((billingAddressEntity.getAddress1().equals(shippingAddressEntity.getAddress1()))
                && (billingAddressEntity.getAddress2().equals(shippingAddressEntity.getAddress2()))
                && (billingAddressEntity.getCity().equals(shippingAddressEntity.getCity()))
                && (billingAddressEntity.getCountry().equals(shippingAddressEntity.getCountry()))
                && (billingAddressEntity.getName().equals(shippingAddressEntity.getName()))
                && (billingAddressEntity.getState().equals(shippingAddressEntity.getState()))
                && (billingAddressEntity.getZipCode().equals(shippingAddressEntity.getZipCode()))){
            return true;
        }
        else{
            return false;
        }
    }
}
