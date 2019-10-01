package main;

import constants.InventoryConstants;
import org.apache.log4j.Logger;
import service.Validator;

import java.io.*;
import java.nio.file.Paths;
import java.util.Properties;

public class InventoryConfig {

    private static InventoryConfig inventoryConfig = null;
    private Logger logger = Logger.getLogger(InventoryConfig.class);
    private Properties appProperties = null;


    public static InventoryConfig getInstance() {
        if (inventoryConfig == null) {
            inventoryConfig = new InventoryConfig();
            loadAll();
        }
        return inventoryConfig;
    }

    public static void loadAll() {
        if (inventoryConfig == null) {
            inventoryConfig = new InventoryConfig();
        }
            inventoryConfig.setAppProperties(inventoryConfig.getProperties());
    }


    private synchronized Properties getProperties() {
        //check whether production File Exists
        if (!Validator.isproductionPropertyFileExists()){

            logger.info("Production property file does not exists in system. Creating a new file");
            createProductionPropertyFile();
            generateDefaultproperties();
        }

        String pathName=Paths.get(".").toAbsolutePath().normalize().toString()+InventoryConstants.productionPropertiesFolder+ InventoryConstants.productionPropertiesFilename;
        Properties props = new Properties();
        try {
            InputStream in = new FileInputStream(new File( pathName));
            props.load(in);
            in.close();
            logger.info("Successfully opened the input stream for properties :: ");
        } catch (IOException e) {
            logger.error("error occurred while loading properties file :: " + e.getMessage(), e);
        }
            return props;

    }

    public void updatePropertyFile(){
        try {
            FileOutputStream out = new FileOutputStream(Paths.get(".").toAbsolutePath().normalize().toString() + "\\src\\" + InventoryConstants.appPropertiesFile);
            inventoryConfig.getAppProperties().store(out, null);
            out.close();
            logger.info("Proerty file updated successfully");
        }
        catch (Exception e){
            logger.warn("unable to update property file "+e.getMessage());
        }
    }

    public Properties getAppProperties() {
        return appProperties;
    }

    public void setAppProperties(Properties appProperties) {
        this.appProperties = appProperties;
    }

    public void createProductionPropertyFile(){
        logger.info(" in createProductionPropertyFile method");
        try {
            File propertyFolder = new File(Paths.get(".").toAbsolutePath().normalize().toString() + InventoryConstants.productionPropertiesFolder);
            File file = new File(Paths.get(".").toAbsolutePath().normalize().toString() +InventoryConstants.productionPropertiesFolder+ InventoryConstants.productionPropertiesFileLocation);
            if (!propertyFolder.isDirectory()) {
                propertyFolder.mkdir();
            }
            if (!file.exists()) {
                if (file.createNewFile()) logger.info("production property file created successfully");
                else logger.warn("unable to create production property file");
            }
        }
        catch (Exception e){
            logger.warn("Unable to create production property File, "+e.getMessage());
        }
    }

    public void generateDefaultproperties(){

        InputStream in = this.getClass()
                .getResourceAsStream(InventoryConstants.appPropertiesFile);
        //logger.info("Successfully opened the input stream for properties :: " + fileName);
        Properties props = new Properties();
        try {
            props.load(in);
            in.close();
            logger.info("Default Properties generated successfully");
            FileOutputStream out = new FileOutputStream(Paths.get(".").toAbsolutePath().normalize().toString()+InventoryConstants.productionPropertiesFolder+ InventoryConstants.productionPropertiesFileLocation);
            props.store(out, null);
            out.close();
        } catch (IOException e) {
            logger.error("error occurred while loading properties file :: " + e.getMessage(), e);
        }

    }
}