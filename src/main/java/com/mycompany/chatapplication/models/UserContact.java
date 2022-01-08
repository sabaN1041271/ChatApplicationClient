/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.chatapplication.models;

import java.util.ArrayList;

/**
 *
 * @author ntu-user
 */
public class UserContact {
    private int userId;
    private ArrayList<Integer> contactIds;
    
     public int getUserId(){
        return userId;
    }
    
    public void setUserId(int newUserID){
         this.userId = newUserID;
    }
    
     public ArrayList<Integer> getContactIds(){
        return contactIds;
    }
    
    public void setContactIds(ArrayList<Integer> newListOfContacts){
         this.contactIds = newListOfContacts;
    }
    
}
