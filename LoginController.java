package com.example.daycareapp;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.File;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ResourceBundle;

public class LoginController implements Initializable {

    @FXML
    private Button loginBtn;
    @FXML
    private Button createNewUserBtn;
    @FXML
    private Label loginMsgLbl;
    @FXML
    private ImageView logoImageView;
    @FXML
    private ImageView lockImageView;
    @FXML
    private TextField usernameTxtFld;
    @FXML
    private PasswordField passwordTxtFld;
    @FXML
    private Label registrationMsgLbl;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        File brandingFile = new File("src/main/resources/com/example/daycareapp/images/logo.png");
        Image brandingImage = new Image(brandingFile.toURI().toString());
        logoImageView.setImage(brandingImage);

        File lockFile = new File("src/main/resources/com/example/daycareapp/images/lock_icon.png");
        Image lockImage = new Image(lockFile.toURI().toString());
        lockImageView.setImage(lockImage);
    }

    public void loginBtnOnAction() {

        if (usernameTxtFld.getText().isBlank() == false && passwordTxtFld.getText().isBlank() == false) {
            validateLogin();
        } else {
            loginMsgLbl.setText("Please enter username and password");
        }
    }

    public void createNewUserBtnOnAction() throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("Register.fxml"));
        Scene registerScene = new Scene(fxmlLoader.load(), 800, 600);
        Stage window = (Stage) createNewUserBtn.getScene().getWindow();
        window.setScene(registerScene);
    }

    /*
    // this method creates a new stage, whereas the above just changes the scene
    public void createNewUserBtnOnAction(){
        try{
            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("Register.fxml"));
            Scene registerScene = new Scene(fxmlLoader.load(), 800, 600);
            Stage registerStage = new Stage();
            registerStage.setTitle("Roskilde Daycare - User Registration");
            registerStage.setScene(registerScene);
            registerStage.show();


        }catch (Exception e){
            e.printStackTrace();
            e.getCause();

        }
    }*/

    public void validateLogin() {
        DatabaseConnection connectNow = new DatabaseConnection();
        Connection connectDB = connectNow.getConnection();

        String verifyLogin = "SELECT COUNT(1) FROM users WHERE username = '" + usernameTxtFld.getText() + "' AND password = '" + passwordTxtFld.getText() + "'";

        try {
            Statement statement = connectDB.createStatement();
            ResultSet queryResult = statement.executeQuery(verifyLogin);

            while (queryResult.next()) {
                if (queryResult.getInt(1) == 1) {
                    goToMainMenu();
                } else {
                    loginMsgLbl.setText("Invalid login. Please try again");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            e.getCause();
        }
    }

    public void goToMainMenu() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("MainMenu.fxml"));
            Scene MainMenuScene = new Scene(fxmlLoader.load(), 800, 600);
            Stage window = (Stage) loginBtn.getScene().getWindow();
            window.setScene(MainMenuScene);


        } catch (Exception e) {
            e.printStackTrace();
            e.getCause();

        }
    }
}