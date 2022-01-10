/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.chatapplication.controllers;

import com.mycompany.chatapplication.App;
import com.mycompany.chatapplication.factories.JSONToObjectConvertorFactory;
import com.mycompany.chatapplication.helpers.ChatClient;
import com.mycompany.chatapplication.models.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Accordion;
import javafx.scene.control.Button;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TextField;
import javafx.scene.control.TitledPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.apache.commons.lang.StringEscapeUtils;
/**
 *
 * @author ntu-user
 */
public class DashboardController implements Initializable
{
    @FXML private Accordion accordion;

    @FXML private TitledPane paneDirectMessages;
    
    @FXML private TitledPane paneChannels;
    
    @FXML private ImageView imgView;
    
    @FXML private Label lblNameOfGroupOrUser;
    @FXML private Button btnEditProfile;
    
    @FXML private VBox accordionVBox;
    
    @FXML private Label lblChannelOrUserName;
    @FXML private ScrollPane scrollChatPane;
    @FXML private VBox ChatVBox;
    @FXML private Pane paneAddMember;
    @FXML private Pane paneImageOfReceiver;
    @FXML private Pane paneMembers;
   @FXML private GridPane grdViewActiveMembers ;
    @FXML private Hyperlink hplViewActiveMembers;
    @FXML private Label lblActiveUserList;
    private ChatClient client;
      public ChatClient getChatClient() {
        return client;
    }
      
    public void setChatClient(ChatClient newClient){
        this.client = newClient;
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
        setTitledPanes();
       FileInputStream input = null;
        try {
             input = new FileInputStream(loggedInUserDetails.getProfilePictureUrl());
             Image image = new Image(input);
             imgView.setImage(image); 
             


        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
       
openConversationOfUser(loggedInUserDetails.getUserName(),loggedInUserDetails.getUserID());    
client.receiveMessageFromServer(ChatVBox);
}
    
    public void openProfilePage(ActionEvent event) throws Exception{
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("Profile.fxml"));
                        ProfileController profileController = new ProfileController();
                        profileController.setLoggedInUserDetails(loggedInUserDetails);
                        fxmlLoader.setController(profileController);
                        
                        Parent root = (Parent)fxmlLoader.load();
                        Stage stage = new Stage();
                        stage.setTitle("Edit your profile");
                        stage.setScene(new Scene(root, 600, 400));
                        stage.show();
                        // Hide this current window (if this is what you want)
                        ((Node)(event.getSource())).getScene().getWindow().hide();
    }
    
