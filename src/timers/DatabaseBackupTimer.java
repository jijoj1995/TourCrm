package timers;

import constants.InventoryConstants;
import db.DbBackupService;
import org.apache.log4j.Logger;

import java.io.File;
import java.nio.file.Paths;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimerTask;

public class DatabaseBackupTimer {

    private static TimerTask timerTask;
    private Logger logger=Logger.getLogger(DatabaseBackupTimer.class);

    public static TimerTask getTimerTask() {
        if(timerTask==null){
            timerTask=new TimerTask() {
                @Override
                public void run() {
                 databaseBackupProcess();
                }
            };
        }
        return timerTask;
    }
    public static void deleteTimerTask() {
        timerTask=null;

    }

    private static void databaseBackupProcess(){
        System.out.println("backup database process");
        String workingDirectory=Paths.get(".").toAbsolutePath().normalize().toString();
        String todayDate=new SimpleDateFormat(InventoryConstants.FileNamedateTimeFormat).format(new Date());
        String fileName="\\InventoryBackup"+todayDate+".sql";

        File file=new File(workingDirectory+fileName);
        if(new DbBackupService().automaticDatabaseBackup(file)){

            //set next backup date
            InventoryTimers.getInstance().reinitializeDbBackupTimers();
        }
        else{
            System.out.println("unable to automatic backup database");
        }

    }

    public static Date getBackupScheduleDate(){
        return new DbBackupService().getDatabaseBackupDate();
    }

}
