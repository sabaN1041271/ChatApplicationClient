module com.mycompany.chatapplication {
    requires javafx.controls;
    requires javafx.fxml;

    opens com.mycompany.chatapplication to javafx.fxml;
    exports com.mycompany.chatapplication;
    
     opens com.mycompany.chatapplication.controllers to javafx.fxml;
    exports com.mycompany.chatapplication.controllers;
    
//    opens com.mycompany.chatapplication.models to com.fasterxml.jackson.databind;
//    exports com.mycompany.chatapplication.models;
    
   
   requires com.fasterxml.jackson.annotation;
   requires com.fasterxml.jackson.core;
   requires com.fasterxml.jackson.databind;
   requires java.base;
   requires java.logging;

opens com.mycompany.chatapplication.models to com.fasterxml.jackson.databind;
exports com.mycompany.chatapplication.models;
    requires commons.lang;
   

}
