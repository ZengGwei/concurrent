package com.zeng;

import java.io.ObjectOutputStream;
import java.net.Socket;

/**
 * create by zenggw at 2019-09-15;
 */
public class Server {

    public static void main(String[] args) {
        Socket socket=null;
        ObjectOutputStream out=null;
        try {
            socket=new Socket("127.0.0.1",8080);
            User user=new User();
            out=new ObjectOutputStream(socket.getOutputStream());
            out.writeObject(user);
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            if(out!=null){
                try {
                    out.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            if(socket!=null){
                try {
                    socket.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

}
