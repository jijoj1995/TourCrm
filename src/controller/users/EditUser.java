package controller.users;

import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import db.UserService;
import dto.CoreLead;
import dto.CoreUserEntity;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.TabPane;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import main.Main;
import org.apache.log4j.Logger;
import service.Toast;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class EditUser implements Initializable {
    @FXML
    private AnchorPane mainPane;
    @FXML
    private TabPane userTabs;
    @FXML
    private JFXTextField email,firstName,lastName,userId;
    @FXML
    private JFXPasswordField passwordField;
    private CoreUserEntity coreUserEntity;
    private Logger logger =Logger.getLogger(EditUser.class);
    private UserService userService=new UserService();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initializeDefaultLayout();
    }
    public void initializeCoreUserDto(CoreUserEntity coreUserEntity){
        //this method called when already present userdata is clicked
        this.coreUserEntity=coreUserEntity;
        initializeAllInputTextsFromDto();
    }
    private void initializeAllInputTextsFromDto(){
        if (coreUserEntity==null){
            logger.warn("core user entity is null. Returning");
            return;
        }
        firstName.setText(coreUserEntity.getFirstName());
        lastName.setText(coreUserEntity.getLastName());
        email.setText(coreUserEntity.getEmailAddress());
        userId.setText(coreUserEntity.getUserName());
        passwordField.setText(coreUserEntity.getUserPassword());

    }
    @FXML
    private void saveUserData(){
        if (coreUserEntity==null)coreUserEntity=new CoreUserEntity();
        coreUserEntity.setFirstName(firstName.getText());
        coreUserEntity.setLastName(lastName.getText());
        coreUserEntity.setEmailAddress(email.getText());
        coreUserEntity.setUserName(userId.getText());
        coreUserEntity.setUserPassword(passwordField.getText());
        if (userService.saveUserData(coreUserEntity)){
            showUserListPage();
        }
        else{
            Stage stage = (Stage) mainPane.getScene().getWindow();
            Toast.makeText(stage,"Unable to save query data. Please check input values or restart application",1000,500,500 );
        }

    }

    @FXML
    private void showUserListPage() {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/view/users/listUsers.fxml"));
            mainPane.getChildren().setAll(root);
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
    private void initializeDefaultLayout() {
        //setting window size based on screen size
        mainPane.setPrefWidth(Main.WIDTH - Main.SIDE_BAR_WIDTH);
        mainPane.setPrefHeight(Main.HEIGHT - 30);
        double paneWidth = (Main.WIDTH - Main.SIDE_BAR_WIDTH) / 2 - 20;
        userTabs.setTabMinWidth(paneWidth);
        userTabs.setTabMaxWidth(paneWidth);

    }
}
