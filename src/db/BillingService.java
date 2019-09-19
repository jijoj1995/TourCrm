package db;

import constants.InventoryConstants;
import dto.*;
import org.apache.log4j.Logger;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;


public class BillingService extends BaseConnection {
    private static Logger log = Logger.getLogger(StockService.class.getName());

    public HashMap<String,Object> addPaymentDetails(ArrayList<Item> itemList, Customer customer, String payed, String totalAmount, String paymentBillingDate) {
        HashMap<String,Object> processResult=new HashMap<>();
        processResult.put("isSuccessful",false);
        processResult.put("paymentId",0);
        log.info("Adding process started to add History details in database");
        if (itemList.isEmpty() || customer == null || payed == "" || totalAmount == "") {
            //no need to proceed
            log.info("Data incomplete. Unable to proceed");
            return processResult;

        }

        int payment_id = 0;
        ResultSet rs = null;
        Connection connection = null;
        Statement statement = null;
        try {
            connection = getDBConnection();
            connection.setAutoCommit(false);
            String query;

            //inserting Customer details
            saveCustomerDetails(customer,connection);

            //inserting History record
            query = "insert into payment(customer_id, price, payed, pending, billingDate) values(" + customer.getCustomerId() + "," +
                    "" + totalAmount + "," + payed + "," + (Integer.parseInt(totalAmount) - Integer.parseInt(payed)) + ", '"+paymentBillingDate+"')";

            log.info("Executing query for History insert: " + query);
            statement = connection.createStatement();
            statement.executeUpdate(query);

            //fetching payment id
            rs= statement.executeQuery("select last_insert_id();");
            if(rs.next()){
                payment_id=rs.getInt(1);
                log.info("last inserted payment id="+payment_id);


                //inserting values to payment History table
                query = "insert into payment_history(payment_id,customer_id, price, payed, pending,billingDate) values(" + payment_id + "," + customer.getCustomerId() + "," +
                        "" + totalAmount + "," + payed + "," + (Integer.parseInt(totalAmount) - Integer.parseInt(payed)) + ",'"+paymentBillingDate+"')";
                log.info("inserting into payment_history: "+query);
                statement.executeUpdate(query);
            }

            //inserting History items Details
            query = "insert into payment_iteam (payment_id,stock_id, name, unit_price, unit_total, unit_enter, price) values ";
            for (Item record : itemList) {
                query += "(" + payment_id + ",'" + record.getStockId() + "','" + record.getName()
                        + "','" + record.getRate() + "','" + record.getUnit() + "'," + record.getQty() + ",'" + record.getPrice() + "'),";
                String Query = "UPDATE stocks " +
                        " SET stockLeft =stockLeft -" + record.getQty() +
                        " WHERE stock_id =" + record.getStockId() + ";";
                statement = connection.createStatement();
                statement.executeUpdate(Query);
            }
            query = query.substring(0, query.length() - 1);
            PreparedStatement ps = connection.prepareStatement(query);
            log.info("Executing query for payment_item insert: " + query);
            statement.executeUpdate(query);

            connection.commit();
            //all process of adding customer, payment table,payment history table and payment item table completed
            processResult.put("isSuccessful",true);
            processResult.put("paymentId",payment_id);
        }
        catch (Exception e) {
            log.warn("exception found while entering Billing data " + e.getMessage());
            try {
                connection.rollback();
            }
            catch (Exception ex){
                log.warn("unable to rollback" +ex.getMessage());
            }
            e.printStackTrace();

        } finally {
            sqlCleanup(connection, statement, rs);
        }
        return processResult;
    }


