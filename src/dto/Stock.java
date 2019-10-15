package dto;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Stock {

    private IntegerProperty stockId=new SimpleIntegerProperty();
    private StringProperty name=new SimpleStringProperty();
    private StringProperty category=new SimpleStringProperty();
    private StringProperty supplier = new SimpleStringProperty();
    private IntegerProperty stockLeft=new SimpleIntegerProperty();
    private IntegerProperty sellingPrice=new SimpleIntegerProperty();
    private IntegerProperty originalPrice=new SimpleIntegerProperty();

    public Stock(){
    }

    public Stock(StringProperty name, StringProperty category, StringProperty supplier, IntegerProperty stockLeft, IntegerProperty sellingPrice, IntegerProperty originalPrice) {
        this.name = name;
        this.category = category;
        this.supplier = supplier;
        this.stockLeft = stockLeft;
        this.sellingPrice = sellingPrice;
        this.originalPrice = originalPrice;
    }

    public Integer getStockId() {
        return stockId.get();
    }

    public IntegerProperty stockIdProperty() {
        return stockId;
    }

    public void setStockId(int stockId) {
        this.stockId.set(stockId);
    }

    public String getName() {
        return name.get();
    }

    public StringProperty nameProperty() {
        return name;
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public String getCategory() {
        return category.get();
    }

    public StringProperty categoryProperty() {
        return category;
    }

    public void setCategory(String category) {
        this.category.set(category);
    }

    public String getSupplier() {
        return supplier.get();
    }

    public void setSupplier(String supplier) {
        this.supplier.set(supplier);
    }

    public StringProperty supplierProperty() {
        return supplier;
    }

    public int getStockLeft() {
        return stockLeft.get();
    }

    public IntegerProperty stockLeftProperty() {
        return stockLeft;
    }

    public void setStockLeft(int stockLeft) {
        this.stockLeft.set(stockLeft);
    }

    public int getSellingPrice() {
        return sellingPrice.get();
    }

    public IntegerProperty sellingPriceProperty() {
        return sellingPrice;
    }

    public void setSellingPrice(int sellingPrice) {
        this.sellingPrice.set(sellingPrice);
    }

    public int getOriginalPrice() {
        return originalPrice.get();
    }

    public IntegerProperty originalPriceProperty() {
        return originalPrice;
    }

    public void setOriginalPrice(int originalPrice) {
        this.originalPrice.set(originalPrice);
    }
}
