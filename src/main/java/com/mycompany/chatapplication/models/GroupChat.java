/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.chatapplication.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.util.Date;

/**
 *
 * @author ntu-user
 */
public class GroupChat {

    private int userID;
    private String userName;
    private String message;
    private Date date;
    
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
    public String getMessage(){
        return message;
    }
    
    public void setMessage(String newMessage){
         this.message = newMessage;
    }
    public Date getDate(){
        return date;
    }
    
    public void setDate(Date newDate){
         this.date = newDate;
    }
}