    public HashMap<String,Object> addQuickPaymentTransaction(ArrayList<Item> itemList, String totalAmount) {
        HashMap<String,Object> processResult=new HashMap<>();
        processResult.put("isSuccessful",false);
        processResult.put("paymentId",0);
        log.info("Adding process started to add History details in database");
        if (itemList.isEmpty() || totalAmount == "") {
            //no need to proceed
            log.info("Data incomplete. Unable to proceed");
            return processResult;
        }

        int payment_id = 0;
        ResultSet rs = null;
        Connection connection = null;
        Statement statement = null;
        try {
            connection = getDBConnection();
            connection.setAutoCommit(false);
            String query;
            SimpleDateFormat dateFormat = new SimpleDateFormat(InventoryConstants.databaseDateTimeFormat);
            Date databaseDate=new Date();
            String paymentBillingDate=dateFormat.format(databaseDate);

            //inserting payment record
            query = "insert into payment(customer_id, price, payed, pending, billingDate) values(" + 1 + "," +
                    "" + totalAmount + "," + totalAmount + "," + 0 + ", '"+paymentBillingDate+"')";

            log.info("Executing query for History insert: " + query);
            statement = connection.createStatement();
            statement.executeUpdate(query);

            //fetching payment id
            rs= statement.executeQuery("select last_insert_id();");
            if(rs.next()){
                payment_id=rs.getInt(1);
                log.info("last inserted payment id="+payment_id);


                //inserting values to payment History table
                query = "insert into payment_history(payment_id,customer_id, price, payed, pending,billingDate) values(" + payment_id + "," + 1 + "," +
                        "" + totalAmount + "," + totalAmount + "," + 0 + ",'"+paymentBillingDate+"')";
                log.info("inserting into payment_history: "+query);
                statement.executeUpdate(query);
            }

            //inserting History items Details
            query = "insert into payment_iteam (payment_id,stock_id, name, unit_price, unit_total, unit_enter, price) values ";
            for (Item record : itemList) {
                query += "(" + payment_id + ",'" + record.getStockId() + "','" + record.getName()
                        + "','" + record.getRate() + "','" + record.getUnit() + "'," + record.getQty() + ",'" + record.getPrice() + "'),";
                String Query = "UPDATE stocks " +
                        " SET stockLeft =stockLeft -" + record.getQty() +
                        " WHERE stock_id =" + record.getStockId() + ";";
                statement = connection.createStatement();
                statement.executeUpdate(Query);
            }
            query = query.substring(0, query.length() - 1);
            PreparedStatement ps = connection.prepareStatement(query);
            log.info("Executing query for payment_item insert: " + query);
            statement.executeUpdate(query);

            connection.commit();
            //all process of adding customer, payment table,payment history table and payment item table completed
            processResult.put("isSuccessful",true);
            processResult.put("paymentId",payment_id);
        }
        catch (Exception e) {
            log.warn("exception found while entering Billing data " + e.getMessage());
            try {
                connection.rollback();
            }
            catch (Exception ex){
                log.warn("unable to rollback" +ex.getMessage());
            }
            e.printStackTrace();

        } finally {
            sqlCleanup(connection, statement, rs);
        }
        return processResult;
    }

