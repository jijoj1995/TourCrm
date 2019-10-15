package controller.login;

import constants.InventoryConstants;
import db.UserService;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;
import main.InventoryConfig;
import main.Main;
import main.WorkIndicatorDialog;
import org.apache.log4j.Logger;
import service.Toast;
import service.Validator;
import timers.InventoryTimers;

import java.net.URL;
import java.util.ResourceBundle;

public class LoginPage  implements Initializable {

    @FXML
    private AnchorPane mainAnchorPane;
    @FXML
    private TextFlow infoTextFlow;
    @FXML
    private TextField userNameInput;
    @FXML
    private PasswordField passwordInput;
    private WorkIndicatorDialog wd=null;

    private Logger logger=Logger.getLogger(LoginPage.class);


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        mainAnchorPane.setPrefWidth(Main.WIDTH);
        mainAnchorPane.setPrefHeight(Main.HEIGHT);

        Text text = new Text(" Here at PROTOTYPE TECHNOLOGIES we provide complete solutions for your need. \n We try our best to match your requirements and build a new level" +
                "software which is completely unique and at the same time as per your need");

        text.setFont(Font.font("Open Sans", 16));
        //text.setFont(Font.font("Monotype Corsiva", 18));
        text.fillProperty().setValue(Paint.valueOf("white"));
        infoTextFlow.getChildren().addAll(text);
        text.setWrappingWidth(500);
        userNameInput.setOnKeyTyped((keyEvent)->{Validator.isValidUserNameInput(keyEvent);
        });
    }

    @FXML
    private void authenticateUser() throws Exception{
        Stage stage=(Stage) mainAnchorPane.getScene().getWindow();
                                                  //first check for non empty
        if(userNameInput.getText().isEmpty()|| passwordInput.getText().isEmpty()){
            Toast.makeText(stage," ENTER BOTH USERNAME & PASSWORD",1000,500,500);
            return;
        }
                                                  //check first if test user login
        if (isTestUserLogin()){
            logger.info("adding currentUser as= "+userNameInput.getText());
            InventoryConfig.getInstance().getAppProperties().setProperty("currentUser",userNameInput.getText());
            Parent root = FXMLLoader.load(getClass().getResource("/view/main/mainPage.fxml"));
            mainAnchorPane.getChildren().setAll(root);
            return;
        }

        wd = new WorkIndicatorDialog(mainAnchorPane.getScene().getWindow(), "Loading...");
        wd.exec("123", inputParam -> {
            // Thinks to do...
            // NO ACCESS TO UI ELEMENTS!
          //  for (int i=0;i<100000;i++)logger.info("reading values"+1);
           /* if (new UserService().authenticateUser(userNameInput.getText(),passwordInput.getText())){
                return InventoryConstants.loginSuccess;
            }*/
            return InventoryConstants.loginFailed;
        });

        wd.addTaskEndNotification(result -> {
            if((Integer) result==InventoryConstants.loginSuccess){
                // if authenticated
                logger.info("adding currentUser as= "+userNameInput.getText());
                InventoryConfig.getInstance().getAppProperties().setProperty("currentUser",userNameInput.getText());
                //fetching current ip address as well
                InventoryConfig.getInstance().getAppProperties().setProperty("currentIpAddress",Validator.getCurrentIpAddress());
                try {
                    Parent root = FXMLLoader.load(getClass().getResource("/view/main/mainPage.fxml"));
                    mainAnchorPane.getChildren().setAll(root);

                }catch (Exception e){
                    e.printStackTrace();
                }
                Platform.runLater(()-> new InventoryTimers().initializeTimers());
            }
            else{
                Toast.makeText(stage,"Invalid UserName or Password",1000,500,500);
            }
            wd=null; // don't keep the object, cleanup
        });


    }

    private boolean isTestUserLogin(){

        return  (userNameInput.getText()!=null&&passwordInput!=null&&userNameInput.getText().equals("test")&&passwordInput.getText().equals("test"));
    }
}
