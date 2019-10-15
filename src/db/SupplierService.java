package db;

import dto.*;
import org.apache.log4j.Logger;
import service.Validator;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

public class SupplierService extends BaseConnection {

    private Logger logger =Logger.getLogger(SupplierService.class);

    public HashMap<String,Object> addPaymentDetails(ArrayList<Item> itemList, Supplier supplier, String payedAmount, String totalAmount, String paymentBillingDate) {
        HashMap<String,Object> processResult=new HashMap<>();
        processResult.put("isSuccessful",false);
        processResult.put("paymentId",0);
        logger.info("Adding process started to add supplier transaction details in database");
        if (itemList.isEmpty() || supplier == null || payedAmount.equals("") || totalAmount.equals("")) {
            //no need to proceed
            logger.info("Data incomplete. Unable to proceed");
            return processResult;
        }

        int supplier_payment_id = 0;
        ResultSet rs = null;
        Connection connection = null;
        Statement statement = null;
        String query;
        try {
            connection = getDBConnection();
            connection.setAutoCommit(false);
            statement = connection.createStatement();
            statement.execute("SAVEPOINT Half_Done; ");

            //inserting Supplier details
            saveSupplierDetails(supplier,connection);
            processResult.put("updatedSupplier",supplier);

            //inserting History record
            query = "insert into supplier_payment(supplier_id, total_amount, payed_amount, pending_amount, billingDate) values(" + supplier.getSupplierId() + "," +
                    "" + totalAmount + "," + payedAmount + "," + (Integer.parseInt(totalAmount) - Integer.parseInt(payedAmount)) + ", '"+paymentBillingDate+"')";

            logger.info("Executing query for supplier Transaction detail insert: " + query);
            statement.executeUpdate(query);

            //fetching payment id
            rs= statement.executeQuery("select last_insert_id();");
            if(rs.next()){
                supplier_payment_id=rs.getInt(1);
                logger.info("last inserted supplier payment id="+supplier_payment_id);

                //inserting values to payment History table
                query = "insert into supplier_payment_history(supplier_payment_id,supplier_id, total_amount, payed_amount, pending_amount,billingDate) values(" + supplier_payment_id + "," + supplier.getSupplierId() + "," +
                        "" + totalAmount + "," + payedAmount + "," + (Integer.parseInt(totalAmount) - Integer.parseInt(payedAmount)) + ",'"+paymentBillingDate+"')";
                logger.info("inserting into supplier payment_history: "+query);
                statement.executeUpdate(query);
            }

            //inserting History items Details
            query = "insert into supplier_payment_item (supplier_payment_id,supplier_id,stock_id, item_name, unit_price, unit_quantity, total_price) values ";
            for (Item record : itemList) {
                query += "(" + supplier_payment_id  + "," + supplier.getSupplierId() + ",'" + record.getStockId() + "','" + record.getName()
                        + "'," + record.getRate() + "," + record.getQty() + "," + record.getPrice() + "),";
                String Query = "UPDATE stocks " +
                        " SET stockLeft =stockLeft +" + record.getQty() +
                        " WHERE stock_id =" + record.getStockId() + ";";
                statement = connection.createStatement();
                statement.executeUpdate(Query);
            }
            query = query.substring(0, query.length() - 1);
            logger.info("Executing query for payment_item insert: " + query);
            statement.executeUpdate(query);
            connection.commit();

            //all process of adding supplier, payment table,payment history table and payment item table completed
            processResult.put("isSuccessful",true);
            processResult.put("paymentId",supplier_payment_id);

        }
        catch (Exception e) {
            logger.warn("exception found while entering Billing data " + e.getMessage());
            try {
                statement.execute("ROLLBACK TO SAVEPOINT Half_Done");
            }
            catch (SQLException e1){
                logger.info("error while restoring savePoint"+e1.getMessage());
            }
            e.printStackTrace();

        } finally {
            sqlCleanup(connection, statement, rs);
        }
        return processResult;
    }



