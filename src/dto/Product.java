package dto;

public class Product {
    private Integer stockId;
    private String name;

    private String packing;

    private String barcode;

    private String unit;

    private String code;

    private String category;

    private Integer stockLeft;

    private Integer rate;

    private Integer discount;

    private Integer tax;

    public Product(Integer stockId, String name, String unit, String category, Integer stockLeft, Integer rate, Integer discount, Integer tax) {
        this.stockId = stockId;
        this.name = name;
        this.unit = unit;
        this.category = category;
        this.stockLeft = stockLeft;
        this.rate = rate;
        this.discount = discount;
        this.tax = tax;
    }

    @Override
    public String toString() {
        return name;
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

    public String getPacking() {
        return packing;
    }

    public void setPacking(String packing) {
        this.packing = packing;
    }

    public String getBarcode() {
        return barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public Integer getStockLeft() {
        return stockLeft;
    }

    public void setStockLeft(Integer stockLeft) {
        this.stockLeft = stockLeft;
    }

    public Integer getRate() {
        return rate;
    }

    public void setRate(Integer rate) {
        this.rate = rate;
    }

    public Integer getDiscount() {
        return discount;
    }

    public void setDiscount(Integer discount) {
        this.discount = discount;
    }

    public Integer getTax() {
        return tax;
    }

    public void setTax(Integer tax) {
        this.tax = tax;
    }
}
