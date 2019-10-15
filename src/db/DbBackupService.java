package db;


import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.Date;

import constants.InventoryConstants;
import main.InventoryConfig;
import org.apache.log4j.Logger;
import org.h2.tools.RunScript;

public class DbBackupService extends BaseConnection {
    private Logger log=Logger.getLogger(DbBackupService.class.getName());
    InventoryConfig inventoryConfig=InventoryConfig.getInstance();

    public boolean backupEntireDatabase(File file){
        log.info("backup process started");
        boolean isSuccessful=false;
        Connection connection=null;
        Statement statement=null;

        try{
            if(!file.exists()){
                file.createNewFile();
            }
            connection=getDBConnection();
            statement=connection.createStatement();
            statement.execute(String.format("SCRIPT TO '%s'",file.getAbsolutePath()));

            isSuccessful=true;
        }
        catch (Exception e){
            log.warn("Unable to create backup ",e);
            e.printStackTrace();
        }
        finally {
            sqlCleanup(connection,statement,null);
        }
        log.info("backup process completed");
        return isSuccessful;
    }

    public boolean restoreEntireDatabase(File file) {
        log.info("restore process started");
        boolean isSuccessful=false;
        Connection connection=null;
        Statement statement=null;
        try{
            connection=getDBConnection();
             statement = connection.createStatement();
            
                statement.execute("DROP ALL OBJECTS");
                          
                RunScript.execute(connection, new FileReader(file));
            isSuccessful=true;
        }
        catch (IOException| SQLException e){
            e.printStackTrace();
        }
        finally {
            sqlCleanup(connection,statement,null);
        }
        log.info("restore process completed");
        return isSuccessful;
    }

    public boolean automaticDatabaseBackup(File file){
        log.info("automatic database backup process started");
        String status="unsuccessful";
        boolean isAutomatic=true;
        if(backupEntireDatabase(file)){
            Date nextDate=new Date();
            nextDate.setTime(nextDate.getTime()+InventoryConstants.sevenDaysValue);
            status="successful";
                    //backup successful now updating database and property file
            if(updateDatabase(file.getName(),nextDate,status,isAutomatic)){
                log.info("automatic database backup process successful");
                log.info("updating properties file with new scheduled backup date");
                InventoryConfig.getInstance().updatePropertyFile();
                return true;
            }
        }
        return false;
    }

    private boolean updateDatabase(String fileName,Date nextDate,String status,boolean isAutomatic){
        log.info("in update backup details table method");
        Connection connection=null;
        Statement statement=null;
        String query="insert into db_backup_status (file_name,status,was_automatic) values" +
                "('"+fileName+"'," +
                "'"+status+"'," +
                ""+isAutomatic+")";
        try{
            connection=getDBConnection();
            statement=connection.createStatement();
            statement.execute(query);
            inventoryConfig.getAppProperties().setProperty("nextDbBackupDate",new SimpleDateFormat(InventoryConstants.databaseDateTimeFormat).format(nextDate));
            return true;

        }catch (Exception e){
            log.warn("Unable to update database table",e);
        }
        finally {
            sqlCleanup(connection,statement,null);
        }
        return false;
    }


    public Date getDatabaseBackupDate(){
        Date backupDate=new Date();
        try {
                        //fetch date from properties file
          backupDate= new SimpleDateFormat(InventoryConstants.databaseDateTimeFormat).parse(inventoryConfig.getAppProperties().getProperty("nextDbBackupDate"));
        }
        catch (Exception e){
            log.warn("error while fetching next automatic bakup date from properties file"+e.getMessage());
        }
        return backupDate;
    }
}
