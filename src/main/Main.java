package main;

import com.sun.javafx.application.LauncherImpl;
import constants.InventoryConstants;
import db.UserService;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.application.Preloader;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.text.Font;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import java.io.*;
import java.nio.file.Paths;

public class Main extends Application {

    public static double WIDTH = 800;
    public static double SIDE_BAR_WIDTH = 200;
    public static double HEIGHT = 300;
    // Just a counter to create some delay while showing preloader.
    private static final int COUNT_LIMIT = 200000;

    private static int stepCount = 1;
    private Logger logger=Logger.getLogger(Main.class);
    Parent root;
    // Used to demonstrate step couns.
    public static String STEP() {
        return stepCount++ + ". ";
    }

    private Stage applicationStage;
    InventoryConfig inventoryConfig=null;


    public static void main(String[] args) {
       /* LauncherImpl.launchApplication(Main.class, MyPreloader.class, args);*/
        LauncherImpl.launchApplication(Main.class, args);
    }

    public Main() {
        // Constructor is called after BEFORE_LOAD.
        System.out.println(Main.STEP() + "MyApplication constructor called, thread: " + Thread.currentThread().getName());
    }

    @Override
    public void init() throws Exception {
        System.out.println(Main.STEP() + "MyApplication#init (doing some heavy lifting), thread: " + Thread.currentThread().getName());

        // Perform some heavy lifting (i.e. database start, check for application updates, etc. )
        try {
            //loading log4j file
            String log4jConfigFile = "/main/log4j.properties";
            PropertyConfigurator.configure(this.getClass().getResourceAsStream(log4jConfigFile));
            //initialising inventory config object
            inventoryConfig=InventoryConfig.getInstance();
            //check whether first startup and add admin login access
            new UserService().insertAdminLoginData();
        }
        catch (Throwable e){
            System.out.println(e.getMessage());
        }

        for (int i = 0; i < COUNT_LIMIT; i++) {
            double progress = (100 * i) / COUNT_LIMIT;
            LauncherImpl.notifyPreloader(this, new Preloader.ProgressNotification(progress));
        }
    }

    @Override
    public void start(Stage primaryStage) throws Exception {


                            //check whether the product is original
        if(/*!Validator.validateProduct()*/false){

            root = FXMLLoader.load(getClass().getResource("/view/main/UnauthorizedPage.fxml"));
            primaryStage.setTitle("Product Validation Failed");
        }
        else {
            root = FXMLLoader.load(getClass().getResource("/view/login/loginPage.fxml"));
            primaryStage.setTitle("CRM");
        }

        primaryStage.getIcons().add(new Image(this.getClass().getResourceAsStream("/resource/images/logomain.png")));


                                                         //fetching window size
        Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
        HEIGHT=screenBounds.getHeight();
        WIDTH=screenBounds.getWidth();
        Scene scene=new Scene(root, screenBounds.getWidth(), screenBounds.getHeight()-40);
        primaryStage.setScene(scene);
        primaryStage.setMaximized(true);
      //  primaryStage.setResizable(false);

                                                       //close properly on window close button
        primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent t) {
                                              //save updated application Properties before exit
                try {
                        FileOutputStream out = new FileOutputStream(Paths.get(".").toAbsolutePath().normalize().toString()+InventoryConstants.productionPropertiesFolder+InventoryConstants.productionPropertiesFileLocation);
        //                      change location in settings page and test
                        inventoryConfig.getAppProperties().store(out, null);
                        out.close();
                    logger.info("property file updated successfully at shutdown");
                }
                catch (Exception e){
                    e.printStackTrace();
                }
                Platform.exit(); System.exit(0); }
        });
        primaryStage.show();
    }

}
