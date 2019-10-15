package db;

import dto.Stock;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;


public class StockService extends BaseConnection {
    private static Logger log=Logger.getLogger(StockService.class.getName());


    public boolean addStock(Stock stock){
        boolean isSuccessfull=false;
        log.info("Adding process started to add stock details in database");
        if(stock ==null) {
            //no need to proceed
            log.info("Null stock object. Unable to proceed");
            return false;
        }
        if(stock.getStockId()!=null && stock.getStockId()!=0){
            log.info("Stock object found. going to follow update process");
            isSuccessfull=updateStock(stock);

        }
        else{
            log.info("New Stock entry. going to follow new add process");
            isSuccessfull=addNewStock(stock);
        }
        return isSuccessfull;
    }

    private boolean addNewStock(Stock stock){
        boolean isAddSuccessfull=false;
        int sqlResult=0;
        Connection connection=null;
        PreparedStatement statement=null;
        try{
            //saving values into database
            String query = "insert into stocks( name,category,stockLeft,originalPrice,sellingPrice,suppliers) values(?,?,?, ?,?,?)";
            connection=getDBConnection();
            statement=connection.prepareStatement(query);
            statement.setString(1,stock.getName());
            statement.setString(2, stock.getCategory());
            statement.setInt(3, stock.getStockLeft());
            statement.setInt(4, stock.getOriginalPrice());
            statement.setInt(5, stock.getSellingPrice());
            statement.setString(6, stock.getSupplier());

            log.info("Executing query: "+statement.toString());
            sqlResult=statement.executeUpdate();
        }
        catch (Exception e){
            log.info("exception found while entering stock data "+e.getMessage());
            e.printStackTrace();
        }
        finally {
            sqlCleanup(connection,statement,null);
            if(sqlResult>0){
                log.info("Value successfully inserted in database");
                isAddSuccessfull=true;
            }
        }

        return isAddSuccessfull;
    }

    private boolean updateStock(Stock stock){
        boolean isUpdateSuccessfull=false;
        int sqlResult=0;
        Connection connection=null;
        PreparedStatement statement=null;
        try{
            //saving values into database
            String query = "update stocks set name =?,category=?,stockLeft=?,originalPrice=?,sellingPrice=?,suppliers=? where stock_Id=?";
            connection=getDBConnection();
            statement=connection.prepareStatement(query);
            statement.setString(1,stock.getName());

            statement.setString(2, stock.getCategory());
            statement.setInt(3, stock.getStockLeft());
            statement.setInt(4, stock.getOriginalPrice());
            statement.setInt(5, stock.getSellingPrice());
            statement.setString(6, stock.getSupplier());

            statement.setInt(7, stock.getStockId());
            log.info("Executing query: "+statement.toString());
            sqlResult=statement.executeUpdate();
        }
        catch (Exception e){
            log.info("exception found while entering stock data "+e.getMessage());
            e.printStackTrace();
        }
        finally {
            sqlCleanup(connection,statement,null);
            if(sqlResult>0){
                log.info("Value successfully inserted in database");
                isUpdateSuccessfull=true;
            }
        }

        return isUpdateSuccessfull;
    }


    public ArrayList<Stock> getStockList(){
        ArrayList<Stock> stockArrayList=new ArrayList<Stock>();
        log.info("fetching stocks from stockList method");

        Connection connection=null;
        ResultSet resultSet=null;
        PreparedStatement preparedStatement=null;
        try{
            connection=getDBConnection();
            preparedStatement=connection.prepareStatement("select * from stocks order by 1 desc");
            resultSet=preparedStatement.executeQuery();
            while (resultSet.next()){
                Stock stock=new Stock();
                stock.setStockId(resultSet.getInt("stock_id"));
                stock.setName(resultSet.getString("name"));
                stock.setOriginalPrice(resultSet.getInt("originalPrice"));
                stock.setSellingPrice(resultSet.getInt("sellingPrice"));
                stock.setCategory(resultSet.getString("category"));
                stock.setStockLeft(resultSet.getInt("stockLeft"));
                stock.setSupplier(resultSet.getString("suppliers"));
                stockArrayList.add(stock);
            }
            log.info("All stock fetched from database");
        }
        catch (Exception e){
            log.info("exception occurred while fetching stock list "+e.getMessage());
        }
        finally {
            sqlCleanup(connection,preparedStatement,resultSet);
        }
        return stockArrayList;
    }
    public ArrayList<Stock> getAscendingStockList(){
        ArrayList<Stock> stockArrayList=new ArrayList<Stock>();
        log.info("fetching stocks from stockList method");

        Connection connection=null;
        ResultSet resultSet=null;
        PreparedStatement preparedStatement=null;
        try{
            connection=getDBConnection();
            preparedStatement=connection.prepareStatement("select * from stocks");
            resultSet=preparedStatement.executeQuery();
            while (resultSet.next()){
                Stock stock=new Stock();
                stock.setStockId(resultSet.getInt("stock_id"));
                stock.setName(resultSet.getString("name"));
                stock.setOriginalPrice(resultSet.getInt("originalPrice"));
                stock.setSellingPrice(resultSet.getInt("sellingPrice"));
                stock.setCategory(resultSet.getString("category"));
                stock.setStockLeft(resultSet.getInt("stockLeft"));
                stock.setSupplier(resultSet.getString("suppliers"));
                stockArrayList.add(stock);
            }
            log.info("All stock fetched from database");
        }
        catch (Exception e){
            log.info("exception occurred while fetching stock list "+e.getMessage());
        }
        finally {
            sqlCleanup(connection,preparedStatement,resultSet);
        }
        return stockArrayList;
    }

    public boolean deleteAllStockData() {
        log.info("deleting  stocks from data method");
        boolean isSuccessful=false;

        Connection connection=null;
        String query="truncate table stocks;ALTER TABLE stocks ALTER COLUMN stock_id RESTART WITH 1";
        PreparedStatement preparedStatement=null;
        try {
            connection = getDBConnection();
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.executeUpdate();

            isSuccessful=true;
        }
        catch (SQLException e){
            log.info("Some error occured while deleting all stock data"+e.getMessage());
        }
        return isSuccessful;
    }

}
