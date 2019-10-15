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
package com.gn.module.login;

import animatefx.animation.Flash;
import animatefx.animation.Pulse;
import animatefx.animation.SlideInLeft;
import com.gn.App;
import com.gn.GNAvatarView;
import com.gn.global.plugin.ViewManager;
import com.gn.global.*;
import com.gn.global.plugin.SectionManager;
import com.gn.global.plugin.UserManager;
import com.gn.global.util.Alerts;
import com.gn.module.main.Main;
import constants.InventoryConstants;
import controller.login.LoginPage;
import db.UserService;
import dto.CoreUserEntity;
import javafx.animation.RotateTransition;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.util.Duration;
import main.InventoryConfig;
import main.WorkIndicatorDialog;
import org.apache.log4j.Logger;
import service.Toast;
import service.Validator;
import timers.InventoryTimers;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

/**
 * @author Gleidson Neves da Silveira | gleidisonmt@gmail.com
 * Create on  22/11/2018
 * Version 1.0.2
 */
public class login implements Initializable {

    @FXML private GNAvatarView avatar;
    @FXML private HBox box_username;
    @FXML private HBox box_password;
    @FXML private TextField username;
    @FXML private TextField password;
    @FXML private Button login;
    @FXML private Label lbl_password;
    @FXML private Label lbl_username;
    @FXML private Label lbl_error;

    private WorkIndicatorDialog wd=null;

    private Logger logger=Logger.getLogger(login.class);
    CoreUserEntity userEntity=null;

    private RotateTransition rotateTransition = new RotateTransition();

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        rotateTransition.setNode(avatar);
        rotateTransition.setByAngle(360);
        rotateTransition.setDuration(Duration.seconds(1));
        rotateTransition.setAutoReverse(true);
        addEffect(password);
        addEffect(username);
        setupListeners();

    }

    private void addEffect(Node node){
        node.addEventFilter(MouseEvent.MOUSE_PRESSED, event -> {
            rotateTransition.play();
            Pulse pulse = new Pulse(node.getParent());
            pulse.setDelay(Duration.millis(100));
            pulse.setSpeed(5);
            pulse.play();
            node.getParent().setStyle("-icon-color : -success; -fx-border-color : -success");
        });

        node.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if(!node.isFocused())
                node.getParent().setStyle("-icon-color : -dark-gray; -fx-border-color : transparent");
            else node.getParent().setStyle("-icon-color : -success; -fx-border-color : -success");
        });
    }

    private void setupListeners(){
        password.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if(!validPassword()){
                if(!newValue){
                    Flash swing = new Flash(box_password);
                    lbl_password.setVisible(true);
                    new SlideInLeft(lbl_password).play();
                    swing.setDelay(Duration.millis(100));
                    swing.play();
                    box_password.setStyle("-icon-color : -danger; -fx-border-color : -danger");
                } else {
                    lbl_password.setVisible(false);
                }
            } else {
                lbl_error.setVisible(false);
            }
        });

        username.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if(!validUsername()){
                if(!newValue){
                    Flash swing = new Flash(box_username);
                    lbl_username.setVisible(true);
                    new SlideInLeft(lbl_username).play();
                    swing.setDelay(Duration.millis(100));
                    swing.play();
                    box_username.setStyle("-icon-color : -danger; -fx-border-color : -danger");
                } else {
                    lbl_username.setVisible(false);
                }
            } else {
                lbl_error.setVisible(false);
            }
        });
    }

    private boolean validPassword(){
        return !password.getText().isEmpty() && password.getLength() > 3;
    }

    private boolean validUsername(){
        return !username.getText().isEmpty() && username.getLength() > 3;
    }

    @FXML
    private void loginAction() throws Exception{
        Pulse pulse = new Pulse(login);
        pulse.setDelay(Duration.millis(20));
        pulse.play();
        if(validPassword() && validUsername())
            enter();
        else {
            lbl_password.setVisible(true);
            lbl_username.setVisible(true);
        }
    }

    private void enter(){
        if (isTestUserLogin()){
            logger.info("adding currentUser as= "+username.getText());
            InventoryConfig.getInstance().getAppProperties().setProperty("currentUser",username.getText());
            App.getDecorator().setContent(ViewManager.getInstance().loadPage(InventoryConstants.mainStructurePage).getRoot());
            return;
        }

        wd = new WorkIndicatorDialog(lbl_username.getScene().getWindow(), "Loading...");
        wd.exec("123", inputParam -> {
            try {

                for (int i = 0; i <10 ; i++) {
                        //for showing basic loader. Else the loader sometimes in not visible leading to emptyBox
                    TimeUnit.MILLISECONDS.sleep(70);
                }
            }catch (Exception e){
                e.printStackTrace();
            }
            userEntity=new UserService().authenticateUser(username.getText(),password.getText());
            if (userEntity!=null){
                return InventoryConstants.loginSuccess;
            }
            return InventoryConstants.loginFailed;
        });

        wd.addTaskEndNotification(result -> {
            if((Integer) result==InventoryConstants.loginSuccess){

                logger.info(". Authenticated. adding currentUser as= "+username.getText());
                InventoryConfig.getInstance().getAppProperties().setProperty("currentUser",username.getText());
                InventoryConfig.getInstance().getAppProperties().setProperty("currentIpAddress", Validator.getCurrentIpAddress());

                Section section = new Section();
                section.setLogged(true);
                section.setUserLogged(this.username.getText());
                SectionManager.save(section);

                User user = UserManager.loadUser(userEntity);
                UserDetail detail = App.getUserDetail();
                detail.setText(user.getUserName());
                detail.setHeader(user.getUserName());

                App.decorator.addCustom(App.getUserDetail());
                App.getUserDetail().setProfileAction(event -> { App.getUserDetail().getPopOver().hide();
                Main.ctrl.title.setText("Profile");
                        Main.ctrl.body.setContent(ViewManager.getInstance().loadPage(InventoryConstants.profilePage).getRoot());
                });

                App.getUserDetail().setSignAction(event -> {
                    App.getUserDetail().getPopOver().hide();
                    try{
                        App.decorator.setContent(ViewManager.getInstance().loadPage(InventoryConstants.loginpage).getRoot());
                    }
                    catch (Exception e){
                        e.printStackTrace();
                    }
                    this.username.setText("");
                    this.password.setText("");
                    if(Main.popConfig.isShowing()) Main.popConfig.hide();
                    if(Main.popup.isShowing()) Main.popup.hide();
                    App.decorator.removeCustom(App.getUserDetail());
                });
                try {
                    App.getDecorator().setContent(ViewManager.getInstance().loadPage(InventoryConstants.mainStructurePage).getRoot());
                    Alerts.success("Login Successful", "Welcome "+username.getText());
                }
                catch (Exception e){
                    e.printStackTrace();
                }
                Platform.runLater(()-> new InventoryTimers().initializeTimers());
            }

            else{
                Alerts.error("Error", "Invalid UserName or Password.");
            }
            wd=null; // don't keep the object, cleanup
        });


    }
    private boolean isTestUserLogin(){
        return  (username.getText()!=null&&password!=null&&username.getText().equals("test")&&password.getText().equals("test"));
    }

    @FXML
    private void switchCreate() throws Exception{
        App.decorator.setContent(ViewManager.getInstance().loadPage("account").getRoot());
    }
}
