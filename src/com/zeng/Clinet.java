package com.zeng;

import java.io.BufferedReader;
import java.io.ObjectInputStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * create by zenggw at 2019-09-15;
 */
public class Clinet {

    public static void main(String[] args) throws Exception {
        ServerSocket serverSocket=null;
        BufferedReader in=null;
        try{
            serverSocket=new ServerSocket(8080);
            Socket socket=serverSocket.accept();
            ObjectInputStream objectInputStream=new ObjectInputStream(socket.getInputStream());
            User user=(User)objectInputStream.readObject();
            System.out.println(user);
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            if(in!=null){
                try {
                    in.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            if(serverSocket!=null){
                serverSocket.close();
            }
        }
    }

}
