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
public class ConvertedContactListObjectFromJSON implements IReadJSONFileAndConvertIntoObject{
    
    private String jsonText;

    @Override
    public void readJSON() {
       
    }

    @Override
    public Object convertJSONToObject(String fileName) {
    ObjectMapper objectMapper = new ObjectMapper();
        ContactList ben;
        try {
           
            ben = objectMapper.readValue(new File("src/main/resources/com/mycompany/chatapplication/ContactList.json"), ContactList.class);
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
       throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