    public ArrayList<Item> getItemList(Integer payment_id) {

        ArrayList<Item> itemsList = new ArrayList<Item>();
        Connection connection=null;
        PreparedStatement stmt=null;
        ResultSet rs=null;
        try {
            connection= getDBConnection();
            stmt = connection.prepareStatement("select * from payment_iteam where payment_id='" + payment_id + "';");
            rs = stmt.executeQuery();
            while (rs.next()) {
                Item item = new Item(rs.getInt("stock_id"),
                        rs.getString("name"),rs.getString("unit_total"),
                        rs.getInt("unit_price"),
                        rs.getInt("price"),rs.getString("unit_enter"), 0
                );

                itemsList.add(item);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            sqlCleanup(connection,stmt,rs);
        }
        return itemsList;
    }

    public ArrayList<Payment> getPaymentList(Integer customer_id) {

        ArrayList<Payment> paymentList = new ArrayList<Payment>();
        Connection connection=null;
        PreparedStatement stmt=null;
        ResultSet rs=null;
        try {
            connection = getDBConnection();
            stmt = connection.prepareStatement("select * from payment where payment_id='" + customer_id + "';");
            rs = stmt.executeQuery();
            while (rs.next()) {
                Payment payment = new Payment(rs.getInt("payment_id"), rs.getInt("customer_id"),
                        rs.getString("price"), rs.getString("payed"),
                        rs.getString("pending"), rs.getString("created_on")
                );

                paymentList.add(payment);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            sqlCleanup(connection,stmt,rs);
        }
        return paymentList;
    }

    public Payment getPaymentObject(String paymentId) {
        Connection connection = getDBConnection();
        Payment payment = null;
        String query = "select * from payment where payment_id ="+paymentId+";";
        log.info("Executing query for History Data: " + query);
        try {
            Statement stmt = connection.createStatement();
            stmt = connection.createStatement();
            ResultSet rs = rs = stmt.executeQuery(query);

            while (rs.next()) {
                payment = new Payment(rs.getInt("payment_id"), rs.getInt("customer_id"),
                        rs.getString("price"), rs.getString("payed"),
                        rs.getString("pending"), rs.getString("created_on"));
            }
        } catch (Exception e) {
            log.warn(e.getMessage());
        }

        return payment;
    }

    public boolean payAllAmount(ArrayList<PaymentList> paymentArrayList, Integer Amount, Payment payment) {
        boolean isSuccessful=false;
        Connection connection=null;
        PreparedStatement stmt=null;
        Statement stmt1 =null;

        try {
            connection = getDBConnection();
            //looping inside all transactions
            for (PaymentList paymentListDto : paymentArrayList) {
                Integer Pending = Integer.parseInt(paymentListDto.getPendingAmount());

                //checking if amount greater than amount in specific transaction
                if (Pending >= Amount) {
                    Integer Pay = Integer.parseInt(paymentListDto.getPayedAmount()) + Amount;
                    Integer PendingAmount = Pending - Amount;

                    stmt = connection.prepareStatement("UPDATE payment " +
                            "SET payed ='" + Pay + "'," +
                            "pending ='" + PendingAmount + "'" +
                            "WHERE payment_id ='" + paymentListDto.getPaymentId() + "';");
                    stmt.executeUpdate();


                    String query = "insert into payment_history(payment_id,customer_id, price, payed, pending,billingDate) values(" + paymentListDto.getPaymentId() + "," + payment.getCustomer_id() + "," +
                            "" + paymentListDto.getTotalPrice()+ "," + Amount + "," + PendingAmount + ",'"+paymentListDto.getBillingDate()+"')";
                    log.info("Executing query for History insert: " + query);
                    stmt1 = connection.createStatement();
                    int result = stmt1.executeUpdate(query);
                    log.info((result == 0) ? "Unable to insert payment_history into database" : "Value inserted successfully");
                    break;
                }
                else {
                    Integer pay = Integer.parseInt(paymentListDto.getPayedAmount()) + Pending;
                    Amount = Amount - Pending;

                    String Query="UPDATE payment " +
                            "SET payed ='" + pay + "'," +
                            " pending =0 " +
                            " WHERE payment_id ='" + paymentListDto.getPaymentId() + "';";
                    stmt = connection.prepareStatement(Query);
                    stmt.executeUpdate();
                    String query = "insert into payment_history(payment_id,customer_id, price, payed, pending,billingDate) values(" + paymentListDto.getPaymentId() + "," + payment.getCustomer_id() + "," +
                            "" + payment.getPrice() + "," + Pending + "," + 0 + ",'"+paymentListDto.getBillingDate()+"')";
                    log.info("Executing query for History insert: " + query);
                    stmt = connection.prepareStatement(query);
                    int result = stmt.executeUpdate();
                    log.info((result == 0) ? "Unable to insert payment_history into database" : "Value inserted successfully");
                }
            }
            isSuccessful=true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            sqlCleanup(connection,stmt,null);
            sqlCleanup(null,stmt1,null);
        }
        return isSuccessful;
    }

    public boolean paysingle(Payment iteam, Integer Amount) {
        boolean isSuccessFul=false;
        Connection connection=null;
        Statement stmt=null;
        try {
            connection = getDBConnection();

            Integer Pending = Integer.parseInt(iteam.getPending());

            Integer Pay = Integer.parseInt(iteam.getPayed()) + Amount;
            Integer PendingAmount = Pending - Amount;
            String Query = "UPDATE payment " +
                    " SET payed ='" + Pay + "'," +
                    " pending ='" + PendingAmount + "'" +
                    " WHERE payment_id ='" + iteam.getPayment_id() + "';";
            stmt = connection.createStatement();
            stmt.executeUpdate(Query);
                   // fetch date from payment table and insert into history table
            String query = "insert into payment_history(payment_id,customer_id, price, payed, pending,billingDate) values(" + iteam.getPayment_id() + "," + iteam.getCustomer_id() + "," +
                    "" + iteam.getPrice() + "," + Amount + "," + PendingAmount + ",'"+iteam.getBillingDate()+"')";
            log.info("Executing query for History insert: " + query);
            //stmt = connection.createStatement();
            int result = stmt.executeUpdate(query);
            log.info((result == 0) ? "Unable to insert payment_history into database" : "Value inserted successfully");
            isSuccessFul=true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            sqlCleanup(connection,stmt,null);
        }
        return isSuccessFul;
    }

    public ArrayList<PaymentList> getPaymentRecordsList(Integer customer_id) {

        ArrayList<PaymentList> paymentList = new ArrayList<PaymentList>();
        Connection connection =null;
        PreparedStatement stmt=null;
        ResultSet rs =null;
        try {
            connection = getDBConnection();
            stmt = connection.prepareStatement("select * from payment where customer_id='" + customer_id + "' and pending!=0;");
            rs = stmt.executeQuery();
            while (rs.next()) {
                PaymentList payment = new PaymentList(String.valueOf(rs.getInt("payment_id")), rs.getString("price"),
                        rs.getString("pending"), rs.getString("payed"), rs.getString("created_on"),
                        rs.getString("billingDate")
                );

                paymentList.add(payment);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            sqlCleanup(connection,stmt,rs);
        }
        return paymentList;
    }

    public ArrayList<PaymentHistoryList> getPaymentHistoryList(Integer limit) {

        ArrayList<PaymentHistoryList> paymentList = new ArrayList<PaymentHistoryList>();
        Connection connection =null;
        PreparedStatement stmt =null;
        ResultSet rs=null;
        int numberOfRows= (limit==null||limit==0)?100:limit;
        try {
            connection = getDBConnection();
            stmt = connection.prepareStatement("select p.*,c.name  from payment_history p inner join customer c " +
                    "on p.customer_id=c.customer_id order by p.created_on desc limit "+numberOfRows+";");
            rs = stmt.executeQuery();
            while (rs.next()) {
                PaymentHistoryList payment = new PaymentHistoryList(rs.getInt("payment_id"),rs.getInt("price"),rs.getInt("pending"),
                        rs.getInt("payed"),rs.getInt("customer_id"),rs.getString("name"),rs.getString("created_on"),
                        rs.getString("billingDate").substring(0,10)
                );
                paymentList.add(payment);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            sqlCleanup(connection,stmt,rs);
        }
        return paymentList;
    }

    public ArrayList<PaymentHistoryList> getCustomerPaymentHistoryList(int customerId,String fromDate) {
        // fromDate="06-03-19";
        ArrayList<PaymentHistoryList> paymentList = new ArrayList<PaymentHistoryList>();
        Connection connection =null;
        PreparedStatement stmt =null;
        ResultSet rs=null;

        try {
            connection = getDBConnection();
            stmt = connection.prepareStatement("select p.*,c.name  from payment_history p inner join customer c " +
                    "on p.customer_id=c.customer_id where c.customer_id="+customerId+" and billingDate > '"+fromDate+"'");
            rs = stmt.executeQuery();
            while (rs.next()) {
                PaymentHistoryList payment = new PaymentHistoryList(rs.getInt("payment_id"),rs.getInt("price"),rs.getInt("pending"),
                        rs.getInt("payed"),rs.getInt("customer_id"),rs.getString("name"),rs.getString("created_on"),
                        rs.getString("billingDate").substring(0,10)
                );
                paymentList.add(payment);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            sqlCleanup(connection,stmt,rs);
        }
        return paymentList;
    }

    public ArrayList<Product> getStockList() {

        ArrayList<Product> productList = new ArrayList<Product>();
        Connection connection =null;
        PreparedStatement stmt=null;
        ResultSet rs =null;
        try {
            connection = getDBConnection();
            stmt = connection.prepareStatement("select * from stocks ");
            rs = stmt.executeQuery();
            while (rs.next()) {
                Product product = new Product(rs.getInt("stock_id"), rs.getString("name"), rs.getString("stockLeft"), rs.getString("category"), rs.getInt("stockLeft"), rs.getInt("sellingPrice"), 0, 0);
                productList.add(product);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            sqlCleanup(connection,stmt,rs);
        }
        return productList;
    }

    private void saveCustomerDetails(Customer customer,Connection connection) throws SQLException {

        //called from another method. so passing connection object as well
        String query="";
        Statement statement=null;
        ResultSet rs=null;
        //check if adding new customer
        if (customer.getCustomerId() == null) {
            query = "insert into customer(name,phone_no,email,address) values('"+customer.getName()+""+
                    "','"+customer.getMobileNumber()+"','"+customer.getEmail()+"','"+customer.getAddress()+"')";

            log.info("Executing query for customer insert: " +query);

            statement = connection.createStatement();
            statement.execute(query);

            //saving customer id inserted
            rs= statement.executeQuery("select last_insert_id();");
            if(rs.next()){
                customer.setCustomerId(rs.getString(1));
            }

        }
        else {
            query = "UPDATE customer  SET "
                    + "name = '" + customer.getName() + "',"
                    + "phone_no ='" + customer.getMobileNumber() + "',"
                    + "email ='" + customer.getEmail() + "',"
                    + "address ='" + customer.getAddress() + "'"
                    + "WHERE customer_id ='" + customer.getCustomerId() + "';";
            statement = connection.createStatement();
            log.info("Executing query for customer Data: " + query);
            statement.executeUpdate(query);
        }
        sqlCleanup(null,statement,rs);

    }

    public boolean saveOnlyCustomerProcess(Customer customer){
        //fro saving customer details only
        boolean isSuccessful=false;
        Connection connection=null;
        try{
            connection=getDBConnection();
            saveCustomerDetails(customer,connection);
            isSuccessful=true;
        }
        catch (Exception e){
            e.printStackTrace();
        }
        finally {
            sqlCleanup(connection,null,null);
        }

        return isSuccessful;
    }
}
