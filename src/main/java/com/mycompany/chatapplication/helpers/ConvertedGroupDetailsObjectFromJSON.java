/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.chatapplication.helpers;

import com.mycompany.chatapplication.Interfaces.IReadJSONFileAndConvertIntoObject;
import java.io.File;
import java.util.List;
import java.io.IOException;
import java.lang.System.Logger;
import java.lang.System.Logger.Level;
import java.net.URL;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mycompany.chatapplication.models.*;
import java.nio.file.Files;
import java.nio.file.Paths;
/**
 *
 * @author ntu-user
 */
public class ConvertedGroupDetailsObjectFromJSON implements IReadJSONFileAndConvertIntoObject{
    
    private String jsonText;

    @Override
    public void readJSON() {
       
    }

    @Override
    public Object convertJSONToObject(String fileName) {
        ObjectMapper objectMapper = new ObjectMapper();
        GroupDetailsList ben;
        try {
           
            ben = objectMapper.readValue(new File("src/main/resources/com/mycompany/chatapplication/GroupDetails.json"), GroupDetailsList.class);
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
        GroupDetailsList ben = new GroupDetailsList();
        for(int i=0; i< obj.size();i++){         
            ben.GroupList.add((GroupDetails)obj.get(i));
        }
        try {
            objectMapper.writeValue(new File("src/main/resources/com/mycompany/chatapplication/GroupDetails.json"), ben);
            return true;
        } catch (IOException ex) {
            return false;
            //java.util.logging.Logger.getLogger(ConvertedGroupDetailsObjectFromJSON.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
    }
    
}
