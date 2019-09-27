package controller.login;

import db.DbBackupService;
import db.UserService;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import main.InventoryConfig;
import main.Main;
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

    @FXML
    private Stage stage;
    @FXML
    private void authenticateUser() throws Exception{
        Stage stage=(Stage) mainAnchorPane.getScene().getWindow();
            //first check for non empty
            if(userNameInput.getText().isEmpty()|| passwordInput.getText().isEmpty()){
                Toast.makeText(stage," ENTER BOTH USERNAME & PASSWORD",1000,500,500);
                return;
            }

        if(new UserService().authenticateUser(userNameInput.getText(),passwordInput.getText())){
                                // if authenticated
            Toast.makeText(stage,"LOGIN SUCCESSFUL! WElCOME ADMIN",1000,500,500);
            Parent root = FXMLLoader.load(getClass().getResource("/view/main/mainPage.fxml"));
            mainAnchorPane.getChildren().setAll(root);
            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    new InventoryTimers().initializeTimers();
                    InventoryConfig.getInstance().getAppProperties().setProperty("currentUser",userNameInput.getText());
                }
            });

        }
        else{
            Toast.makeText(stage,"Invalid UserName or Password",1000,500,500);
            return;
        }
    }



    @Override
    public void initialize(URL location, ResourceBundle resources) {
        mainAnchorPane.setPrefWidth(Main.WIDTH);
        mainAnchorPane.setPrefHeight(Main.HEIGHT);
        Text text = new Text(" Here at PROTOTYPE TECHNOLOGIES we provide complete solutions for your need. \n We try our best to match your requirements and build a new level" +
                "software which is completely unique and at the same time as per your need");

        text.setFont(Font.font("Monotype Corsiva", 18));
        text.fillProperty().setValue(Paint.valueOf("white"));
        infoTextFlow.getChildren().addAll(text);
        text.setWrappingWidth(500);
        userNameInput.setOnKeyTyped(new EventHandler<KeyEvent>()
        {
            public void handle(final KeyEvent keyEvent)
            {
                Validator.isValidUserNameInput(keyEvent);
            }
        });

        passwordInput.setOnKeyReleased(event -> {
            if (event.getCode() == KeyCode.ENTER){
                try {
                    authenticateUser();
                }
                catch (Exception ex){
                    Stage stage=(Stage) mainAnchorPane.getScene().getWindow();
                    Toast.makeText(stage," Some error Occurred while logging",1000,500,500);
                }
            }
        });

    }
}
