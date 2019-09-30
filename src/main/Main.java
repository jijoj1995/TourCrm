package main;

import com.sun.javafx.application.LauncherImpl;
import constants.InventoryConstants;
import db.BaseConnection;
import db.UserService;
import dto.*;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import org.apache.log4j.PropertyConfigurator;
import org.hibernate.Session;
import org.hibernate.query.Query;
import service.HibernateUtil;

import java.io.*;
import java.nio.file.Paths;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
        try {
            inventoryConfig=InventoryConfig.getInstance();
            //check whether first startup and add admin login access
            new UserService().insertAdminLoginData();
            String log4jConfigFile = "/main/log4j.properties";

            //loading log4j file
            PropertyConfigurator.configure(this.getClass().getResourceAsStream(log4jConfigFile));

            //initialising inventory config object
        }
        catch (Throwable e){
            System.out.println(e.getMessage());
        }

      /*  for (int i = 0; i < COUNT_LIMIT; i++) {
            double progress = (100 * i) / COUNT_LIMIT;
            LauncherImpl.notifyPreloader(this, new Preloader.ProgressNotification(progress));
        }*/
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
      /*  Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
*//*
        String hql = "FROM CoreLeadEntity";
        Query query = session.createQuery(hql);
        List results = query.list();
        System.out.println(results.get(0));*//*

        // Add new Employee object
        CoreBookingEntity emp = new CoreBookingEntity();
        //emp.setCoreLeadId(1);
        emp.setFirstName("ssss");
        emp.setMiddleName("demo");
        emp.setLastName("joseph");

        ArrayList<CoreBookingPassengerEntity> notesEntitySet=new ArrayList<>();


        for(int i=0;i<3;i++) {
            CoreBookingPassengerEntity passengerEntity = new CoreBookingPassengerEntity();
            passengerEntity.setFirstName("bbbbbbbbb");
            notesEntitySet.add(passengerEntity);

        }
        //coreLeadCommunication.setCoreLeadEntity(emp);
        emp.setCoreBookingPassengerEntities(notesEntitySet);
        session.save(emp);
        session.getTransaction().commit();
        HibernateUtil.shutdown();
*/



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
                    FileOutputStream out = new FileOutputStream(Paths.get(".").toAbsolutePath().normalize().toString()+"/src/"+ InventoryConstants.appPropertiesFile);
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
