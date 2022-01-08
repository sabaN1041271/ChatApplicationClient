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
public class GroupDetails {
     private int groupID;
    private String groupName;
    private String groupDescription;
    public ArrayList<Integer> lstMappedUserIds;
    
    public int getGroupID(){
        return groupID;
    }
    
    public void setGroupID(int newGroupID){
         this.groupID = newGroupID;
    }
    
    public String getGroupName(){
        return groupName;
    }
    
    public void setGroupName(String newGroupName){
         this.groupName = newGroupName;
    }
     public String getGroupDescription(){
        return groupDescription;
    }
    
    public void setGroupDescription(String newGroupDescription){
         this.groupDescription = newGroupDescription;
    }
    public ArrayList<Integer> getUserIds(){
        return lstMappedUserIds;
    }
    
    public void setUserIds(ArrayList<Integer> newLstMappedUserIds){
         this.lstMappedUserIds = newLstMappedUserIds;
    }
}
