/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package constants;

/**
 *
 * @author jijo
 */
public class InventoryConstants {
    
    public static final String companyName="WADHWA ELECTRIC & ELECTRONIC";
    public static final String companyAddress1="MAIN CHOWK GUHLA ROAD";
    public static final String companyAddress2="CHEEKA 136034,DISTT. KAITHAL(HRY.)";
    public static final String companyAddress3="DEALS IN ALL TYE OF ELECTRICAL GOODS";
    public static final String dateFormat="dd-MM-yy";
    public static final String dateTimeFormat="dd-MM-yy hh:mm:ss";
    public static final String FileNamedateTimeFormat="dd_MM_yy-HH_mm_ss";
    public static final String databaseDateTimeFormat="yyyy-MM-dd HH:mm:ss";
    public static final Long oneDaysValue=1000*60*60*24L;
    public static final Long sevenDaysValue=1000*60*60*24*7L;
    public static final String appPropertiesFile="/resource/properties/applicationProp.properties";
    public static final String productionPropertiesFilename="applicationProp.properties";
    public static final String productionPropertiesFileLocation="/applicationProp.properties";
    public static final String productionPropertiesFolder="/resource/";
    public static final String landlineRegex="^\\(?(\\d{3})\\)?[- ]?(\\d{3})[- ]?(\\d{4})$";
    public static final String emailRegex="^(.+)@(.+)$";
    public static final String dashboardPage="dashboard";
    public static final String mainStructurePage="main";
    public static final String profilePage="profile";
    public static final String loginpage="login";
    public static final String mainQueryPage="mainQuery";
    public static final String subQueryPage="subQuery";

    public static final int userInsertionSuccess=1;
    public static final int userInsertionFailed=0;
    public static final int userInsertionConstraintViolation=2;
    public static final int queryInsertionSuccess=1;
    public static final int queryInsertionFailed=0;
    public static final int queryInsertionEmailFailed=2;
    public static final int loginSuccess=1;
    public static final int loginFailed=0;


    
    
}
