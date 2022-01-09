/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.chatapplication.helpers;

import com.mycompany.chatapplication.controllers.DashboardController;
import java.io.*;
import java.net.*;
import java.text.ParseException;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.layout.VBox;
 
/**
 * This is the chat server program.
 *
 * @author www.codejava.net
 */
public class ChatClient {
    private Socket socket;
    private BufferedReader bufferedReader;
    private BufferedWriter bufferedWriter;
    public Set<String> userNames = new HashSet<>();
    
    public ChatClient(Socket socket, String userName) throws IOException, InterruptedException{
        try{
             System.out.println("Connecting...");
            this.socket = socket;
            Thread.sleep(1000);
            System.out.println("Connected!");
            bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            bufferedWriter.write(userName);
            bufferedWriter.newLine();
            bufferedWriter.flush();
            
//            if(bufferedReader.readLine()!= null){
//                userNames = new BufferedReader(new InputStreamReader(socket.getInputStream())).readLine();
//            }
//            System.out.println("Members connected are " + userNames);
        }
        catch(IOException e){
            close(socket,bufferedReader, bufferedWriter);
        }
    }
    
    public void close(Socket socket, BufferedReader bufferedReader, BufferedWriter bufferedWriter) throws IOException{
        try{
            if(bufferedReader != null){
                bufferedReader.close();
            }
            if(bufferedWriter != null){
                bufferedWriter.close();
            }
            if(socket != null){
                socket.close();
            }
        }
        catch(IOException ex){
            
        }
    }
    
    public void sendMessageToServer(String messageToServer, int groupId, int senderUserId, int receiverUserId, Date dateOfSending) throws IOException{
        try{
            bufferedWriter.write(messageToServer);
            bufferedWriter.write("-" + groupId);
            bufferedWriter.write("-" + senderUserId);
            bufferedWriter.write("-" + receiverUserId);
            bufferedWriter.write("-" + dateOfSending.toString());
            bufferedWriter.newLine();
            bufferedWriter.flush();
            
        }
        catch(IOException ex){
            close(socket,bufferedReader, bufferedWriter);
        }
    }
    
    public void receiveMessageFromServer(VBox vBox){
        new Thread(new Runnable(){
            @Override
            public void run() {
                while(socket.isConnected()){
                    try {
                        String messageFromServer = bufferedReader.readLine();
                        System.out.println(messageFromServer);
                        if(!messageFromServer.startsWith("Active")){
                              var stringArray = messageFromServer.split("-");
                        DashboardController.addNewTitledPane(stringArray[0],Integer.parseInt(stringArray[1]),Integer.parseInt(stringArray[2]),Integer.parseInt(stringArray[3]) ,new Date(stringArray[4]) , vBox);
                    
                        }
                        else{
                            userNames = new HashSet<>(Arrays.asList(messageFromServer.split(":")[1].split(",")));
                        }
                      } catch (IOException ex) {
                        Logger.getLogger(ChatClient.class.getName()).log(Level.SEVERE, null, ex);
                        try {
                            close(socket,bufferedReader, bufferedWriter);
                        } catch (IOException ex1) {
                            Logger.getLogger(ChatClient.class.getName()).log(Level.SEVERE, null, ex1);
                        }
                    } catch (ParseException ex) {
                        Logger.getLogger(ChatClient.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
            
        }).start();
    }
}
