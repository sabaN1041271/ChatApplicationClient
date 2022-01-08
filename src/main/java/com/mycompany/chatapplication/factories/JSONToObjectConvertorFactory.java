/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.mycompany.chatapplication.factories;


import com.mycompany.chatapplication.Interfaces.IReadJSONFileAndConvertIntoObject;
import com.mycompany.chatapplication.helpers.*;

/**
 *
 * @author ntu-user
 */
public class JSONToObjectConvertorFactory {
     public IReadJSONFileAndConvertIntoObject createObject(String type) {
        if(type == "userDetails"){
            return new ConvertedUserDetailsObjectFromJSON();
        }
        else if(type == "groupDetails"){
            return new ConvertedGroupDetailsObjectFromJSON();
        }
        else if(type == "groupChat"){
            return new ConvertedGroupChatDetailsObjectFromJSON();
        }
         else if(type == "directChat"){
            return new ConvertedDirectChatDetailsObjectFromJSON();
        }
         else if(type == "contactList"){
             return new ConvertedContactListObjectFromJSON();
         }
       return new ConvertedUserDetailsObjectFromJSON();
    }
}
