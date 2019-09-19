package db;


import org.apache.log4j.Logger;

import java.sql.*;



public class BaseConnection {

    private static Connection connection;

    private static Logger log=Logger.getLogger(BaseConnection.class.getName());

    public static Connection getDBConnection(){
       // log.info("Fetching connection object");

        if(connection==null) {
            try {
                String createCustomerTable="CREATE TABLE if not exists `customer` (" +
                        "  `customer_id` int(50) NOT NULL AUTO_INCREMENT," +
                        "  `name` varchar(100) DEFAULT NULL," +
                        "  `phone_no` varchar(100) DEFAULT NULL," +
                        "  `email` varchar(200) DEFAULT NULL," +
                        "  `address` varchar(100) DEFAULT NULL," +
                        "  `created_on` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP," +
                        "  PRIMARY KEY (`customer_id`)" +
                        ");";
                 String createPaymentTable="CREATE TABLE if not exists `payment` (" +
                         "  `payment_id` int(50) NOT NULL AUTO_INCREMENT," +
                         "  `customer_id` int(50) DEFAULT NULL," +
                         "  `price` varchar(100) DEFAULT NULL," +
                         "  `payed` varchar(200) DEFAULT NULL," +
                         "  `pending` int(8) DEFAULT NULL," +
                         "  `created_on` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP," +
                         "   `billingDate` timestamp DEFAULT NULL," +
                         "  PRIMARY KEY (`payment_id`)," +
                         "  FOREIGN KEY (`customer_id`) REFERENCES `customer` (`customer_id`)" +
                         ")";

                 String createPaymentItemTable="CREATE TABLE if not exists `payment_iteam` (" +
                         "  `payment_id` int(50) NOT NULL," +
                         "  `iteam_id` int(50) NOT NULL AUTO_INCREMENT," +
                         "  `name` varchar(100) DEFAULT NULL," +
                         "  `unit_price` int(8) DEFAULT NULL," +
                         "  `unit_total` int(8) DEFAULT NULL," +
                         "  `unit_enter` int(8) DEFAULT NULL," +
                         "  `price` int(8) DEFAULT NULL," +
                         "  `created_on` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP," +
                         "  `stock_id` int(8) DEFAULT NULL," +
                         "  PRIMARY KEY (`iteam_id`)," +
                         "  FOREIGN KEY (`payment_id`) REFERENCES `payment` (`payment_id`)" +
                         ") ;";
                String createSupplierPaymentItemTable="CREATE TABLE if not exists `supplier_payment_item` (" +
                        "  `supplier_payment_id` int(50) NOT NULL," +
                        "  `supplier_id` int(50) DEFAULT NULL," +
                        "  `item_id` int(50) NOT NULL AUTO_INCREMENT," +
                        "  `item_name` varchar(100) DEFAULT NULL," +
                        "  `unit_price` int(20) DEFAULT NULL," +
                        "  `unit_quantity` int(20) DEFAULT NULL," +
                        "  `total_price` int(20) DEFAULT NULL," +
                        "  `created_on` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP," +
                        "  `stock_id` int(20) DEFAULT NULL," +
                        "  PRIMARY KEY (`item_id`)," +
                        "  FOREIGN KEY (`supplier_payment_id`) REFERENCES `supplier_payment` (`supplier_payment_id`)," +
                        "  FOREIGN KEY (`supplier_id`) REFERENCES `suppliers` (`supplier_id`)" +
                        ") ;";
                 String createStocksTable="CREATE TABLE if not exists `stocks`  ( `stock_id` int(50) NOT NULL AUTO_INCREMENT, " +
                         "`name` varchar(100) DEFAULT NULL, `category` varchar(100) DEFAULT NULL, " +
                         "`stockLeft` int(20) DEFAULT NULL, `originalPrice` int(50) DEFAULT NULL," +
                         " `suppliers` varchar(300) DEFAULT NULL, `sellingPrice` int(50) DEFAULT NULL," +
                         " PRIMARY KEY (`stock_id`) );";

                String createPaymentHistoryTable = "CREATE TABLE if not exists `payment_history` (" +
                        "  `payment_id` int(50) NOT NULL," +
                        "  `customer_id` int(50) DEFAULT NULL," +
                        "  `price` varchar(100) DEFAULT NULL," +
                        "  `payed` varchar(200) DEFAULT NULL," +
                        "  `pending` int(8) DEFAULT NULL," +
                        "  `created_on` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP," +
                        "   `billingDate` timestamp DEFAULT NULL," +
                        "  FOREIGN KEY (`customer_id`) REFERENCES `customer` (`customer_id`)" +
                        ");";
                String createSupplierPaymentHistoryTable = "CREATE TABLE if not exists `supplier_payment_history` (" +
                        "  `supplier_payment_history_id` int(50) NOT NULL AUTO_INCREMENT," +
                        "  `supplier_payment_id` int(50) NOT NULL," +
                        "  `supplier_id` int(50) DEFAULT NULL," +
                        "  `total_amount` varchar(100) DEFAULT NULL," +
                        "  `payed_amount` varchar(200) DEFAULT NULL," +
                        "  `pending_amount` int(8) DEFAULT NULL," +
                        "  `created_on` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP," +
                        "   `billingDate` timestamp DEFAULT NULL," +
                        "  PRIMARY KEY (`supplier_payment_history_id`)," +
                        "  FOREIGN KEY (`supplier_payment_id`) REFERENCES `supplier_payment` (`supplier_payment_id`)," +
                        "  FOREIGN KEY (`supplier_id`) REFERENCES `suppliers` (`supplier_id`)" +
                        ");";
                String createSuppliersTable="CREATE TABLE if not exists `suppliers` (" +
                        "  `supplier_id` int(50) NOT NULL AUTO_INCREMENT," +
                        "  `name` varchar(100) DEFAULT NULL," +
                        "  `supplier_phone` varchar(100) DEFAULT NULL," +
                        "  `supplier_address` varchar(100) DEFAULT NULL," +
                        "  `created_on` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP," +
                        "  PRIMARY KEY (`supplier_id`)" +
                        ");";
                String createSuppliersPaymentTable="CREATE TABLE if not exists `supplier_payment` (" +
                        "  `supplier_payment_id` int(50) NOT NULL AUTO_INCREMENT," +
                        "  `supplier_id` int(50) DEFAULT NULL," +
                        "  `total_amount` varchar(100) DEFAULT NULL," +
                        "  `payed_amount` varchar(200) DEFAULT NULL," +
                        "  `pending_amount` int(50) DEFAULT NULL," +
                        "  `created_on` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP," +
                        "   `billingDate` timestamp DEFAULT NULL," +
                        "  PRIMARY KEY (`supplier_payment_id`)," +
                        "  FOREIGN KEY (`supplier_id`) REFERENCES `suppliers` (`supplier_id`)" +
                        ")";
                String createUsersTable="CREATE TABLE if not exists `users`(userId int(50) not null auto_increment,userName varchar(100),password varchar(200)," +
                        " primary key(userId));";
                String insertUserValues="INSERT INTO users(userId,userName,password) SELECT userId,userName,password FROM (SELECT 1 AS userId, 'admin' AS userName,'admin' AS password) sub " +
                        "WHERE NOT EXISTS (SELECT 1 FROM users l WHERE sub.userId = l.userId);";
                String insertAnotherUser="INSERT INTO users(userId,userName,password) SELECT userId,userName,password FROM (SELECT 2 AS userId, 'wadhwa' AS userName,'wadhwa' AS password) sub\n" +
                        "WHERE NOT EXISTS (SELECT 1 FROM users l WHERE sub.userId = l.userId);";
                String databaseBackupTable=" create table if not exists db_backup_status " +
                        "(db_backup_status_id int (50) not null auto_increment primary key," +
                        " file_name varchar(200)," +
                        "status varchar(50)," +
                        "backup_date timestamp default current timestamp," +
                        " was_automatic boolean default false," +
                        "next_backup_date timestamp);";
                // Class.forName("com.mysql.jdbc.Driver");
                Class.forName("org.h2.Driver");


                connection= DriverManager.
                        getConnection("jdbc:h2:./Database/inventory;IGNORECASE=TRUE;DB_CLOSE_ON_EXIT=FALSE", "root", "");
                //connection = DriverManager.getConnection(connectionUrl, dbUser, dbPwd);
                Statement statement=connection.createStatement();
                statement.addBatch(createCustomerTable);
                statement.addBatch(createPaymentTable);
                statement.addBatch(createPaymentItemTable);
                statement.addBatch(createStocksTable);
                statement.addBatch(createPaymentHistoryTable);
                statement.addBatch(createSuppliersTable);
                statement.addBatch(createSuppliersPaymentTable);
                statement.addBatch(createSupplierPaymentHistoryTable);
                statement.addBatch(createSupplierPaymentItemTable);
                statement.addBatch(createUsersTable);
                statement.addBatch(insertUserValues);
                statement.addBatch(insertAnotherUser);
                statement.addBatch(databaseBackupTable);
                statement.executeBatch();
            }

            catch (Exception e) {
                log.info("Connection error while fetching connection object");
                e.printStackTrace();
            }
        }else{try{
            connection=connection.isClosed()?DriverManager.
                    getConnection("jdbc:h2:./Database/inventory;IGNORECASE=TRUE;DB_CLOSE_ON_EXIT=FALSE", "root", ""):connection;
        }catch (Exception e){
            log.warn("exception occured while fetching database connection "+e.getMessage());
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


}
