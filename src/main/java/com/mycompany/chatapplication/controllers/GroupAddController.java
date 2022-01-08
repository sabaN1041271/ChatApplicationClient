/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.mycompany.chatapplication.controllers;

import com.mycompany.chatapplication.App;
import com.mycompany.chatapplication.factories.JSONToObjectConvertorFactory;
import com.mycompany.chatapplication.models.*;
import java.net.URL;
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
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author ntu-user
 */
public class GroupAddController implements Initializable {
    
    @FXML private TextField txtGroupName;
    @FXML private Label lblGroupNameEmpty;
    @FXML private TextField txtDescription;
    private UserDetails loggedInUserDetails;
    
    public UserDetails getLoggedInUserDetails() {
        return loggedInUserDetails;
    }
      
    public void setLoggedInUserDetails(UserDetails newloggedInUserDetails){
        this.loggedInUserDetails = newloggedInUserDetails;
    }
  
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
   @FXML
 public void btnCreateClick(ActionEvent event) throws Exception{
     
     if(txtGroupName.getText().isEmpty()){
         lblGroupNameEmpty.setText("Groupname cannot be empty.");
         lblGroupNameEmpty.setVisible(true);
         //showAlertBox(Alert.AlertType.ERROR,owner,"Login Form Error","Username cannot be empty.");
         return;
     }
     else{
         lblGroupNameEmpty.setText("");
         lblGroupNameEmpty.setVisible(false);
     }
      JSONToObjectConvertorFactory factory = new JSONToObjectConvertorFactory();
        
        GroupDetailsList groupList = (GroupDetailsList) factory.createObject("groupDetails").convertJSONToObject("");
        List<Object> objList = new ArrayList<Object>();
        for(int i=0; i< groupList.GroupList.size();i++){
           objList.add(groupList.GroupList.get(i));
        }
        int totalCount = groupList.GroupList.size();
        int groupIDofLastElement = groupList.GroupList.get(totalCount-1).getGroupID();
        int groupID = groupIDofLastElement +1;
        
        GroupDetails group = new GroupDetails();
        group.setGroupID(groupID);
        group.setGroupName(txtGroupName.getText());
        group.setGroupDescription(txtDescription.getText());
                
        objList.add(group);
        Boolean success = factory.createObject("groupDetails").writeObjectToJSON(objList, "");
        if(success){
          //  App.setRoot("Dashboard");
             FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("Dashboard.fxml"));
             DashboardController dashboardController = new DashboardController();                       
                        dashboardController.setLoggedInUserDetails(loggedInUserDetails);
                        fxmlLoader.setController(dashboardController);
                        Parent root = (Parent)fxmlLoader.load();
                        Stage stage = new Stage();
                        stage.setTitle("Dashboard");
                        stage.setScene(new Scene(root, 650, 450));
                        stage.show();
                        // Hide this current window (if this is what you want)
                        ((Node)(event.getSource())).getScene().getWindow().hide();
        }
        
 
 }
 
  @FXML
    public void btnCancelClick(ActionEvent event) throws Exception{
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("Dashboard.fxml"));
          DashboardController dashboardController = new DashboardController();                       
                        dashboardController.setLoggedInUserDetails(loggedInUserDetails);
                        fxmlLoader.setController(dashboardController);
                        Parent root = (Parent)fxmlLoader.load();
                        Stage stage = new Stage();
                        stage.setTitle("Dashboard");
                        stage.setScene(new Scene(root, 650, 450));
                        stage.show();
                        // Hide this current window (if this is what you want)
                        ((Node)(event.getSource())).getScene().getWindow().hide();
    }
    
}
