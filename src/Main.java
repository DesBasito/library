import service.PostServer;

import java.io.*;


public class Main {

    public static void main(String[] args) {
       try {
           new PostServer("localhost",8998).start();
       }catch (IOException e){
           e.printStackTrace();
       }
    }


}