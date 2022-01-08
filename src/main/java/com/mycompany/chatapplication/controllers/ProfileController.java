/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.chatapplication.controllers;

import com.mycompany.chatapplication.App;
import com.mycompany.chatapplication.factories.JSONToObjectConvertorFactory;
import com.mycompany.chatapplication.helpers.PasswordEncryptionAES;
import com.mycompany.chatapplication.models.UserDetails;
import com.mycompany.chatapplication.models.UserDetailsList;
import java.io.File;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

/**
 *
 * @author ntu-user
 */
public class ProfileController implements Initializable {

    @FXML private TextField txtUserName;
    @FXML private Label lblUserNameEmpty;
    @FXML private PasswordField txtPassword;
    @FXML private Label lblPasswordEmpty;
    @FXML private Pane paneProfilePicture;
    @FXML private Button btnFileUpload;
    @FXML private Label lblFilePath;
    private UserDetails loggedInUserDetails;
    private File selectedFile;
    
    public UserDetails getLoggedInUserDetails() {
        return loggedInUserDetails;
    }
      
    public void setLoggedInUserDetails(UserDetails newloggedInUserDetails){
        this.loggedInUserDetails = newloggedInUserDetails;
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {  
        final String secretKey = "ssshhhhhhhhhhh!!!!";
     
   
    String decryptPassword = PasswordEncryptionAES.decrypt(loggedInUserDetails.getPassword(), secretKey) ;
       txtUserName.setText(loggedInUserDetails.getUserName());
       txtPassword.setText(decryptPassword);
    
    }
    
    public void btnFileUploadClick(ActionEvent event) throws Exception{
                FileChooser fileChooser = new FileChooser();
                Stage thisStage = (Stage) btnFileUpload.getScene().getWindow();
                FileChooser.ExtensionFilter extFilterJPG= new FileChooser.ExtensionFilter("JPG", "*.jpg");
                 FileChooser.ExtensionFilter extFilterPNG= new FileChooser.ExtensionFilter("PNG", "*.png");
                fileChooser.getExtensionFilters().addAll(extFilterJPG,extFilterPNG);
                selectedFile = fileChooser.showOpenDialog(thisStage);
                lblFilePath.setText(selectedFile.getAbsolutePath());
                lblFilePath.setVisible(true);

    }
    
    public void btnSaveClick(ActionEvent event) throws Exception{
        String userName = txtUserName.getText();
        String password = txtPassword.getText();
        
         final String secretKey = "ssshhhhhhhhhhh!!!!";
     
   
    String encryptPassword = PasswordEncryptionAES.encrypt(password, secretKey) ;
        
        
    
        Path copied = Paths.get("src/main/resources/com/mycompany/chatapplication/assets/" + loggedInUserDetails.getUserName());
        if(selectedFile != null){
            Path originalPath = selectedFile.toPath();
            Files.copy(originalPath, copied, StandardCopyOption.REPLACE_EXISTING);
        }
       
        
        JSONToObjectConvertorFactory factory = new JSONToObjectConvertorFactory();
        
        UserDetailsList userList = (UserDetailsList) factory.createObject("userDetails").convertJSONToObject("");
        List<Object> objList = new ArrayList<Object>();
        for(int i=0; i< userList.UserList.size();i++){
           if(userList.UserList.get(i).getUserID() == loggedInUserDetails.getUserID()){
               userList.UserList.get(i).setUserName(userName);
               userList.UserList.get(i).setPassword(encryptPassword);
              
                loggedInUserDetails.setUserName(userName);
                loggedInUserDetails.setPassword(encryptPassword);
                if(selectedFile != null){
                    userList.UserList.get(i).setProfilePictureUrl(copied.toString());
                    loggedInUserDetails.setProfilePictureUrl(copied.toString());
                }                 
           }
           objList.add(userList.UserList.get(i));
        }
   
       Boolean success = factory.createObject("userDetails").writeObjectToJSON(objList, "");
        if(success){
             FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("Dashboard.fxml"));
             DashboardController dashboardController = new DashboardController();                       
                        dashboardController.setLoggedInUserDetails(loggedInUserDetails);
                        fxmlLoader.setController(dashboardController);
                        Parent root = (Parent)fxmlLoader.load();
                        Stage stage = new Stage();
                        stage.setTitle("Dashboard");
                        stage.setScene(new Scene(root, 650, 450));
                        stage.show();
                        ((Node)(event.getSource())).getScene().getWindow().hide();
        }
        
    }
    
}
