package db;

import dto.Customer;
import dto.CustomerList;
import dto.Item;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public class CustomerService extends BaseConnection {

    public ArrayList<CustomerList> getCustomerList() {

        ArrayList<CustomerList> customerList = new ArrayList<CustomerList>();
        try {
            Connection connection = getDBConnection();
            PreparedStatement stmt = connection.prepareStatement("select c.* ,sum(p.pending) as pending from customer c left join payment p on c.customer_id=p.customer_id group by c.customer_id;\n");
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                CustomerList customer = new CustomerList();
                customer.setCustomerId(rs.getString("customer_id"));
                customer.setAddress(rs.getString("address"));
                customer.setName(rs.getString("name"));
                customer.setMobileNumber(rs.getString("phone_no"));
                customer.setEmail(rs.getString("email"));
                customer.setPending((rs.getString("pending") == null) ? "0" : rs.getString("pending"));
                customerList.add(customer);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return customerList;
    }

    public Customer getCustomer(Integer customer_id) {

        Customer customer = new Customer();
        try {
            Connection connection = getDBConnection();
            PreparedStatement stmt = connection.prepareStatement("select * from customer where customer_id='" + customer_id + "';");
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                customer.setCustomerId(rs.getString("customer_id"));
                customer.setAddress(rs.getString("address"));
                customer.setName(rs.getString("name"));
                customer.setMobileNumber(rs.getString("phone_no"));
                customer.setEmail(rs.getString("email"));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return customer;
    }

    public HashMap<String, ArrayList<Item>> getPaymentDetailsCustomer(Set<String> Ids) {
        HashMap<String, ArrayList<Item>> dataMap = new HashMap<String, ArrayList<Item>>();
        try {
            Connection connection = getDBConnection();
            String id=""+Ids;
            id=id.replace("[", "(").replace("]",")");
            PreparedStatement stmt = connection.prepareStatement("select * from payment_iteam where payment_id in" + id + ";");
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                if (dataMap.containsKey(rs.getString("payment_id"))) {
                    Item item = new Item(rs.getInt("stock_id"),
                            rs.getString("name"),rs.getString("unit_total"),
                            rs.getInt("unit_price"),
                            rs.getInt("price"),rs.getString("unit_enter"), 0
                    );
                    ArrayList<Item> dataValue = dataMap.get(rs.getString("payment_id"));
                    dataValue.add(item);
                    dataMap.put(rs.getString("payment_id"), dataValue);
                } else {
                    Item item = new Item(rs.getInt("stock_id"),
                            rs.getString("name"),rs.getString("unit_total"),
                            rs.getInt("unit_price"),
                            rs.getInt("price"),rs.getString("unit_enter"), 0
                    );
                    ArrayList<Item> dataValue = new ArrayList<Item>();
                    dataValue.add(item);
                    dataMap.put(rs.getString("payment_id"), dataValue);
                }


            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return dataMap;
    }

    public void deleteCustomer(String customer_id) {

        Customer customer = new Customer();
        try {
            Connection connection = getDBConnection();
            PreparedStatement stmt = connection.prepareStatement("select * from payment where customer_id='" + customer_id + "';");
            ResultSet rs = stmt.executeQuery();
            Set<String> paymentIds=new HashSet<>();
            while (rs.next()) {
                paymentIds.add(rs.getString("payment_id"));
            }
            if(!paymentIds.isEmpty()){
                String PIds=paymentIds+"";
                PIds=PIds.replace("[", "(").replace("]",")");

                stmt = connection.prepareStatement("delete  from payment_iteam where payment_id in" + PIds + ";");
                stmt.execute();
                stmt = connection.prepareStatement("delete  from payment where payment_id in" + PIds + ";");
                stmt.execute();
                stmt = connection.prepareStatement("delete  from payment_history where payment_id in" + PIds + ";");
                stmt.execute();}
            stmt = connection.prepareStatement("delete from customer where customer_id='" + customer_id + "';");
            stmt.execute();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ;
    }
}
