/*
 * Copyright (C) Gleidson Neves da Silveira
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package com.gn;

import com.gn.global.*;
import com.gn.global.plugin.SectionManager;
import com.gn.global.plugin.UserManager;
import com.gn.global.plugin.ViewManager;
import com.gn.decorator.GNDecorator;
import com.gn.decorator.options.ButtonType;
import com.gn.module.loader.Loader;
import com.gn.module.main.Main;
import com.sun.javafx.application.LauncherImpl;
import constants.InventoryConstants;
import db.UserService;
import javafx.application.Application;
import javafx.application.HostServices;
import javafx.application.Platform;
import javafx.application.Preloader;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import main.InventoryConfig;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.scenicview.ScenicView;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

/**
 * Init the app class.
 * @author Gleidson Neves da Silveira | gleidisonmt@gmail.com
 * Create on  07/10/2018
 * Version 1.0
 */
public class App extends Application {

    private float  increment = 0;
    private float  progress = 0;
    private Section section;
    public static final GNDecorator decorator = new GNDecorator();
    public static final Scene scene = decorator.getScene();
    private Logger logger=Logger.getLogger(App.class);

    public static ObservableList<String>    stylesheets;
    public static HostServices hostServices;
    private static UserDetail userDetail = null;
    private InventoryConfig inventoryConfig=null;

