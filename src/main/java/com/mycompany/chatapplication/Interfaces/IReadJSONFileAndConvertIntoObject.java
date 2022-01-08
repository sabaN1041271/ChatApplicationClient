/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.mycompany.chatapplication.Interfaces;

import java.util.List;

/**
 *
 * @author ntu-user
 */
public interface IReadJSONFileAndConvertIntoObject {
      public void readJSON();
      public Object convertJSONToObject(String fileName);
      public Boolean writeObjectToJSON(List<Object> obj, String fileName);
}