    private void setTitledPanes() {

      Accordion accordion = new Accordion();
        
      TitledPane paneChannels = new TitledPane();
      paneChannels.setText("Groups");
      
      TitledPane paneDirectMessages = new TitledPane();
      paneDirectMessages.setText("Direct Messages");

      VBox box1 = new VBox(10);
    
     VBox box = new VBox(10);
      
      
        JSONToObjectConvertorFactory factory = new JSONToObjectConvertorFactory();
        
        GroupDetailsList groupList = (GroupDetailsList) factory.createObject("groupDetails").convertJSONToObject("");

        for(int i=0; i< groupList.GroupList.size();i++){
            if(groupList.GroupList.get(i).getUserIds() == null || groupList.GroupList.get(i).getUserIds().contains(loggedInUserDetails.getUserID())){
                var btnId = "button" + "_" + i;
                Hyperlink button_i = new Hyperlink();
                button_i.setText("# "+ groupList.GroupList.get(i).getGroupName());
                button_i.setStyle("-fx-color:white;");
                String labelText = groupList.GroupList.get(i).getGroupName();
                int groupId = groupList.GroupList.get(i).getGroupID();
                button_i.setOnAction(e -> openConversation(labelText,groupId)); 
                box1.getChildren().add(button_i);
            }

            
        }
        
          Hyperlink hpl_AddChannels = new Hyperlink("Add Groups");
          FileInputStream addIcon = null;
            try {
            addIcon = new FileInputStream("src/main/resources/com/mycompany/chatapplication/assets/add.png");
            Image imageAdd = new Image(addIcon);
            ImageView imageViewAdd = new ImageView();
            imageViewAdd.setImage(imageAdd);
            imageViewAdd.setFitHeight(20);
            imageViewAdd.setFitWidth(20);

            hpl_AddChannels.setGraphic(imageViewAdd);           
               } catch (FileNotFoundException e) {
               e.printStackTrace();
               }
 
            hpl_AddChannels.setOnAction(new EventHandler<ActionEvent>() {
                public void handle(ActionEvent event) {
 
                    try {
                        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("GroupAdd.fxml"));
                        GroupAddController groupAddController = new GroupAddController();
                        groupAddController.setLoggedInUserDetails(loggedInUserDetails);
                        fxmlLoader.setController(groupAddController);
                        
                        Parent root = (Parent)fxmlLoader.load();
                        Stage stage = new Stage();
                        stage.setTitle("Create a new group");
                        stage.setScene(new Scene(root, 600, 400));
                        stage.show();
                        // Hide this current window (if this is what you want)
                        ((Node)(event.getSource())).getScene().getWindow().hide();
                    } catch (IOException ex) {
                        //Logger.getLogger(DashboardController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            });
            box1.getChildren().add(hpl_AddChannels);
            paneChannels.setContent(box1);

            
        
        
        
        accordion.getPanes().add(paneChannels);
        
        
        UserDetailsList userList = (UserDetailsList) factory.createObject("userDetails").convertJSONToObject("");
        ContactList contactList = (ContactList) factory.createObject("contactList").convertJSONToObject("");	
        ArrayList<Integer> contactListOfLoggedInUser = new ArrayList<Integer>();	
        	
        for(int j=0; j < contactList.ContactList.size();j++){	
            if(contactList.ContactList.get(j).getUserId() == loggedInUserDetails.getUserID())	
            {	
                contactListOfLoggedInUser.addAll(contactList.ContactList.get(j).getContactIds());	
                break;	
            }	
        }	
         for(int i=0; i< userList.UserList.size();i++){	
            if(contactListOfLoggedInUser.contains(userList.UserList.get(i).getUserID()) || userList.UserList.get(i).getUserID() == loggedInUserDetails.getUserID()){	

            Hyperlink buttonUser = new Hyperlink();
            buttonUser.setText(userList.UserList.get(i).getUserName());
            FileInputStream userImage = null;
            try {
            String imagePath = userList.UserList.get(i).getProfilePictureUrl();
            userImage = new FileInputStream(imagePath);
            Image imageUser = new Image(userImage);
            ImageView imageViewUser = new ImageView();
            imageViewUser.setImage(imageUser);
            imageViewUser.setFitHeight(20);
            imageViewUser.setFitWidth(20);

            buttonUser.setGraphic(imageViewUser);           
               } catch (FileNotFoundException e) {
               e.printStackTrace();
               }
            String labelText = userList.UserList.get(i).getUserName();
            int userId = userList.UserList.get(i).getUserID();
            buttonUser.setOnAction(e -> openConversationOfUser(labelText,userId)); 
            box.getChildren().add(buttonUser);
            paneDirectMessages.setContent(box);
            }
        }
 
          accordion.getPanes().add(paneDirectMessages);
          accordionVBox.getChildren().add(accordion);
          accordionVBox.setStyle("-fx-background-color:grey;");
    }

    private void openConversation(String labelText, int groupId) {
         grdViewActiveMembers.setVisible(true); 
   hplViewActiveMembers.setVisible(true);
        lblChannelOrUserName.setText("#" +labelText);
        lblChannelOrUserName.setStyle("-fx-font-weight:bold;");
        lblChannelOrUserName.setVisible(true);
        paneImageOfReceiver.setVisible(false);
        paneAddMember.setVisible(true);
        ChatVBox.getChildren().clear();
        Hyperlink hpl_AddMembers = new Hyperlink("Add Members");
          FileInputStream addIcon = null;
            try {
            addIcon = new FileInputStream("src/main/resources/com/mycompany/chatapplication/assets/add.png");
            Image imageAdd = new Image(addIcon);
            ImageView imageViewAdd = new ImageView();
            imageViewAdd.setImage(imageAdd);
            imageViewAdd.setFitHeight(20);
            imageViewAdd.setFitWidth(20);

            hpl_AddMembers.setGraphic(imageViewAdd);           
               } catch (FileNotFoundException e) {
               e.printStackTrace();
               }
 
            hpl_AddMembers.setOnAction(new EventHandler<ActionEvent>() {
                public void handle(ActionEvent event) {
 
                    try {
                        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("MembersAdd.fxml"));
                        MembersAddController membersAddController = new MembersAddController();
                        GroupDetails groupMembersAdd = new GroupDetails();
                        groupMembersAdd.setGroupID(groupId);
                        groupMembersAdd.setGroupName("#" + labelText);
                        membersAddController.setLoggedInUserDetails(loggedInUserDetails);
                        membersAddController.setGroupIdAndName(groupMembersAdd);
                        fxmlLoader.setController(membersAddController);
                        Parent root = (Parent)fxmlLoader.load();
                        Stage stage = new Stage();
                        stage.setTitle("Add members to a group");
                        stage.setScene(new Scene(root, 600, 400));
                        stage.show();
                        // Hide this current window (if this is what you want)
                        ((Node)(event.getSource())).getScene().getWindow().hide();
                    } catch (IOException ex) {
                        //Logger.getLogger(DashboardController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            });

         paneAddMember.getChildren().add(hpl_AddMembers);
         JSONToObjectConvertorFactory factory = new JSONToObjectConvertorFactory();
         String fileName = labelText + "_" + "GroupChat";
         VBox vList = new VBox(10);
         File cssfile = new File("src/main/resources/com/mycompany/chatapplication/assets/CSS/dashboard.css");
         
         VBox vVerticalFirst = new VBox(10);
             
             
             Label groupName = new Label();
             groupName.setText("#" + labelText);
             groupName.setContentDisplay(ContentDisplay.LEFT);
             groupName.setLayoutX(14);
             groupName.setLayoutY(27);
             groupName.setStyle("-fx-font-weight:bold;");
             Label groupMessage = new Label();
             
             groupMessage.setText(" This is the very beginning of the #" + labelText  + " group.");           
             
             groupMessage.setAlignment(Pos.TOP_LEFT);
             groupMessage.setWrapText(true);
             groupMessage.setMaxWidth(400);
     
             vVerticalFirst.getChildren().addAll(groupName,groupMessage);
             
             ChatVBox.getChildren().add(vVerticalFirst);
         
         
        
        GroupChatDetailsList groupChatDetails = (GroupChatDetailsList) factory.createObject("groupChat").convertJSONToObject(fileName);
        UserDetailsList userList = (UserDetailsList) factory.createObject("userDetails").convertJSONToObject("");
        List<Object> obj = new ArrayList<>();
        if(groupChatDetails != null && groupChatDetails.GroupChatDetailsList.size() >0){
        for(int i=0; i< groupChatDetails.GroupChatDetailsList.size();i++){
             obj.add(groupChatDetails.GroupChatDetailsList.get(i));
            TitledPane chatWithDate = new TitledPane();
            chatWithDate.setExpanded(true);
            chatWithDate.setCollapsible(false);
            chatWithDate.setText(groupChatDetails.GroupChatDetailsList.get(i).getDate().toString());
            chatWithDate.setPrefWidth(457);
            chatWithDate.setAlignment(Pos.CENTER);
            
            
            chatWithDate.getStylesheets().add(cssfile.getAbsolutePath());
            
            VBox boxChat = new VBox(10);
            SplitPane splitPane = new SplitPane();
            splitPane.setOrientation(Orientation.HORIZONTAL);
            splitPane.setDividerPosition(1,0.5);
            splitPane.setPrefWidth(200);
            splitPane.setStyle("-fx-box-border: transparent;");
            
            
            for(int j=0;j<userList.UserList.size();j++){
                if(userList.UserList.get(j).getUserID() == groupChatDetails.GroupChatDetailsList.get(i).getUserID()){
                    ImageView imgChatProfileImage = new ImageView();
          
            FileInputStream input = null;
             try {
             input = new FileInputStream(userList.UserList.get(j).getProfilePictureUrl());
             Image image = new Image(input);
             
             imgChatProfileImage.setImage(image);            
                } catch (FileNotFoundException e) {
                e.printStackTrace();
                }
             imgChatProfileImage.setFitWidth(90);
             imgChatProfileImage.setFitHeight(66);
             

             VBox vVertical = new VBox(10);
             
             
             Label userName = new Label();
             userName.setText(userList.UserList.get(j).getUserName());
             userName.setContentDisplay(ContentDisplay.LEFT);
             userName.setLayoutX(14);
             userName.setLayoutY(27);
             userName.setStyle("-fx-font-weight:bold;");
             Label message = new Label();
             message.setText(groupChatDetails.GroupChatDetailsList.get(i).getMessage());
             message.setAlignment(Pos.TOP_LEFT);
     
             vVertical.getChildren().addAll(userName,message);
             
             splitPane.getItems().addAll(imgChatProfileImage,vVertical);
                }
            }
                    
             boxChat.getChildren().add(splitPane);
             chatWithDate.setContent(boxChat);
             
             ChatVBox.getChildren().add(chatWithDate);

        }
      }
       // scrollChatPane.setContent(vList);
        
        ChatVBox.heightProperty().addListener(new ChangeListener<Number>(){
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
               scrollChatPane.setVvalue((Double) newValue);
            }
        
        });
        
       // client.receiveMessageFromServer(ChatVBox);
        
        
        
        Pane paneText = new Pane();
        paneText.setLayoutX(2);
        paneText.setLayoutY(292);
        paneText.setPrefHeight(60);
        paneText.setPrefWidth(469);
        
        TextField txtMessage = new TextField();
        txtMessage.setLayoutX(3);
        txtMessage.setLayoutY(4);
        txtMessage.setPrefHeight(50);
        txtMessage.setPrefWidth(469);

        Button btnSend = new Button();
        btnSend.setLayoutX(389);
        btnSend.setLayoutY(67);
        btnSend.setPrefHeight(22);
        btnSend.setPrefWidth(80);
        btnSend.setText("Send");
        
        ScrollPane paneEmoticons = new ScrollPane();
        paneEmoticons.setLayoutX(5);
        paneEmoticons.setLayoutY(65);
        paneEmoticons.setPrefHeight(60);
        paneEmoticons.setPrefWidth(200);
        addEmoticons(paneEmoticons, txtMessage);
        
        paneText.getChildren().addAll(txtMessage,btnSend,paneEmoticons);

        paneMembers.getChildren().add(paneText);
        
        
        btnSend.setOnAction(new EventHandler<ActionEvent>(){
            @Override
            public void handle(ActionEvent t) {
                String messageToSend = txtMessage.getText();
                if(!messageToSend.isEmpty()){
                    TitledPane newChatTitledPane = new TitledPane();
                    newChatTitledPane.setExpanded(true);
                    newChatTitledPane.setCollapsible(false);
                    newChatTitledPane.setText(new Date().toString());
                    newChatTitledPane.setPrefWidth(457);
                    newChatTitledPane.setAlignment(Pos.CENTER);
            
            
            newChatTitledPane.getStylesheets().add(cssfile.getAbsolutePath());
            
            VBox newBoxChat = new VBox(10);
            SplitPane newSplitPane = new SplitPane();
            newSplitPane.setOrientation(Orientation.HORIZONTAL);
            newSplitPane.setDividerPosition(1,0.5);
            newSplitPane.setPrefWidth(200);
            newSplitPane.setStyle("-fx-box-border: transparent;");
            
            ImageView newImgChatProfileImage = new ImageView();
            FileInputStream inputNew = null;
             try {
             inputNew = new FileInputStream(loggedInUserDetails.getProfilePictureUrl());
             Image imageNew = new Image(inputNew);
             
             newImgChatProfileImage.setImage(imageNew);            
                } catch (FileNotFoundException e) {
                e.printStackTrace();
                }
             newImgChatProfileImage.setFitWidth(90);
             newImgChatProfileImage.setFitHeight(66);
            
             
             VBox vVerticalNew = new VBox(10);
             
             
             Label userNameNew = new Label();
             userNameNew.setText(loggedInUserDetails.getUserName());
             userNameNew.setContentDisplay(ContentDisplay.LEFT);
             userNameNew.setLayoutX(14);
             userNameNew.setLayoutY(27);
             userNameNew.setStyle("-fx-font-weight:bold;");
             
             Label messageNew = new Label();
             messageNew.setText(messageToSend);
             messageNew.setAlignment(Pos.TOP_LEFT);
     
             vVerticalNew.getChildren().addAll(userNameNew,messageNew);
             
             newSplitPane.getItems().addAll(newImgChatProfileImage,vVerticalNew);
                
            
                    
             newBoxChat.getChildren().add(newSplitPane);
             newChatTitledPane.setContent(newBoxChat);
             ChatVBox.getChildren().add(newChatTitledPane);
             Date dateOfSending = new Date();	
             GroupChat groupChatNew = new GroupChat();	
             groupChatNew.setUserID(loggedInUserDetails.getUserID());	
             groupChatNew.setUserName(loggedInUserDetails.getUserName());	
             groupChatNew.setMessage(messageToSend);	
             groupChatNew.setDate(dateOfSending);	
             obj.add(groupChatNew);	
             Boolean success = factory.createObject("groupChat").writeObjectToJSON(obj, fileName);       
                    try {
                        client.sendMessageToServer(messageToSend,groupId, loggedInUserDetails.getUserID(),0, new Date());
                    } catch (IOException ex) {
                        Logger.getLogger(DashboardController.class.getName()).log(Level.SEVERE, null, ex);
                    }
             txtMessage.clear();
                }
            }
        });

    }
    
     public static void addNewTitledPane(String messageFromServer, int groupId, int senderUserId, int receiverUserId, Date dateOfMessageSent, VBox vBox ) throws ParseException{
         String profilePictureUrlOfSenderUser = "";
         String senderUserName = "";
         String groupName = "";
         String fileName = "";
         String fileNameReverseUser = "";
         String receiverUserName = "";
         List<Integer> mapperIds = new ArrayList<Integer>();
         JSONToObjectConvertorFactory factory = new JSONToObjectConvertorFactory();
          
       //if(senderUserId != loggedInUserDetails.getUserID()){
          
          UserDetailsList userList = (UserDetailsList) factory.createObject("userDetails").convertJSONToObject("");
          String dateToDisplay = "";
          dateToDisplay = dateOfMessageSent.toGMTString();
         
        
         for(int i=0; i< userList.UserList.size();i++){
                if(userList.UserList.get(i).getUserID() == senderUserId){
                    profilePictureUrlOfSenderUser = userList.UserList.get(i).getProfilePictureUrl();
                    senderUserName = userList.UserList.get(i).getUserName();
                }
                if(userList.UserList.get(i).getUserID() == receiverUserId){
                    receiverUserName = userList.UserList.get(i).getUserName();
                }
                
            }
         
         if(groupId != 0){
               GroupDetailsList groupList = (GroupDetailsList) factory.createObject("groupDetails").convertJSONToObject("");
               for(int i=0;i<groupList.GroupList.size();i++){
                   if(groupList.GroupList.get(i).getGroupID() == groupId){
                       groupName = groupList.GroupList.get(i).getGroupName();
                       fileName = groupName + "_" + "GroupChat.json";
                      
                       break;
                   }
               }
         }
         else{
             fileName = receiverUserName + "_" + senderUserName + "_" + "DirectChat.json";
             fileNameReverseUser = senderUserName + "_" + receiverUserName + "_" + "DirectChat.json";
         }
        
          File cssfile = new File("src/main/resources/com/mycompany/chatapplication/assets/CSS/dashboard.css");
        
         final TitledPane newChatTitledPane = new TitledPane();
                    newChatTitledPane.setExpanded(true);
                    newChatTitledPane.setCollapsible(false);                  
                    newChatTitledPane.setText(dateToDisplay);
                    newChatTitledPane.setPrefWidth(457);
                    newChatTitledPane.setAlignment(Pos.CENTER);
            
          
     
            newChatTitledPane.getStylesheets().add(cssfile.getAbsolutePath());
            
            VBox newBoxChat = new VBox(10);
            SplitPane newSplitPane = new SplitPane();
            newSplitPane.setOrientation(Orientation.HORIZONTAL);
            newSplitPane.setDividerPosition(1,0.5);
            newSplitPane.setPrefWidth(200);
            newSplitPane.setStyle("-fx-box-border: transparent;");
            
            ImageView newImgChatProfileImage = new ImageView();
            FileInputStream inputNew = null;
             try {
             inputNew = new FileInputStream(profilePictureUrlOfSenderUser);
             Image imageNew = new Image(inputNew);
             
             newImgChatProfileImage.setImage(imageNew);            
                } catch (FileNotFoundException e) {
                e.printStackTrace();
                }
             newImgChatProfileImage.setFitWidth(90);
             newImgChatProfileImage.setFitHeight(66);
            
             
             VBox vVerticalNew = new VBox(10);
             
             
             Label userNameNew = new Label();
             userNameNew.setText(senderUserName);
             userNameNew.setContentDisplay(ContentDisplay.LEFT);
             userNameNew.setLayoutX(14);
             userNameNew.setLayoutY(27);
             userNameNew.setStyle("-fx-font-weight:bold;");
             
             Label messageNew = new Label();
             messageNew.setText(messageFromServer);
             messageNew.setAlignment(Pos.TOP_LEFT);
     
             vVerticalNew.getChildren().addAll(userNameNew,messageNew);
             
             newSplitPane.getItems().addAll(newImgChatProfileImage,vVerticalNew);
                
            
                    
             newBoxChat.getChildren().add(newSplitPane);
             newChatTitledPane.setContent(newBoxChat);
               File f = new File("src/main/resources/com/mycompany/chatapplication/" + fileName);
               File fReverse =  new File("src/main/resources/com/mycompany/chatapplication/" + fileNameReverseUser);
                var fileNameArray = fileName.split("\\.");
                var fileNameArrayReverse = fileName.split("\\.");
               if(groupId != 0){
                  // if(mapperIds.contains(loggedInUserDetails.getUserID())){
                        if(f.exists() && !f.isDirectory()) { 

                            GroupChatDetailsList groupChatDetails = (GroupChatDetailsList) factory.createObject("groupChat").convertJSONToObject(fileNameArray[0]);
                            List<Object> objList = new ArrayList<Object>();
                            if(groupChatDetails.GroupChatDetailsList != null && groupChatDetails.GroupChatDetailsList.size() > 0){
                                for(int i=0; i< groupChatDetails.GroupChatDetailsList.size();i++){
                                   objList.add(groupChatDetails.GroupChatDetailsList.get(i));
                                }
                            }
                 

                            GroupChat groupChat = new GroupChat();
                            groupChat.setUserID(senderUserId);
                            groupChat.setUserName(senderUserName);
                            groupChat.setMessage(messageFromServer);
                            groupChat.setDate(dateOfMessageSent);

                            objList.add(groupChat);

                            Boolean success = factory.createObject("groupChat").writeObjectToJSON(objList, fileNameArray[0]);
                        }
                        else{
                            List<Object> objList = new ArrayList<Object>();
                            GroupChat groupChat = new GroupChat();
                            groupChat.setUserID(senderUserId);
                            groupChat.setUserName(senderUserName);
                            groupChat.setMessage(messageFromServer);
                            groupChat.setDate(dateOfMessageSent);

                            objList.add(groupChat);

                            Boolean success = factory.createObject("groupChat").writeObjectToJSON(objList, fileNameArray[0]);
                        }
                   
               }
               else{
                   if((f.exists() && !f.isDirectory()) || (fReverse.exists() && !fReverse.isDirectory())) { 
                    if(f.exists()){
                        DirectChatDetailsList directChatDetails = (DirectChatDetailsList) factory.createObject("directChat").convertJSONToObject(fileNameArray[0]);
                        List<Object> objList = new ArrayList<Object>();
                        if(directChatDetails.DirectChatDetailsList != null && directChatDetails.DirectChatDetailsList.size() > 0){
                        for(int i=0; i< directChatDetails.DirectChatDetailsList.size();i++){
                           objList.add(directChatDetails.DirectChatDetailsList.get(i));
                        }
                        }

                    
                    DirectChat directChat = new DirectChat();
                    directChat.setUserID(senderUserId);
                    directChat.setUserName(senderUserName);
                    directChat.setMessage(messageFromServer);
                    directChat.setDate(dateOfMessageSent);

                    objList.add(directChat);

                    Boolean success = factory.createObject("directChat").writeObjectToJSON(objList, fileNameArray[0]);
                    }
                    else{
                         
                        DirectChatDetailsList directChatDetails = (DirectChatDetailsList) factory.createObject("directChat").convertJSONToObject(fileNameArrayReverse[0]);
                    List<Object> objList = new ArrayList<Object>();
                    if(directChatDetails.DirectChatDetailsList != null && directChatDetails.DirectChatDetailsList.size() > 0){
                    for(int i=0; i< directChatDetails.DirectChatDetailsList.size();i++){
                       objList.add(directChatDetails.DirectChatDetailsList.get(i));
                    }
                    }

                    
                    DirectChat directChat = new DirectChat();
                    directChat.setUserID(senderUserId);
                    directChat.setUserName(senderUserName);
                    directChat.setMessage(messageFromServer);
                    directChat.setDate(dateOfMessageSent);

                    objList.add(directChat);

                    Boolean success = factory.createObject("directChat").writeObjectToJSON(objList, fileNameArrayReverse[0]);
                    }
                    
                }
                else{
                     List<Object> objList = new ArrayList<Object>();
                     DirectChat directChat = new DirectChat();
                    directChat.setUserID(senderUserId);
                    directChat.setUserName(senderUserName);
                    directChat.setMessage(messageFromServer);
                    directChat.setDate(dateOfMessageSent);

                    objList.add(directChat);

                    Boolean success = factory.createObject("directChat").writeObjectToJSON(objList, fileNameArray[0]);
                }
               }
                             
           Platform.runLater(() -> {
               vBox.getChildren().add(newChatTitledPane);
         });
       
    }

    private void openConversationOfUser(String labelText, int userId) {
         grdViewActiveMembers.setVisible(false); 
   hplViewActiveMembers.setVisible(false);
         VBox vList = new VBox(10);
         File cssfile = new File("src/main/resources/com/mycompany/chatapplication/assets/CSS/dashboard.css");
         lblChannelOrUserName.setText(labelText);
         lblChannelOrUserName.setVisible(true);
         paneAddMember.setVisible(false);
         paneImageOfReceiver.setVisible(true);
         lblChannelOrUserName.setStyle("-fx-font-weight:bold");
            ChatVBox.getChildren().clear();
         JSONToObjectConvertorFactory factory = new JSONToObjectConvertorFactory();
         UserDetailsList userList = (UserDetailsList) factory.createObject("userDetails").convertJSONToObject("");
         String profilePictureUrlOfReceiverUser = "";
         String receiverUserName = "";
         for(int i=0; i< userList.UserList.size();i++){
                if(userList.UserList.get(i).getUserID() == userId){
                    profilePictureUrlOfReceiverUser = userList.UserList.get(i).getProfilePictureUrl();
                    receiverUserName = userList.UserList.get(i).getUserName();
                }
                
            }
            ImageView imgUserProfilePicture = new ImageView();
            FileInputStream profilePictureOfReceiver = null;
            try {
            profilePictureOfReceiver = new FileInputStream(profilePictureUrlOfReceiverUser);
            Image imageProfilePictureOfReceiver = new Image(profilePictureOfReceiver);           
            imgUserProfilePicture.setImage(imageProfilePictureOfReceiver);
             } catch (FileNotFoundException e) {
                e.printStackTrace();
             }
            imgUserProfilePicture.setFitWidth(50);
            imgUserProfilePicture.setFitHeight(50);
            paneImageOfReceiver.getChildren().add(imgUserProfilePicture);
            
            String fileName = loggedInUserDetails.getUserName() + "_" + labelText + "_" + "DirectChat";
            String fileNameReverse = labelText + "_" + loggedInUserDetails.getUserName() + "_" + "DirectChat";
         
            VBox boxHeading = new VBox(10);
            SplitPane splitPane = new SplitPane();
            splitPane.setOrientation(Orientation.HORIZONTAL);
            splitPane.setDividerPosition(1,0.5);
            splitPane.setPrefWidth(450);
            splitPane.setStyle("-fx-box-border: transparent;");
            
            ImageView imgChatProfileImage = new ImageView();
           
            FileInputStream input = null;
             try {
             input = new FileInputStream(profilePictureUrlOfReceiverUser);
             Image image = new Image(input);
             
             imgChatProfileImage.setImage(image);            
                } catch (FileNotFoundException e) {
                e.printStackTrace();
                }
             imgChatProfileImage.setFitWidth(90);
             imgChatProfileImage.setFitHeight(66);
             

             VBox vVertical = new VBox(10);
             
             
             Label userName = new Label();
             userName.setText(receiverUserName);
             userName.setContentDisplay(ContentDisplay.LEFT);
             userName.setLayoutX(14);
             userName.setLayoutY(27);
             userName.setStyle("-fx-font-weight:bold;");
             Label message = new Label();
             if(loggedInUserDetails.getUserID() == userId)
             {
                 message.setText("This is your space. Draft messages, make to-do lists or keep links and files to hand. You can also talk to yourself here, but please bear in mind you’ll have to provide both sides of the conversation.");                
             }
             else{
                 message.setText("This is the very beginning of your direct message history with " + receiverUserName  + ". Only the two of you are in this conversation, and no one else can join it.");           
             }
             message.setAlignment(Pos.TOP_LEFT);
             message.setWrapText(true);
             message.setMaxWidth(400);
     
             vVertical.getChildren().addAll(userName,message);
             
             splitPane.getItems().addAll(imgChatProfileImage,vVertical);
             
             boxHeading.getChildren().add(splitPane);            
             
             ChatVBox.getChildren().add(boxHeading);
             
            File f = new File("src/main/resources/com/mycompany/chatapplication/" + fileName + ".json");
            File fReverse =  new File("src/main/resources/com/mycompany/chatapplication/" + fileNameReverse + ".json");
            DirectChatDetailsList directChatDetails = new DirectChatDetailsList();
            if(f.exists()){
                 directChatDetails = (DirectChatDetailsList) factory.createObject("directChat").convertJSONToObject(fileName);
       
            }
            else{
               directChatDetails= (DirectChatDetailsList) factory.createObject("directChat").convertJSONToObject(fileNameReverse);
       
            }
            
            
             List<Object> obj = new ArrayList<Object>();
        if(directChatDetails != null && directChatDetails.DirectChatDetailsList.size() >0){
        for(int i=0; i< directChatDetails.DirectChatDetailsList.size();i++){
            obj.add(directChatDetails.DirectChatDetailsList.get(i));
            TitledPane chatWithDate = new TitledPane();
            chatWithDate.setExpanded(true);
            chatWithDate.setCollapsible(false);
            chatWithDate.setText(directChatDetails.DirectChatDetailsList.get(i).getDate().toString());
            chatWithDate.setPrefWidth(457);
            chatWithDate.setAlignment(Pos.CENTER);
            
            
            chatWithDate.getStylesheets().add(cssfile.getAbsolutePath());
            
            VBox boxChat = new VBox(10);
            SplitPane splitPaneChat = new SplitPane();
            splitPaneChat.setOrientation(Orientation.HORIZONTAL);
            splitPaneChat.setDividerPosition(1,0.5);
            splitPaneChat.setPrefWidth(200);
            splitPaneChat.setStyle("-fx-box-border: transparent;");
            
            
            for(int j=0;j<userList.UserList.size();j++){
                if(userList.UserList.get(j).getUserID() == directChatDetails.DirectChatDetailsList.get(i).getUserID()){
                    ImageView imgChatProfileImageUser = new ImageView();
          
            FileInputStream inputUser = null;
             try {
             inputUser = new FileInputStream(userList.UserList.get(j).getProfilePictureUrl());
             Image image = new Image(inputUser);
             
             imgChatProfileImageUser.setImage(image);            
                } catch (FileNotFoundException e) {
                e.printStackTrace();
                }
             imgChatProfileImageUser.setFitWidth(90);
             imgChatProfileImageUser.setFitHeight(66);
             

             VBox vVerticalChat = new VBox(10);
             
             
             Label userNameChat = new Label();
             userNameChat.setText(userList.UserList.get(j).getUserName());
             userNameChat.setContentDisplay(ContentDisplay.LEFT);
             userNameChat.setLayoutX(14);
             userNameChat.setLayoutY(27);
             userNameChat.setStyle("-fx-font-weight:bold;");
             Label messageChat = new Label();
             String messageWithEmoticon = StringEscapeUtils.unescapeJava(directChatDetails.DirectChatDetailsList.get(i).getMessage());
           
             messageChat.setText(messageWithEmoticon);
             messageChat.setAlignment(Pos.TOP_LEFT);
     
             vVerticalChat.getChildren().addAll(userNameChat,messageChat);
             
             splitPaneChat.getItems().addAll(imgChatProfileImageUser,vVerticalChat);
                }
            }
                
             boxChat.getChildren().add(splitPaneChat);
             chatWithDate.setContent(boxChat);
             
             ChatVBox.getChildren().add(chatWithDate);
            }                             
        }          
      
        
         ChatVBox.heightProperty().addListener(new ChangeListener<Number>(){
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
               scrollChatPane.setVvalue((Double) newValue);
            }
        
        });
        
      
        
        
        Pane paneText = new Pane();
        paneText.setLayoutX(2);
        paneText.setLayoutY(276);
        paneText.setPrefHeight(93);
        paneText.setPrefWidth(469);
        
        TextField txtMessage = new TextField();
        txtMessage.setLayoutX(3);
        txtMessage.setLayoutY(4);
        txtMessage.setPrefHeight(55);
        txtMessage.setPrefWidth(469);

        Button btnSend = new Button();
        btnSend.setLayoutX(389);
        btnSend.setLayoutY(67);
        btnSend.setPrefHeight(22);
        btnSend.setPrefWidth(80);
        btnSend.setText("Send");
        
        ScrollPane paneEmoticons = new ScrollPane();
        paneEmoticons.setLayoutX(5);
        paneEmoticons.setLayoutY(65);
        paneEmoticons.setPrefHeight(60);
        paneEmoticons.setPrefWidth(200);
        addEmoticons(paneEmoticons, txtMessage);
        
        paneText.getChildren().addAll(txtMessage,btnSend,paneEmoticons);
        paneMembers.getChildren().add(paneText); 
        
        btnSend.setOnAction(new EventHandler<ActionEvent>(){
            @Override
            public void handle(ActionEvent t) {
                String messageToSend = txtMessage.getText();
                if(!messageToSend.isEmpty()){
                    TitledPane newChatTitledPane = new TitledPane();
                    newChatTitledPane.setExpanded(true);
                    newChatTitledPane.setCollapsible(false);
                    newChatTitledPane.setText(new Date().toString());
                    newChatTitledPane.setPrefWidth(457);
                    newChatTitledPane.setAlignment(Pos.CENTER);
            
            
            newChatTitledPane.getStylesheets().add(cssfile.getAbsolutePath());
            
            VBox newBoxChat = new VBox(10);
            SplitPane newSplitPane = new SplitPane();
            newSplitPane.setOrientation(Orientation.HORIZONTAL);
            newSplitPane.setDividerPosition(1,0.5);
            newSplitPane.setPrefWidth(200);
            newSplitPane.setStyle("-fx-box-border: transparent;");
            
            ImageView newImgChatProfileImage = new ImageView();
            FileInputStream inputNew = null;
             try {
             inputNew = new FileInputStream(loggedInUserDetails.getProfilePictureUrl());
             Image imageNew = new Image(inputNew);
             
             newImgChatProfileImage.setImage(imageNew);            
                } catch (FileNotFoundException e) {
                e.printStackTrace();
                }
             newImgChatProfileImage.setFitWidth(90);
             newImgChatProfileImage.setFitHeight(66);
            
             
             VBox vVerticalNew = new VBox(10);
             
             
             Label userNameNew = new Label();
             userNameNew.setText(loggedInUserDetails.getUserName());
             userNameNew.setContentDisplay(ContentDisplay.LEFT);
             userNameNew.setLayoutX(14);
             userNameNew.setLayoutY(27);
             userNameNew.setStyle("-fx-font-weight:bold;");
             
             Label messageNew = new Label();
             messageNew.setText(messageToSend);
             messageNew.setAlignment(Pos.TOP_LEFT);
     
             vVerticalNew.getChildren().addAll(userNameNew,messageNew);
             
             newSplitPane.getItems().addAll(newImgChatProfileImage,vVerticalNew);
                
            
                    
             newBoxChat.getChildren().add(newSplitPane);
             newChatTitledPane.setContent(newBoxChat);
             ChatVBox.getChildren().add(newChatTitledPane);
             DirectChat chatNew = new DirectChat();
             chatNew.setDate(new Date());
             chatNew.setMessage(messageToSend);
             chatNew.setUserID(loggedInUserDetails.getUserID());
             obj.add(chatNew);
             Boolean success = factory.createObject("directChat").writeObjectToJSON(obj, fileName);
             String fullMessage = messageToSend + "-" + 0 + "-" + loggedInUserDetails.getUserID() + "-" + userId + "-" +  new Date();
             try {
                        client.sendMessageToServer(messageToSend, 0,loggedInUserDetails.getUserID(), userId, new Date());
                    } catch (IOException ex) {
                        Logger.getLogger(DashboardController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                   
             txtMessage.clear();
                }
            }
        });
    }
    
    		
    private void addEmoticons(ScrollPane paneEmoticons, TextField txtMessage) {		
     Button e1 = new Button();		
     Button e2 = new Button();		
     Button e3 = new Button();		
     Button e4 = new Button();		
     Button e5 = new Button();		
     Button e6 = new Button();		
     Button e7 = new Button();		
     Button e8 = new Button();		
     Button e9 = new Button();		
     Button e10 = new Button();		
     Button e11 = new Button();		
  	
     		
      e1.setLayoutY(66);		
        e1.setLayoutX(7);		
        e1.setPrefSize(20,20);		
        e1.setPadding(Insets.EMPTY);		
        e1.setText("☹");		
        e1.setStyle("-fx-text-fill: #ff0000;");		
        e2.setLayoutY(66);		
        e2.setLayoutX(80);		
        e2.setPrefSize(20,20);		
        e2.setPadding(Insets.EMPTY);		
        e2.setText("❤");		
        e2.setStyle("-fx-text-fill: #ff0000;");		
        		
        e3.setLayoutY(66);		
        e3.setLayoutX(120);		
        e3.setPrefSize(20,20);		
        e3.setPadding(Insets.EMPTY);		
        e3.setText("☺");		
        e3.setStyle("-fx-text-fill: #ff0000;");		
        e4.setLayoutY(66);		
        e4.setLayoutX(150);		
        e4.setPrefSize(20,20);		
        e4.setPadding(Insets.EMPTY);		
        e4.setText("✌");		
        e4.setStyle("-fx-text-fill: #ff9900;");		
        e5.setLayoutY(66);		
        e5.setLayoutX(180);		
        e5.setPrefSize(20,20);		
        e5.setPadding(Insets.EMPTY);		
        e5.setText("☕");		
        e5.setStyle("-fx-text-fill: #ff9900;");		
        e6.setLayoutY(66);		
        e6.setLayoutX(220);		
        e6.setPrefSize(20,20);		
        e6.setPadding(Insets.EMPTY);		
        e6.setText("☀");		
        e6.setStyle("-fx-text-fill: #ff9900;");		
        e7.setLayoutY(66);		
        e7.setLayoutX(260);		
        e7.setPrefSize(20,20);		
        e7.setPadding(Insets.EMPTY);		
        e7.setText("❃");		
        e7.setStyle("-fx-text-fill: #ff0099;");		
        e8.setLayoutY(66);		
        e8.setLayoutX(300);		
        e8.setPrefSize(20,20);		
        e8.setPadding(Insets.EMPTY);		
        e8.setText("♫");		
        e8.setStyle("-fx-text-fill: #ff0099;");		
		
        e10.setLayoutY(90);		
        e10.setLayoutX(120);		
        e10.setPrefSize(20,20);		
        e10.setPadding(Insets.EMPTY);		
        e10.setText("☁");		
        e10.setStyle("-fx-text-fill: #449c32;");		
        e11.setLayoutY(90);		
        e11.setLayoutX(160);		
        e11.setPrefSize(20,20);		
        e11.setPadding(Insets.EMPTY);		
        e11.setText("♜");		
        e11.setStyle("-fx-text-fill: #449c32;");		

        
        e1.setOnAction(event -> {
            txtMessage.appendText(e1.getText());
        });
        e2.setOnAction(event -> {
            txtMessage.appendText(e2.getText());
        });
        e3.setOnAction(event -> {
            txtMessage.appendText(e3.getText());
        });
        e4.setOnAction(event -> {
            txtMessage.appendText(e4.getText());
        });
        e5.setOnAction(event -> {
            txtMessage.appendText(e5.getText());
        });
        e6.setOnAction(event -> {
            txtMessage.appendText(e6.getText());
        });
        e7.setOnAction(event -> {
            txtMessage.appendText(e7.getText());
        });
        e8.setOnAction(event -> {
            txtMessage.appendText(e8.getText());
        });
        e9.setOnAction(event -> {
            txtMessage.appendText(e9.getText());
        });
        e10.setOnAction(event -> {
            txtMessage.appendText(e10.getText());
        });
        e11.setOnAction(event -> {
            txtMessage.appendText(e11.getText());
        });

        HBox hBoxEmoticons = new HBox();
        hBoxEmoticons.getChildren().addAll(e1,e2,e3,e4,e5,e6,e7,e8,e10,e11

        );

     paneEmoticons.setContent(hBoxEmoticons);
     
    }

 public void btnLogoutClick(ActionEvent event) throws Exception{
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("LoginMain.fxml"));
                        
                        Parent root = (Parent)fxmlLoader.load();
                        Stage stage = new Stage();
                        stage.setTitle("Login");
                        stage.setScene(new Scene(root, 600, 400));
                        stage.show();
                        ((Node)(event.getSource())).getScene().getWindow().hide();
    }
 
  public void hplViewActiveMembersClick(ActionEvent event) throws Exception{
//        if(client.userNames.contains(loggedInUserDetails.getUserName())){
//            client.userNames.remove(loggedInUserDetails.getUserName());
//        }
        String activeMembers = String.join(",", client.userNames);
        lblActiveUserList.setText("Active members are :" + activeMembers);
    }
   
}
