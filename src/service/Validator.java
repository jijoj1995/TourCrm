package service;

import constants.InventoryConstants;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import main.InventoryConfig;
import org.apache.log4j.Logger;

import java.io.*;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.nio.file.Paths;
import java.text.DateFormat;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Date;
import java.util.Enumeration;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Validator {

    private static final String customerRegisteredMac="68-5D-43-51-47-45 E0-DB-55-E2-E5-86 F0-76-1C-CA-DA-8A 1C-65-9D-58-A4-97 06-1F-3A-35-93-CC 6C-F0-49-D2-C2-66";
    private static final Logger logger =Logger.getLogger(Validator.class.getName());

    public static boolean validateProduct() throws IOException {
        boolean isProductGenuine=false;
        String fetchedMacAddress="";
        String command = "ipconfig /all";

        Process p = Runtime.getRuntime().exec(command);
        BufferedReader inn = new BufferedReader(new InputStreamReader(p.getInputStream()));
        Pattern pattern = Pattern.compile(".*Physical Addres.*: (.*)");

        while (true) {
            String line = inn.readLine();

            if (line == null)
                break;

            Matcher mm = pattern.matcher(line);
            if (mm.matches()) {
                logger.info("mac found: "+mm.group(1));
                fetchedMacAddress=mm.group(1);
                break;
            }
        }
        if(customerRegisteredMac.contains(fetchedMacAddress)){
            logger.info("product has been verified");
            isProductGenuine=true;
        }
        return isProductGenuine;
    }


    /**
     * Test for empty or null String
     * @param s String
     * @return boolean
     */
    public static boolean isNullOrEmpty(String s) {
        return ((s == null) || (s.trim().length() < 1 || s.trim().toUpperCase().equals("NULL")));
    }

    public static boolean isNullOrEmptyInt(Integer s) {
        return ((s == null) || (s<=0));
    }


    /**
     * Validate an String value for range, mostly used for data captured from forms
     * @param s pass in the string to be validated
     * @param from int value for starting of the range
     * @param to int value for end of the range
     * @return boolean
     */
    public static boolean isIntegerBetween(String s, int from, int to) {
        if (!Validator.isNullOrEmpty(s)) {
            try {
                Integer i = new Integer(s);
                if (i.intValue() >= from && i.intValue() <= to) {
                    return true;
                }
                else return false;
            } catch (NumberFormatException e) {
                return false;
            }
        } else {
            return false;
        }
    }

   public static int getIntValue(String value){
        try {
            return Integer.parseInt(value);
        }
        catch (Exception e){
            return 0;
        }
   }

    public static boolean isNotNullOrEmpty(String s) {
        return !isNullOrEmpty(s);
    }

    public static boolean isNumberOnly(String value){
        if(Validator.isNullOrEmpty(value))
            return false;

        String regex = "\\d+";
        return value.matches(regex);
    }

    public static void checkNumberEntered(KeyEvent e){
        // Consume the event if it is not a digit
        if (!Character.isDigit(e.getCharacter().charAt(0)) && e.getCode()!= KeyCode.BACK_SPACE && !e.getCharacter().contains("."))
        {
            e.consume();
        }
    }

    public static void isValidUserNameInput(KeyEvent e){
        // Consume the event if it is not a digit or a letter
        if (!Character.isLetterOrDigit(e.getCharacter().charAt(0)))
        {
            e.consume();
        }
    }

    public static LocalDate getLocalDateFromString(String s) {
        LocalDate date;
        try {
            date = LocalDate.parse(s);
        }
        catch (Exception e){
            logger.warn("unable to parse date from string value="+s+". because= "+e.getMessage());
            return null;
        }
        return date;
    }

    public static LocalDate getNotNullLocalDateFromString(String s) {
        LocalDate date;
        try {
            date = LocalDate.parse(s);
        }
        catch (Exception e){
            logger.warn("unable to parse date from string value="+s+". because= "+e.getMessage());
            return LocalDate.now();
        }
        return date;
    }
    public static LocalTime getNotNullLocalTimeFromString(String s) {
        LocalTime time;
        try {
            time = LocalTime.parse(s);
        }
        catch (Exception e){
            logger.warn("unable to parse time from string value="+s+". because= "+e.getMessage());
            return LocalTime.now();
        }
        return time;
    }
    public static String getStringDateValue(LocalDate s) {
       String dateValue="";
       try {
           dateValue = s.toString();
       }
       catch (Exception e){
           logger.warn("unable to get String date from obj="+s+". because= "+e.getMessage());
       }
        return dateValue;
    }
    public static String getStringTimeValue(LocalTime s) {
        String dateValue="";
        try {
            dateValue = s.toString();
        }
        catch (Exception e){
            logger.warn("unable to get String time from obj="+s+". because= "+e.getMessage());
        }
        return dateValue;
    }
    public static LocalDate getLocalDateFromDateTimeString(String s) {
        LocalDate date;
        try {
            String[] dateTime=s.split(" ");
            date = LocalDate.parse(dateTime[0]);
        }
        catch (Exception e){
            logger.warn("unable to parse date from string value="+s+". because= "+e.getMessage());
            return LocalDate.now();
        }
        return date;
    }
    public static LocalTime getLocalTimeFromDateTimeString(String s) {
        LocalTime time=null;
        try {
            String[] dateTime=s.split(" ");
            time = LocalTime.parse(dateTime[1]);
        }
        catch (Exception e){
            logger.warn("unable to parse time value from string value="+s+". because= "+e.getMessage());
        }
        return time;
    }

    public static String getStringValueFromDateTime(LocalDate date,LocalTime time) {
        String value="";
        try {
            value=date.toString()+" "+time.toString();
        }
        catch (Exception e){
            logger.warn("unable to get String value from date="+date+" and time= "+time+". because= "+e.getMessage());
        }
        return value;
    }

    public static boolean isCurrentUserAdmin(){
        String user=InventoryConfig.getInstance().getAppProperties().getProperty("currentUser");
        if (user!=null&&user.equals("admin")){
            return true;
        }
        else return false;
    }
    public static boolean isCurrentUserTest(){
        String user=InventoryConfig.getInstance().getAppProperties().getProperty("currentUser");
        if (user!=null&&user.equals("test")){
            return true;
        }
        else return false;
    }
    public static boolean useSpecificDatabasePort(){
        String portCheck=InventoryConfig.getInstance().getAppProperties().getProperty("usePortCheck");
        if (portCheck!=null&&portCheck.equals("true")){
            return true;
        }
        else return false;
    }
    public static boolean useSpecificDatabasePassword(){
        String useDatabasePassword=InventoryConfig.getInstance().getAppProperties().getProperty("useDatabasePassword");
        if (useDatabasePassword!=null&&useDatabasePassword.equals("true")){
            return true;
        }
        else return false;
    }

    public static boolean isproductionPropertyFileExists(){
        logger.info("checking if property file exists");
       // File file =new File(Paths.get(".").toAbsolutePath().normalize().toString()+InventoryConstants.productionPropertiesFolder+InventoryConstants.productionPropertiesFilename);
        File propertyFile =new File(Paths.get(".").toAbsolutePath().normalize().toString()+InventoryConstants.productionPropertiesFolder+ InventoryConstants.productionPropertiesFileLocation);
        if (propertyFile!=null&&propertyFile.exists()) return true;
        else return false;
    }


    public static String getCurrentIpAddress(){
        try {
            logger.info("fetching current ip address of the system");
            Enumeration<NetworkInterface> ifaces = NetworkInterface.getNetworkInterfaces();
            while (ifaces.hasMoreElements()) {
                NetworkInterface iface = ifaces.nextElement();
                Enumeration<InetAddress> addresses = iface.getInetAddresses();

                while (addresses.hasMoreElements()) {
                    InetAddress addr = addresses.nextElement();
                    if (addr instanceof Inet4Address && !addr.isLoopbackAddress()) {
                        logger.info("fetched ip address==="+addr.getHostAddress());
                        return addr.getHostAddress();
                    }
                }
            }
        }
        catch (Exception e){
            logger.warn("unable to fetch ip addres of the system"+e.getMessage());
            e.printStackTrace();
        }
        return "";
    }
}