    @Override
    public synchronized void init(){
        section = SectionManager.get();
         User    user;
        if(/*section.isLogged()*/false){
            user = UserManager.get(section.getUserLogged());
            userDetail = new UserDetail(section.getUserLogged(), user.getFullName(), "subtitle");
        } else {
            userDetail = new UserDetail();
        }

        float total = 25; // the difference represents the views not loaded
        increment = 100f / total;


        try {
            //loading log4j file
            String log4jConfigFile = "/main/log4j.properties";
            PropertyConfigurator.configure(this.getClass().getResourceAsStream(log4jConfigFile));
            for (int i = 0; i <20 ; i++) {
                preloaderNotify();
                TimeUnit.MILLISECONDS.sleep(45);
            }
            //initialising inventory config object
            inventoryConfig=InventoryConfig.getInstance();

            //check whether first startup and add admin login access
            new UserService().insertAdminLoginData();
            preloaderNotify();
            preloaderNotify();
            preloaderNotify();
            preloaderNotify();
        }
        catch (Throwable e){
            System.out.println(e.getMessage());
        }


        load("jfoenix", "jfx-text-field");
        load("query", "mainQuery");
        load("query", "listQueries");
        load("query", "subQuery");

        load("booking", "mainBooking");
        load("booking", "subBooking");

        load("users", "listUsers");
        load("users", "editUser");

        load("settings", "settingsList");

        load("designer", "cards");
        load("designer", "banners");
        load("designer", "carousel");
        load("designer", "animated-button");
        load("designer", "alerts");

        load("controls", "button");
        load("controls", "toggle");
        load("controls", "textfield");
        load("controls", "text-area");
        load("controls", "datepicker");
        load("controls", "checkbox");
        load("controls", "radiobutton");
        load("controls", "combobox");
        load("controls", "choicebox");
        load("controls", "splitmenubutton");
        load("controls", "menubutton");
        load("controls", "menubar");
        load("controls", "colorpicker");
        load("controls", "slider");
        load("controls", "spinner");
        load("controls", "progressbar");
        load("controls", "progressindicator");
        load("controls", "pagination");
        load("controls", "mediaview");
        load("controls", "listview");
        load("controls", "label");
        load("controls", "hyperlink");
        load("controls", "imageview");
        load("controls", "tableview");
        load("controls", "scrollbar");
        load("controls", "passwordfield");
        load("controls", "treeview");
        load("controls", "treetableview");

        load("dashboard", "dashboard");

        load("charts", "piechart");
        load("charts", "areachart");
        load("charts", "barchart");
        load("charts", "bubblechart");
        load("charts", "linechart");
        load("charts", "stackedbarchart");
        load("charts", "stackedareachart");
        load("charts", "scatterchart");

        load("main",     "main");

        load("profile", "profile");

        load("login", "login");
        load("login", "account");

//        System.out.println(ViewManager.getInstance().getSize());

        // delay
        try {
            wait(300);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void stop(){
    logger.info("in stop method of main App Class");
        //save updated application Properties before exit
        try {
            FileOutputStream out = new FileOutputStream(Paths.get(".").toAbsolutePath().normalize().toString()+ InventoryConstants.productionPropertiesFolder+InventoryConstants.productionPropertiesFileLocation);
            //                      change location in settings page and test
            inventoryConfig.getAppProperties().store(out, null);
            out.close();
            logger.info("property file updated successfully at shutdown");
        }
        catch (Exception e){
            e.printStackTrace();
        }
        Platform.exit(); System.exit(0);
    }




    public static GNDecorator getDecorator(){
        return decorator;
    }

    private void configServices(){
        hostServices = getHostServices();
    }

    private void initialScene(){

        decorator.setTitle("Tour Crm");
//        decorator.setIcon(null);
        decorator.addButton(ButtonType.FULL_EFFECT);
        decorator.initTheme(GNDecorator.Theme.DEFAULT);

        String log = logged();
        assert log != null;

        try {
            App.decorator.setContent(ViewManager.getInstance().loadPage("login").getRoot());
        }
        catch (Exception e){
            e.printStackTrace();
        }
        section.setLogged(false);
        SectionManager.save(section);
        userDetail.getPopOver().hide();
        if(Main.popConfig.isShowing()) Main.popConfig.hide();
        if(Main.popup.isShowing()) Main.popup.hide();
        App.decorator.removeCustom(userDetail);

    }

    @Override
    public  void start(Stage primary) {

        configServices();
        initialScene();

        stylesheets = decorator.getScene().getStylesheets();

        stylesheets.addAll(
                getClass().getResource("/com/gn/theme/css/fonts.css").toExternalForm(),
                getClass().getResource("/com/gn/theme/css/material-color.css").toExternalForm(),
                getClass().getResource("/com/gn/theme/css/skeleton.css").toExternalForm(),
                getClass().getResource("/com/gn/theme/css/light.css").toExternalForm(),
                getClass().getResource("/com/gn/theme/css/bootstrap.css").toExternalForm(),
                getClass().getResource("/com/gn/theme/css/shape.css").toExternalForm(),
                getClass().getResource("/com/gn/theme/css/typographic.css").toExternalForm(),
                getClass().getResource("/com/gn/theme/css/helpers.css").toExternalForm(),
                getClass().getResource("/com/gn/theme/css/master.css").toExternalForm()
        );

        decorator.setMaximized(true);
        decorator.getStage().getIcons().add(new Image("/com/gn/module/media/logomain.png"));
        decorator.show();

//        ScenicView.show(decorator.getScene());
    }

    public static void main(String[] args) {
        LauncherImpl.launchApplication(App.class, Loader.class, args);
    }

    private void load(String module, String name){
        try {
          //  FXMLLoader loader=new FXMLLoader();
            //loader.setLocation(getClass().getResource("/com/gn/module/" + module + "/" + name + ".fxml"));
            ViewManager.getInstance().put(name,"/com/gn/module/" + module + "/" + name + ".fxml" );
            preloaderNotify();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private synchronized void preloaderNotify() {
        progress += increment;
        LauncherImpl.notifyPreloader(this, new Preloader.ProgressNotification(progress));
    }

    @SuppressWarnings("ResultOfMethodCallIgnored")
    private String logged(){
        try {
            File file = new File("dashboard.properties");
            Properties properties = new Properties();

            if(!file.exists()){
                file.createNewFile();
                return "account";
            } else {
                FileInputStream fileInputStream = new FileInputStream(file);
                properties.load(fileInputStream);
                properties.putIfAbsent("logged", "false");
                FileOutputStream fileOutputStream = new FileOutputStream(file);
                properties.store(fileOutputStream, "Dashboard properties");

                File directory = new File("user/");
                properties.load(fileInputStream);
                if(directory.exists()){
                    if(properties.getProperty("logged").equals("false"))
                        return "login";
                    else
                        return "main";
                } else
                    return "account";
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static UserDetail getUserDetail() {
        return userDetail;
    }
}
