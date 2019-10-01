package main;

import constants.InventoryConstants;
import org.apache.log4j.Logger;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
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
            inventoryConfig.setAppProperties(inventoryConfig.getProperties(InventoryConstants.appPropertiesFile));
    }


    private synchronized Properties getProperties(String fileName) {
        InputStream in = this.getClass()
                .getResourceAsStream(fileName);
        logger.info("Successfully opened the input stream for properties :: " + fileName);
        Properties props = new Properties();
        try {
            props.load(in);
            in.close();
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

}