package dto;

public class ReportData {
    String 	productid=new String();
    String 	productname=new String();
    String 	unitprice=new String();
    String 	quantity=new String();
    String 	cost=new String();

    public ReportData(String productid, String productname, String unitprice, String quantity, String cost) {
        this.productid = productid;
        this.productname = productname;
        this.unitprice = unitprice;
        this.quantity = quantity;
        this.cost = cost;
    }

    public String getProductid() {
        return productid;
    }

    public void setProductid(String productid) {
        this.productid = productid;
    }

    public String getProductname() {
        return productname;
    }

    public void setProductname(String productname) {
        this.productname = productname;
    }

    public String getUnitprice() {
        return unitprice;
    }

    public void setUnitprice(String unitprice) {
        this.unitprice = unitprice;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getCost() {
        return cost;
    }

    public void setCost(String cost) {
        this.cost = cost;
    }

}
