package controller.users;

import com.gn.global.plugin.ViewManager;
import com.gn.global.util.Alerts;
import com.gn.module.main.Main;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import constants.InventoryConstants;
import db.UserService;
import dto.CoreLead;
import dto.CoreUserEntity;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.TabPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import org.apache.log4j.Logger;
import service.Toast;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class EditUser implements Initializable {
    @FXML
    private StackPane mainPane;
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
        Stage stage = (Stage) mainPane.getScene().getWindow();
        if (!isRequiredFieldsEntered()){
            Toast.makeText(stage,"Enter Required Fields",1000,500,500 );
            return;
        }
        if (coreUserEntity==null)coreUserEntity=new CoreUserEntity();
        coreUserEntity.setFirstName(firstName.getText());
        coreUserEntity.setLastName(lastName.getText());
        coreUserEntity.setEmailAddress(email.getText());
        coreUserEntity.setUserName(userId.getText());
        coreUserEntity.setUserPassword(passwordField.getText());
        int userInsertionResult=userService.saveUserData(coreUserEntity);
        if (userInsertionResult== InventoryConstants.userInsertionSuccess){
            showUserListPage();
        }
        else if (userInsertionResult== InventoryConstants.userInsertionConstraintViolation){
            Alerts.warning("UserName Error","UserName Already assigned");
        }
        else {
            Alerts.warning("Error","Unable to save query data. Please check input values or restart application");
        }
    }

    private boolean isRequiredFieldsEntered(){
        return  !(firstName.getText().isEmpty()||lastName.getText().isEmpty()||passwordField.getText().isEmpty()||email.getText().isEmpty()||userId.getText().isEmpty());
    }

    @FXML
    private void showUserListPage() {
        Main.ctrl.body.setContent(ViewManager.getInstance().loadPage(InventoryConstants.listUsersPage).getRoot());
        Main.ctrl.title.setText("Users List");
    }

}
