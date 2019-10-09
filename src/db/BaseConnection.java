package db;


import main.InventoryConfig;
import org.apache.log4j.Logger;
import service.HibernateUtil;

import java.sql.*;



public class BaseConnection {

    private static Connection connection;

    private static Logger log=Logger.getLogger(BaseConnection.class.getName());

    public static Connection getDBConnection(){

        if(connection==null) {
            String ipAddress= InventoryConfig.getInstance().getAppProperties().getProperty("databaseIpAddress");
            String databaseName=InventoryConfig.getInstance().getAppProperties().getProperty("databaseName");
            String USER = InventoryConfig.getInstance().getAppProperties().getProperty("databaseUserName");
            String PASS = InventoryConfig.getInstance().getAppProperties().getProperty("databasePassword");
            //String DB_URL = "jdbc:mysql://"+ipAddress+"/";
            String DB_URL = getDataBaseUrl();

            try{
                //STEP 2: Register JDBC driver
                Class.forName("com.mysql.jdbc.Driver");
                //STEP 3: Open a connection
                connection = DriverManager.getConnection(DB_URL, USER, PASS);

            }catch(Exception se){
                //Handle errors for JDBC
                se.printStackTrace();
            }

        }
        return connection;
    }

    public static void sqlCleanup(Connection connection, PreparedStatement statement, ResultSet resultSet) {
      //  log.info("sql cleanup process started");
        try {
            if (connection != null) {
                connection.close();
            }
            if (statement != null) {
                statement.close();
            }
            if (resultSet != null) {
                resultSet.close();
            }
          //  log.info("sql cleanup process completed");
        }
        catch (Exception e){
            log.info("Exception found while sql cleanup " +e.getMessage());
        }
    }

    public static void sqlCleanup(Connection connection, Statement statement, ResultSet resultSet) {
       // log.info("sql cleanup process started");
        try {
            if (connection != null) {
                connection.close();
            }
            if (statement != null) {
                statement.close();
            }
            if (resultSet != null) {
                resultSet.close();
            }
            log.info("sql cleanup process completed");
        }
        catch (Exception e){
            log.info("Exception found while sql cleanup " +e.getMessage());
        }
    }
    public static String getDataBaseUrl(){
        InventoryConfig inventoryConfig=InventoryConfig.getInstance();
        String ipAddress=inventoryConfig.getAppProperties().getProperty("databaseIpAddress");
        String usePortCheck=inventoryConfig.getAppProperties().getProperty("usePortCheck");
        String portNumber=inventoryConfig.getAppProperties().getProperty("databasePortNumber");
        String databaseName=inventoryConfig.getAppProperties().getProperty("databaseName");
        String databaseUrl="jdbc:mysql://"
                + ipAddress
                +((usePortCheck!=null&&usePortCheck.equals("true")) ? ":"+portNumber : "")
                +"/"
                +databaseName
                +"?useSSL=false";
        return databaseUrl;
    }


}
