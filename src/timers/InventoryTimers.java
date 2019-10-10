package timers;

import main.InventoryConfig;
import org.apache.log4j.Logger;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

public class InventoryTimers {
Logger logger =Logger.getLogger(InventoryTimers.class);
InventoryConfig inventoryConfig=InventoryConfig.getInstance();
static Timer databaseBackupTimer=null;
private static InventoryTimers instance;
public static InventoryTimers getInstance(){
    if (instance==null){
        return new InventoryTimers();
    }
    return instance;
}

    public void initializeTimers(){
        logger.info("initializing all timers");
        if (Boolean.parseBoolean(inventoryConfig.getAppProperties().getProperty("automaticBackup","false"))) {
            initializeDatabaseBackupTimer();
        }
    }
    /*
        initialising automatic database backup timer fetching details from property file
 */
    public void initializeDatabaseBackupTimer(){
     logger.info("initializing auto database backup timer");
        if(databaseBackupTimer==null) {
            databaseBackupTimer = new Timer();
            TimerTask timerTask = DatabaseBackupTimer.getTimerTask();

                    //fetch next backup date
            Date backupDate=fetchBackupScheduleDate();
            databaseBackupTimer.schedule(timerTask, backupDate);
            logger.info("automatic db backup scheduled to date:"+backupDate);
        }
        else {
            logger.warn("database auto backup timer already initialized");
        }
    }

    /*
    reinitialising timer work after successful database backup
     */
    public void reinitializeDbBackupTimers(){

                            //need to cancel and purge as well
        databaseBackupTimer.cancel();
        databaseBackupTimer.purge();
                            //need to delete timer task as well as you can can create only once
        DatabaseBackupTimer.deleteTimerTask();
        databaseBackupTimer=new Timer();
        databaseBackupTimer.schedule(DatabaseBackupTimer.getTimerTask(),fetchBackupScheduleDate());
    }

    /*
            fetch next backup date from properties file
     */
    private Date fetchBackupScheduleDate(){
        return  DatabaseBackupTimer.getBackupScheduleDate();
    }

}
