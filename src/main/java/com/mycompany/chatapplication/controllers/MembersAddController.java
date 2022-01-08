/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.chatapplication.controllers;

import com.mycompany.chatapplication.App;
import com.mycompany.chatapplication.factories.JSONToObjectConvertorFactory;
import com.mycompany.chatapplication.models.GroupDetails;
import com.mycompany.chatapplication.models.GroupDetailsList;
import com.mycompany.chatapplication.models.UserDetails;
import com.mycompany.chatapplication.models.UserDetailsList;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 *
 * @author ntu-user
 */
public class MembersAddController implements Initializable {
    
    @FXML private Label lblGroupName;
    @FXML private VBox vBoxMembersAdd;
    @FXML private ComboBox membersComboBox;
    @FXML private ListView addedMemberList;
    private GroupDetails group;
    
    public GroupDetails getGroupIdAndName() {
        return group;
    }
    
    
    
    public void setGroupIdAndName(GroupDetails newGroup){
        this.group = newGroup;
    }
    
    private UserDetails loggedInUserDetails;
    
    public UserDetails getLoggedInUserDetails() {
        return loggedInUserDetails;
    }
      
    public void setLoggedInUserDetails(UserDetails newloggedInUserDetails){
        this.loggedInUserDetails = newloggedInUserDetails;
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        int groupId = group.getGroupID();
        ArrayList<Integer> memberAlreadyMapped = new ArrayList<Integer>();
        ObservableList userNamesAdded = FXCollections.observableArrayList();
        String groupName = group.getGroupName();
        JSONToObjectConvertorFactory factory = new JSONToObjectConvertorFactory();
        UserDetailsList userList = (UserDetailsList) factory.createObject("userDetails").convertJSONToObject("");
        GroupDetailsList groupList = (GroupDetailsList) factory.createObject("groupDetails").convertJSONToObject("");

        for(int i=0; i< groupList.GroupList.size();i++){
            if(groupList.GroupList.get(i).getGroupID() == groupId){
                memberAlreadyMapped = groupList.GroupList.get(i).getUserIds();
            }
            
        }
        if(memberAlreadyMapped != null && memberAlreadyMapped.size() > 0){
            for(int i=0; i< memberAlreadyMapped.size();i++){
               for(int j=0; j< userList.UserList.size();j++){
                    if(userList.UserList.get(j).getUserID()== memberAlreadyMapped.get(i)){
                        userNamesAdded.add(userList.UserList.get(j).getUserName());
                    }            
                }
            }
        }
        addedMemberList.setItems(userNamesAdded);
                             
        lblGroupName.setText("Add member to " + groupName);
        lblGroupName.setStyle("-fx-font-weight:bold;-fx-font-size:24;");       
       
        ObservableList userNames = FXCollections.observableArrayList();
        for(int i=0; i< userList.UserList.size();i++){
            if(memberAlreadyMapped != null)
            {
                if(!memberAlreadyMapped.contains(userList.UserList.get(i).getUserID())){
                 userNames.add(userList.UserList.get(i).getUserName());
                }
            }
            else{
                userNames.add(userList.UserList.get(i).getUserName());
            }
           
        }
        membersComboBox.setItems(userNames);
     
    }
    
    @FXML
    public void btnAddClick(ActionEvent event) throws Exception{
        int groupId = group.getGroupID();
        JSONToObjectConvertorFactory factory = new JSONToObjectConvertorFactory();
        int mappedUserId = 0;
        ArrayList<Integer> memberAlreadyMapped = new ArrayList<Integer>();
        GroupDetailsList groupList = (GroupDetailsList) factory.createObject("groupDetails").convertJSONToObject("");
        UserDetailsList userList = (UserDetailsList) factory.createObject("userDetails").convertJSONToObject("");
        String selectedUser = (String)membersComboBox.getValue();
        for(int i=0; i< userList.UserList.size();i++){
            if(userList.UserList.get(i).getUserName().equalsIgnoreCase(selectedUser)){
                mappedUserId = userList.UserList.get(i).getUserID();
            }
            
        }
        List<Object> objList = new ArrayList<Object>();
        for(int i=0; i< groupList.GroupList.size();i++){
            if(groupList.GroupList.get(i).getGroupID() == groupId){
                memberAlreadyMapped = groupList.GroupList.get(i).getUserIds();
                if(memberAlreadyMapped != null){
                     memberAlreadyMapped.add(mappedUserId);
                }
                else{
                    memberAlreadyMapped = new ArrayList<Integer>();
                    memberAlreadyMapped.add(mappedUserId);
                }
               
                groupList.GroupList.get(i).setUserIds(memberAlreadyMapped);
                
            }
            objList.add(groupList.GroupList.get(i));
        }
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
                        ((Node)(event.getSource())).getScene().getWindow().hide();
    }
    
    
    
}