    public boolean payAllAmount(ArrayList<PaymentList> paymentArrayList, Integer Amount, Supplier supplier) {
        boolean isSuccessful=false;
        Connection connection=null;
        PreparedStatement stmt=null;
        Statement statement =null;

        try {
            connection = getDBConnection();
            connection.setAutoCommit(false);
            statement=connection.createStatement();
            statement.execute("SAVEPOINT Half_Done_payment;");
            //looping inside all transactions
            for (PaymentList paymentListDto : paymentArrayList) {
                Integer Pending = Integer.parseInt(paymentListDto.getPendingAmount());

                //checking if amount greater than amount in specific transaction
                if (Pending >= Amount) {
                    Integer Pay = Integer.parseInt(paymentListDto.getPayedAmount()) + Amount;
                    Integer PendingAmount = Pending - Amount;


                     statement.execute("UPDATE supplier_payment " +
                            "SET payed_amount ='" + Pay + "'," +
                            "pending_amount ='" + PendingAmount + "'" +
                            "WHERE supplier_payment_id ='" + paymentListDto.getPaymentId() + "';");
                   // stmt.executeUpdate();

                    String query = "insert into supplier_payment_history(supplier_payment_id,supplier_id, total_amount, payed_amount, pending_amount,billingDate) " +
                            "values(" + paymentListDto.getPaymentId() + "," + supplier.getSupplierId() + "," +
                            "" + paymentListDto.getTotalPrice() + "," + Amount + "," + PendingAmount + ",'"+paymentListDto.getBillingDate()+"')";
                    logger.info("Executing query for History insert: " + query);
                    statement.execute(query);

                    logger.info("Value inserted successfully");
                    break;
                }
                else {
                    Integer pay = Integer.parseInt(paymentListDto.getPayedAmount()) + Pending;
                    Amount = Amount - Pending;

                    String Query="UPDATE supplier_payment " +
                            "SET payed_amount ='" + pay + "'," +
                            " pending_amount =0 " +
                            " WHERE supplier_payment_id ='" + paymentListDto.getPaymentId() + "';";
                    statement.execute(Query);

                    String query = "insert into supplier_payment_history(supplier_payment_id,supplier_id, total_amount, payed_amount, pending_amount,billingDate) " +
                            "values(" + paymentListDto.getPaymentId() + "," + supplier.getSupplierId() + "," +
                            "" + paymentListDto.getTotalPrice() + "," + Pending + "," + 0 + ",'"+paymentListDto.getBillingDate()+"')";
                    logger.info("Executing query for History insert: " + query);
                    statement.execute(query);

                    logger.info("Value inserted successfully");
                }
            }
            connection.commit();
            isSuccessful=true;

        } catch (Exception e) {
            e.printStackTrace();
            try {
                statement.execute("ROLLBACK TO SAVEPOINT Half_Done_payment");
            }
            catch (SQLException e1){
                logger.info("error while restoring savePoint"+e1.getMessage());
            }
        }
        finally {
            sqlCleanup(connection,stmt,null);
            sqlCleanup(null,statement,null);
        }
        return isSuccessful;
    }
    public ArrayList<PaymentList> getPaymentRecordsList(Integer supplier_id) {

        ArrayList<PaymentList> paymentList = new ArrayList<PaymentList>();
        Connection connection =null;
        PreparedStatement stmt=null;
        ResultSet rs =null;
        try {
            connection = getDBConnection();
            stmt = connection.prepareStatement("select * from supplier_payment where supplier_id=" + supplier_id + " and pending_amount!=0;");
            rs = stmt.executeQuery();
            while (rs.next()) {
                PaymentList payment = new PaymentList(String.valueOf(rs.getInt("supplier_payment_id")),
                        rs.getString("total_amount"),
                        rs.getString("pending_amount"),
                        rs.getString("payed_amount"),
                        rs.getString("created_on"),
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
    public ArrayList<SupplierPaymentHistoryList> getSupplierPaymentHistoryList(Integer limit) {

        ArrayList<SupplierPaymentHistoryList> paymentList = new ArrayList<SupplierPaymentHistoryList>();
        Connection connection =null;
        PreparedStatement stmt =null;
        ResultSet rs=null;
        int numberOfRows= (limit==null||limit==0)?100:limit;
        try {
            connection = getDBConnection();
            stmt = connection.prepareStatement("select sph.*,s.name  from supplier_payment_history sph " +
                    " inner join suppliers s on sph.supplier_id=s.supplier_id" +
                    " order by sph.created_on desc limit "+numberOfRows+";");
            rs = stmt.executeQuery();
            while (rs.next()) {
                SupplierPaymentHistoryList payment = new SupplierPaymentHistoryList(rs.getInt("supplier_payment_id"),
                        rs.getInt("total_amount"),rs.getInt("pending_amount"),
                        rs.getInt("payed_amount"),rs.getInt("supplier_id"),rs.getString("name"),
                        rs.getString("created_on"),
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

    public ArrayList<SupplierPaymentHistoryList> getSupplierPaymentHistoryList(int supplierId,String fromDate) {

        ArrayList<SupplierPaymentHistoryList> paymentList = new ArrayList<SupplierPaymentHistoryList>();
        Connection connection =null;
        PreparedStatement stmt =null;
        ResultSet rs=null;

        try {
            connection = getDBConnection();
            stmt = connection.prepareStatement("select p.*,s.name  from supplier_payment_history p inner join suppliers s " +
                    "on p.supplier_id=s.supplier_id where s.supplier_id="+supplierId+" and billingDate > '"+fromDate+"'");
            rs = stmt.executeQuery();
            while (rs.next()) {
                SupplierPaymentHistoryList payment = new SupplierPaymentHistoryList(rs.getInt("supplier_payment_id"),rs.getInt("total_amount"),rs.getInt("pending_amount"),
                        rs.getInt("payed_amount"),rs.getInt("supplier_id"),rs.getString("name"),rs.getString("created_on"),
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


    public ArrayList<SupplierList> getSupplierList(){
        logger.info("fetching suppliers list");
        ArrayList<SupplierList>supplierLists=new ArrayList<>();

        Connection connection=null;
        Statement statement=null;
        ResultSet resultSet=null;
        try{
            connection=getDBConnection();
            statement=connection.createStatement();
            resultSet=statement.executeQuery("select s.* ,sum(sp.pending_amount) as pending_amount from suppliers s left join supplier_payment sp on s.supplier_id=sp.supplier_id group by s.supplier_id;");

            while(resultSet.next()){
                SupplierList supplierList=new SupplierList();
                supplierList.setSupplierId(resultSet.getInt("supplier_id"));
                supplierList.setName(resultSet.getString("name"));
                supplierList.setMobileNumber(resultSet.getString("supplier_phone"));
                supplierList.setAddress(resultSet.getString("supplier_address"));
                supplierList.setPendingAmount((resultSet.getString("pending_amount")==null)?"0":resultSet.getString("pending_amount"));
                supplierLists.add(supplierList);
            }

        }
        catch(Exception e){
            logger.warn("Unable to fetch supplier list "+e.getMessage());
        }
        finally {
            sqlCleanup(connection,statement,resultSet);
        }
        logger.info("fetched number of suppliers= "+supplierLists.size());
        return supplierLists;
    }

    public boolean saveSupplierOnly(Supplier supplier){
        boolean isSuccessful=false;
        Connection connection=null;
        try{
            connection=getDBConnection();
            saveSupplierDetails(supplier,connection);
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
    private void saveSupplierDetails(Supplier supplier, Connection connection) throws SQLException {

        //called from another method. so passing connection object as well
        String query="";
        Statement statement=null;
        ResultSet rs=null;
        //check if adding new supplier
        if ( Validator.isNullOrEmptyInt( supplier.getSupplierId())||supplier.getSupplierId()==0) {
            query = "insert into suppliers(name,supplier_phone,supplier_address) values('"+supplier.getName()+""+
                    "','"+supplier.getMobileNumber()+"','"+supplier.getAddress()+"')";

            logger.info("Executing query for supplier insert: " +query);

            statement = connection.createStatement();
            statement.execute(query);

            //saving supplier id inserted
            rs= statement.executeQuery("select last_insert_id();");
            if(rs.next()){
                supplier.setSupplierId(rs.getInt(1));
            }

        }
        else {
            query = "UPDATE suppliers  SET "
                    + "name = '" + supplier.getName() + "',"
                    + "supplier_phone ='" + supplier.getMobileNumber() + "',"
                    + "supplier_address ='" + supplier.getAddress() + "'"
                    + " WHERE supplier_id ='" + supplier.getSupplierId() + "';";
            statement = connection.createStatement();
            logger.info("Executing query for supplier Data: " + query);
            statement.executeUpdate(query);
        }
        sqlCleanup(null,statement,rs);

    }

    public Supplier getSupplier(Integer supplierId) {

        Supplier supplier = new Supplier();
        try {
            Connection connection = getDBConnection();
            PreparedStatement stmt = connection.prepareStatement("select * from suppliers where supplier_id='" + supplierId + "';");
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                supplier.setSupplierId(rs.getInt("supplier_id"));
                supplier.setAddress(rs.getString("supplier_address"));
                supplier.setName(rs.getString("name"));
                supplier.setMobileNumber(rs.getString("supplier_phone"));

            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return supplier;
    }

    public ArrayList<SupplierPaymentItem> getItemList(Integer payment_id) {

        ArrayList<SupplierPaymentItem> itemsList = new ArrayList<SupplierPaymentItem>();
        Connection connection=null;
        PreparedStatement stmt=null;
        ResultSet rs=null;
        try {
            connection= getDBConnection();
            stmt = connection.prepareStatement("select * from supplier_payment_item where supplier_payment_id='" + payment_id + "';");
            rs = stmt.executeQuery();
            while (rs.next()) {
                SupplierPaymentItem item = new SupplierPaymentItem(rs.getInt("stock_id"),
                        rs.getString("item_name"),
                        rs.getInt("unit_quantity"),
                        rs.getInt("unit_price"),rs.getInt("total_price")
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

    public boolean paySingleTransaction(PaymentList item, Integer Amount,Supplier supplier) {
        boolean isSuccessFul=false;
        Connection connection=null;
        Statement stmt=null;
        try {
            connection = getDBConnection();
            connection.setAutoCommit(false);
            stmt = connection.createStatement();
            stmt.execute("SAVEPOINT Half_Done_payment;");
            Integer Pending = Integer.parseInt(item.getPendingAmount());

            Integer Pay = Integer.parseInt(item.getPayedAmount()) + Amount;
            Integer PendingAmount = Pending - Amount;
            String Query = "UPDATE supplier_payment " +
                    " SET payed_amount ='" + Pay + "'," +
                    " pending_amount ='" + PendingAmount + "'" +
                    " WHERE supplier_payment_id ='" + item.getPaymentId() + "';";
            stmt.executeUpdate(Query);

            String query = "insert into supplier_payment_history(supplier_payment_id,supplier_id, total_amount, payed_amount, pending_amount,billingDate) values(" + item.getPaymentId() + "," + supplier.getSupplierId() + "," +
                    "" + item.getTotalPrice() + "," + Amount + "," + PendingAmount +",'"+item.getBillingDate()+ "')";
            logger.info("Executing query for History insert: " + query);
            //stmt = connection.createStatement();
            int result = stmt.executeUpdate(query);
            connection.commit();
            logger.info((result == 0) ? "Unable to insert payment_history into database" : "Value inserted successfully");
            isSuccessFul=true;
        } catch (Exception e) {
            e.printStackTrace();
            try {
                stmt.execute("ROLLBACK TO SAVEPOINT Half_Done_payment");
            }
            catch (SQLException e1){
                logger.info("error while restoring savePoint"+e1.getMessage());
            }
        }
        finally {
            sqlCleanup(connection,stmt,null);
        }
        return isSuccessFul;
    }

    public HashMap<String, ArrayList<SupplierPaymentItem>> getPaymentDetailsSupplier(Set<String> Ids) {
        HashMap<String, ArrayList<SupplierPaymentItem>> dataMap = new HashMap<String, ArrayList<SupplierPaymentItem>>();
        try {
            Connection connection = getDBConnection();
            String id=""+Ids;
            id=id.replace("[", "(").replace("]",")");
            PreparedStatement stmt = connection.prepareStatement("select * from supplier_payment_item where supplier_payment_id in" + id + ";");
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                if (dataMap.containsKey(rs.getString("supplier_payment_id"))) {
                    SupplierPaymentItem item = new SupplierPaymentItem(rs.getInt("stock_id"),
                          rs.getString("item_name"),
                            rs.getInt("unit_quantity"),
                            rs.getInt("unit_price"),
                            rs.getInt("total_price")
                    );
                    ArrayList<SupplierPaymentItem> dataValue = dataMap.get(rs.getString("supplier_payment_id"));
                    dataValue.add(item);
                    dataMap.put(rs.getString("supplier_payment_id"), dataValue);
                } else {
                    SupplierPaymentItem item = new SupplierPaymentItem(rs.getInt("stock_id"),
                            rs.getString("item_name"),
                            rs.getInt("unit_quantity"),
                            rs.getInt("unit_price"),
                            rs.getInt("total_price")
                    );
                    ArrayList<SupplierPaymentItem> dataValue = new ArrayList<>();
                    dataValue.add(item);
                    dataMap.put(rs.getString("supplier_payment_id"), dataValue);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return dataMap;
    }
}
