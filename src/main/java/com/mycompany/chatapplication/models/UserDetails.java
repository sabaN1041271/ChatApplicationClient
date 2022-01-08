/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author ntu-user
 */

package com.mycompany.chatapplication.models;

public class UserDetails {
    private int userID;
    private String userName;
    private String password;
    private int defaultGroupID;
    private String profilePictureUrl;
    
    public int getUserID(){
        return userID;
    }
    
    public void setUserID(int newUserID){
         this.userID = newUserID;
    }
    
    public String getUserName(){
        return userName;
    }
    
    public void setUserName(String newUserName){
         this.userName = newUserName;
    }
    public String getPassword(){
        return password;
    }
    
    public void setPassword(String newPassword){
         this.password = newPassword;
    }
    public int getDefaultGroupID(){
        return defaultGroupID;
    }
    
    public void setDefaultGroupID(int newDefaultGroupID){
         this.defaultGroupID = newDefaultGroupID;
    }

 public String getProfilePictureUrl(){
        return profilePictureUrl;
    }
    
    public void setProfilePictureUrl(String newProfilePictureUrl){
         this.profilePictureUrl = newProfilePictureUrl;
    }
}
