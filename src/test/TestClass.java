package test;

import java.io.File;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.security.CodeSource;
import java.util.Enumeration;

public class TestClass {

    public static void main(String[] args) {
        try{
        /*NOTE: Getting path to the Jar file being executed*/
        /*NOTE: YourImplementingClass-> replace with the class executing the code*/
        CodeSource codeSource = TestClass.class.getProtectionDomain().getCodeSource();
        File jarFile = new File(codeSource.getLocation().toURI().getPath());
        String jarDir = jarFile.getParentFile().getPath();


        /*NOTE: Creating Database Constraints*/
        String dbName = "test";
        String dbUser = "root";
        String dbPass = "test";

        /*NOTE: Creating Path Constraints for folder saving*/
        /*NOTE: Here the backup folder is created for saving inside it*/
        String folderPath = jarDir + "\\backup";

        /*NOTE: Creating Folder if it does not exist*/
        File f1 = new File(folderPath);
        f1.mkdir();

        /*NOTE: Creating Path Constraints for backup saving*/
        /*NOTE: Here the backup is saved in a folder called backup with the name backup.sql*/
        /*String savePath = "\"" + jarDir + "\\backup\\" + "backup.sql\"";*/
        String savePath = "C:\\Users\\jijo\\Downloads\\mflix-java\\TourCrm\\out\\production\\backup\\backup.sql";
       // File saveFile=new File("C:\\Users\\jijo\\Downloads\\mflix-java\\TourCrm\\out\\production\\backup\\backup.sql");
        //saveFile.createNewFile();
        /*NOTE: Used to create a cmd command*/
        String executeCmd = "C:\\Program Files\\MySQL\\MySQL Workbench 8.0 CE\\mysqldump -u" + dbUser + " -p" + dbPass + " --database " + dbName + " -r " +savePath ;

        /*NOTE: Executing the command here*/
        Process runtimeProcess = Runtime.getRuntime().exec(executeCmd);
        int processComplete = runtimeProcess.waitFor();

        /*NOTE: processComplete=0 if correctly executed, will contain other values if not*/
        if (processComplete == 0) {
            System.out.println("Backup Complete");
        } else {
            System.out.println("Backup Failure");
        }

    } catch (Exception e) {
        e.printStackTrace();
    }

    }
}
