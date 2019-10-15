package dto;

public class Item {


    Integer index;
    Integer stockId;
    String name;
    String unit;
    String qty;

    Integer rate;
    Integer price;

    public Item(Integer stockId, String name, String unit, Integer rate, Integer price, String qty, Integer index) {
        this.stockId = stockId;
        this.name = name;
        this.unit = unit;
        this.qty = qty;
        this.rate = rate;
        this.price = price;
        this.index = index;
    }

    public Integer getIndex() {
        return index;
    }

    public void setIndex(Integer index) {
        this.index = index;
    }

    public String getQty() {
        return qty;
    }

    public void setQty(String qty) {
        this.qty = qty;
    }

    public Integer getStockId() {
        return stockId;
    }

    public void setStockId(Integer stockId) {
        this.stockId = stockId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public Integer getRate() {
        return rate;
    }

    public void setRate(Integer rate) {
        this.rate = rate;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }
}
