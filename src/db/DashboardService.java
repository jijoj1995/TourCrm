package db;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.HashMap;

public class DashboardService extends BaseConnection {

    public int getTotalCustomers(){
        Connection connection=null;
        Statement statement=null;
        ResultSet resultSet=null;
        try{
            connection=getDBConnection();
            statement=connection.createStatement();
            resultSet=statement.executeQuery("select count(customer_id) as totalCustomers from customer");
            if(resultSet.next()){
                return resultSet.getInt("totalCustomers");
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
        finally {
            sqlCleanup(connection,statement,resultSet);
        }

        return 0;
    }

    public int getTotalSuppliers(){
        Connection connection=null;
        Statement statement=null;
        ResultSet resultSet=null;
        try{
            connection=getDBConnection();
            statement=connection.createStatement();
            resultSet=statement.executeQuery("select count(supplier_id) as totalSuppliers from suppliers");
            if(resultSet.next()){
                return resultSet.getInt("totalSuppliers");
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
        finally {
            sqlCleanup(connection,statement,resultSet);
        }

        return 0;
    }

    public int getTotalStocks(){
        Connection connection=null;
        Statement statement=null;
        ResultSet resultSet=null;
        try{
            connection=getDBConnection();
            statement=connection.createStatement();
            resultSet=statement.executeQuery("select sum(stockLeft) as totalStocks from stocks");
            if(resultSet.next()){
                return resultSet.getInt("totalStocks");
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
        finally {
            sqlCleanup(connection,statement,resultSet);
        }

        return 0;
    }

    public int getTotalCategory(){
        Connection connection=null;
        Statement statement=null;
        ResultSet resultSet=null;
        try{
            connection=getDBConnection();
            statement=connection.createStatement();
            resultSet=statement.executeQuery("select count(distinct category) as totalCategory from stocks");
            if(resultSet.next()){
                return resultSet.getInt("totalCategory");
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
        finally {
            sqlCleanup(connection,statement,resultSet);
        }

        return 0;
    }
    public HashMap<String,Integer> getPaymentTableDetails(){
        HashMap<String,Integer> paymentTableDetails=new HashMap<>();
        Connection connection=null;
        Statement statement=null;
        ResultSet resultSet=null;
        try{
            connection=getDBConnection();
            statement=connection.createStatement();
            resultSet=statement.executeQuery("select sum(pending+0) as totalPendingAmount ,sum(payed+0) as totalPayedAmount,sum(price+0) as totalRevenueAmount from payment");
            if(resultSet.next()){
                paymentTableDetails.put("totalPendingAmount", resultSet.getInt("totalPendingAmount"));
                paymentTableDetails.put("totalPayedAmount", resultSet.getInt("totalPayedAmount"));
                paymentTableDetails.put("totalRevenueAmount", resultSet.getInt("totalRevenueAmount"));
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
        finally {
            sqlCleanup(connection,statement,resultSet);
        }

        return paymentTableDetails;
    }

}
