/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.mycompany.chatapplication.controllers;


import com.mycompany.chatapplication.App;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Window;
import com.mycompany.chatapplication.models.UserDetailsList;
import javafx.scene.control.Label;
import com.mycompany.chatapplication.factories.JSONToObjectConvertorFactory;
import com.mycompany.chatapplication.helpers.ChatClient;
import com.mycompany.chatapplication.helpers.PasswordEncryptionAES;
import com.mycompany.chatapplication.models.UserDetails;
import java.io.IOException;
import java.net.Socket;
import java.net.URL;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.Initializable;
/**
 *
 * @author ntu-user
 */
public class LoginController implements Initializable{
 @FXML
 private TextField txtUserName;
 
 @FXML
 private PasswordField password;
 
 @FXML
 private Button btnLogin;
 
 @FXML
 private Label lblUsernameEmpty;
 
 @FXML
 private Label lblPasswordEmpty;
 
 @FXML
 private ImageView imgView;
 
 public UserDetailsList userList;
  private ChatClient client;
 
 
 
 @FXML
 public void login(ActionEvent event) throws Exception{
     
     
     Window owner = btnLogin.getScene().getWindow();
     
     if(txtUserName.getText().isEmpty()){
         lblUsernameEmpty.setText("Username cannot be empty.");
         lblUsernameEmpty.setVisible(true);
         return;
     }
     else{
         lblUsernameEmpty.setText("");
         lblUsernameEmpty.setVisible(false);
     }
     
     if(password.getText().isEmpty()){
          lblPasswordEmpty.setText("Password cannot be empty.");
         lblPasswordEmpty.setVisible(true);
         return;
     }
     else{
         lblPasswordEmpty.setText("");
         lblPasswordEmpty.setVisible(false);
     }
     
     if(!txtUserName.getText().isEmpty() && !password.getText().isEmpty()){
        boolean flag = validateLoginCredential(txtUserName.getText(),password.getText());
        if(flag == true){
             FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("Dashboard.fxml"));
                        DashboardController dashboardController = new DashboardController();
                        UserDetails loggedInUserDetails = new UserDetails();
                         for(int i=0; i< userList.UserList.size();i++){
                                if(userList.UserList.get(i).getUserName().equalsIgnoreCase(txtUserName.getText())){
                                    loggedInUserDetails = userList.UserList.get(i);
                                    break;
                                }
                          }
                         client = new ChatClient(new Socket("localhost",8999),loggedInUserDetails.getUserName());

                        dashboardController.setLoggedInUserDetails(loggedInUserDetails);
                        dashboardController.setChatClient(client);
                        fxmlLoader.setController(dashboardController);
                        Parent root = (Parent)fxmlLoader.load();
                        Stage stage = new Stage();
                        stage.setTitle("Dashboard");
                        stage.setScene(new Scene(root, 650, 450));
                        stage.show();
                        ((Node)(event.getSource())).getScene().getWindow().hide();
        }
        else{
            showAlertBox(Alert.AlertType.ERROR,owner,"Login","Username or password mismatch.");
        }
     }
 }

    private void showAlertBox(Alert.AlertType alertType, Window owner, String login_Form_Error, String message) {
       Alert alert = new Alert(alertType);
       alert.setTitle(login_Form_Error);
       alert.setHeaderText(null);
       alert.setContentText(message);
       alert.initOwner(owner);
       alert.show();
    }

    private boolean validateLoginCredential(String userName, String password) throws Exception {
        boolean flag = false;
        JSONToObjectConvertorFactory factory = new JSONToObjectConvertorFactory();
        
        userList = (UserDetailsList) factory.createObject("userDetails").convertJSONToObject("");
    
        for(int i=0; i< userList.UserList.size();i++){
            
            if(userList.UserList.get(i).getUserName().equalsIgnoreCase(userName))
            {
              final String secretKey = "ssshhhhhhhhhhh!!!!";
      
                String decryptedString = PasswordEncryptionAES.decrypt(userList.UserList.get(i).getPassword(), secretKey) ;
               if(decryptedString.equalsIgnoreCase(password)){
                flag= true;
                }          
            }
        }
       if(flag == true){
           return true;
       }
        return false;
    }   

    @Override
    public void initialize(URL url, ResourceBundle rb) {
//           try {
//            client = new ChatClient(new Socket("localhost",8999)); /// connect on port 8999 for group chat.
//        } catch (IOException ex) {
//            Logger.getLogger(DashboardController.class.getName()).log(Level.SEVERE, null, ex);
//        }
    }

   

}

