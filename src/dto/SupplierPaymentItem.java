package dto;

import javafx.beans.InvalidationListener;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;

public class SupplierPaymentItem {

    IntegerProperty index =new SimpleIntegerProperty();
    IntegerProperty stockId=new SimpleIntegerProperty();
    StringProperty itemName=new SimpleStringProperty();
    IntegerProperty itemQuantity =new SimpleIntegerProperty();

    IntegerProperty itemPrice=new SimpleIntegerProperty();
    IntegerProperty totalAmount =new SimpleIntegerProperty();

    public SupplierPaymentItem() {
    }

    public SupplierPaymentItem(Integer stockId, String itemName, Integer itemQuantity, Integer itemPrice, Integer totalAmount) {
        this.stockId.set(stockId);
        this.itemName.set( itemName);
        this.itemQuantity.set(itemQuantity);
        this.itemPrice.set(itemPrice);
        this.totalAmount.set( totalAmount);
    }

    public SupplierPaymentItem( Integer index,Integer stockId, String itemName, Integer itemQuantity, Integer itemPrice, Integer totalAmount) {
        this.index.set(index);
        this.stockId.set(stockId);
        this.itemName.set( itemName);
        this.itemQuantity.set(itemQuantity);
        this.itemPrice.set(itemPrice);
        this.totalAmount.set( totalAmount);
    }

    public int getIndex() {
        return index.get();
    }

    public IntegerProperty indexProperty() {
        return index;
    }

    public void setIndex(int index) {
        this.index.set(index);
    }

    public int getStockId() {
        return stockId.get();
    }

    public IntegerProperty stockIdProperty() {
        return stockId;
    }

    public void setStockId(int stockId) {
        this.stockId.set(stockId);
    }

    public String getItemName() {
        return itemName.get();
    }

    public StringProperty itemNameProperty() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName.set(itemName);
    }

    public int getItemQuantity() {
        return itemQuantity.get();
    }

    public IntegerProperty itemQuantityProperty() {
        return itemQuantity;
    }

    public void setItemQuantity(int itemQuantity) {
        this.itemQuantity.set(itemQuantity);
    }

    public int getItemPrice() {
        return itemPrice.get();
    }

    public IntegerProperty itemPriceProperty() {
        return itemPrice;
    }

    public void setItemPrice(int itemPrice) {
        this.itemPrice.set(itemPrice);
    }

    public int getTotalAmount() {
        return totalAmount.get();
    }

    public IntegerProperty totalAmountProperty() {
        return totalAmount;
    }

    public void setTotalAmount(int totalAmount) {
        this.totalAmount.set(totalAmount);
    }
}
