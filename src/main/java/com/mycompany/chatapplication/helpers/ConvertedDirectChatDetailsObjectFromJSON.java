/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.chatapplication.helpers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mycompany.chatapplication.Interfaces.IReadJSONFileAndConvertIntoObject;
import com.mycompany.chatapplication.models.*;
import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 *
 * @author ntu-user
 */
public class ConvertedDirectChatDetailsObjectFromJSON implements IReadJSONFileAndConvertIntoObject{
    
    private String jsonText;

    @Override
    public void readJSON() {
       
    }

    @Override
    public Object convertJSONToObject(String fileName) {
        ObjectMapper objectMapper = new ObjectMapper();
        DirectChatDetailsList ben;
        String pathName = "src/main/resources/com/mycompany/chatapplication/" + fileName + ".json";
        try {
           
            ben = objectMapper.readValue(new File(pathName), DirectChatDetailsList.class);
            System.out.println(ben);
            return ben;
        } catch (IOException ex) {
            //Logger.Level.ERROR(ConvertedUserDetailsObjectFromJSON.class.getName()).log(Level.ERROR, null, ex);
        }
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        return null;
    }

  
    @Override
    public Boolean writeObjectToJSON(List<Object> obj, String fileName) {
        ObjectMapper objectMapper = new ObjectMapper();
        DirectChatDetailsList ben = new DirectChatDetailsList();
        String pathName = "src/main/resources/com/mycompany/chatapplication/" + fileName + ".json";
        for(int i=0; i< obj.size();i++){         
            ben.DirectChatDetailsList.add((DirectChat)obj.get(i));
        }
        try {
            objectMapper.writeValue(new File(pathName), ben);
            return true;
        } catch (IOException ex) {
            return false;
            //java.util.logging.Logger.getLogger(ConvertedGroupDetailsObjectFromJSON.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
    }
    
}