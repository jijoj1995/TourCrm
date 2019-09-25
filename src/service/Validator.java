package service;

import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.DateFormat;
import java.text.ParseException;
import java.time.LocalDate;
import java.util.Date;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Validator {

    private static final String customerRegisteredMac="68-5D-43-51-47-45 E0-DB-55-E2-E5-86 F0-76-1C-CA-DA-8A 1C-65-9D-58-A4-97 06-1F-3A-35-93-CC 6C-F0-49-D2-C2-66";
    private static final Logger log=Logger.getLogger(Validator.class.getName());

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
                log.info("mac found: "+mm.group(1));
                fetchedMacAddress=mm.group(1);
                break;
            }
        }
        if(customerRegisteredMac.contains(fetchedMacAddress)){
            log.info("product has been verified");
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

    /**
     * Validate an String value for range, mostly used for data captured from forms
     * @param s pass in the string to be validated
     * @return boolean
     */



    /**
     * Validate if a string is a long value
     * @param s
     * @return
     */
    public static boolean isLong(String s) {
        if (!Validator.isNullOrEmpty(s)) {
            try {
                Long i = new Long(s);
            } catch (NumberFormatException e) {
                return false;
            }
        } else {
            return false;
        }
        return true;
    }

    /**
     * Validate if a string is double value
     * @param s
     * @return
     */
    public static boolean isDouble(String s) {
        if (!Validator.isNullOrEmpty(s)) {
            try {
                double d = Double.parseDouble(s);
            } catch (NumberFormatException e) {
                return false;
            }
        } else {
            return false;
        }
        return true;
    }


    public static boolean isFloat(String s) {
        if (!Validator.isNullOrEmpty(s)) {
            try {
                float d = Float.parseFloat(s);
            } catch (NumberFormatException e) {
                return false;
            }
        } else {
            return false;
        }
        return true;
    }
    /**
     * Validate is a string is a date value
     * @param s
     * @return
     */
    public static boolean isDate(String s) {
        if (!Validator.isNullOrEmpty(s)) {
            try {
                DateFormat f = DateFormat.getDateInstance(DateFormat.SHORT);
                Date d = f.parse(s);
                if (d == null) {
                    return false;
                }

            } catch (ParseException e) {
                return false;
            }
        } else {
            return false;
        }
        return true;

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

   /* public static LocalDate getLocalDateFromString(String s) {

        return true;

    }*/
}
