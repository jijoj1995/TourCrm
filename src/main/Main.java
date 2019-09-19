package main;

import com.sun.javafx.application.LauncherImpl;
import constants.InventoryConstants;
import db.BaseConnection;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.application.Preloader;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.Border;
import javafx.scene.layout.VBox;
import javafx.scene.text.TextAlignment;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.Window;
import javafx.stage.WindowEvent;
import org.apache.log4j.PropertyConfigurator;
import service.Validator;
import timers.InventoryTimers;

import java.io.*;
import java.nio.file.Paths;
import java.sql.Connection;

public class Main extends Application {

    public static double WIDTH = 800;
    public static double SIDE_BAR_WIDTH = 200;
    public static double HEIGHT = 300;


    // Just a counter to create some delay while showing preloader.
    private static final int COUNT_LIMIT = 200000;

    private static int stepCount = 1;
    Parent root;
    // Used to demonstrate step couns.
    public static String STEP() {
        return stepCount++ + ". ";
    }

    private Stage applicationStage;
    InventoryConfig inventoryConfig=null;


    public static void main(String[] args) {
        /*LauncherImpl.launchApplication(Main.class, MyPreloader.class, args);*/
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
      /*  for (int i = 0; i < COUNT_LIMIT; i++) {
            double progress = (100 * i) / COUNT_LIMIT;
            LauncherImpl.notifyPreloader(this, new Preloader.ProgressNotification(progress));
        }*/
    }

    @Override
    public void start(Stage primaryStage) throws Exception {

        //loading log4j file
        String log4jConfigFile = "/main/log4j.properties";
        PropertyConfigurator.configure(this.getClass().getResourceAsStream(log4jConfigFile));

                            //check whether the product is original
        if(!Validator.validateProduct()){
            root = FXMLLoader.load(getClass().getResource("/view/main/UnauthorizedPage.fxml"));
            primaryStage.setTitle("Product Validation Failed");
        }
        else {
            root = FXMLLoader.load(getClass().getResource("/view/login/loginPage.fxml"));
            primaryStage.setTitle("Inventory Management");
        }


                //initialising inventory config object
        inventoryConfig=InventoryConfig.getInstance();

        primaryStage.getIcons().add(new Image(this.getClass().getResourceAsStream("/resource/images/logomain.png")));

                                                           //initialising conn object
        System.out.println("going to fetch connection object");
        Connection connection=BaseConnection.getDBConnection();
        BaseConnection.sqlCleanup(connection,null,null);

                                                         //fetching window size
        Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
        HEIGHT=screenBounds.getHeight();
        WIDTH=screenBounds.getWidth();

        primaryStage.setScene(new Scene(root, screenBounds.getWidth(), screenBounds.getHeight()-40));
        primaryStage.setMaximized(true);
      //  primaryStage.setResizable(false);

                                                       //close properly on window close button
        primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent t) {
                                              //save updated application Properties before exit
                try {
                    FileOutputStream out = new FileOutputStream(Paths.get(".").toAbsolutePath().normalize().toString()+"\\src\\"+ InventoryConstants.appPropertiesFile);
                    inventoryConfig.getAppProperties().store(out, null);
                    out.close();
                }
                catch (Exception e){
                    e.printStackTrace();
                }
                Platform.exit(); System.exit(0); }
        });
        primaryStage.show();
    }

}
